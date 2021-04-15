package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.Set;
import java.util.HashSet;

import gal.usc.mercadovalores.aplicacion.EstadoUsuario;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;

public final class DAOUsuarioEmpresa extends DAO<UsuarioEmpresa> {

	public DAOUsuarioEmpresa(Connection con) {
		super(con);
	}

	public Set<UsuarioEmpresa> getAll() {
		Set<UsuarioEmpresa> setFinal = new HashSet<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = getConexion().prepareStatement("select * from usuario_empresa inner join usuario_mercado using(id)");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UsuarioEmpresa usuario;
				try {
					String id = resultSet.getString("id");
					String clave = resultSet.getString("clave");
					double saldo = resultSet.getDouble("saldo");
					String direccion = resultSet.getString("direccion");
					String telefono = resultSet.getString("telefono");
					EstadoUsuario estado = EstadoUsuario.getByName(resultSet.getString("estado"));
					String cif = resultSet.getString("cif");
					String nombreComercial = resultSet.getString("nombre_comercial");
					double importeBloqueado = resultSet.getDouble("importe_bloqueado");

					usuario = new UsuarioEmpresa(id, clave, saldo, direccion, telefono, estado, cif, nombreComercial,
							importeBloqueado);
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

	public UsuarioEmpresa getById(String idToGet) {
		UsuarioEmpresa usuario = null;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = getConexion()
					.prepareStatement("select * from usuario_empresa inner join usuario_mercado using(id) where id=?");
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
					String cif = resultSet.getString("cif");
					String nombreComercial = resultSet.getString("nombre_comercial");
					double importeBloqueado = resultSet.getDouble("importe_bloqueado");

					usuario = new UsuarioEmpresa(id, clave, saldo, direccion, telefono, estado, cif, nombreComercial,
							importeBloqueado);
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
        
        
        
	public void update(UsuarioEmpresa u) {
		PreparedStatement preparedStatement = null;

		try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"update usuario_empresa set cif=?, nombre_comercial=?, importe_bloqueado=? where id=?");
			preparedStatement.setString(1, u.getCif());
			preparedStatement.setString(2, u.getNombreComercial());
			preparedStatement.setDouble(3, u.getImporteBloqueado());
			preparedStatement.setString(4, u.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = getConexion().prepareStatement(
					"update usuario_mercado set clave=?, saldo=?, direccion=?, telefono=?, estado=? where id=?");
			preparedStatement.setString(1, u.getClave());
			preparedStatement.setDouble(2, u.getSaldo());
			preparedStatement.setString(3, u.getDireccion());
			preparedStatement.setString(4, u.getTelefono());
			preparedStatement.setString(5, u.getEstado().toString());
			preparedStatement.setString(6, u.getId());
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

	public void add(UsuarioEmpresa u) {
		PreparedStatement preparedStatement = null;

		try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"insert into usuario_mercado(clave, saldo, direccion, telefono, estado, id) values (?,?,?,?,?,?)");
			preparedStatement.setString(1, u.getClave());
			preparedStatement.setDouble(2, u.getSaldo());
			preparedStatement.setString(3, u.getDireccion());
			preparedStatement.setString(4, u.getTelefono());
			preparedStatement.setString(5, u.getEstado().toString());
			preparedStatement.setString(6, u.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = getConexion().prepareStatement(
					"insert into usuario_empresa(cif, nombre_comercial, importe_bloqueado, id) values (?,?,?,?)");
			preparedStatement.setString(1, u.getCif());
			preparedStatement.setString(2, u.getNombreComercial());
			preparedStatement.setDouble(3, u.getImporteBloqueado());
			preparedStatement.setString(4, u.getId());
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
        
        public void delete(UsuarioEmpresa user){
                PreparedStatement preparedStatement = null;
                try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"delete from usuario_empresa where id=?");
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
