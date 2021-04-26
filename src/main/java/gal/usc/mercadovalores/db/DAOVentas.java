/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import gal.usc.mercadovalores.aplicacion.AnuncioVenta;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.Participacion;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;

/**
 *
 * @author PC
 */
public class DAOVentas extends DAO<Participacion> {
    public DAOVentas(Connection con) {
		super(con);
	}

    public void publicarVenta(UsuarioDeMercado u1,UsuarioEmpresa u2,Integer numero,double precio,double comision) throws SQLException{
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"Insert into anuncio_venta (id1,id2,num_participaciones,fecha_pago,precio,comision_en_fecha)" +
                                        "Values (?,?,?,?,?,?)" );
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
    
    
    
    public void confirmarVenta(String id1,String id2,Timestamp fecha) throws SQLException{
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"delete from anuncio_venta " +
                                        "where id1=? and id2=? and fecha_pago=?" );
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
    
    public void retirarVenta(String id1,String id2,Timestamp fecha) throws SQLException{
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"delete from anuncio_venta " +
                                        "where id1=? and id2=? and fecha_pago=?" );
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
                FachadaDB f=FachadaDB.getFachada();
		Connection c = startTransaction();
		Set<AnuncioVenta> setFinal = new HashSet<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = getConexion()
					.prepareStatement("select * from anuncio_venta");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				AnuncioVenta a;
				try {
					String id = resultSet.getString("id1");
                                        String id2 = resultSet.getString("id2");
                                        Integer numero = resultSet.getInt("num_participaciones");
                                        Timestamp tim=resultSet.getTimestamp("fecha_pago");
                                        double precio=resultSet.getDouble("precio");
                                        double comision=resultSet.getDouble("comision_en_fecha");

					a = new AnuncioVenta((UsuarioDeMercado)f.getUsuarioById(id), (UsuarioEmpresa)f.getUsuarioById(id2), tim, precio, comision, numero);
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
                FachadaDB f=FachadaDB.getFachada();
		Connection c = startTransaction();
		Set<AnuncioVenta> setFinal = new HashSet<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = getConexion()
					.prepareStatement("select * from anuncio_venta where id1=?");
                        preparedStatement.setString(1,u.getId() );
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				AnuncioVenta a;
				try {
					String id = resultSet.getString("id1");
                                        String id2 = resultSet.getString("id2");
                                        Integer numero = resultSet.getInt("num_participaciones");
                                        Timestamp tim=resultSet.getTimestamp("fecha_pago");
                                        double precio=resultSet.getDouble("precio");
                                        double comision=resultSet.getDouble("comision_en_fecha");

					a = new AnuncioVenta((UsuarioDeMercado)f.getUsuarioById(id), (UsuarioEmpresa)f.getUsuarioById(id2), tim, precio, comision, numero);
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
     
     public Integer getParticipacionesDeEmpresaALaVentaPorUsuario(String id1,String id2){
        Connection c = startTransaction();
        Integer ret=0;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet;
		try {
			preparedStatement = getConexion()
					.prepareStatement("select * from anuncio_venta where id1=? and id2=?");
                        preparedStatement.setString(1,id1 );
                        preparedStatement.setString(2,id2 );
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				try {
                                    Integer aux=resultSet.getInt("num_participaciones");
                                    ret+=aux;
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
    
}
