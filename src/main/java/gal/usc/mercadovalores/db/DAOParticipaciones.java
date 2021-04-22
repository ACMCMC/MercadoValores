package gal.usc.mercadovalores.db;

import java.sql.Connection;

import gal.usc.mercadovalores.aplicacion.Participacion;

public class DAOParticipaciones extends DAO<Participacion> {
    public DAOParticipaciones(Connection con) {
		super(con);
	}
}
