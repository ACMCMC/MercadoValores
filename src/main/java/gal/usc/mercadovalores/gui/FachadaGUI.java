/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import gal.usc.mercadovalores.aplicacion.UsuarioInversor;
import gal.usc.mercadovalores.aplicacion.UsuarioRegulador;


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
    private VPrincipalEmpresa vempresa;

    public static void muestraExcepcion(Throwable t) {
        System.out.println(t.getMessage());
    }

    public FachadaGUI(FachadaAplicacion fa) {
        this.fa = fa;
        this.vi = new VInicio(this.fa);
    }

    // Mostramos ventana inicial de la aplicación
    public void iniciarAplicacion() {
        vi.setVisible(true);
    }

    // inicio de sesion
    public void iniciarSesion() {
        this.vaut = new VAutentificacion(vi, true, fa);
        vaut.setVisible(true);
    }
    
    public void registro(){
        VRegistro vreg = new VRegistro(vi, true, fa);
        vreg.setVisible(true);
    }


    // aplicacion de admin (regulador de mercado)
    public void iniciarAdmin(UsuarioRegulador usr) {
        // ya no necesitamos la ventana de inicio
        this.vi.dispose();
        // iniciamos la vista de administrador
        this.vadmin = new VPrincipalAdmin(usr, fa);
        vadmin.setVisible(true);
    }

    public void iniciarInversor(UsuarioInversor usr) {
        // ya no necesitamos la ventana de inicio
        this.vi.dispose();
        // iniciamos la vista de usuario de mercado
        this.vinversor = new VPrincipalInversor(usr,fa);
        vinversor.setVisible(true);
    }

    public void iniciarEmpresa(UsuarioEmpresa usr){
        //ya no necesitamos la ventana de inicio
        this.vi.dispose();
        //iniciamos la vista de empresa
        this.vempresa = new VPrincipalEmpresa(usr, fa);
        vempresa.setVisible(true);
    }

    public void cerrarSesion(javax.swing.JFrame frame){
        frame.dispose();
        this.vi = new VInicio(this.fa);
        vi.setVisible(true);
    }
    
    public void ventanaSaldos(){
        VSaldo vS = new VSaldo(this.vadmin,true);
        vS.setVisible(true);
    }
}
