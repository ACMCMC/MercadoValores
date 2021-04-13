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
    
    public void iniciarAdmin(){
        this.fgui.iniciarAdmin();
    }
    
    public void iniciarInversor(){
        this.fgui.iniciarInversor();
    }
    
    public void iniciarEmpresa(){
        this.fgui.iniciarEmpresa();
    }
    
    public static void muestraExcepcion(Throwable t) {
        FachadaGUI.muestraExcepcion(t);
    }
}
