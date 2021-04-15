package gal.usc.mercadovalores.aplicacion;

import java.util.Map;
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
    
    public String getNombreCompleto(){
        return this.nombre_completo;
    }
    
}