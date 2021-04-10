package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.UsuarioInversor;
import gal.usc.mercadovalores.aplicacion.UsuarioRegulador;

public final class DAOUsuarioRegulador extends DAO<UsuarioRegulador> {

    public DAOUsuarioRegulador(Connection con) {
        super(con);
    }

    public UsuarioRegulador get() {
        PreparedStatement preparedStatement = null;
		ResultSet resultSet;
        UsuarioRegulador regulador = null;

		try {
			preparedStatement = getConexion().prepareStatement("select * from usuario_regulador");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
                regulador = new UsuarioRegulador(resultSet.getString("id"), resultSet.getString("clave"), resultSet.getDouble("saldo"), resultSet.getDouble("comision_actual"));
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

        return regulador;
    }

}
