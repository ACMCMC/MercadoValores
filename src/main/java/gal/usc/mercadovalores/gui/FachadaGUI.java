/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;


/**
 *
 * @author acmc
 */
public class FachadaGUI {
    private FachadaAplicacion fa;
    private VInicio vi;
    private VAutentificacion vaut;
    private VPrincipalAdmin vadmin;
    private VPrincipalInversor vinversor;



    public FachadaGUI(FachadaAplicacion fa){
        this.fa = fa;
        this.vi = new VInicio(this.fa);
    } 
    
    //mostramos ventana inicial de la aplicación
    public void iniciarAplicacion(){
        vi.setVisible(true);
    }
    
    //inicio de sesion
    public void iniciarSesion(){
        this.vaut = new VAutentificacion(vi,true,fa);
        vaut.setVisible(true);
    }
    
    //aplicacion de admin (regulador de mercado)
    public void iniciarAdmin(){
        //ya no necesitamos la ventana de inicio 
        this.vi.dispose();
        //iniciamos la vista de administrador
        this.vadmin = new VPrincipalAdmin();
        vadmin.setVisible(true);
    }
    
    public void iniciarInversor(){
        //ya no necesitamos la ventana de inicio
        this.vi.dispose();
        //iniciamos la vista de usuario de mercado
        this.vinversor = new VPrincipalInversor();
        vinversor.setVisible(true);
    }
}
