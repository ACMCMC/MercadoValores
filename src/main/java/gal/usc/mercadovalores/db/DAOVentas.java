/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.db;

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
import java.util.HashSet;

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
					"delete from anuncio_venta (id1,id2,num_participaciones,fecha_pago,precio,comision_en_fecha)" +
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
    
    
}
