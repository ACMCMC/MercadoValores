/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;
import javax.swing.JFrame;

import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;
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

    private JFrame currentFrame;

    public void muestraExcepcion(Throwable t) {
        System.out.println(t.getMessage());
        VAviso aviso = new VAviso(currentFrame, true, t.getMessage());
        aviso.setVisible(true);
    }

    public FachadaGUI(FachadaAplicacion fa) {
        this.fa = fa;
        this.vi = new VInicio(this.fa);
        this.currentFrame = vi;
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
        this.currentFrame = vadmin;
    }

    public void iniciarInversor(UsuarioInversor usr) {
        // ya no necesitamos la ventana de inicio
        this.vi.dispose();
        // iniciamos la vista de usuario de mercado
        this.vinversor = new VPrincipalInversor(usr,fa);
        vinversor.setVisible(true);
        this.currentFrame = vinversor;
    }

    public void iniciarEmpresa(UsuarioEmpresa usr){
        //ya no necesitamos la ventana de inicio
        this.vi.dispose();
        //iniciamos la vista de empresa
        this.vempresa = new VPrincipalEmpresa(usr, fa);
        vempresa.setVisible(true);
        this.currentFrame = vempresa;
    }

    public void cerrarSesion(javax.swing.JFrame frame){
        frame.dispose();
        this.vi = new VInicio(this.fa);
        vi.setVisible(true);
        this.currentFrame = vi;
    }
    
    public void ventanaParticipaciones(UsuarioEmpresa usr){
        VGestionParticipacion vp = new VGestionParticipacion(usr);
        vp.setVisible(true);
    }
    
    public void ventanaSaldos(){
        VSaldo vS = new VSaldo(this.vadmin,true);
        vS.setVisible(true);
    }
    
    public void ventanaVender(UsuarioDeMercado usr){
        VVenta vV = new VVenta(usr);
        vV.setVisible(true);
    }
    
    public void ventanaAnuncios(UsuarioDeMercado usr){
        VAnunciosUsuario vAU = new VAnunciosUsuario(usr);
        vAU.setVisible(true);
    }
    
    public void ventanaBeneficios(UsuarioEmpresa usr){
        VBeneficios vB = new VBeneficios(usr, fa);
        vB.setVisible(true);
    }
    
    public void verBeneficios(){
        VBeneficiosAdmin vBA = new VBeneficiosAdmin();
        vBA.setVisible(true);
    }
    
}
