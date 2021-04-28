/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;

import gal.usc.mercadovalores.aplicacion.Participacion;
import gal.usc.mercadovalores.aplicacion.AnuncioVenta;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import gal.usc.mercadovalores.aplicacion.UsuarioInversor;
import gal.usc.mercadovalores.db.FachadaDB;
import java.util.*;

/**
 *
 * @author icaro
 */
public class VCompra extends javax.swing.JFrame {
    private UsuarioDeMercado usr;
    private UsuarioEmpresa emp;
    /**
     * Creates new form VCompra
     */
    public VCompra(UsuarioDeMercado usr) {
        initComponents();
        this.usr = usr;
        textoSaldo();
        updateTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        SalirBoton = new javax.swing.JButton();
        ComprarBoton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        numParticipacionesSpinner = new javax.swing.JSpinner();
        saldoActual = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAnuncios = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        precioTexto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Compra de participaciones:");

        SalirBoton.setText("Salir");
        SalirBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SalirBotonMouseClicked(evt);
            }
        });

        ComprarBoton.setText("Comprar");
        ComprarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComprarBotonActionPerformed(evt);
            }
        });

        jLabel2.setText("Saldo actual:");

        jLabel3.setText("Empresa:");

        jLabel4.setText("Número de participaciones:");

        numParticipacionesSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        tablaAnuncios.setModel(new TablaEmpresasConAnuncios());
        tablaAnuncios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaAnunciosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaAnuncios);

        jLabel5.setText("Precio máximo a pagar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SalirBoton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ComprarBoton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saldoActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(numParticipacionesSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                    .addComponent(precioTexto))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saldoActual, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(numParticipacionesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(precioTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComprarBoton)
                    .addComponent(SalirBoton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SalirBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirBotonMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_SalirBotonMouseClicked

    private void ComprarBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComprarBotonActionPerformed
        // TODO add your handling code here:
        if(emp!=null){
            FachadaDB.getFachada().compraParticipaciones(usr, emp, (Integer)numParticipacionesSpinner.getValue(),Integer.valueOf(precioTexto.getText()));
        }
        updateTable();
        textoSaldo();
    }//GEN-LAST:event_ComprarBotonActionPerformed

    private void tablaAnunciosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAnunciosMousePressed
        // TODO add your handling code here:
        TablaEmpresasConAnuncios te=(TablaEmpresasConAnuncios) tablaAnuncios.getModel();
        emp = te.getEmpresaAt(tablaAnuncios.getSelectedRow(),1);
    }//GEN-LAST:event_tablaAnunciosMousePressed

    private void textoSaldo(){
        
        //mostra o saldo actual do comprador
        this.usr = (UsuarioDeMercado) FachadaDB.getFachada().getUsuarioById(usr.getId());
        Double saldo = usr.getSaldo();
        String saldoText = saldo.toString();
        this.saldoActual.setText(saldoText + "€");
    }
    
    private void updateTable(){
        TablaEmpresasConAnuncios tA = (TablaEmpresasConAnuncios) this.tablaAnuncios.getModel();
        Set<UsuarioEmpresa> setEmpresas = FachadaDB.getFachada().getEmpresasConAnuncios();
       
        ArrayList<UsuarioEmpresa> arrEmpresas = new ArrayList<>();
        for(UsuarioEmpresa aV : setEmpresas){
            arrEmpresas.add(aV);
        }
        
        tA.setFilas(arrEmpresas);
        if(tA.getRowCount()>0){
            this.tablaAnuncios.setRowSelectionInterval(0, 0);
        }
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ComprarBoton;
    private javax.swing.JButton SalirBoton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner numParticipacionesSpinner;
    private javax.swing.JTextField precioTexto;
    private javax.swing.JLabel saldoActual;
    private javax.swing.JTable tablaAnuncios;
    // End of variables declaration//GEN-END:variables
}
