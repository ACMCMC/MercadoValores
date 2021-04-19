DROP TABLE beneficios;
DROP TABLE tener_participaciones;
DROP TABLE usuario_mercado CASCADE;
DROP TABLE usuario_empresa CASCADE;
DROP TABLE usuario_inversor;
DROP TABLE usuario_regulador;
DROP TABLE anuncio_venta;
DROP TRIGGER comprueba_participaciones ON anuncio_venta;
DROP FUNCTION comprueba_participaciones;