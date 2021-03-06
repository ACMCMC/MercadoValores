CREATE EXTENSION IF NOT EXISTS pgcrypto; --Para encriptar las contrasenas

--Los saldos los ponemos como números en punto flotante
--Los números de participaciones son enteros, las participaciones son indivisibles

--T-01
CREATE TABLE usuario_regulador(
  id varchar(30), --Parece un limite razonable
  clave text NOT NULL, --La guardaremos encriptada (funcionalidad optativa)
  saldo double precision NOT NULL DEFAULT 0.0,
  comision_actual double precision NOT NULL,
  primary key (id),
  CHECK (saldo >= 0::double precision AND comision_actual >= 0::double precision AND comision_actual <= 1::double precision)
);

CREATE TYPE enum_estado AS ENUM ('SOLICITANDO_ALTA', 'SOLICITANDO_BAJA', 'DADO_DE_ALTA'); --Los estados en los que puede estar un usuario

--T-02
CREATE TABLE usuario_mercado(
    id varchar(30),
    clave text NOT NULL,
    saldo double precision DEFAULT 0.0,
    direccion text,
    telefono text, --text porque no sabemos el formato concreto, los telefonos internacionalmente se escriben de formas muy distintas
    estado enum_estado NOT NULL,
    primary key (id),
    CHECK (saldo >= 0::double precision)
);

--T-03
CREATE TABLE usuario_inversor(
  id varchar(30),
  dni char(9) NOT NULL, --Porque sabemos la longitud concreta
  nombre_completo text NOT NULL,
  primary key (id),
    foreign key (id) references usuario_mercado
   		 on update cascade --Si actualizamos el ID del usuario, esta tabla se deberia actualizar tambien
   		 on delete cascade, --Al borrar la entrada del usuario en usuario_mercado, se borra automaticamente la entrada en usuario_inversor
  CHECK (dni SIMILAR TO '\d{8}[A-Z]') --Comprobamos que realmente sea un DNI
);

--T-04
CREATE TABLE usuario_empresa(
  id varchar(30),
  cif char(9) NOT NULL, --Igual que para el DNI
  nombre_comercial text NOT NULL,
  importe_bloqueado double precision DEFAULT 0.0,
  primary key (id),
    foreign key (id) references usuario_mercado
   		 on update cascade --La justificacion es la misma que para usuario_inversor
   		 on delete cascade,
  CHECK (importe_bloqueado >= 0::double precision),
  CHECK (cif SIMILAR TO '[A-Z]\d{8}')
);

--T-05
CREATE TABLE beneficios(
  id varchar(30),
  fecha_pago timestamp,
  importe_por_participacion double precision,
  num_participaciones integer,
  primary key (id,fecha_pago),
	foreign key (id) references usuario_empresa
        	on update cascade  --Actualizar el id de una empresa deberia hacer que sus anuncios de pago se actualicen tambien, no tendriamos razon para restringirlo
        	on delete cascade, --Si una empresa se borra desaparecen sus anuncios de pago de beneficios
  CHECK (importe_por_participacion >= 0::double precision AND num_participaciones >= 0::integer)
);

--T-06
CREATE TABLE tener_participaciones(
  id1 varchar(30),
  id2 varchar(30),
  num_participaciones integer,
  primary key (id1,id2),
	foreign key (id1) references usuario_mercado(id)
        	on update cascade
        	on delete restrict, --Restringido, si queremos borrar un usuario antes tenemos que asegurarnos de que ya no tiene participaciones
	foreign key (id2) references usuario_empresa(id)
        	on update cascade
        	on delete restrict, --Idem
  CHECK (num_participaciones >= 0::integer)
);

--T-07
CREATE TABLE anuncio_venta(
  id1 varchar(30),
  id2 varchar(30),
  num_participaciones integer NOT NULL,
  fecha timestamptz,
  precio double precision NOT NULL,
  comision_en_fecha double precision NOT NULL,
  primary key (id1,id2,fecha),
	foreign key (id1,id2) references tener_participaciones
        	on update cascade
        	on delete cascade, --Si se borra el usuario se borran sus anuncios de venta. Si se borra una empresa esto no entra en juego porque antes tiene que acabar con sus participaciones en el mercado
  CHECK (precio >= 0::double precision AND comision_en_fecha >= 0::double precision AND comision_en_fecha <= 1::double precision AND num_participaciones >= 0::integer)
);

