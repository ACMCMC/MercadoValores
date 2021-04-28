CREATE EXTENSION pgcrypto;

CREATE TABLE usuario_regulador(
  id varchar(30),
  clave text,
  saldo double precision,
  comision_actual double precision,
  primary key (id),
  CHECK (saldo >= 0::double precision AND comision_actual >= 0::double precision)
);

CREATE TYPE enum_estado AS ENUM ('SOLICITANDO_ALTA', 'SOLICITANDO_BAJA', 'DADO_DE_ALTA');

CREATE TABLE usuario_mercado(
    id varchar(30),
    clave text,
    saldo double precision,
    direccion text,
    telefono text, --15 valores porque si se pretendiese ser internacional haria falta el prefijo internacional
    estado enum_estado,
    primary key (id),
    CHECK (saldo >= 0::double precision)
);

CREATE TABLE usuario_inversor(
  id varchar(30),
  dni char(9),
  nombre_completo text,
  primary key (id),
    foreign key (id) references usuario_mercado
   		 on update cascade
   		 on delete cascade,
  CHECK (dni SIMILAR TO '\d{8}[A-Z]')
);

CREATE TABLE usuario_empresa(
  id varchar(30),
  cif char(9),
  nombre_comercial text,
  importe_bloqueado double precision DEFAULT 0.0,
  primary key (id),
    foreign key (id) references usuario_mercado
   		 on update cascade
   		 on delete cascade,
  CHECK (importe_bloqueado >= 0::double precision),
  CHECK (cif SIMILAR TO '[A-Z]\d{8}')
);

CREATE TABLE beneficios(
  id varchar(30),
  fecha_pago timestamp,
  importe_por_participacion double precision,
  primary key (id,fecha_pago),
	foreign key (id) references usuario_empresa
        	on update cascade
        	on delete cascade, --si una empresa se borra desaparecen sus anuncios de pago de beneficios
  CHECK (importe_por_participacion >= 0::double precision)
);

CREATE TABLE tener_participaciones(
  id1 varchar(30),
  id2 varchar(30),
  num_participaciones integer,
  primary key (id1,id2),
	foreign key (id1) references usuario_mercado(id)
        	on update cascade
        	on delete restrict, --restringido, si queremos borrar un usuario antes tenemos que hacer algo con sus participaciones
	foreign key (id2) references usuario_empresa(id)
        	on update cascade
        	on delete restrict, --idem
  CHECK (num_participaciones >= 0::integer)
);

CREATE TABLE anuncio_venta(
  id1 varchar(30),
  id2 varchar(30),
  num_participaciones integer,
  fecha timestamptz,
  precio double precision,
  comision_en_fecha double precision,
  primary key (id1,id2,fecha),
	foreign key (id1,id2) references tener_participaciones
        	on update cascade
        	on delete cascade, --si se borra el usuario se borran sus anuncios de venta. si se borra una empresa esto no entra en juego porque antes tiene que acabar con sus participaciones en el mercado
  CHECK (precio >= 0::double precision AND comision_en_fecha >= 0::double precision AND num_participaciones >= 0::integer)
);

--Funcionalidades extra

CREATE TABLE compra(
    id_compra serial UNIQUE,
	empresa varchar(30),
	comprador varchar(30),
	fecha timestamptz,
	primary key(id_compra),
	foreign key (comprador) references usuario_mercado(id)
        	on update cascade
        	on delete restrict, 
	foreign key (empresa) references usuario_empresa(id)
        	on update cascade
        	on delete restrict
);
CREATE TABLE parte_compra(
	id_compra serial,
	id_parte serial, --Cada parte de compra se identifica independientemente, esto nos permite usar generacion de valores por defecto
	vendedor varchar(30),
	precio double precision,
	cantidad integer,
	primary key(id_compra,id_parte),
	foreign key (vendedor) references usuario_mercado(id)
        	on update cascade
        	on delete restrict, 
	foreign key (id_compra) references compra(id_compra)
        		on update cascade
        		on delete restrict,
	CHECK (precio >= 0::double precision AND cantidad >= 0::integer)
);

