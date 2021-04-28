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
import java.util.Objects;

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
					"Insert into anuncio_venta (id1,id2,num_participaciones,fecha,precio,comision_en_fecha)" +
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
    
    
    

    
    public void retirarVenta(String id1,String id2,Timestamp fecha) throws SQLException{
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"delete from anuncio_venta " +
                                        "where id1=? and id2=? and fecha=?" );
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
                                        Timestamp tim=resultSet.getTimestamp("fecha");
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
                                        Timestamp tim=resultSet.getTimestamp("fecha");
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
    
     public void ventaParticipaciones(UsuarioDeMercado Usuario,UsuarioEmpresa empresa,Integer numero,Integer precio){
        Connection c = startTransaction();
        Integer ret=numero;
        Double saldoARestar=0.0;
	PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;
        PreparedStatement preparedStatement5 = null;
        PreparedStatement preparedStatement6 = null;
	ResultSet resultSet,resultSet2;
        //Variiables para hacer update
        ArrayList<String> ids=new ArrayList();
        ArrayList<Double> sumaSaldos=new ArrayList();
        ArrayList<Integer> participacionesVendidas=new ArrayList();
        Double Comision=0.0;
        Integer numCompradas=0;
        
		try {
                        c.setAutoCommit(false);
                        
			preparedStatement = getConexion()
					.prepareStatement("select * from anuncio_venta " +
                                                          "where ?<=precio and id2=? " +
                                                          "order by precio asc,fecha asc");
                        preparedStatement.setInt(1,precio );
                        preparedStatement.setString(2,empresa.getId() );
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next() && ret!=0) {
				try {
                                    Integer aux=resultSet.getInt("num_participaciones");
                                    String idUsuarioaux=resultSet.getString("id1");
                                    String idEmpresaaux=resultSet.getString("id2");
                                    Timestamp fecha=resultSet.getTimestamp("fecha");
                                    Double precioaux=resultSet.getDouble("precio");
                                    
                                    
                                    
                                        //Guardamos los ids para hacer luego update
                                        ids.add(idUsuarioaux);
                                        
                                       
                                        
                                        if(aux>ret){//Si es mayor el numero de participaciones a la venta de la tupla se hace update

                                            preparedStatement2 = getConexion()
                                            .prepareStatement("update anuncio_venta " +
                                                              "set num_participaciones=num_participaciones-? "+ 
                                                              "where id1=? and id2=? and fecha=?");
                                            preparedStatement2.setInt(1, ret);
                                            preparedStatement2.setString(2, idUsuarioaux);
                                            preparedStatement2.setString(3, idEmpresaaux);
                                            preparedStatement2.setTimestamp(4, fecha);

                                            //Cantdad a restar al usuario que compra
                                            saldoARestar+=aux*precioaux;
                                            //Se compraron todas las que se querían

                                            //Comision
                                            participacionesVendidas.add(ret);
                                            Comision+=precioaux*ret*resultSet.getDouble("comision_en_fecha");

                                            //Cantidad a sumar a cada usuario que vende(venta total - comisión)
                                             sumaSaldos.add(ret*precioaux-precioaux*ret*resultSet.getDouble("comision_en_fecha"));
                                             numCompradas+=ret;
                                             ret=0;
                                            preparedStatement2.executeUpdate();
                                            
                                        }else{//En todos los demas casos se borra la tupla
                                             preparedStatement2 = getConexion()
                                            .prepareStatement("delete from anuncio_venta " + 
                                                              "where id1=? and id2=? and fecha=? ");
                                            preparedStatement2.setString(1, idUsuarioaux);
                                            preparedStatement2.setString(2, idEmpresaaux);
                                            preparedStatement2.setTimestamp(3, fecha);

                                            //Cantdad a restar al usuario que compra
                                            participacionesVendidas.add(aux);
                                            saldoARestar+=aux*precioaux;
                                            ret-=aux;//Se compraron un numero hasta que llegue a 0
                                            numCompradas+=aux;
                                            //Comision
                                            Comision+=precioaux*aux*resultSet.getDouble("comision_en_fecha");

                                            //Cantidad a sumar a cada usuario que vende(venta total - comisión)
                                            sumaSaldos.add(aux*precioaux-precioaux*aux*resultSet.getDouble("comision_en_fecha"));
                                            preparedStatement2.executeUpdate();
                                        }
                                    
				} catch (EnumConstantNotPresentException e) {
					FachadaAplicacion.muestraExcepcion(e);
				}
			}
                        //FALTA ACTUALIZAR LOS SALDOS DE LOS USUARIOS Y PAGAR LAS COMISIONES
                        
                        //Attualizacion regulador
                        preparedStatement3 = getConexion()
					.prepareStatement("update usuario_regulador " +
                                                          "set saldo=?");
                        preparedStatement3.setDouble(1, Comision);
                        preparedStatement3.executeUpdate();
                        
                         //ACTUALIZAMOS EL SALDO DEL USUARIO QUE COMPRA
                         preparedStatement4 = getConexion()
					.prepareStatement("update usuario_mercado " +
                                                          "set saldo=saldo-? where id=?");
                         preparedStatement4.setDouble(1, saldoARestar);
                         preparedStatement4.setString(2, Usuario.getId());
                         preparedStatement4.executeUpdate();
                         
                         //Actualizamos el saldo de los usuarios que venden
                        for(int i=0;i<ids.size();i++){
                            preparedStatement5 = getConexion()
					.prepareStatement("update usuario_mercado " +
                                                          "set saldo=saldo+? where id=? ");
                         preparedStatement5.setDouble(1, sumaSaldos.get(i));
                         preparedStatement5.setString(2, ids.get(i));
                         preparedStatement5.executeUpdate();
                        }
                        
                        //Actualizar la tabla de tener_participaciones para los que venden
                        for(int i=0;i<ids.size();i++){
                                preparedStatement5 = getConexion()
					.prepareStatement("update tener_participaciones " +
                                                          "set num_participaciones=num_participaciones-? where id1=? and id2=?");
                            preparedStatement5.setInt(1,participacionesVendidas.get(i) );
                            preparedStatement5.setString(2, ids.get(i));
                            preparedStatement5.setString(3, empresa.getId());
                         preparedStatement5.executeUpdate();
                         
                        }
                        
                        
                                
                        //Actualizar tabla tener_participaciones para el comprador
                        preparedStatement6 = getConexion()
					.prepareStatement("select count(*) as numero from tener_participaciones where id1=? and id2=? ");
                            preparedStatement6.setString(1, Usuario.getId());
                            preparedStatement6.setString(2, empresa.getId());
                         resultSet2=preparedStatement6.executeQuery();
                         
                         Integer existe=0;
                         while(resultSet2.next()){
                             existe=resultSet2.getInt("numero");
                         }
                         if(existe==0){
                             preparedStatement6 = getConexion()
					.prepareStatement("insert into tener_participaciones values(?,?,?)");
                            preparedStatement6.setString(1, Usuario.getId());
                            preparedStatement6.setString(2, empresa.getId());
                            preparedStatement6.setInt(3, numCompradas);
                         preparedStatement6.executeUpdate();
                         }else{
                             preparedStatement6 = getConexion()
					.prepareStatement("update tener_participaciones set num_participaciones=num_participaciones+? where id1=? and id2=?");
                            preparedStatement6.setInt(1, numCompradas);
                            preparedStatement6.setString(2, Usuario.getId());
                            preparedStatement6.setString(3, empresa.getId());
                            preparedStatement6.executeUpdate();
                         
                         }
                        
                        c.commit();
		} catch (SQLException e) {
			FachadaAplicacion.muestraExcepcion(e);
		} finally {
			try {
				preparedStatement.close();
                                //preparedStatement2.close();
                                preparedStatement3.close();
                                preparedStatement4.close();
                                preparedStatement5.close();
                                preparedStatement6.close();
			} catch (SQLException e) {
				FachadaAplicacion.muestraExcepcion(e);
			}
		}
     }
     
     
     public Set<UsuarioEmpresa> empresasConAnuncios(){
         Connection c = startTransaction();
        Set<UsuarioEmpresa> ret=new HashSet<>();
	PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
	ResultSet resultSet,resultSet2;
        UsuarioEmpresa usuario=null;
		try {
			preparedStatement = getConexion()
					.prepareStatement("select distinct id2 from anuncio_venta");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				try {
                                    String id=resultSet.getString("id2");
                                    preparedStatement2 = getConexion()
					.prepareStatement("select * from usuario_empresa inner join usuario_mercado using(id) where id=? ");
                                preparedStatement2.setString(1,id);
                                    
			resultSet2 = preparedStatement2.executeQuery();
                                while(resultSet2.next()){
                                    
					String id2 = resultSet2.getString("id");
					String clave = resultSet2.getString("clave");
					double saldo = resultSet2.getDouble("saldo");
					String direccion = resultSet2.getString("direccion");
					String telefono = resultSet2.getString("telefono");
					EstadoUsuario estado = EstadoUsuario.getByName(resultSet2.getString("estado"));
					String cif = resultSet2.getString("cif");
					String nombreComercial = resultSet2.getString("nombre_comercial");
					double importeBloqueado = resultSet2.getDouble("importe_bloqueado");

					usuario = new UsuarioEmpresa(id2, clave, saldo, direccion, telefono, estado, cif, nombreComercial,
							importeBloqueado);
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