--Funcionalidades extra

--T-08
CREATE TABLE compra(
    id_compra serial UNIQUE, --Usamos un serial y asi podemos anadir nuevas compras con ids autoincrementales
	empresa varchar(30) NOT NULL,
	comprador varchar(30) NOT NULL,
	fecha timestamptz,
	primary key(id_compra),
	foreign key (comprador) references usuario_mercado(id)
        	on update cascade --La actualizacion es logico que sea en cascada, cambiar el id deberia poder hacerse y que se propaguen los cambios
        	on delete cascade, --Podriamos plantearnos plantearnos una restriccion a la hora de borrar, pero como el historial no es una parte clave, entonces hemos decidido que si se borre el usuario tambien se borre su historial. Otra opcion seria poner un NULL por defecto cuando se borra
	foreign key (empresa) references usuario_empresa(id)
        	on update cascade --Idem
        	on delete cascade
);

--T-09
CREATE TABLE parte_compra(
	id_compra serial,
	id_parte serial UNIQUE, --Cada parte de compra se identifica independientemente, esto nos permite usar generacion de valores por defecto
	vendedor varchar(30) NOT NULL,
	precio double precision NOT NULL,
	cantidad integer NOT NULL,
	primary key(id_compra,id_parte),
	foreign key (vendedor) references usuario_mercado(id)
        	on update cascade
        	on delete cascade, --Podriamos plantearnos plantearnos una restriccion a la hora de borrar
	foreign key (id_compra) references compra(id_compra)
        		on update cascade
        		on delete restrict,
	CHECK (precio >= 0::double precision AND cantidad >= 0::integer)
);

--Esta vista nos muestra el pago total que se haria por un anuncio de beneficios. La columna de pago total es calculada.
CREATE OR REPLACE VIEW pago_total_beneficios AS SELECT id, fecha_pago, COALESCE(importe_por_participacion*(SELECT SUM(num_participaciones) FROM tener_participaciones WHERE id2=beneficios.id), 0) AS importe_total FROM beneficios;

--Permisos: Funcionalidad optativa
CREATE ROLE regulador_user;
CREATE ROLE mercado_user;
CREATE ROLE inversor_user INHERIT;
GRANT mercado_user TO inversor_user; --inversor_user hereda todos los permisos de mercado_user
CREATE ROLE empresa_user INHERIT;
GRANT mercado_user TO empresa_user; --empresa_user hereda todos los permisos de empresa_user

GRANT SELECT, UPDATE (estado, saldo) ON usuario_mercado TO regulador_user; --El regulador solo puede modificar el estado (para dar de alta y baja) de un usuario de mercado, y su saldo, pero no otras columnas

GRANT INSERT, SELECT, UPDATE ON usuario_regulador TO regulador_user;
GRANT INSERT, SELECT, UPDATE, DELETE ON tener_participaciones TO mercado_user; --Los usuarios de mercado operan emitiendo, comprando, vendiendo, etc.
GRANT INSERT, SELECT, UPDATE ON usuario_mercado TO mercado_user;
GRANT INSERT, SELECT, UPDATE, DELETE ON anuncio_venta TO mercado_user; --Todos los usuarios de mercado pueden comprar y vender
GRANT INSERT, SELECT, UPDATE ON compra TO mercado_user; --Mismo motivo
GRANT INSERT, SELECT, UPDATE ON parte_compra TO mercado_user; --Mismo motivo
GRANT INSERT, SELECT, UPDATE ON usuario_empresa TO empresa_user;
GRANT INSERT, SELECT, UPDATE, DELETE ON beneficios TO empresa_user;
GRANT SELECT ON beneficios TO mercado_user;
GRANT INSERT, SELECT, UPDATE ON usuario_inversor TO inversor_user;
GRANT SELECT ON usuario_regulador TO mercado_user;
GRANT UPDATE (saldo) ON usuario_regulador TO mercado_user; --Cuando pagamos una comision, anadimos saldo al regulador
GRANT SELECT, UPDATE ON usuario_empresa TO inversor_user;
GRANT SELECT ON usuario_inversor TO empresa_user; --Un usuario empresa puede necesitar ver los datos de usuario_inversor
GRANT SELECT, DELETE ON beneficios TO regulador_user; --El usuario regulador puede ver y eliminar anuncios de beneficios
GRANT SELECT ON tener_participaciones TO regulador_user; --El usuario regulador puede ver la tabla tener_participaciones
GRANT SELECT, DELETE, UPDATE ON usuario_empresa TO regulador_user; --El usuario regulador puede eliminar usuarios 
GRANT SELECT, DELETE ON usuario_inversor TO regulador_user; --Idem
GRANT SELECT, DELETE, UPDATE ON usuario_mercado TO regulador_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO mercado_user; --Las secuencias son de acceso publico