/*
CREATE ROLE Regulador;

GRANT INSERT, SELECT, UPDATE (estado) ON usuario_mercado TO Regulador;

CREATE ROLE MercadoUser;

GRANT INSERT, SELECT (num_participaciones) ON tener_participaciones TO MercadoUser;

GRANT INSERT, SELECT, UPDATE (fecha, precio, comision_en_fecha, numero_participaciones) ON anuncio_venta TO MercadoUser;

CREATE ROLE InversorUser INHERIT;

GRANT MercadoUser TO InversorUser;

CREATE ROLE EmpresaUser INHERIT;

GRANT MercadoUser TO EmpresaUser;

GRANT SELECT ON beneficios TO EmpresaUser;
*/

CREATE OR REPLACE FUNCTION comprueba_participaciones() RETURNS trigger AS $comprueba_participaciones$
    DECLARE
		total double precision;
		max double precision;
    BEGIN
		SELECT COALESCE( SUM(num_participaciones) - old.num_participaciones, 0) into total
		FROM anuncio_venta
		WHERE id1=new.id1
		  AND id2=new.id2;
		  
		SELECT SUM(num_participaciones) into max
		FROM tener_participaciones
		WHERE id1=new.id1
		  AND id2=new.id2;
		
		IF new.num_participaciones > max THEN
            		RAISE EXCEPTION 'El número de participaciones a la venta no puede exceder el total poseído';
		END IF;
		IF total IS NOT NULL THEN
            		IF total+new.num_participaciones > max THEN
				RAISE EXCEPTION 'El número de participaciones a la venta no puede exceder el total poseído';
			END IF;
		END IF;
		RETURN NEW;
		 
    END;
$comprueba_participaciones$ LANGUAGE plpgsql;

CREATE TRIGGER comprueba_participaciones BEFORE INSERT OR UPDATE ON anuncio_venta
FOR EACH ROW EXECUTE PROCEDURE comprueba_participaciones();

CREATE OR REPLACE VIEW pago_total_beneficios AS SELECT id, fecha_pago, COALESCE(importe_por_participacion*(SELECT SUM(num_participaciones) FROM tener_participaciones WHERE id2=beneficios.id), 0) AS importe_total FROM beneficios;

CREATE OR REPLACE FUNCTION comprueba_pago_beneficios() RETURNS trigger AS $comprueba_pago_beneficios$
    DECLARE
		beneficios_a_pagar double precision;
		max double precision;
    BEGIN
		SELECT COALESCE(SUM(new.num_participaciones*beneficios.importe_por_participacion),0) into beneficios_a_pagar
		FROM beneficios
		WHERE id=new.id2;
		
		SELECT saldo into max
		FROM usuario_mercado
		WHERE id=new.id2;
		  
		IF beneficios_a_pagar > max THEN
            		RAISE EXCEPTION 'Los beneficios no podrían pagarse';
		END IF;
		RETURN NEW;
		 
    END;
$comprueba_pago_beneficios$ LANGUAGE plpgsql;

CREATE TRIGGER comprueba_pago_beneficios BEFORE INSERT OR UPDATE ON tener_participaciones
FOR EACH ROW EXECUTE PROCEDURE comprueba_pago_beneficios();

--Actualiza el saldo bloqueado al actualizar la tabla de beneficios en base a las participaciones ya existentes en el mercado
CREATE OR REPLACE FUNCTION procesa_bloqueo_importe_pago_beneficios() RETURNS trigger AS $procesa_bloqueo_importe_pago_beneficios$
    DECLARE
		diferencia_beneficios_a_pagar double precision;
		max double precision;
    BEGIN
		SELECT (new.importe_por_participacion - COALESCE(old.importe_por_participacion,0))*(COALESCE(SUM(tener_participaciones.num_participaciones),0)) into diferencia_beneficios_a_pagar --el importe que vamos a pagar ahora - el que pagaríamos antes
		FROM tener_participaciones
		WHERE id2=new.id;
		
		SELECT saldo into max
		FROM usuario_mercado
		WHERE id=new.id;
		  
		IF diferencia_beneficios_a_pagar > max THEN
            		RAISE EXCEPTION 'Los beneficios no podrían pagarse';
		END IF;

		--sumamos al saldo bloqueado, y restamos al saldo disponible
		UPDATE usuario_empresa SET importe_bloqueado=importe_bloqueado+diferencia_beneficios_a_pagar WHERE id=new.id;
		UPDATE usuario_mercado SET saldo=saldo-diferencia_beneficios_a_pagar WHERE id=new.id;

		RETURN NEW;
		 
    END;
