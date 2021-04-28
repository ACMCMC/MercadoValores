/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.db;

import gal.usc.mercadovalores.aplicacion.AnuncioVenta;
import gal.usc.mercadovalores.aplicacion.EstadoUsuario;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import java.sql.Connection;
import java.util.Set;

import gal.usc.mercadovalores.aplicacion.Participacion;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import gal.usc.mercadovalores.aplicacion.AnuncioVenta;
import gal.usc.mercadovalores.aplicacion.Compra;
import gal.usc.mercadovalores.aplicacion.EstadoUsuario;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.ParteCompra;
import gal.usc.mercadovalores.aplicacion.Participacion;
import gal.usc.mercadovalores.aplicacion.Usuario;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import java.util.Objects;

/**
 *
 * @author PC
 */
public class DAOVentas extends DAO<Participacion> {
        public DAOVentas(Connection con) {
                super(con);
        }

        public void publicarVenta(UsuarioDeMercado u1, UsuarioEmpresa u2, Integer numero, double precio,
                        double comision) throws SQLException {
                Connection c = startTransaction();
                PreparedStatement preparedStatement = null;
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                try {
                        getConexion().setAutoCommit(false);
                        preparedStatement = getConexion().prepareStatement(
                                        "Insert into anuncio_venta (id1,id2,num_participaciones,fecha,precio,comision_en_fecha)"
                                                        + "Values (?,?,?,?,?,?)");
                        preparedStatement.setString(1, u1.getId());
                        preparedStatement.setString(2, u2.getId());
                        preparedStatement.setInt(3, numero);
                        preparedStatement.setTimestamp(4, timestamp);
                        preparedStatement.setDouble(5, precio);
                        preparedStatement.setDouble(6, comision);

                        preparedStatement.executeUpdate();

                        getConexion().commit();
                } catch (SQLException e) {
                        throw e;
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }

        }

        public void confirmarVenta(String id1, String id2, Timestamp fecha) throws SQLException {
                Connection c = startTransaction();
                PreparedStatement preparedStatement = null;
                try {
                        getConexion().setAutoCommit(false);
                        preparedStatement = getConexion().prepareStatement(
                                        "delete from anuncio_venta " + "where id1=? and id2=? and fecha=?");
                        preparedStatement.setString(1, id1);
                        preparedStatement.setString(2, id2);
                        preparedStatement.setTimestamp(3, fecha);

                        preparedStatement.executeUpdate();

                        getConexion().commit();
                } catch (SQLException e) {
                        throw e;
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }
        }

        public void retirarVenta(String id1, String id2, Timestamp fecha) throws SQLException {
                Connection c = startTransaction();
                PreparedStatement preparedStatement = null;
                try {
                        getConexion().setAutoCommit(false);
                        preparedStatement = getConexion().prepareStatement(
                                        "delete from anuncio_venta " + "where id1=? and id2=? and fecha=?");
                        preparedStatement.setString(1, id1);
                        preparedStatement.setString(2, id2);
                        preparedStatement.setTimestamp(3, fecha);

                        preparedStatement.executeUpdate();

                        getConexion().commit();
                } catch (SQLException e) {
                        throw e;
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }

        }

