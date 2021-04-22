package gal.usc.mercadovalores.aplicacion;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author
 */
public final class UsuarioInversor extends UsuarioDeMercado {
    private String dni;
    private String nombre_completo;

    public UsuarioInversor(String id, String clave, double saldo, String direccion, String telefono, EstadoUsuario estado, String dni, String nombre_completo) {
        super(id, clave, saldo, direccion, telefono, estado);
        this.dni = dni;
        this.nombre_completo = nombre_completo;
    }

    public String getDni(){
        return this.dni;
    }
    
    public void setDni(String dni){
        this.dni = dni;
    }

    public String getNombreCompleto(){
        return this.nombre_completo;
    }

    public void setNombreCompleto(String nombre_completo) {
        this.nombre_completo = nombre_completo;
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
        final UsuarioInversor other = (UsuarioInversor) obj;
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }
        return true;
    }
    
    
    
}
