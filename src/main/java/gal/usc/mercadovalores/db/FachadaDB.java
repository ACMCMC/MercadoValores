/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.db;

import gal.usc.mercadovalores.aplicacion.*;
import gal.usc.mercadovalores.gui.FachadaGUI;

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
            String url = "jdbc:" + gestor + "://" + configuracion.getProperty("servidor") + ":" + configuracion.getProperty("puerto") + "/" + configuracion.getProperty("baseDatos");
            this.conexion = java.sql.DriverManager.getConnection(url, usuario);
        } catch (FileNotFoundException f) {
            FachadaAplicacion.muestraExcepcion(f);
        } catch (IOException i) {
            FachadaAplicacion.muestraExcepcion(i);
        } catch (java.sql.SQLException e) {
            FachadaAplicacion.muestraExcepcion(e);
        }

        daoUsuarioEmpresa = new DAOUsuarioEmpresa(conexion);
        daoUsuarioInversor = new DAOUsuarioInversor(conexion);
    }

    public Set<UsuarioEmpresa> getUsuariosEmpresa() {
        return daoUsuarioEmpresa.getAll();
    }

    public Set<UsuarioInversor> getUsuariosInversores() {
        // Aquí se devuelve null
        return null;
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
    
    public Usuario obtenerUsuarioById(String id, String password){
        Usuario res = null;
        
        //si son de una empresa
        res = daoUsuarioEmpresa.getById(id);
        if(res!= null){
        
            //comprobamos que la clave coincida
            if(res.getClave().equals(password)){
                return res;
            }
        }
        
        //si el id y contraseña son de regulador 
        res = daoUsuarioRegulador.get();
        if(res != null){
            
            //comprobamos los datos
            if(res.getId().equals(id) && res.getClave().equals(password)){
                return res; 
            }
        }
        

        /*
        if(res = daoUsuarioInversor.getByid(id,password)){
            return res;
        }else{
            res = null;
            return res;
        }*/
        return res;
    }
}