--FUNCIONES DE LA BASE DE DATOS

--Esta funcion comprueba que no se venden mas participaciones de las que se tienen. Se usa como trigger. Esto ocurre tambien en las siguientes.
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
            		RAISE EXCEPTION 'El numero de participaciones a la venta no puede exceder el total poseido';
		END IF;
		IF total IS NOT NULL THEN
            		IF total+new.num_participaciones > max THEN
				RAISE EXCEPTION 'El numero de participaciones a la venta no puede exceder el total poseido';
			END IF;
		END IF;
		RETURN NEW;
		 
    END;
$comprueba_participaciones$ LANGUAGE plpgsql;

CREATE TRIGGER comprueba_participaciones BEFORE INSERT OR UPDATE ON anuncio_venta
FOR EACH ROW EXECUTE PROCEDURE comprueba_participaciones();

--Se asegura de que se puede realizar el pago de beneficios al actualizar la tabla tener_participaciones
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
            		RAISE EXCEPTION 'Los beneficios no podrian pagarse';
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
		SELECT (new.importe_por_participacion - COALESCE(old.importe_por_participacion,0))*(COALESCE(SUM(tener_participaciones.num_participaciones),0)) into diferencia_beneficios_a_pagar --el importe que vamos a pagar ahora - el que pagariamos antes
		FROM tener_participaciones
		WHERE id2=new.id;
		
		SELECT saldo into max
		FROM usuario_mercado
		WHERE id=new.id;
		  
		IF diferencia_beneficios_a_pagar > max THEN
            		RAISE EXCEPTION 'Los beneficios no podrian pagarse';
		END IF;

		--sumamos al saldo bloqueado, y restamos al saldo disponible
		UPDATE usuario_empresa SET importe_bloqueado=importe_bloqueado+diferencia_beneficios_a_pagar WHERE id=new.id;
		UPDATE usuario_mercado SET saldo=saldo-diferencia_beneficios_a_pagar WHERE id=new.id;

		RETURN NEW;
		 
    END;
$procesa_bloqueo_importe_pago_beneficios$ LANGUAGE plpgsql;

CREATE TRIGGER procesa_bloqueo_importe_pago_beneficios BEFORE INSERT OR UPDATE ON beneficios
FOR EACH ROW EXECUTE PROCEDURE procesa_bloqueo_importe_pago_beneficios();

--Igual que la anterior, pero cuando hacemos un borrado de una fila
CREATE OR REPLACE FUNCTION procesa_bloqueo_importe_pago_beneficios_al_borrar() RETURNS trigger AS $procesa_bloqueo_importe_pago_beneficios_al_borrar$
    DECLARE
		diferencia_beneficios_a_pagar double precision;
		max double precision;
    BEGIN
		SELECT (-COALESCE(old.importe_por_participacion,0))*(COALESCE(SUM(tener_participaciones.num_participaciones),0)) into diferencia_beneficios_a_pagar --El importe que vamos a pagar ahora - el que pagariamos antes
		FROM tener_participaciones
		WHERE id2=old.id;
		
		SELECT saldo into max
		FROM usuario_mercado
		WHERE id=old.id;

		--Sumamos al saldo bloqueado, y restamos al saldo disponible
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
            		RAISE EXCEPTION 'Los beneficios no podrian pagarse';
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
            		RAISE EXCEPTION 'Los beneficios no podrian pagarse';
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

--Revisa que al actualizar la tabla tener_participaciones no se produzca una actualizacion que haga que el numero de participaciones que vende el usuario de una empresa sea mayor que las que tiene realmente
CREATE OR REPLACE FUNCTION comprobar_max_anuncio_venta_actualizar_tener_participaciones() RETURNS trigger AS $comprobar_max_anuncio_venta_actualizar_tener_participaciones$
    BEGIN
		
		IF (SELECT sum(anuncio_venta.num_participaciones) FROM anuncio_venta WHERE anuncio_venta.id1=new.id1 and anuncio_venta.id2=new.id2) > new.num_participaciones THEN
			RAISE EXCEPTION 'Los anuncios de venta sobrepasan a las participaciones poseidas';
		END IF;

		RETURN NEW;

    END;
