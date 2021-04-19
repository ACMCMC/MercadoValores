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
        	on delete cascade,
  CHECK (importe_por_participacion >= 0::double precision)
);

CREATE TABLE tener_participaciones(
  id_1 varchar(30),
  id_2 varchar(30),
  num_participaciones double precision,
  primary key (id_1,id_2),
	foreign key (id_1) references usuario_empresa
        	on update cascade
        	on delete cascade,
	foreign key (id_2) references usuario_mercado
        	on update cascade
        	on delete cascade,
  CHECK (num_participaciones >= 0::double precision)
);

CREATE TABLE anuncio_venta(
  id_1 varchar(30),
  id_2 varchar(30),
  num_participaciones double precision,
  fecha_pago timestamp,
  precio double precision,
  comision_en_fecha double precision,
  primary key (id_1,id_2,fecha_pago),
	foreign key (id_1) references usuario_empresa
        	on update cascade
        	on delete cascade,
	foreign key (id_2) references usuario_mercado
        	on update cascade
        	on delete cascade,
  CHECK (precio >= 0::double precision AND comision_en_fecha >= 0::double precision AND num_participaciones > 0::double precision)
);

CREATE FUNCTION comprueba_participaciones() RETURNS trigger AS $comprueba_participaciones$
    DECLARE
		total double precision;
		max double precision;
    BEGIN
		SELECT SUM(num_participaciones) into total
		FROM anuncio_venta
		WHERE id_1=new.id_1
		  AND id_2=new.id_2;
		  
		SELECT num_participaciones into max
		FROM tener_participaciones
		WHERE id_1=new.id_1
		  AND id_2=new.id_2;
		
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
