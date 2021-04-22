package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.util.Set;

import gal.usc.mercadovalores.aplicacion.Participacion;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;

public class DAOParticipaciones extends DAO<Participacion> {
    public DAOParticipaciones(Connection con) {
		super(con);
	}

    public Set<Participacion> getParticipacionesUsuarioDeMercado(UsuarioDeMercado u) {

    }
    public Set<Participacion> getParticipacionesUsuarioEmpresa(UsuarioEmpresa u) {

    }
}