$procesa_bloqueo_importe_pago_beneficios$ LANGUAGE plpgsql;

CREATE TRIGGER procesa_bloqueo_importe_pago_beneficios BEFORE INSERT OR UPDATE ON beneficios
FOR EACH ROW EXECUTE PROCEDURE procesa_bloqueo_importe_pago_beneficios();

CREATE OR REPLACE FUNCTION procesa_bloqueo_importe_pago_beneficios_al_borrar() RETURNS trigger AS $procesa_bloqueo_importe_pago_beneficios_al_borrar$
    DECLARE
		diferencia_beneficios_a_pagar double precision;
		max double precision;
    BEGIN
		SELECT (-COALESCE(old.importe_por_participacion,0))*(COALESCE(SUM(tener_participaciones.num_participaciones),0)) into diferencia_beneficios_a_pagar --el importe que vamos a pagar ahora - el que pagaríamos antes
		FROM tener_participaciones
		WHERE id2=old.id;
		
		SELECT saldo into max
		FROM usuario_mercado
		WHERE id=old.id;

		--sumamos al saldo bloqueado, y restamos al saldo disponible
		UPDATE usuario_empresa SET importe_bloqueado=importe_bloqueado+diferencia_beneficios_a_pagar WHERE id=old.id;
		UPDATE usuario_mercado SET saldo=saldo-diferencia_beneficios_a_pagar WHERE id=old.id;

		RETURN OLD;
		 
    END;
$procesa_bloqueo_importe_pago_beneficios_al_borrar$ LANGUAGE plpgsql;

CREATE TRIGGER procesa_bloqueo_importe_pago_beneficios_al_borrar BEFORE DELETE ON beneficios
FOR EACH ROW EXECUTE PROCEDURE procesa_bloqueo_importe_pago_beneficios_al_borrar();

--Actualiza el saldo bloqueado al actualizar la tabla de tener_participaciones en base a los anuncios de beneficios existentes
CREATE OR REPLACE FUNCTION procesa_saldo_bloqueado_al_modificar_tabla_participaciones() RETURNS trigger AS $procesa_saldo_bloqueado_al_modificar_tabla_participaciones$
    DECLARE
		diferencia_beneficios_a_pagar double precision;
		max double precision;
    BEGIN
		SELECT (COALESCE(SUM((new.num_participaciones - COALESCE(old.num_participaciones,0)) * (beneficios.importe_por_participacion)),0)) into diferencia_beneficios_a_pagar --cogemos la fila anterior de tener_participaciones, y la nueva, y multiplicamos su diferencia por cada uno de los importes por participacion anunciados. Sumamos todo, y esa es la diferencia total que tendremos que reservar.
		FROM beneficios
		WHERE id=new.id2;
		
		SELECT saldo into max
		FROM usuario_mercado
		WHERE id=new.id2;
		  
		IF diferencia_beneficios_a_pagar > max THEN
            		RAISE EXCEPTION 'Los beneficios no podrían pagarse';
		END IF;

		--sumamos al saldo bloqueado, y restamos al saldo disponible
		UPDATE usuario_empresa SET importe_bloqueado=importe_bloqueado+diferencia_beneficios_a_pagar WHERE id=new.id2;
		UPDATE usuario_mercado SET saldo=saldo-diferencia_beneficios_a_pagar WHERE id=new.id2;

		RETURN NEW;
		 
    END;
$procesa_saldo_bloqueado_al_modificar_tabla_participaciones$ LANGUAGE plpgsql;