$comprobar_max_anuncio_venta_actualizar_tener_participaciones$ LANGUAGE plpgsql;

CREATE TRIGGER comprobar_max_anuncio_venta_actualizar_tener_participaciones BEFORE UPDATE ON tener_participaciones
FOR EACH ROW EXECUTE PROCEDURE comprobar_max_anuncio_venta_actualizar_tener_participaciones();

--Si una fila tiene numero de participaciones 0, la borramos
CREATE OR REPLACE FUNCTION borrar_fila_anuncio_venta_si_es_cero() RETURNS trigger AS $borrar_fila_anuncio_venta_si_es_cero$
    BEGIN
		
		DELETE FROM anuncio_venta WHERE id1=new.id1 and id2=new.id2 and fecha=new.fecha and num_participaciones=0;

		RETURN NEW;
		 
    END;
$borrar_fila_anuncio_venta_si_es_cero$ LANGUAGE plpgsql;

CREATE TRIGGER borrar_fila_anuncio_venta_si_es_cero AFTER UPDATE OR INSERT ON anuncio_venta
FOR EACH ROW EXECUTE PROCEDURE borrar_fila_anuncio_venta_si_es_cero();

--Comprobamos que un usuario no intenta vender mas participaciones que las que tiene
CREATE OR REPLACE FUNCTION comprobar_maximo_anuncios_venta() RETURNS trigger AS $comprobar_maximo_anuncios_venta$
    BEGIN

		IF ((SELECT sum(anuncio_venta.num_participaciones) FROM anuncio_venta WHERE anuncio_venta.id1=new.id1 and anuncio_venta.id2=new.id2 and anuncio_venta.fecha <> new.fecha) + new.num_participaciones) > (SELECT tener_participaciones.num_participaciones FROM tener_participaciones WHERE tener_participaciones.id1=new.id1 and tener_participaciones.id2=new.id2) THEN
			RAISE EXCEPTION 'Se ha excedido el maximo de participaciones a la venta';
		END IF;

		RETURN NEW;
		 
    END;
$comprobar_maximo_anuncios_venta$ LANGUAGE plpgsql;

CREATE TRIGGER comprobar_maximo_anuncios_venta BEFORE UPDATE OR INSERT ON anuncio_venta
FOR EACH ROW EXECUTE PROCEDURE comprobar_maximo_anuncios_venta();

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

--Se lanza cuando insertamos filas en cualquier tabla de usuarios. Podria plantearse ejecutarla tambien al actualizar.
CREATE TRIGGER comprueba_tipo_unico_usuario BEFORE INSERT ON usuario_regulador
FOR EACH ROW EXECUTE PROCEDURE comprueba_tipo_unico_usuario();
CREATE TRIGGER comprueba_tipo_unico_usuario BEFORE INSERT ON usuario_mercado
FOR EACH ROW EXECUTE PROCEDURE comprueba_tipo_unico_usuario();
CREATE TRIGGER comprueba_tipo_unico_usuario BEFORE INSERT ON usuario_inversor
FOR EACH ROW EXECUTE PROCEDURE comprueba_tipo_unico_usuario();
CREATE TRIGGER comprueba_tipo_unico_usuario BEFORE INSERT ON usuario_empresa
FOR EACH ROW EXECUTE PROCEDURE comprueba_tipo_unico_usuario();

