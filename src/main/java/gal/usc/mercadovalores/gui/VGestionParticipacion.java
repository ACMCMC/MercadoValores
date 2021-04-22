/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;

import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import gal.usc.mercadovalores.db.FachadaDB;
import java.sql.SQLException;

/**
 *
 * @author icaro
 */
public class VGestionParticipacion extends javax.swing.JFrame {

    private UsuarioEmpresa usr;
    /**
     * Creates new form VGestionParticipacion
     */
    public VGestionParticipacion(UsuarioEmpresa usr) {
        initComponents();
        this.usr = usr;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        TextoParticipaciones = new javax.swing.JLabel();
        DarAltaBoton = new javax.swing.JButton();
        CancelarBoton = new javax.swing.JButton();
        NumeroParticipacionesSpinner = new javax.swing.JSpinner();
        DarBajaBoton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TextoParticipaciones.setText("Número de participaciones:");

        DarAltaBoton.setText("Dar de alta");
        DarAltaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DarAltaBotonActionPerformed(evt);
            }
        });

        CancelarBoton.setText("Cancelar");
        CancelarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarBotonActionPerformed(evt);
            }
        });

        DarBajaBoton.setText("Dar de baja");
        DarBajaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DarBajaBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NumeroParticipacionesSpinner)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TextoParticipaciones)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(DarAltaBoton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DarBajaBoton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CancelarBoton)))
                        .addGap(0, 12, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TextoParticipaciones)
                .addGap(18, 18, 18)
                .addComponent(NumeroParticipacionesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DarAltaBoton)
                    .addComponent(CancelarBoton)
                    .addComponent(DarBajaBoton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelarBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarBotonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_CancelarBotonActionPerformed

    private void DarAltaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DarAltaBotonActionPerformed
        // TODO add your handling code here:
        try{
            FachadaDB.getFachada().addParticipacion(usr,(int)NumeroParticipacionesSpinner.getValue());
        }
        catch(SQLException e){
            FachadaAplicacion.muestraExcepcion(e);
        }    
    }//GEN-LAST:event_DarAltaBotonActionPerformed

    private void DarBajaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DarBajaBotonActionPerformed
        // TODO add your handling code here:
        try{
            FachadaDB.getFachada().removeParticipacion(usr,(int)NumeroParticipacionesSpinner.getValue());
        }
        catch(SQLException e){
            FachadaAplicacion.muestraExcepcion(e);
        }    
    }//GEN-LAST:event_DarBajaBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelarBoton;
    private javax.swing.JButton DarAltaBoton;
    private javax.swing.JButton DarBajaBoton;
    private javax.swing.JSpinner NumeroParticipacionesSpinner;
    private javax.swing.JLabel TextoParticipaciones;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration//GEN-END:variables
}
