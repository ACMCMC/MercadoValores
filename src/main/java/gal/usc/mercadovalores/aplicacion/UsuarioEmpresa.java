package gal.usc.mercadovalores.aplicacion;

import java.util.Map;
import java.util.Set;

public final class UsuarioEmpresa extends UsuarioDeMercado {
    private String cif;
    private String nombreComercial;
    private double importeBloqueado;

    public UsuarioEmpresa(String id, String clave, double saldo, String direccion, String telefono, EstadoUsuario estado, String cif, String nombreComercial, double importeBloqueado) {
        super(id, clave, saldo, direccion, telefono, estado);
        this.cif = cif;
        this.nombreComercial = nombreComercial;
        this.importeBloqueado = importeBloqueado;
    }

    /**
     * @return the cif
     */
    public String getCif() {
        return cif;
    }

    /**
     * @return the importeBloqueado
     */
    public double getImporteBloqueado() {
        return importeBloqueado;
    }

    /**
     * @return the nombreComercial
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * @return the usuariosConParticipaciones
     */
    public Set<UsuarioDeMercado> getUsuariosConParticipaciones() {
        return null;
    }
}
