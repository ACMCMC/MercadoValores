/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.db.FachadaDB;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import gal.usc.mercadovalores.aplicacion.UsuarioInversor;
import gal.usc.mercadovalores.aplicacion.EstadoUsuario;

/**
 *
 * @author user
 */
public class VRegistro extends javax.swing.JDialog {
    private FachadaAplicacion fa;
    private java.awt.Frame parentX;
    /**
     * Creates new form VRegistro
     */
    public VRegistro(java.awt.Frame parent, boolean modal, FachadaAplicacion fa) {
        super(parent, modal);
        this.fa = fa;
        initComponents();
        this.parentX = parent;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nombreUsuarioEmpresa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        passEmpresa = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        dirEmpresa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        telfEmpresa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cifEmpresa = new javax.swing.JTextField();
        nombreEmpresa = new javax.swing.JTextField();
        botonRegistroEmpresa = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        nombreUsuarioInversor = new javax.swing.JTextField();
        passInversor = new javax.swing.JPasswordField();
        dirInversor = new javax.swing.JTextField();
        telfInversor = new javax.swing.JTextField();
        dniInversor = new javax.swing.JTextField();
        nombreInversor = new javax.swing.JTextField();
        botonRegistroInversor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Registro Empresa");

        nombreUsuarioEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreUsuarioEmpresaActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre de usuario:");

        jLabel3.setText("Contraseña:");

        jLabel4.setText("Dirección:");

        jLabel5.setText("Teléfono:");

        jLabel6.setText("CIF:");

        botonRegistroEmpresa.setText("Registrarse");
        botonRegistroEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistroEmpresaActionPerformed(evt);
            }
        });

        jLabel15.setText("Nombre:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(botonRegistroEmpresa)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel15))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(nombreEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                .addComponent(nombreUsuarioEmpresa, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(passEmpresa, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dirEmpresa, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(telfEmpresa, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cifEmpresa, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreUsuarioEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(dirEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(telfEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cifEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(nombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(botonRegistroEmpresa)
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Empresa", jPanel1);

        jLabel8.setText("Registro Inversor");

        jLabel9.setText("Nombre de usuario:");

        jLabel10.setText("Contraseña:");

        jLabel11.setText("Dirección:");

        jLabel12.setText("Teléfono:");

        jLabel13.setText("DNI:");

        jLabel14.setText("Nombre completo:");

        botonRegistroInversor.setText("Registrarse");
        botonRegistroInversor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistroInversorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nombreUsuarioInversor)
                                .addComponent(passInversor)
                                .addComponent(dirInversor)
                                .addComponent(telfInversor)
                                .addComponent(dniInversor, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                            .addComponent(nombreInversor, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonRegistroInversor))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(nombreUsuarioInversor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(passInversor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dirInversor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(telfInversor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(dniInversor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(nombreInversor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(botonRegistroInversor)
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Inversor", jPanel2);

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

    private void nombreUsuarioEmpresaActionPerformed(java.awt.event.ActionEvent evt){
        
    }
    
    private void botonRegistroEmpresaActionPerformed(java.awt.event.ActionEvent evt) {

        //intentamos registrar una empresa
        //comprobamos que ningún campo esté vacío
        if(!this.cifEmpresa.getText().isEmpty() &&
           !this.nombreEmpresa.getText().isEmpty() &&
           !this.nombreUsuarioEmpresa.getText().isEmpty() &&
           !this.passEmpresa.getText().isEmpty() &&
           !this.telfEmpresa.getText().isEmpty() &&
           !this.dirEmpresa.getText().isEmpty()){

            UsuarioEmpresa u = new UsuarioEmpresa(this.nombreUsuarioEmpresa.getText(),
            this.passEmpresa.getText(),0.,this.dirEmpresa.getText(),this.telfEmpresa.getText(),
            EstadoUsuario.SOLICITANDO_ALTA,this.cifEmpresa.getText(),this.nombreEmpresa.getText(),0.);

            FachadaDB.getFachada().add(u);

            //abrimos ventana de aviso
            VAviso x = new VAviso(this.parentX,true,"La cuenta necesita ser confirmada, espere para iniciar sesión");
            x.setVisible(true);

            this.cifEmpresa.setText("");
            this.nombreEmpresa.setText("");
            this.nombreUsuarioEmpresa.setText("");
            this.passEmpresa.setText("");
            this.telfEmpresa.setText("");
            this.dirEmpresa.setText("");
        }

    }

    private void botonRegistroInversorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistroInversorActionPerformed
        //intentamos registrar una empresa
        //comprobamos que ningún campo esté vacío
        if(!this.dniInversor.getText().isEmpty() &&
           !this.nombreInversor.getText().isEmpty() &&
           !this.nombreUsuarioInversor.getText().isEmpty() &&
           !this.passInversor.getText().isEmpty() &&
           !this.telfInversor.getText().isEmpty() &&
           !this.dirInversor.getText().isEmpty()){

            UsuarioInversor u = new UsuarioInversor(this.nombreUsuarioInversor.getText(),
            this.passInversor.getText(),0.,this.dirInversor.getText(),this.telfInversor.getText(),
            EstadoUsuario.SOLICITANDO_ALTA,this.dniInversor.getText(),this.nombreInversor.getText());

            FachadaDB.getFachada().add(u);

            //abrimos ventana de aviso
            VAviso x = new VAviso(this.parentX,true,"La cuenta necesita ser confirmada, espere para iniciar sesión");
            x.setVisible(true);

            this.dniInversor.setText("");
            this.nombreInversor.setText("");
            this.nombreUsuarioInversor.setText("");
            this.passInversor.setText("");
            this.telfInversor.setText("");
            this.dirInversor.setText("");
        }
    }//GEN-LAST:event_botonRegistroInversorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonRegistroEmpresa;
    private javax.swing.JButton botonRegistroInversor;
    private javax.swing.JTextField cifEmpresa;
    private javax.swing.JTextField dirEmpresa;
    private javax.swing.JTextField dirInversor;
    private javax.swing.JTextField dniInversor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField nombreEmpresa;
    private javax.swing.JTextField nombreInversor;
    private javax.swing.JTextField nombreUsuarioEmpresa;
    private javax.swing.JTextField nombreUsuarioInversor;
    private javax.swing.JPasswordField passEmpresa;
    private javax.swing.JPasswordField passInversor;
    private javax.swing.JTextField telfEmpresa;
    private javax.swing.JTextField telfInversor;
    // End of variables declaration//GEN-END:variables
}
