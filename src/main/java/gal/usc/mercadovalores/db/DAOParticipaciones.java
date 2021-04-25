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
                            preparedStatement = getConexion().prepareStatement(
                       			"update usuario_empresa set importe_bloqueado=? where id=?");
                            preparedStatement.setDouble(1, this.numeroParticipacionesTotales(u)*this.getImportePorParticipacion(u));
                            preparedStatement.setString(2, u.getId());
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
    
    public double getImportePorParticipacion(UsuarioEmpresa u) throws SQLException{
        double ret=0;
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
			getConexion().setAutoCommit(false);
                            preparedStatement = getConexion().prepareStatement(
                       			"select importe_por_participacion from beneficios where id=?");
                            preparedStatement.setString(1, u.getId());
                        resultSet=preparedStatement.executeQuery();
                        while (resultSet.next()) {//Si la consulta devuelve 0 tuplas no entra aqui y se devuelve 0
				try {
                                        ret=resultSet.getDouble("importe_por_participacion");
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
    
    public int numeroParticipacionesTotales(UsuarioEmpresa u) throws SQLException{
        int ret=0;
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
			getConexion().setAutoCommit(false);
                            preparedStatement = getConexion().prepareStatement(
                       			"select sum(num_participaciones)from tener_participaciones where id2=?");
                            preparedStatement.setString(1, u.getId());
                        resultSet=preparedStatement.executeQuery();
                        while (resultSet.next()) {//Si la consulta devuelve 0 tuplas no entra aqui y se devuelve 0
				try {
                                        ret=resultSet.getInt(1);
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
    
    public void bajaParticipaciones(UsuarioEmpresa u,Integer x) throws SQLException{
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        int participaciones=0;

		try {
			getConexion().setAutoCommit(false);
                        participaciones=tenerParticipaciones(u,u);
                        if(participaciones-x>0){
                            preparedStatement = getConexion().prepareStatement(
                       			"update tener_participaciones set num_participaciones=? where id1=? and id2=?");
                            preparedStatement.setInt(1, participaciones-x);
                            preparedStatement.setString(2, u.getId());
                            preparedStatement.setString(3, u.getId());
                        }else{
                            preparedStatement = getConexion().prepareStatement(
                       			"delete from tener_participaciones where id1=? and id2=?");
                            preparedStatement.setString(1, u.getId());
                            preparedStatement.setString(2, u.getId());
                            
                        }
                        preparedStatement.executeUpdate();
                            preparedStatement = getConexion().prepareStatement(
                       			"update usuario_empresa set importe_bloqueado=? where id=?");
                            preparedStatement.setDouble(1, this.numeroParticipacionesTotales(u)*this.getImportePorParticipacion(u));
                            preparedStatement.setString(2, u.getId());
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
                        preparedStatement.setString(1, u.getId());
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
    
    public void altaBeneficios(UsuarioEmpresa u,double porcentaje,Timestamp fecha){
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        try {
			preparedStatement = getConexion()
					.prepareStatement("insert into beneficios values(?,?,?)");
                        preparedStatement.setString(1, u.getId());
                        preparedStatement.setTimestamp(2, fecha);
                        preparedStatement.setDouble(3, porcentaje);
                        preparedStatement.executeUpdate();
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
    }
    
    public void BajaBeneficios(UsuarioEmpresa u){
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        
        try {
			preparedStatement = getConexion()
					.prepareStatement("delete from beneficios where id=?");
                        preparedStatement.setString(1, u.getId());
                        preparedStatement.executeUpdate();
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
    }
    
    public void pagoBeneficios(UsuarioEmpresa u){
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        ResultSet resultSet;
        try {
                        
			preparedStatement = getConexion()
					.prepareStatement("select id1 from tener_participaciones where id2=?");
                        preparedStatement.setString(1, u.getId());
                        resultSet=preparedStatement.executeQuery();
                        while(resultSet.next()){
                            String id=resultSet.getString("id1");
                            preparedStatement2 = getConexion()
					.prepareStatement("update usuario_mercado set saldo=? where id=?");
                            preparedStatement2.setDouble(1, calcularBeneficioUsuario(id,u));
                            preparedStatement2.setString(2, id);
                            preparedStatement2.executeUpdate();
                            
                            
                        }
                         getConexion().commit();
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
    }
    
    public double calcularBeneficioUsuario(String u,UsuarioEmpresa u2){//FUNCION AUXILIAR PARA PGAO BENEFICIOS
        double ret=0.0;
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        ResultSet resultSet;
        ResultSet resultSet2;
        ResultSet resultSet3;
        try {
                        
			preparedStatement = getConexion()
					.prepareStatement("select num_participaciones from tener_participaciones where id1=? and id2=?");
                        preparedStatement.setString(1, u);
                        preparedStatement.setString(2, u2.getId());
                        resultSet=preparedStatement.executeQuery();
                        while(resultSet.next()){
                            Integer num=resultSet.getInt("num_participaciones");
                            preparedStatement2 = getConexion()
					.prepareStatement("select importe_por_participacion from beneficios where id=?");
                            
                            preparedStatement2.setString(1, u2.getId());
                            resultSet2=preparedStatement2.executeQuery();
                            while(resultSet2.next()){
                                double aux=resultSet2.getDouble("importe_por_participacion");
                                ret=aux*num;
                            }
                            preparedStatement3 = getConexion()
					.prepareStatement("select saldo from usuario_mercado where id=?");
                            
                            preparedStatement3.setString(1, u);
                            resultSet3=preparedStatement3.executeQuery();
                             while(resultSet3.next()){
                                double aux=resultSet3.getDouble("saldo");
                                ret+=aux;
                            }
                            
                        }
                         getConexion().commit();
		} catch (SQLException e) {
			FachadaAplicacion.muestraExcepcion(e);
		} finally {
			try {
				preparedStatement.close();
                                preparedStatement2.close();
                                preparedStatement3.close();
			} catch (SQLException e) {
				FachadaAplicacion.muestraExcepcion(e);
			}
		}
        return ret;
    }
    
    
}
