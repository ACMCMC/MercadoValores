CREATE TABLE usuario_regulador(
  id varchar(30) ,
  clave varchar(40),
  saldo double precision,
  comision_actual double precision,
  primary key (id)
CONSTRAINT valores CHECK (saldo >= 0::double precision AND comision_actual >= 0::double precision) NOT VALID
);

CREATE TYPE enum_estado AS ENUM ('SOLICITANDO_ALTA', 'SOLICITANDO_BAJA', 'DADO_DE_ALTA');

CREATE TABLE usuario_mercado(
    id varchar(30),
    clave varchar(40),
    saldo double precision,
    direccion varchar(256),
    telefono varchar(15), --15 valores porque si se pretendiese ser internacional haria falta el prefijo internacional
    estado enum_estado,
    primary key (id)
CONSTRAINT valores CHECK (saldo >= 0::double precision) NOT VALID
);

CREATE TABLE usuario_inversor(
  id varchar(30),
  dni char(9),
  nombre_completo varchar(64),
  primary key (id),
    foreign key (id) references usuario_mercado
   		 on update cascade
   		 on delete cascade);

CREATE TABLE usuario_empresa(
  id varchar(30),
  cif char(9),
  nombre_comercial varchar(64),
  importe_bloqueado double precision,
  primary key (id),
    foreign key (id) references usuario_mercado
   		 on update cascade
   		 on delete cascade
CONSTRAINT valores CHECK (importe_bloqueado >= 0::double precision) NOT VALID
);

CREATE TABLE beneficios(
  id varchar(30),
  fecha_pago timestamp,
  importe_por_participacion double precision,
  primary key (id,fecha_pago),
	foreign key (id) references usuario_empresa
        	on update cascade
        	on delete cascade
CONSTRAINT valores CHECK (importe_por_participacion >= 0::double precision) NOT VALID
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
        	on delete cascade
CONSTRAINT valores CHECK (num_participaciones >= 0::double precision) NOT VALID
);

CREATE TABLE anuncio_venta(
  id_1 varchar(30),
  id_2 varchar(30),
  num_participaciones double precision,
  fecha_pago timestamp,
  precio double precision,
  comision_en_fecha double precision,
  primary key (id_1,id_2,comision_en_fecha),
	foreign key (id_1) references usuario_empresa
        	on update cascade
        	on delete cascade,
	foreign key (id_2) references usuario_mercado
        	on update cascade
        	on delete cascade
CONSTRAINT valores CHECK (precio >= 0::double precision AND comision_en_fecha >= 0::double precision AND num_participaciones > 0::double precision) NOT VALID
);

CREATE FUNCTION comprueba_participaciones() RETURNS trigger AS $comprueba_participaciones$
    BEGIN
		 IF SUM (num_participaciones) + NEW.num_participaciones > tener_participaciones.num_participaciones THEN
            		RAISE EXCEPTION 'El número de participaciones a la venta no puede exceder el total poseído';
		 END IF;
		 RETURN NEW;
    END;
$comprueba_participaciones$ LANGUAGE plpgsql;

CREATE TRIGGER comprueba_participaciones BEFORE INSERT OR UPDATE ON anuncio_venta
    FOR EACH ROW EXECUTE PROCEDURE comprueba_participaciones();

-- Para obtener la tabla en la que se cumple la restricción la consulta sería:(No he comprobado que de datos correctos, solo se que se ejecuta)

SELECT anuncio_venta.*
FROM tener_participaciones,anuncio_venta,
	(SELECT SUM(num_participaciones) as a
	FROM anuncio_venta) as A
WHERE tener_participaciones.id_1=anuncio_venta.id_1
  AND tener_participaciones.id_2=anuncio_venta.id_2
  AND A.a<=tener_participaciones.num_participaciones

