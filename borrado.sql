DROP TABLE beneficios CASCADE;
DROP TABLE tener_participaciones CASCADE;
DROP TABLE usuario_mercado CASCADE;
DROP TABLE usuario_empresa CASCADE;
DROP TABLE usuario_inversor;
DROP TABLE usuario_regulador;
DROP TRIGGER comprueba_participaciones ON anuncio_venta;
DROP FUNCTION comprueba_participaciones;