CREATE TRIGGER procesa_saldo_bloqueado_al_modificar_tabla_participaciones BEFORE INSERT OR UPDATE ON tener_participaciones
FOR EACH ROW EXECUTE PROCEDURE procesa_saldo_bloqueado_al_modificar_tabla_participaciones();

--Actualiza el saldo bloqueado al actualizar la tabla de tener_participaciones en base a los anuncios de beneficios existentes
CREATE OR REPLACE FUNCTION procesa_saldo_bloqueado_al_borrar_tabla_participaciones() RETURNS trigger AS $procesa_saldo_bloqueado_al_borrar_tabla_participaciones$
    DECLARE
		diferencia_beneficios_a_pagar double precision;
		max double precision;
    BEGIN
		SELECT (COALESCE((- COALESCE(old.num_participaciones,0)) * (beneficios.importe_por_participacion),0)) into diferencia_beneficios_a_pagar --cogemos la fila anterior de tener_participaciones, y la nueva, y multiplicamos su diferencia por cada uno de los importes por participacion anunciados. Sumamos todo, y esa es la diferencia total que tendremos que reservar.
		FROM beneficios
		WHERE id=old.id2;
		
		SELECT saldo into max
		FROM usuario_mercado
		WHERE id=old.id2;
		  
		IF diferencia_beneficios_a_pagar > max THEN
            		RAISE EXCEPTION 'Los beneficios no podrían pagarse';
		END IF;

		--sumamos al saldo bloqueado, y restamos al saldo disponible
		UPDATE usuario_empresa SET importe_bloqueado=importe_bloqueado+diferencia_beneficios_a_pagar WHERE id=old.id2;
		UPDATE usuario_mercado SET saldo=saldo-diferencia_beneficios_a_pagar WHERE id=old.id2;

		RETURN OLD;
		 
    END;
$procesa_saldo_bloqueado_al_borrar_tabla_participaciones$ LANGUAGE plpgsql;

CREATE TRIGGER procesa_saldo_bloqueado_al_borrar_tabla_participaciones BEFORE DELETE ON tener_participaciones
FOR EACH ROW EXECUTE PROCEDURE procesa_saldo_bloqueado_al_borrar_tabla_participaciones();

--Si una fila tiene saldo 0, la borramos
CREATE OR REPLACE FUNCTION borrar_fila_tener_participaciones_si_es_cero() RETURNS trigger AS $borrar_fila_tener_participaciones_si_es_cero$
    BEGIN
		
		DELETE FROM tener_participaciones WHERE id1=new.id1 and id2=new.id2 and num_participaciones=0;

		RETURN NEW;
		 
    END;
$borrar_fila_tener_participaciones_si_es_cero$ LANGUAGE plpgsql;

CREATE TRIGGER borrar_fila_tener_participaciones_si_es_cero AFTER UPDATE ON tener_participaciones
FOR EACH ROW EXECUTE PROCEDURE borrar_fila_tener_participaciones_si_es_cero();

--Si una fila tiene numero de participaciones 0, la borramos
CREATE OR REPLACE FUNCTION borrar_fila_anuncio_venta_si_es_cero() RETURNS trigger AS $borrar_fila_anuncio_venta_si_es_cero$
    BEGIN
		
		DELETE FROM anuncio_venta WHERE id1=new.id1 and id2=new.id2 and fecha=new.fecha and num_participaciones=0;

		RETURN NEW;
		 
    END;
$borrar_fila_anuncio_venta_si_es_cero$ LANGUAGE plpgsql;

CREATE TRIGGER borrar_fila_anuncio_venta_si_es_cero AFTER UPDATE OR INSERT ON anuncio_venta
FOR EACH ROW EXECUTE PROCEDURE borrar_fila_anuncio_venta_si_es_cero();

