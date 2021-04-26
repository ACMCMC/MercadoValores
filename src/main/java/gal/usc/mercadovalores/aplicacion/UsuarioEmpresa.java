package gal.usc.mercadovalores.aplicacion;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import gal.usc.mercadovalores.db.FachadaDB;

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
    public Set<Participacion> getUsuariosConParticipaciones() {
        return FachadaDB.getFachada().getParticipacionesEmpresa(this);
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public void setImporteBloqueado(double importeBloqueado) {
        this.importeBloqueado = importeBloqueado;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioEmpresa other = (UsuarioEmpresa) obj;
        if (!Objects.equals(this.cif, other.cif)) {
            return false;
        }
        return true;
    }
    
    
    
}
