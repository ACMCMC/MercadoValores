/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.aplicacion;

/**
 *
 * @author user
 */
public class UsuarioDeMercado {
    
    //tipos de datos temporales -> pendiente de diccionario de datos
    String id;
    String clave;
    int saldo;
    int importeBloqueado;
    String direccion;
    String telefono;
    int estado;
    
    //constructor
    
    //getters & setters

    public String getId() {
        return id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getImporteBloqueado() {
        return importeBloqueado;
    }

    public void setImporteBloqueado(int importeBloqueado) {
        this.importeBloqueado = importeBloqueado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}
