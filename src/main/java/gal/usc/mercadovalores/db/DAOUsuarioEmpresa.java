package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;

public final class DAOUsuarioEmpresa extends DAO<UsuarioEmpresa> {

    public DAOUsuarioEmpresa(Connection con) {
        super(con);
    }

    @Override
    protected String getNombreTabla() {
        return "usuario_empresa";
    }

    @Override
    protected UsuarioEmpresa getTFromRS(ResultSet rs) throws SQLException {
        return new UsuarioEmpresa(null, null, 0, null, null, null, null, null, null, 0, null);
    }
}