        public Set<AnuncioVenta> getAll() {
                FachadaDB f = FachadaDB.getFachada();
                Connection c = startTransaction();
                Set<AnuncioVenta> setFinal = new HashSet<>();

                PreparedStatement preparedStatement = null;
                ResultSet resultSet;

                try {
                        preparedStatement = getConexion().prepareStatement("select * from anuncio_venta");
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                                AnuncioVenta a;
                                try {
                                        String id = resultSet.getString("id1");
                                        String id2 = resultSet.getString("id2");
                                        Integer numero = resultSet.getInt("num_participaciones");
                                        Timestamp tim = resultSet.getTimestamp("fecha");
                                        double precio = resultSet.getDouble("precio");
                                        double comision = resultSet.getDouble("comision_en_fecha");

                                        a = new AnuncioVenta((UsuarioDeMercado) f.getUsuarioById(id),
                                                        (UsuarioEmpresa) f.getUsuarioById(id2), tim, precio, comision,
                                                        numero);
                                        setFinal.add(a);
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                        getConexion().commit();
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }

                return setFinal;
        }

        public Set<AnuncioVenta> getAnuncioUsuario(UsuarioDeMercado u) {
                FachadaDB f = FachadaDB.getFachada();
                Connection c = startTransaction();
                Set<AnuncioVenta> setFinal = new HashSet<>();

                PreparedStatement preparedStatement = null;
                ResultSet resultSet;

                try {
                        preparedStatement = getConexion().prepareStatement("select * from anuncio_venta where id1=?");
                        preparedStatement.setString(1, u.getId());
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                                AnuncioVenta a;
                                try {
                                        String id = resultSet.getString("id1");
                                        String id2 = resultSet.getString("id2");
                                        Integer numero = resultSet.getInt("num_participaciones");
                                        Timestamp tim = resultSet.getTimestamp("fecha");
                                        double precio = resultSet.getDouble("precio");
                                        double comision = resultSet.getDouble("comision_en_fecha");

                                        a = new AnuncioVenta((UsuarioDeMercado) f.getUsuarioById(id),
                                                        (UsuarioEmpresa) f.getUsuarioById(id2), tim, precio, comision,
                                                        numero);
                                        setFinal.add(a);
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                        getConexion().commit();
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }

                return setFinal;
        }

        public Integer getParticipacionesDeEmpresaALaVentaPorUsuario(String id1, String id2) {
                Connection c = startTransaction();
                Integer ret = 0;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet;
                try {
                        preparedStatement = getConexion()
                                        .prepareStatement("select * from anuncio_venta where id1=? and id2=?");
                        preparedStatement.setString(1, id1);
                        preparedStatement.setString(2, id2);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                                try {
                                        Integer aux = resultSet.getInt("num_participaciones");
                                        ret += aux;
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }
                return ret;
        }

        /**
         * Devuelve el precio medio de las últimas X compras a la empresa, o NULL si no
         * se han producido compras.
         * 
         * @param empresa
         * @param numCompras
         * @return
         */
        public Double getPrecioMedioComprasEmpresa(UsuarioEmpresa empresa, int numCompras) {

                Connection c = startTransaction();
                Double ret = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet;
                try {
                        preparedStatement = c.prepareStatement("select precio_medio_compras_empresa(?,?)");
                        preparedStatement.setString(1, empresa.getId());
                        preparedStatement.setInt(2, numCompras);
                        resultSet = preparedStatement.executeQuery();
                        c.commit();
                        if (resultSet.next()) {
                                try {
                                        ret = resultSet.getDouble(1);
                                        if (resultSet.wasNull()) {
                                                ret = null;
                                        }
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }
                return ret;
        }

        public Set<UsuarioEmpresa> empresasConAnuncios() {
                Connection c = startTransaction();
                Set<UsuarioEmpresa> ret = new HashSet<>();
                PreparedStatement preparedStatement = null;
                PreparedStatement preparedStatement2 = null;
                ResultSet resultSet, resultSet2;
                UsuarioEmpresa usuario = null;
                try {
                        preparedStatement = getConexion().prepareStatement("select distinct id2 from anuncio_venta");
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                                try {
                                        String id = resultSet.getString("id2");
                                        preparedStatement2 = getConexion().prepareStatement(
                                                        "select * from usuario_empresa inner join usuario_mercado using(id) where id=? ");
                                        preparedStatement2.setString(1, id);

                                        resultSet2 = preparedStatement2.executeQuery();
                                        while (resultSet2.next()) {

                                                String id2 = resultSet2.getString("id");
                                                String clave = resultSet2.getString("clave");
                                                double saldo = resultSet2.getDouble("saldo");
                                                String direccion = resultSet2.getString("direccion");
                                                String telefono = resultSet2.getString("telefono");
                                                EstadoUsuario estado = EstadoUsuario
                                                                .getByName(resultSet2.getString("estado"));
                                                String cif = resultSet2.getString("cif");
                                                String nombreComercial = resultSet2.getString("nombre_comercial");
                                                double importeBloqueado = resultSet2.getDouble("importe_bloqueado");

                                                usuario = new UsuarioEmpresa(id2, clave, saldo, direccion, telefono,
                                                                estado, cif, nombreComercial, importeBloqueado);
                                                ret.add(usuario);

                                        }
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                                preparedStatement2.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }
                return ret;

        }

        /**
         * Se encarga de realizar una compra
         * 
         * @param Usuario
         * @param empresa
         * @param numero
         * @param precio_max_por_participacion
         * @return
         */
        public Compra comprar(UsuarioDeMercado Usuario, UsuarioEmpresa empresa, Integer numero,
                        double precio_max_por_participacion) {
                Connection c = startTransaction();
                Compra ret = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet;
                try {
                        preparedStatement = c.prepareStatement("select comprar(?,?,?,?)");
                        preparedStatement.setString(1, Usuario.getId());
                        preparedStatement.setString(2, empresa.getId());
                        preparedStatement.setInt(3, numero);
                        preparedStatement.setDouble(4, precio_max_por_participacion);
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                                try {
                                        Integer idCompra = resultSet.getInt(1);
                                        ret = getCompra(idCompra);
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                        c.commit();
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }
                return ret;
        }

        public Set<Compra> getAllCompras() {
                Connection c = startTransaction();
                Set<Compra> ret = new HashSet<>();
                PreparedStatement preparedStatement = null;
                ResultSet resultSet;
                try {
                        preparedStatement = c
                                        .prepareStatement("select id_compra, empresa, comprador, fecha FROM compra");
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                                try {
                                        UsuarioEmpresa empresa = (UsuarioEmpresa) FachadaDB.getFachada()
                                                        .getUsuarioById(resultSet.getString("empresa"));
                                        UsuarioDeMercado comprador = (UsuarioDeMercado) FachadaDB.getFachada()
                                                        .getUsuarioById(resultSet.getString("comprador"));
                                        Timestamp fecha = resultSet.getTimestamp("fecha");
                                        int idCompra = resultSet.getInt("id_compra");
                                        Set<ParteCompra> partes = getPartesCompra(idCompra);
                                        ret.add(new Compra(partes, idCompra, fecha, empresa, comprador));
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                        c.commit();
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }
                return ret;
        }

        public Compra getCompra(int idCompra) {
                Connection c = startTransaction();
                Compra ret = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet;
                try {
                        preparedStatement = c.prepareStatement(
                                        "select empresa, comprador, fecha FROM compra WHERE id_compra=?");
                        preparedStatement.setInt(1, idCompra);
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                                try {
                                        UsuarioEmpresa empresa = (UsuarioEmpresa) FachadaDB.getFachada()
                                                        .getUsuarioById(resultSet.getString("empresa"));
                                        UsuarioDeMercado comprador = (UsuarioDeMercado) FachadaDB.getFachada()
                                                        .getUsuarioById(resultSet.getString("comprador"));
                                        Timestamp fecha = resultSet.getTimestamp("fecha");
                                        Set<ParteCompra> partes = getPartesCompra(idCompra);
                                        ret = new Compra(partes, idCompra, fecha, empresa, comprador);
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                        c.commit();
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }
                return ret;
        }

        public Set<ParteCompra> getPartesCompra(int idCompra) {
                Connection c = startTransaction();
                Set<ParteCompra> ret = new HashSet<>();
                PreparedStatement preparedStatement = null;
                ResultSet resultSet;
                try {
                        preparedStatement = c.prepareStatement(
                                        "select id_parte, vendedor, precio, cantidad FROM parte_compra WHERE id_compra=?");
                        preparedStatement.setInt(1, idCompra);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                                try {
                                        int id_parte = resultSet.getInt("id_parte");
                                        UsuarioDeMercado vendedor = (UsuarioDeMercado) FachadaDB.getFachada()
                                                        .getUsuarioById(resultSet.getString("vendedor"));
                                        double precio = resultSet.getInt("precio");
                                        int cantidad = resultSet.getInt("cantidad");
                                        ParteCompra parte = new ParteCompra(id_parte, precio, cantidad, vendedor);
                                        ret.add(parte);
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                        c.commit();
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }
                return ret;
        }

        public Set<UsuarioEmpresa> getEmpresasConAnuncios() {
                Connection c = startTransaction();
                Set<UsuarioEmpresa> ret = new HashSet<>();
                PreparedStatement preparedStatement = null;
                PreparedStatement preparedStatement2 = null;
                ResultSet resultSet, resultSet2;
                UsuarioEmpresa usuario = null;
                try {
                        preparedStatement = getConexion().prepareStatement("select distinct id2 from anuncio_venta");
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                                try {
                                        String id = resultSet.getString("id2");
                                        preparedStatement2 = getConexion().prepareStatement(
                                                        "select * from usuario_empresa inner join usuario_mercado using(id) where id=? ");

                                        preparedStatement2.setString(1, id);

                                        resultSet2 = preparedStatement2.executeQuery();
                                        while (resultSet2.next()) {
                                                String id2 = resultSet2.getString("id");
                                                String clave = resultSet2.getString("clave");
                                                double saldo = resultSet2.getDouble("saldo");
                                                String direccion = resultSet2.getString("direccion");
                                                String telefono = resultSet2.getString("telefono");
                                                EstadoUsuario estado = EstadoUsuario
                                                                .getByName(resultSet2.getString("estado"));
                                                String cif = resultSet2.getString("cif");
                                                String nombreComercial = resultSet2.getString("nombre_comercial");
                                                double importeBloqueado = resultSet2.getDouble("importe_bloqueado");

                                                usuario = new UsuarioEmpresa(id2, clave, saldo, direccion, telefono,
                                                                estado, cif, nombreComercial, importeBloqueado);
                                                ret.add(usuario);

                                        }
                                } catch (EnumConstantNotPresentException e) {
                                        FachadaAplicacion.muestraExcepcion(e);
                                }
                        }
                } catch (SQLException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                } finally {
                        try {
                                preparedStatement.close();
                                preparedStatement2.close();
                        } catch (SQLException e) {
                                FachadaAplicacion.muestraExcepcion(e);
                        }
                }
                return ret;

        }

}
