    package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gal.usc.mercadovalores.aplicacion.UsuarioInversor;

public final class DAOUsuarioInversor extends DAO<UsuarioInversor> {

    public DAOUsuarioInversor(Connection con) {
        super(con);
    }

}
