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
					usuario = new UsuarioEmpresa(resultSet.getString("id"), resultSet.getString("clave"), resultSet.getDouble("saldo"), resultSet.getString("direccion"), resultSet.getString("telefono"), EstadoUsuario.getByName(resultSet.getString("estado")), resultSet.getString("cif"), resultSet.getString("nombre_comercial"), resultSet.getDouble("importe_bloqueado"));
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
}
