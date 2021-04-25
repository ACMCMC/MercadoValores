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
  importe_bloqueado double precision,
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
  fecha_pago timestamp,
  precio double precision,
  comision_en_fecha double precision,
  primary key (id1,id2,fecha_pago),
	foreign key (id1,id2) references tener_participaciones
        	on update cascade
        	on delete cascade, --si se borra el usuario se borran sus anuncios de venta. si se borra una empresa esto no entra en juego porque antes tiene que acabar con sus participaciones en el mercado
  CHECK (precio >= 0::double precision AND comision_en_fecha >= 0::double precision AND num_participaciones > 0::integer)
);

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

		UPDATE usuario_empresa(importe_bloqueado) SET importe_bloqueado=importe_bloqueado+diferencia_beneficios_a_pagar WHERE id=new.id;
		UPDATE usuario_mercado(saldo) SET saldo=saldo-diferencia_beneficios_a_pagar WHERE id=new.id;

		RETURN NEW;
		 
    END;
$procesa_bloqueo_importe_pago_beneficios$ LANGUAGE plpgsql;

CREATE TRIGGER procesa_bloqueo_importe_pago_beneficios BEFORE INSERT OR UPDATE OR DELETE ON beneficios
FOR EACH ROW EXECUTE PROCEDURE procesa_bloqueo_importe_pago_beneficios();

--Funcionalidades extra

CREATE TABLE compra(
    id_compra serial UNIQUE,
	empresa varchar(30),
	comprador varchar(30),
	fecha timestamp,
	primary key(id_compra,empresa,comprador),
	foreign key (comprador) references usuario_mercado(id)
        	on update cascade
        	on delete restrict, 
	foreign key (empresa) references usuario_empresa(id)
        	on update cascade
        	on delete restrict
);
CREATE TABLE parte_compra(
	id_parte serial UNIQUE,
	id_compra integer,
	vendedor varchar(30),
	precio double precision,
	cantidad integer,
	primary key(id_compra,id_parte,vendedor),
	foreign key (vendedor) references usuario_mercado(id)
        	on update cascade
        	on delete restrict, 
	constraint claveforanealineal
		foreign key (id_compra) references compra(id_compra)
        		on update cascade
        		on delete restrict,
	CHECK (precio >= 0::double precision AND cantidad >= 0::integer)
);
