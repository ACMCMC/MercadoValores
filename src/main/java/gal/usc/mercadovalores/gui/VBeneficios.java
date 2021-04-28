/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;
import gal.usc.mercadovalores.aplicacion.Beneficios;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import gal.usc.mercadovalores.db.FachadaDB;
import gal.usc.mercadovalores.aplicacion.Participacion;
import java.util.Set;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class VBeneficios extends javax.swing.JFrame {

    private UsuarioEmpresa usr;
    private int nParts;
    /**
     * Creates new form VBeneficios
     */
    public VBeneficios(UsuarioEmpresa usr) {
        initComponents();
        this.usr = usr;
        this.setCampos();
        this.updateTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nParticipaciones = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        campoParticipaciones = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textoSaldo = new javax.swing.JLabel();
        botonAnunciar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        botonPagarAhora = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaBeneficios = new javax.swing.JTable();
        botonPagar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Número de participaciones: ");

        nParticipaciones.setText("Participaciones");

        jLabel2.setText("Precio por participaciones:");

        campoParticipaciones.setText("0");
        campoParticipaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoParticipacionesActionPerformed(evt);
            }
        });

        jLabel5.setText("Saldo actual: ");

        textoSaldo.setText("saldo");

        botonAnunciar.setText("Anunciar");
        botonAnunciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnunciarActionPerformed(evt);
            }
        });

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        botonPagarAhora.setText("Pagar Ahora");
        botonPagarAhora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPagarAhoraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoParticipaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textoSaldo)
                                    .addComponent(nParticipaciones)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botonPagarAhora)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonAnunciar)))
                        .addGap(33, 33, 33))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nParticipaciones))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoParticipaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textoSaldo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAnunciar)
                    .addComponent(jButton1)
                    .addComponent(botonPagarAhora))
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Anunciar Pago", jPanel1);

        tablaBeneficios.setModel(new TablaBeneficios());
        jScrollPane1.setViewportView(tablaBeneficios);

        botonPagar.setText("Pagar");
        botonPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPagarActionPerformed(evt);
            }
        });

        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(160, 160, 160)
                        .addComponent(botonPagar)))
                .addGap(33, 33, 33))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonPagar)
                    .addComponent(jButton3))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pagar Beneficios", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAnunciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnunciarActionPerformed

        //anunciamos beneficios
        try{
            Double precio = Double.parseDouble(this.campoParticipaciones.getText());
            FachadaDB.getFachada().anunciarBeneficios(this.usr,precio,new Timestamp(System.currentTimeMillis()));
            this.setCampos();
            this.updateTabla();
        }catch(Exception e){
            VAviso x = new VAviso(this,true,e.getMessage());
            x.setVisible(true);
        }
    }//GEN-LAST:event_botonAnunciarActionPerformed

    private void campoParticipacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoParticipacionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoParticipacionesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPagarActionPerformed
        // TODO add your handling code here:
        try{
            TablaBeneficios tB = (TablaBeneficios)this.tablaBeneficios.getModel();
            Beneficios b = tB.obtenerBeneficios(this.tablaBeneficios.getSelectedRow());
            //pagar beneficios
            FachadaDB.getFachada().pagarAnuncioBeneficios(b.getEmpresa(),b.getFecha());
            this.updateTabla();
        }catch(Exception e){
            VAviso x = new VAviso(this,true,e.getMessage());
            x.setVisible(true);
        }
        
    }//GEN-LAST:event_botonPagarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void botonPagarAhoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPagarAhoraActionPerformed
        try{
            Double precio = Double.parseDouble(this.campoParticipaciones.getText());
            FachadaDB.getFachada().pagarBeneficiosInmediatamente(this.usr, precio);
            this.setCampos();
        }catch(Exception e){
            VAviso x = new VAviso(this,true,e.getMessage());
            x.setVisible(true);
        }
    }//GEN-LAST:event_botonPagarAhoraActionPerformed

    //obtenemos numero de participaciones de la empresa
    private void setCampos(){
        Set<Participacion> allParts = FachadaDB.getFachada().getParticipacionesEmpresa(usr);
        
        //obtener numero total de participaciones
        nParts = 0;
        for(Participacion p: allParts){
            nParts += p.getNumero();
        }
        Integer totalParticipaciones = this.nParts;
        this.nParticipaciones.setText(totalParticipaciones.toString());
        
        Double totalSaldo = FachadaDB.getFachada().getUsuarioById(this.usr.getId()).getSaldo();
        this.textoSaldo.setText(totalSaldo.toString());
    }
    
   private void updateTabla(){
       TablaBeneficios tB = (TablaBeneficios)this.tablaBeneficios.getModel();
       
       Set<Beneficios> allBens = FachadaDB.getFachada().getBeneficiosEmpresa(this.usr);
       ArrayList<Beneficios> benFinales = new ArrayList<>();
       for(Beneficios b: allBens){
           benFinales.add(b);
       }
       
       tB.setFilas(benFinales);
       
        if(tB.getRowCount()>0){
            this.tablaBeneficios.setRowSelectionInterval(0, 0);
        }
   }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnunciar;
    private javax.swing.JButton botonPagar;
    private javax.swing.JButton botonPagarAhora;
    private javax.swing.JTextField campoParticipaciones;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel nParticipaciones;
    private javax.swing.JTable tablaBeneficios;
    private javax.swing.JLabel textoSaldo;
    // End of variables declaration//GEN-END:variables
}
