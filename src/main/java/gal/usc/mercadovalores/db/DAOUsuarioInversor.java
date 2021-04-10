package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gal.usc.mercadovalores.aplicacion.UsuarioInversor;

public final class DAOUsuarioInversor extends DAO<UsuarioInversor> {

    public DAOUsuarioInversor(Connection con) {
        super(con);
    }

    @Override
    protected String getNombreTabla() {
        return "usuario_inversor";
    }

    @Override
    protected UsuarioInversor getTFromRS(ResultSet rs) throws SQLException {
        return new UsuarioInversor(null, null, 0, null, null, null, null, null, null, null);
    }
}
