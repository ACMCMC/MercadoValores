package gal.usc.mercadovalores.aplicacion;

import java.util.Map;
import java.util.Set;

/**
 * @author
 */
public final class UsuarioInversor extends UsuarioDeMercado {
    private String dni;
    private String nombre_completo;

<<<<<<< HEAD
    public UsuarioInversor(String id, String clave, double saldo, String direccion, String telefono, EstadoUsuario estado, String dni, String nombre_completo) {
        super(id, clave, saldo, direccion, telefono, estado);
=======
    public UsuarioInversor(String id, String clave, double saldo, String direccion, String telefono, EstadoUsuario estado, Map<UsuarioEmpresa, Integer> tenerParticipaciones, String dni, String nombre_completo, Set<UsuarioDeMercado> usuariosConParticipaciones) {
        super(id, clave, saldo, direccion, telefono, estado, tenerParticipaciones);
>>>>>>> aaron
        this.dni = dni;
        this.nombre_completo = nombre_completo;
    }
}
