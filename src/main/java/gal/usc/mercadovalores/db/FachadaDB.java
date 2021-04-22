/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.db;

import gal.usc.mercadovalores.aplicacion.*;
import gal.usc.mercadovalores.gui.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author acmc
 */
public class FachadaDB {

    private static FachadaDB fachada = new FachadaDB();
    private Connection conexion;
    private DAOUsuarioEmpresa daoUsuarioEmpresa;
    private DAOUsuarioInversor daoUsuarioInversor;
    private DAOUsuarioRegulador daoUsuarioRegulador;

    public static FachadaDB getFachada() {
        return fachada;
    }

    private FachadaDB() {

        Properties configuracion = new Properties();
        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            usuario.setProperty("ssl", "true");
            String url = "jdbc:" + gestor + "://" + configuracion.getProperty("servidor") + ":"
                    + configuracion.getProperty("puerto") + "/" + configuracion.getProperty("baseDatos");
            this.conexion = java.sql.DriverManager.getConnection(url, usuario);
            this.conexion.setAutoCommit(false);
        } catch (FileNotFoundException f) {
            FachadaAplicacion.muestraExcepcion(f);
        } catch (IOException i) {
            FachadaAplicacion.muestraExcepcion(i);
        } catch (java.sql.SQLException e) {
            FachadaAplicacion.muestraExcepcion(e);
        }
        daoUsuarioRegulador = new DAOUsuarioRegulador(conexion);
        daoUsuarioEmpresa = new DAOUsuarioEmpresa(conexion);
        daoUsuarioInversor = new DAOUsuarioInversor(conexion);
    }

    public Set<UsuarioEmpresa> getUsuariosEmpresa() {
        return daoUsuarioEmpresa.getAll();
    }

    public Set<UsuarioInversor> getUsuariosInversores() {
        // Aquí se devuelve null
        return daoUsuarioInversor.getAll();
    }

    public Set<UsuarioDeMercado> getUsuariosDeMercado() {
        Set<UsuarioDeMercado> set = new HashSet<>();
        set.addAll(getUsuariosEmpresa());
        set.addAll(getUsuariosInversores());
        return set;
    }

    public Set<Usuario> getUsuarios() {
        Set<Usuario> set = new HashSet<>();
        set.addAll(getUsuariosDeMercado());
        set.add(getUsuarioRegulador());
        return set;
    }

    public UsuarioRegulador getUsuarioRegulador() {
        return daoUsuarioRegulador.get();
    }

    public Usuario getUsuarioById(String id) {
        Usuario res = null;

        // si son de una empresa
        res = daoUsuarioEmpresa.getById(id);
        if (res != null) {
            return res;
        }

        res = daoUsuarioInversor.getById(id);
        if (res != null) {
            return res;
        }

        // si el id y contraseña son de regulador
        res = daoUsuarioRegulador.get();
        if (res != null && res.getId().equals(id)) {
            return res;
        }
        return res;
    }

    public void add(Usuario u) {
        if (u instanceof UsuarioEmpresa) {
            daoUsuarioEmpresa.add((UsuarioEmpresa) u);
        } else if (u instanceof UsuarioDeMercado) {
            daoUsuarioInversor.add((UsuarioInversor) u);
        } else {
            throw new IllegalArgumentException("No se acepta el tipo de usuario seleccionado");
        }
    }
    
    public void  autorizarRegistro(UsuarioDeMercado u){
        if(u instanceof UsuarioEmpresa){
            //autorizamos registro usuario empresa
            daoUsuarioEmpresa.autorizarRegistro((UsuarioEmpresa) u);
        }else if(u instanceof UsuarioInversor){
            //autorizamos registro de usuario inversor
            daoUsuarioInversor.autorizarRegistro((UsuarioInversor) u);
        }
    }
    
    public void  autorizarBaja(UsuarioDeMercado u){
        if(u instanceof UsuarioEmpresa){
            //autorizamos la baja usuario empresa
            daoUsuarioEmpresa.delete((UsuarioEmpresa) u);
        }else if(u instanceof UsuarioInversor){
            //autorizamos la baja de usuario inversor
            daoUsuarioInversor.delete((UsuarioInversor) u);

        }
    }
    
    public void  solicitarBaja(UsuarioDeMercado u){
        if(u instanceof UsuarioEmpresa){                        
            //actualizamos el estado del usuario empresa
            daoUsuarioEmpresa.solicitarBaja((UsuarioEmpresa) u);
        }else if(u instanceof UsuarioInversor){
            //actualizamos el estado del usuario inversor
            daoUsuarioInversor.solicitarBaja((UsuarioInversor) u);
        }
    }
    
    public void actualizarUser(UsuarioDeMercado u){
        if(u instanceof UsuarioEmpresa){
            daoUsuarioEmpresa.update((UsuarioEmpresa) u);
        }else if(u instanceof UsuarioInversor){
            daoUsuarioInversor.update((UsuarioInversor) u);
        }
    }
    
    public void actualizarComision(UsuarioRegulador u){
        daoUsuarioRegulador.update(u);
    }
}