--Esta funcion realiza la compra de participaciones
CREATE OR REPLACE FUNCTION comprar(id_empresa usuario_empresa.id%TYPE, id_comprador usuario_mercado.id%TYPE, numero integer, precio_max_por_participacion double precision) RETURNS integer AS $$
    DECLARE
		participaciones_por_comprar integer;
		id_compra_creada compra.id_compra%TYPE;
		id_vendedor anuncio_venta.id1%TYPE;
		fecha_anuncio_venta anuncio_venta.fecha%TYPE;
		cantidad_compra integer;
		comision anuncio_venta.comision_en_fecha%TYPE;
    BEGIN

		SELECT numero into participaciones_por_comprar;
		INSERT INTO compra(empresa, comprador, fecha) VALUES (id_empresa, id_comprador, CURRENT_TIMESTAMP) RETURNING id_compra into id_compra_creada; --Puede que luego no se consiga hacer ninguna compra

		WHILE exists(SELECT * FROM anuncio_venta WHERE anuncio_venta.id1 <> id_comprador and anuncio_venta.id2=id_empresa and anuncio_venta.precio <= precio_max_por_participacion) and participaciones_por_comprar > 0 loop

		SELECT anuncio_venta.id1, anuncio_venta.fecha, anuncio_venta.comision_en_fecha into id_vendedor, fecha_anuncio_venta, comision FROM anuncio_venta WHERE anuncio_venta.id1 <> id_comprador and anuncio_venta.id2=id_empresa ORDER BY anuncio_venta.precio asc, anuncio_venta.fecha asc FETCH FIRST ROW ONLY;

		SELECT LEAST(participaciones_por_comprar, (SELECT anuncio_venta.num_participaciones FROM anuncio_venta WHERE anuncio_venta.fecha=fecha_anuncio_venta and anuncio_venta.id1=id_vendedor and anuncio_venta.id2=id_empresa)) into cantidad_compra; --El numero de acciones a comprar es el minimo de dos valores: el de las participaciones que nos faltan por comprar y el de las participaciones que se pueden comprar segun este anuncio

		SELECT participaciones_por_comprar-cantidad_compra into participaciones_por_comprar; --Restamos las participaciones que vamos a comprar

		INSERT INTO parte_compra(id_compra, vendedor, precio, cantidad) VALUES (id_compra_creada, id_vendedor, (SELECT precio FROM anuncio_venta WHERE anuncio_venta.fecha=fecha_anuncio_venta and anuncio_venta.id1=id_vendedor and anuncio_venta.id2=id_empresa), cantidad_compra);

		UPDATE usuario_mercado SET saldo=saldo - cantidad_compra * (SELECT anuncio_venta.precio FROM anuncio_venta WHERE anuncio_venta.fecha=fecha_anuncio_venta and anuncio_venta.id1=id_vendedor and anuncio_venta.id2=id_empresa) WHERE id=id_comprador; --Le quitamos el saldo correspondiente al comprador
		UPDATE usuario_mercado SET saldo=saldo+cantidad_compra*(1.0-comision)*(SELECT anuncio_venta.precio FROM anuncio_venta WHERE anuncio_venta.fecha=fecha_anuncio_venta and anuncio_venta.id1=id_vendedor and anuncio_venta.id2=id_empresa) WHERE id=id_vendedor; --Le sumamos la parte correspondiente a lo que no son comisiones de la venta al vendedor
		UPDATE usuario_regulador SET saldo=saldo+cantidad_compra*(comision)*(SELECT anuncio_venta.precio FROM anuncio_venta WHERE anuncio_venta.fecha=fecha_anuncio_venta and anuncio_venta.id1=id_vendedor and anuncio_venta.id2=id_empresa); --Le sumamos la parte correspondiente a las comisiones al regulador

		UPDATE anuncio_venta SET num_participaciones=num_participaciones-cantidad_compra WHERE anuncio_venta.fecha=fecha_anuncio_venta and anuncio_venta.id1=id_vendedor and anuncio_venta.id2=id_empresa; --Le restamos al anuncio de venta las participaciones que hemos vendido. Si pasa a haber 0, se borra automaticamente la fila
		UPDATE tener_participaciones SET num_participaciones=num_participaciones-cantidad_compra WHERE id1=id_vendedor and id2=id_empresa; --Restamos al que vende en la tabla tener_participaciones
		IF not exists(SELECT * FROM tener_participaciones WHERE id1=id_comprador and id2=id_empresa) THEN
			INSERT INTO tener_participaciones(id1, id2, num_participaciones) VALUES (id_comprador, id_empresa, 0);
		END IF;
		UPDATE tener_participaciones SET num_participaciones=num_participaciones+cantidad_compra WHERE id1=id_comprador and id2=id_empresa; --Sumamos al que compra

		end loop;

		RETURN id_compra_creada;
		 
    END;
$$ LANGUAGE plpgsql;

