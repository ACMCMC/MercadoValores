/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.aplicacion;
import gal.usc.mercadovalores.gui.FachadaGUI;

import gal.usc.mercadovalores.db.FachadaDB;
import gal.usc.mercadovalores.gui.FachadaGUI;

/**
 *
 * @author acmc
 * uwu
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

    public static void muestraExcepcion(Throwable t) {
        FachadaGUI.muestraExcepcion(t);
    }
}
