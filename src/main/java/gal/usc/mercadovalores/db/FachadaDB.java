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
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author acmc
 */
public class FachadaDB {

    private static FachadaDB fachada = new FachadaDB();
    private Connection conexion;

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
            this.conexion = java.sql.DriverManager.getConnection(
                    "jdbc:" + gestor + "://" + configuracion.getProperty("servidor") + ":"
                            + configuracion.getProperty("puerto") + "/" + configuracion.getProperty("baseDatos"),
                    usuario);

        } catch (FileNotFoundException f) {
            FachadaAplicacion.muestraExcepcion(f);
        } catch (IOException i) {
            FachadaAplicacion.muestraExcepcion(i);
        } catch (java.sql.SQLException e) {
            FachadaAplicacion.muestraExcepcion(e);
        }
    }

    public Set<UsuarioEmpresa> getUsuariosEmpresa() {
        return null;
    }

    public Set<UsuarioInversor> getUsuariosInversores() {
        // Aquí se devuelve null
        return null;
    }

    public Set<UsuarioDeMercado> getUsuariosDeMercado() {
        return null;
    }

    public Set<Usuario> getUsuarios() {
        return null;
    }

    public UsuarioRegulador getUsuarioRegulador() {
        return null;
    }
}
