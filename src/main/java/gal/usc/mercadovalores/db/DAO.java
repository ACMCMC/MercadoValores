package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO<T> {

	private Connection conexion;

	public DAO(Connection con) {
		this.conexion = con;
	}

	protected Connection getConexion() {
		return this.conexion;
	}

	protected Connection startTransaction() {
		try {
			this.conexion.setAutoCommit(false);
			this.conexion.rollback();
		} catch (SQLException e) {
		}
		return this.conexion;
	}

}
