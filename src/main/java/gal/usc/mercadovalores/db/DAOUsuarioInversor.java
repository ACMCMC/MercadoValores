package gal.usc.mercadovalores.db;

import gal.usc.mercadovalores.aplicacion.EstadoUsuario;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gal.usc.mercadovalores.aplicacion.UsuarioInversor;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Set;
public final class DAOUsuarioInversor extends DAO<UsuarioInversor> {

    public DAOUsuarioInversor(Connection con) {
        super(con);
    }

    public UsuarioInversor getById(String idToGet) {
		UsuarioInversor usuario = null;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = getConexion()
					.prepareStatement("select * from usuario_inversor inner join usuario_mercado using(id) where id=?");
			preparedStatement.setString(1, idToGet);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				try {
					String id = resultSet.getString("id");
					String clave = resultSet.getString("clave");
					double saldo = resultSet.getDouble("saldo");
					String direccion = resultSet.getString("direccion");
					String telefono = resultSet.getString("telefono");
					EstadoUsuario estado = EstadoUsuario.getByName(resultSet.getString("estado"));
					String cif = resultSet.getString("dni");
					String nombreCompleto = resultSet.getString("nombre_completo");

					usuario = new UsuarioInversor(id, clave, saldo, direccion, telefono, estado, cif, nombreCompleto);
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

		return usuario;
	}
    
    public void add(UsuarioInversor user){
		PreparedStatement preparedStatement = null;

		try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"insert into usuario_mercado(clave, saldo, direccion, telefono, estado, id) values (?,?,?,?,CAST (? AS enum_estado),?)");
			preparedStatement.setString(1, user.getClave());
			preparedStatement.setDouble(2, user.getSaldo());
			preparedStatement.setString(3, user.getDireccion());
			preparedStatement.setString(4, user.getTelefono());
			preparedStatement.setString(5, user.getEstado().toString());
			preparedStatement.setString(6, user.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
                        
			preparedStatement = getConexion().prepareStatement(
					"insert into usuario_inversor(dni, nombre_completo, id) values (?,?,?)");
			preparedStatement.setString(1, user.getDni());
			preparedStatement.setString(2, user.getNombreCompleto());
			preparedStatement.setString(3, user.getId());
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
    
    public void update(UsuarioInversor user){
        
		PreparedStatement preparedStatement = null;

		try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"update usuario_inversor set dni=?, nombre_completo=? where id=?");
			preparedStatement.setString(1, user.getDni());
			preparedStatement.setString(2, user.getNombreCompleto());
			preparedStatement.setString(3, user.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = getConexion().prepareStatement(
					"update usuario_mercado set clave=?, saldo=?, direccion=?, telefono=?, estado=? where id=?");
			preparedStatement.setString(1, user.getClave());
			preparedStatement.setDouble(2, user.getSaldo());
			preparedStatement.setString(3, user.getDireccion());
			preparedStatement.setString(4, user.getTelefono());
			preparedStatement.setString(5, user.getEstado().toString());
			preparedStatement.setString(6, user.getId());
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
    
    
    public Set<UsuarioInversor> getAll() {
		Set<UsuarioInversor> setFinal = new HashSet<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = getConexion().prepareStatement("select * from usuario_inversor inner join usuario_mercado using(id)");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UsuarioInversor usuario;
				try {
					String id = resultSet.getString("id");
					String clave = resultSet.getString("clave");
					double saldo = resultSet.getDouble("saldo");
					String direccion = resultSet.getString("direccion");
					String telefono = resultSet.getString("telefono");
					EstadoUsuario estado = EstadoUsuario.getByName(resultSet.getString("estado"));
					String dni = resultSet.getString("dni");
					String nombreCompleto = resultSet.getString("nombre_completo");

					usuario = new UsuarioInversor(id, clave, saldo, direccion, telefono, estado, dni, nombreCompleto);
					setFinal.add(usuario);
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

		return setFinal;
	}
  
    
     public void delete(UsuarioInversor user){
                PreparedStatement preparedStatement = null;
                try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"delete from usuario_inversor where id=?");
			preparedStatement.setString(1, user.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = getConexion().prepareStatement(
					"delete from usuario_mercado where id=?");
			preparedStatement.setString(1, user.getId());
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

}
