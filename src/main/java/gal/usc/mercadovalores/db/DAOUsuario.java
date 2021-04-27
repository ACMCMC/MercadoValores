package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.Usuario;

public class DAOUsuario extends DAO<Usuario> {

    public DAOUsuario(Connection con) {
        super(con);
    }

    public boolean comprobarContrasena(String contrasenaTextoPlano, String contrasenaEncriptada) {
        boolean resultado = false;
		Connection c = startTransaction();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = c
					.prepareStatement("select ?=crypt(?,?)");
			preparedStatement.setString(1, contrasenaEncriptada);
			preparedStatement.setString(2, contrasenaTextoPlano);
			preparedStatement.setString(3, contrasenaEncriptada);

			resultSet = preparedStatement.executeQuery();
            c.commit();
			if (resultSet.next()) {
				try {
					resultado = resultSet.getBoolean(1);
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

		return resultado;
    }

    public String getContrasenaEncriptada(String contrasenaTextoPlano) {
        String resultado = null;
		Connection c = startTransaction();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet;

		try {
			preparedStatement = c
					.prepareStatement("select crypt(?, gen_salt('bf')");
			preparedStatement.setString(1, contrasenaTextoPlano);

			resultSet = preparedStatement.executeQuery();
            c.commit();
			if (resultSet.next()) {
				try {
					resultado = resultSet.getString(1);
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

		return resultado;
    }
    
}
