/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.aplicacion;
import gal.usc.mercadovalores.gui.FachadaGUI;

import gal.usc.mercadovalores.db.*;
import gal.usc.mercadovalores.gui.*;
import java.util.Set;

/**
 *
 * @author acmc
 */
public class FachadaAplicacion {
    
    private FachadaGUI fgui;

    public FachadaAplicacion() {
        this.fgui = new FachadaGUI(this);
    }

    public static void main(String args[]){
        FachadaAplicacion fa;
        fa= new FachadaAplicacion();
        fa.iniciarAplicacion();

        Set<UsuarioEmpresa> s = FachadaDB.getFachada().getUsuariosEmpresa();
    }

    public void iniciarAplicacion(){
        this.fgui.iniciarAplicacion();
    }

    public void iniciarSesion(){
        this.fgui.iniciarSesion();
    }
        
    public void registro(){
        this.fgui.registro();
    }


    public void iniciarAdmin(UsuarioRegulador usr){
        this.fgui.iniciarAdmin(usr);
    }

    public void iniciarInversor(UsuarioInversor usr){
        this.fgui.iniciarInversor(usr);
    }

    public void iniciarEmpresa(UsuarioEmpresa usr){
        this.fgui.iniciarEmpresa(usr);
    }

    public static void muestraExcepcion(Throwable t) {
        FachadaGUI.muestraExcepcion(t);
    }
    
    public void cerrarSesion(javax.swing.JFrame frame){
        this.fgui.cerrarSesion(frame);
    }
}
