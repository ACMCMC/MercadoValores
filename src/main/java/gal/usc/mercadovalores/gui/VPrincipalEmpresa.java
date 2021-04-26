/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.Usuario;
import gal.usc.mercadovalores.aplicacion.UsuarioInversor;
import gal.usc.mercadovalores.aplicacion.UsuarioRegulador;
import gal.usc.mercadovalores.db.FachadaDB;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author icaro
 */
public class VPrincipalEmpresa extends javax.swing.JFrame {
    private UsuarioEmpresa usr;
    private FachadaAplicacion fa;
    private boolean modificando;
    /**
     * Creates new form VPrincipalMercado
     * @param usr
     */
    public VPrincipalEmpresa(UsuarioEmpresa usr, FachadaAplicacion fa) {
        initComponents();
        this.usr = usr;
        this.ActualizarTablaDatos();
        this.fa = fa;
        this.botonActualizar.setEnabled(this.modificando);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        SalirBoton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        BotonCerrarSesion = new javax.swing.JButton();
        botonActualizar = new javax.swing.JButton();
        Menu = new javax.swing.JMenuBar();
        CuentaMenu = new javax.swing.JMenu();
        ModificarMenuItem = new javax.swing.JMenuItem();
        BajaMenuItem = new javax.swing.JMenuItem();
        ParticipacionesMenu = new javax.swing.JMenu();
        GestionParticipacionMenuItem = new javax.swing.JMenuItem();
        VenderMenuItem = new javax.swing.JMenuItem();
        ComprarMenuItem = new javax.swing.JMenuItem();
        BajaVentaMenuItem = new javax.swing.JMenuItem();
        BeneficiosMenu = new javax.swing.JMenu();
        AltaPagoMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ventana  Empresa");
        setResizable(false);

        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"ID", ""},
                {"Saldo", null},
                {"Direccion", null},
                {"Telefono", null},
                {"CIF", null},
                {"Nombre Comercial", null},
                {"Importe Bloqueado", null},
                {"Participaciones ", null},
                {"Participaciones creadas", null},
                {"Clave", null}
            },
            new String [] {
                "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaDatos.setAutoscrolls(false);
        TablaDatos.setCellSelectionEnabled(true);
        TablaDatos.setTableHeader(null);
        jScrollPane2.setViewportView(TablaDatos);

        SalirBoton.setText("Salir");
        SalirBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirBotonActionPerformed(evt);
            }
        });

        jLabel1.setText("Información de la empresa:");

        BotonCerrarSesion.setText("Cerrar Sesión");
        BotonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCerrarSesionActionPerformed(evt);
            }
        });

        botonActualizar.setText("Actualizar");
        botonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarActionPerformed(evt);
            }
        });

        CuentaMenu.setText("Cuenta");

        ModificarMenuItem.setText("Modificar Datos");
        ModificarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarMenuItemActionPerformed(evt);
            }
        });
        CuentaMenu.add(ModificarMenuItem);

        BajaMenuItem.setText("Solicitar Baja");
        BajaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BajaMenuItemActionPerformed(evt);
            }
        });
        CuentaMenu.add(BajaMenuItem);

        Menu.add(CuentaMenu);

        ParticipacionesMenu.setText("Participaciones");

        GestionParticipacionMenuItem.setText("Dar de alta / baja");
        GestionParticipacionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GestionParticipacionMenuItemActionPerformed(evt);
            }
        });
        ParticipacionesMenu.add(GestionParticipacionMenuItem);

        VenderMenuItem.setText("Vender");
        VenderMenuItem.setToolTipText("");
        VenderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VenderMenuItemActionPerformed(evt);
            }
        });
        ParticipacionesMenu.add(VenderMenuItem);

        ComprarMenuItem.setText("Comprar");
        ParticipacionesMenu.add(ComprarMenuItem);

        BajaVentaMenuItem.setText("Dar de baja anuncio");
        BajaVentaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BajaVentaMenuItemActionPerformed(evt);
            }
        });
        ParticipacionesMenu.add(BajaVentaMenuItem);

        Menu.add(ParticipacionesMenu);

        BeneficiosMenu.setText("Beneficios");

        AltaPagoMenuItem.setText("Altas y pagos");
        AltaPagoMenuItem.setToolTipText("");
        AltaPagoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AltaPagoMenuItemActionPerformed(evt);
            }
        });
        BeneficiosMenu.add(AltaPagoMenuItem);

        Menu.add(BeneficiosMenu);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BotonCerrarSesion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SalirBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonActualizar))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonActualizar)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SalirBoton)
                    .addComponent(BotonCerrarSesion))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GestionParticipacionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GestionParticipacionMenuItemActionPerformed
        // TODO add your handling code here:
        VGestionParticipacion vp = new VGestionParticipacion(usr);
        vp.setVisible(true);
    }//GEN-LAST:event_GestionParticipacionMenuItemActionPerformed

    //salimos de la aplicacion
    private void SalirBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirBotonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_SalirBotonActionPerformed

    private void BotonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCerrarSesionActionPerformed
        this.fa.cerrarSesion(this);
    }//GEN-LAST:event_BotonCerrarSesionActionPerformed

    private void BajaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BajaMenuItemActionPerformed
        FachadaDB.getFachada().solicitarBaja(this.usr);
    }//GEN-LAST:event_BajaMenuItemActionPerformed

    private void botonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarActionPerformed
        // TODO add your handling code here:
        
        //comprobar que ni el nombre ni la contraseña esten vacios
        String idCheck = (String) this.TablaDatos.getValueAt(0, 1);
        String passCheck = (String) this.TablaDatos.getValueAt(9, 1);
        if(!idCheck.isEmpty() && !passCheck.isEmpty()){
            
            String idActual = this.usr.getId();
            this.usr.setId((String) this.TablaDatos.getValueAt(0, 1));
            this.usr.setDireccion((String) this.TablaDatos.getValueAt(2, 1));
            this.usr.setTelefono((String) this.TablaDatos.getValueAt(3, 1));
            this.usr.setCif((String) this.TablaDatos.getValueAt(4, 1));
            this.usr.setNombreComercial((String) this.TablaDatos.getValueAt(5,1));

            Usuario res;
            res = FachadaDB.getFachada().getUsuarioById(this.usr.getId());

            if(res != null && res.getId().equals(idActual)){
                FachadaDB.getFachada().actualizarUser(this.usr);
            }else if(res != null ){
                VAviso x = new VAviso(this,true,"Nombre de usuario ya en uso, por favor elige otro.");
                x.setVisible(true);
                this.usr.setId(idActual);
            }else{
                try{
                    FachadaDB.getFachada().add(this.usr);
                }catch(Exception e){
                    VAviso x = new VAviso(this,true,e.getMessage());
                    x.setVisible(true);
                }
            }


        }else{
            VAviso x = new VAviso(this,true,"Los campos deben estar completos");
            x.setVisible(true);
        }
        this.ActualizarTablaDatos();

        
    }//GEN-LAST:event_botonActualizarActionPerformed

    private void ModificarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarMenuItemActionPerformed
        //Permite modificar los datos->
        this.modificando = !this.modificando;
        this.botonActualizar.setEnabled(this.modificando);

    }//GEN-LAST:event_ModificarMenuItemActionPerformed

    private void VenderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VenderMenuItemActionPerformed
        this.fa.ventanaVender(this.usr);
    }//GEN-LAST:event_VenderMenuItemActionPerformed

    private void AltaPagoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AltaPagoMenuItemActionPerformed

        this.fa.ventanaBeneficios(this.usr);
        
    }//GEN-LAST:event_AltaPagoMenuItemActionPerformed

    private void BajaVentaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BajaVentaMenuItemActionPerformed
        this.fa.ventanaAnuncios(this.usr);
    }//GEN-LAST:event_BajaVentaMenuItemActionPerformed

    private void ActualizarTablaDatos(){
        this.TablaDatos.setValueAt(usr.getId(), 0, 1);
        this.TablaDatos.setValueAt(usr.getSaldo(), 1, 1);
        this.TablaDatos.setValueAt(usr.getDireccion(), 2, 1);
        this.TablaDatos.setValueAt(usr.getTelefono(), 3, 1);
        this.TablaDatos.setValueAt(usr.getCif(), 4, 1);
        this.TablaDatos.setValueAt(usr.getNombreComercial(), 5, 1);
        this.TablaDatos.setValueAt(usr.getImporteBloqueado(), 6, 1);
        this.TablaDatos.setValueAt(usr.getClave(), 9, 1);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AltaPagoMenuItem;
    private javax.swing.JMenuItem BajaMenuItem;
    private javax.swing.JMenuItem BajaVentaMenuItem;
    private javax.swing.JMenu BeneficiosMenu;
    private javax.swing.JButton BotonCerrarSesion;
    private javax.swing.JMenuItem ComprarMenuItem;
    private javax.swing.JMenu CuentaMenu;
    private javax.swing.JMenuItem GestionParticipacionMenuItem;
    private javax.swing.JMenuBar Menu;
    private javax.swing.JMenuItem ModificarMenuItem;
    private javax.swing.JMenu ParticipacionesMenu;
    private javax.swing.JButton SalirBoton;
    private javax.swing.JTable TablaDatos;
    private javax.swing.JMenuItem VenderMenuItem;
    private javax.swing.JButton botonActualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
