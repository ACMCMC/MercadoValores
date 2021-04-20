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
