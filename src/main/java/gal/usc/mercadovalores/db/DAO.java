package gal.usc.mercadovalores.db;

import java.util.Set;
import java.util.function.Function;

import gal.usc.mercadovalores.aplicacion.*;
import gal.usc.mercadovalores.gui.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public abstract class DAO<T> {

	private Connection conexion;

	public DAO(Connection con) {
		this.conexion = con;
	}

	public Set<T> getAll() {
		Set<T> setFinal = new HashSet<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = conexion.prepareStatement("select * from ?");
			preparedStatement.setString(0, this.getNombreTabla());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				setFinal.add(this.getTFromRS(resultSet));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			FachadaAplicacion.muestraExcepcion(e);
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Imposible cerrar cursores");
			}
		}

		return setFinal;
	}

	protected abstract String getNombreTabla();

	protected abstract T getTFromRS(ResultSet rs) throws SQLException;
}