--Realiza inmediatamente un pago de beneficios
CREATE OR REPLACE FUNCTION pagar_beneficios(id_empresa usuario_empresa.id%TYPE, pago_por_participacion double precision, num_participaciones_por_participacion integer) RETURNS void AS $$
    BEGIN

		IF pago_por_participacion > 0::double precision THEN

		UPDATE usuario_mercado SET saldo=saldo-(SELECT sum(num_participaciones * pago_por_participacion) FROM tener_participaciones WHERE tener_participaciones.id2=id_empresa) WHERE usuario_mercado.id=id_empresa; --Primero le restamos a la empresa el saldo que se usaria para pagar

		UPDATE usuario_mercado SET saldo=saldo+(SELECT num_participaciones * pago_por_participacion FROM tener_participaciones WHERE tener_participaciones.id2=id_empresa and tener_participaciones.id1=usuario_mercado.id) WHERE usuario_mercado.id in (SELECT id1 FROM tener_participaciones WHERE tener_participaciones.id2=id_empresa); --A cada usuario le sumamos el importe correspondiente a sus participaciones

		END IF;

		IF num_participaciones_por_participacion > 0 THEN

		--Esto esta comentado, porque hemos decidido finalmente que las participaciones se crean, no se le quitan a la empresa. Hemos decidido mantenerlo para que sea sencillo recuperar el comportamiento anterior.

		--IF COALESCE((SELECT num_participaciones FROM tener_participaciones WHERE tener_participaciones.id1=id_empresa and tener_participaciones.id2=id_empresa),0) < (SELECT sum(t2.num_participaciones * num_participaciones_por_participacion) FROM tener_participaciones as t2 WHERE t2.id1 <> id_empresa and t2.id2=id_empresa) THEN
		--	RAISE EXCEPTION 'No se dispone de suficientes participaciones para hacer el pago';
		--END IF;

		--UPDATE tener_participaciones SET num_participaciones=num_participaciones-(SELECT sum(t2.num_participaciones * num_participaciones_por_participacion) FROM tener_participaciones as t2 WHERE t2.id1 <> id_empresa and t2.id2=id_empresa) WHERE tener_participaciones.id1=id_empresa and tener_participaciones.id2=id_empresa; --Primero le restamos a la empresa las participaciones que va a otorgar, no pagamos participaciones a la propia empresa.

		UPDATE tener_participaciones SET num_participaciones=num_participaciones+(num_participaciones * num_participaciones_por_participacion) WHERE tener_participaciones.id1 <> id_empresa and tener_participaciones.id2=id_empresa; --A cada usuario le sumamos las participaciones adecuadas

		END IF;

    END;
$$ LANGUAGE plpgsql;

--Funcion para realizar el pago de un anuncio de beneficios
CREATE OR REPLACE FUNCTION pagar_anuncio_beneficios(id_empresa beneficios.id%TYPE, fecha beneficios.fecha_pago%TYPE) RETURNS void AS $$
	DECLARE
		importe beneficios.importe_por_participacion%TYPE;
		numero_participaciones_por_participacion beneficios.num_participaciones%TYPE;
    BEGIN

		SELECT beneficios.importe_por_participacion, beneficios.num_participaciones into importe, numero_participaciones_por_participacion FROM beneficios WHERE beneficios.id=id_empresa and beneficios.fecha_pago=fecha;

		DELETE FROM beneficios WHERE beneficios.id=id_empresa and beneficios.fecha_pago=fecha; --Primero borramos el anuncio, por lo que el saldo se vuelve disponible inmediatamente para la funcion pagar_beneficios

		PERFORM pagar_beneficios(id_empresa, importe, numero_participaciones_por_participacion); --Llamamos a la funcion

    END;
$$ LANGUAGE plpgsql;

--Obtiene el precio medio de las X ultimas compras de una empresa
CREATE OR REPLACE FUNCTION precio_medio_compras_empresa(id_empresa beneficios.id%TYPE, num_ultimas_compras integer) RETURNS double precision AS $$
    BEGIN

		RETURN (SELECT avg(precio_medio.precio_medio_compra) FROM (SELECT avg(precio) as precio_medio_compra FROM parte_compra WHERE id_compra in (SELECT id_compra FROM compra WHERE compra.empresa=id_empresa ORDER BY compra.fecha desc LIMIT num_ultimas_compras) GROUP BY id_compra) as precio_medio);

    END;
$$ LANGUAGE plpgsql;
