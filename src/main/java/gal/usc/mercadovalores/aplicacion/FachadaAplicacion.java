/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.aplicacion;
import gal.usc.mercadovalores.gui.FachadaGUI;

/**
 *
 * @author acmc
 */
public class FachadaAplicacion {
    
    private FachadaGUI fgui;

    private static FachadaAplicacion singleton = new FachadaAplicacion();

    public static FachadaAplicacion getFachada() {
        return singleton;
    }

    public FachadaAplicacion() {
        this.fgui = new FachadaGUI(this);
    }

    public static void main(String args[]){
        FachadaAplicacion.getFachada().iniciarAplicacion();
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
        FachadaAplicacion.getFachada().fgui.muestraExcepcion(t);
    }
    
    public void cerrarSesion(javax.swing.JFrame frame){
        this.fgui.cerrarSesion(frame);
    }

    public void ventanaParticipaciones(UsuarioEmpresa usr){
        this.fgui.ventanaParticipaciones(usr);
    }
    
    public void ventanaSaldos(){
        this.fgui.ventanaSaldos();
    }

    public void ventanaVender(UsuarioDeMercado usr){
        this.fgui.ventanaVender(usr);
    }
    
    public void ventanaAnuncios(UsuarioDeMercado usr){
        this.fgui.ventanaAnuncios(usr);
    }
    
    public void ventanaBeneficios(UsuarioEmpresa usr){
        this.fgui.ventanaBeneficios(usr);
    }
    
    public void ventanaCompras(UsuarioDeMercado usr){
        this.fgui.ventanaCompras(usr);
    }
    
    public void verBeneficios(){
        this.fgui.verBeneficios();
    }
    
    public void mostrarCompra(javax.swing.JFrame frame, Compra c){
        this.fgui.mostrarCompra(frame,c);
    }
}
