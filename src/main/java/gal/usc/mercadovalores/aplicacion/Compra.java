package gal.usc.mercadovalores.aplicacion;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Compra {
    private Set<ParteCompra> partes;
    private int id;
    private Timestamp fecha;
    private UsuarioEmpresa empresa;
    private UsuarioDeMercado comprador;

    public Compra(Set<ParteCompra> partes, int id, Timestamp fecha, UsuarioEmpresa empresa, UsuarioDeMercado comprador) {
        this.partes = partes;
        this.id = id;
        this.fecha = fecha;
        this.empresa = empresa;
        this.comprador = comprador;
    }

    

    @Override
    public String toString() {
        return "Compra{" + "id=" + id + ", fecha=" + fecha + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.partes);
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.fecha);
        hash = 97 * hash + Objects.hashCode(this.empresa);
        hash = 97 * hash + Objects.hashCode(this.comprador);
        return hash;
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
        final Compra other = (Compra) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.partes, other.partes)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (!Objects.equals(this.comprador, other.comprador)) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public UsuarioEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(UsuarioEmpresa empresa) {
        this.empresa = empresa;
    }

    public UsuarioDeMercado getComprador() {
        return comprador;
    }

    public void setComprador(UsuarioDeMercado comprador) {
        this.comprador = comprador;
    }

    public Set<ParteCompra> getPartes() {
        return partes;
    }

    public void setPartes(Set<ParteCompra> partes) {
        this.partes = partes;
    }
    
    
}