--Comprueba que no se repite el ID entre usuarios_empresa, inversores y el regulador
CREATE OR REPLACE FUNCTION comprueba_tipo_unico_usuario() RETURNS trigger AS $comprueba_tipo_unico_usuario$
    DECLARE
		num_ids_duplicados integer;
    BEGIN
		SELECT count(*) into num_ids_duplicados FROM (SELECT todos_los_ids.id FROM (SELECT id FROM usuario_regulador
UNION ALL
SELECT id FROM usuario_empresa
UNION ALL
SELECT id FROM usuario_inversor
UNION ALL
SELECT new.id) as todos_los_ids GROUP BY id HAVING COUNT(id) > 1) as ids_duplicados;
		  
		IF num_ids_duplicados<>0 THEN
            		RAISE EXCEPTION 'Ya existe un usuario con ese ID';
		END IF;

		RETURN NEW;
		 
    END;
$comprueba_tipo_unico_usuario$ LANGUAGE plpgsql;

CREATE TRIGGER comprueba_tipo_unico_usuario BEFORE INSERT ON usuario_regulador
FOR EACH ROW EXECUTE PROCEDURE comprueba_tipo_unico_usuario();
CREATE TRIGGER comprueba_tipo_unico_usuario BEFORE INSERT ON usuario_mercado
FOR EACH ROW EXECUTE PROCEDURE comprueba_tipo_unico_usuario();
CREATE TRIGGER comprueba_tipo_unico_usuario BEFORE INSERT ON usuario_inversor
FOR EACH ROW EXECUTE PROCEDURE comprueba_tipo_unico_usuario();
CREATE TRIGGER comprueba_tipo_unico_usuario BEFORE INSERT ON usuario_empresa
FOR EACH ROW EXECUTE PROCEDURE comprueba_tipo_unico_usuario();

--Realiza inmediatamente un pago de beneficios
CREATE OR REPLACE FUNCTION pagar_beneficios(id_empresa usuario_empresa.id%TYPE, pago_por_participacion double precision) RETURNS void AS $$
	DECLARE
		--comision double precision;
    BEGIN

		--SELECT usuario_regulador.comision_actual into comision FROM usuario_regulador LIMIT 1;

		--UPDATE usuario_mercado SET saldo=saldo-((SELECT sum(num_participaciones * pago_por_participacion) FROM tener_participaciones WHERE tener_participaciones.id2=id_empresa) * (1.0 + comision)) WHERE usuario_mercado.id=id_empresa; --Primero le restamos a la empresa el saldo que se usaría para pagar (Esta versión no la usamos porque tiene en cuenta la comisión actual)
		UPDATE usuario_mercado SET saldo=saldo-(SELECT sum(num_participaciones * pago_por_participacion) FROM tener_participaciones WHERE tener_participaciones.id2=id_empresa) WHERE usuario_mercado.id=id_empresa; --Primero le restamos a la empresa el saldo que se usaría para pagar

		UPDATE usuario_mercado SET saldo=saldo+(SELECT num_participaciones * pago_por_participacion FROM tener_participaciones WHERE tener_participaciones.id2=id_empresa and tener_participaciones.id1=usuario_mercado.id) WHERE usuario_mercado.id in (SELECT id1 FROM tener_participaciones WHERE tener_participaciones.id2=id_empresa); --A cada usuario le sumamos el importe correspondiente a sus participaciones

    END;
$$ LANGUAGE plpgsql;

--Función para realizar el pago de un anuncio de beneficios
CREATE OR REPLACE FUNCTION pagar_anuncio_beneficios(id_empresa beneficios.id%TYPE, fecha beneficios.fecha_pago%TYPE) RETURNS void AS $$
	DECLARE
		importe beneficios.importe_por_participacion%TYPE;
    BEGIN

		SELECT beneficios.importe_por_participacion into importe FROM beneficios WHERE beneficios.id=id_empresa and beneficios.fecha_pago=fecha;

		DELETE FROM beneficios WHERE beneficios.id=id_empresa and beneficios.fecha_pago=fecha; --Primero borramos el anuncio, por lo que el saldo se vuelve disponible inmediatamente para la funcion pagar_beneficios

		PERFORM pagar_beneficios(id_empresa, importe); --Llamamos a la funcion

    END;
$$ LANGUAGE plpgsql;
