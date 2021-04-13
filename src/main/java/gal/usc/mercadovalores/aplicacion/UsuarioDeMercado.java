/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.aplicacion;

import java.util.Map;

/**
 *
 * @author user
 */
public abstract class UsuarioDeMercado extends Usuario {
    
    //tipos de datos temporales -> pendiente de diccionario de datos
    //el resto de atributos se heredan de Usuario
    private String direccion;
    private String telefono;
    private EstadoUsuario estado;
    private Map<UsuarioEmpresa, Integer> tenerParticipaciones;

    public UsuarioDeMercado(String id, String clave, double saldo, String direccion, String telefono, EstadoUsuario estado) {
        super(id, clave, saldo);
        this.direccion = direccion;
        this.estado = estado;
        this.telefono = telefono;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @return the estado
     */
    public EstadoUsuario getEstado() {
        return estado;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @return the tenerParticipaciones
     */
    public Map<UsuarioEmpresa, Integer> getTenerParticipaciones() {
        return tenerParticipaciones;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @param tenerParticipaciones the tenerParticipaciones to set
     */
    public void setTenerParticipaciones(Map<UsuarioEmpresa, Integer> tenerParticipaciones) {
        this.tenerParticipaciones = tenerParticipaciones;
    }
}
