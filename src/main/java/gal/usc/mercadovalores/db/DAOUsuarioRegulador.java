package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
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
    
    public void update(UsuarioRegulador user){
        PreparedStatement preparedStatement = null;
        try {
			getConexion().setAutoCommit(false);
			preparedStatement = getConexion().prepareStatement(
					"update usuario_empresa set clave=?, saldo=?, comision_actual=? where id=?");
			preparedStatement.setString(1, user.getClave());
			preparedStatement.setDouble(2, user.getSaldo());
			preparedStatement.setDouble(3, user.getComision_actual());
			preparedStatement.setString(4, user.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
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
