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
			preparedStatement = getConexion()
					.prepareStatement("select * from usuario_empresa inner join usuario_mercado using(id)");
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


}
