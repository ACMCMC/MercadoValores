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

public class DAOParticipaciones extends DAO<Participacion> {
    public DAOParticipaciones(Connection con) {
		super(con);
	}

    
    public void crearParticipaciones(UsuarioEmpresa u,Integer x) throws SQLException{
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        int participaciones=0;

		try {
			getConexion().setAutoCommit(false);
                        participaciones=tenerParticipaciones(u,u);
                        if(participaciones!=0){
                            preparedStatement = getConexion().prepareStatement(
                       			"update tener_participaciones set num_participaciones=? where id1=? and id2=?");
                            preparedStatement.setInt(1, x+participaciones);
                            preparedStatement.setString(2, u.getId());
                            preparedStatement.setString(3, u.getId());
                        }else{
                            preparedStatement = getConexion().prepareStatement(
                       			"insert into tener_participaciones values(?,?,?)");
                            preparedStatement.setString(1, u.getId());
                            preparedStatement.setString(2, u.getId());
                            preparedStatement.setInt(3, x);
                            
                        }
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
    
    public Integer tenerParticipaciones(UsuarioDeMercado u1,UsuarioEmpresa u2) throws SQLException{
        
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        Integer ret=0;
        try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"select num_participaciones from tener_participaciones where id1=? and id2=?");
                        preparedStatement.setString(1, u1.getId());
                        preparedStatement.setString(2, u2.getId());
			resultSet = preparedStatement.executeQuery();
                        
                        while (resultSet.next()) {//Si la consulta devuelve 0 tuplas no entra aqui y se devuelve 0
				try {
                                        ret=resultSet.getInt("num_participaciones");
				} catch (EnumConstantNotPresentException e) {
					FachadaAplicacion.muestraExcepcion(e);
				}
			}
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
        return ret;
    }
    
    public void venderParticipaciones(Participacion p,UsuarioDeMercado u,Integer x,Timestamp fechapublicacion) throws SQLException{//El que vende, elque compra , cuanto se vende
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        FachadaDB f=FachadaDB.getFachada();
        int participaciones=0;

		try {
			getConexion().setAutoCommit(false);
                        participaciones=tenerParticipaciones(u,p.getEmpresa());//Nº Participaciones que tiene el usuario que compra
                        if(participaciones!=0){
                            preparedStatement = getConexion().prepareStatement(//Le sumamos al numero al que compra
                       			"update tener_participaciones set num_participaciones=? where id1=? and id2=?");
                            preparedStatement.setInt(1, x+participaciones);
                            preparedStatement.setString(2, u.getId());
                            preparedStatement.setString(3, p.getEmpresa().getId());
                            preparedStatement.executeUpdate();
                            
                            if(p.getNumero()-x!=0){//Si no es cero esta resta se actualiza el valor si no se elimina de la tabla
                                preparedStatement = getConexion().prepareStatement(//Le restamos al numero al que vende
                                        	"update tener_participaciones set num_participaciones=? where id1=? and id2=?");
                                preparedStatement.setInt(1, p.getNumero()-x);
                                preparedStatement.setString(2, p.getUsuarioMercado().getId());
                                preparedStatement.setString(3, p.getEmpresa().getId());
                                preparedStatement.executeUpdate();
                                
                            }else{
                                preparedStatement = getConexion().prepareStatement(//Le restamos al numero al que vende
                                        	"delete from tener_participaciones where id1=? and id2=?");
                                preparedStatement.setString(1, p.getUsuarioMercado().getId());
                                preparedStatement.setString(2, p.getEmpresa().getId());
                                preparedStatement.executeUpdate();
                            }
                        }else{
                             preparedStatement = getConexion().prepareStatement(//Añadimos la tupla a la tabla
                       			"insert into tener_participaciones values (?,?,?)");
                            preparedStatement.setString(1, u.getId());
                            preparedStatement.setString(2, p.getEmpresa().getId());
                            preparedStatement.setInt(3, x);
                            preparedStatement.executeUpdate();
                            
                            if(p.getNumero()-x!=0){//Si no es cero esta resta se actualiza el valor si no se elimina de la tabla
                                preparedStatement = getConexion().prepareStatement(//Le restamos al numero al que vende
                                        	"update tener_participaciones set num_participaciones=? where id1=? and id2=?");
                                preparedStatement.setInt(1, p.getNumero()-x);
                                preparedStatement.setString(2, p.getUsuarioMercado().getId());
                                preparedStatement.setString(3, p.getEmpresa().getId());
                                preparedStatement.executeUpdate();
                            }else{
                                preparedStatement = getConexion().prepareStatement(//Le restamos al numero al que vende
                                        	"delete from tener_participaciones where id1=? and id2=?");
                                preparedStatement.setString(1, p.getUsuarioMercado().getId());
                                preparedStatement.setString(2, p.getEmpresa().getId());
                                preparedStatement.executeUpdate();
                            }
                            
                        }
                        
                        f.confirmarVenta(p.getUsuarioMercado().getId(), p.getEmpresa().getId(), fechapublicacion);
                        
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

    
    public Set<Participacion> getAll() {
                FachadaDB f=FachadaDB.getFachada();
		Connection c = startTransaction();
		Set<Participacion> setFinal = new HashSet<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = getConexion()
					.prepareStatement("select * from tener_participaciones");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Participacion p;
				try {
					String id = resultSet.getString("id1");
                                        String id2 = resultSet.getString("id2");
                                        Integer numero = resultSet.getInt("num_participaciones");

					p = new Participacion((UsuarioDeMercado)f.getUsuarioById(id), (UsuarioEmpresa)f.getUsuarioById(id2), numero);
					setFinal.add(p);
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
    
    public Set<Participacion> getAllUsuarioMercado(UsuarioDeMercado u) {
                FachadaDB f=FachadaDB.getFachada();
		Connection c = startTransaction();
		Set<Participacion> setFinal = new HashSet<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = getConexion()
					.prepareStatement("select * from tener_participaciones where id1=?");
                        preparedStatement.setString(0, u.getId());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Participacion p;
				try {
                                        String id2 = resultSet.getString("id2");
                                        Integer numero = resultSet.getInt("num_participaciones");

					p = new Participacion((UsuarioDeMercado)u, (UsuarioEmpresa)f.getUsuarioById(id2), numero);
					setFinal.add(p);
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

}
