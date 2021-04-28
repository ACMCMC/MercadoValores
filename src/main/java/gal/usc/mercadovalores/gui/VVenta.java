/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;
import gal.usc.mercadovalores.aplicacion.EstadoUsuario;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import gal.usc.mercadovalores.aplicacion.Participacion;
import gal.usc.mercadovalores.db.FachadaDB;
import java.util.ArrayList;
import java.util.Set;
/**
 *
 * @author user
 */
public class VVenta extends javax.swing.JFrame {
    private UsuarioDeMercado usr;
    private UsuarioEmpresa emp = null;
    /**
     * Creates new form VCompra
     */
    public VVenta(UsuarioDeMercado usr) {
        this.usr = usr;
        initComponents();
        this.textoComision();
        this.updateTabla();
        this.textoPrecioMedio();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        campoCantidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        campoPrecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        precioComision = new javax.swing.JLabel();
        textoLargoPrecio = new javax.swing.JLabel();
        precioMedio = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEmpresas = new javax.swing.JTable();
        botonSalir = new javax.swing.JButton();
        botonVender = new javax.swing.JButton();
        textoX = new javax.swing.JLabel();
        textoNombreEmpresa = new javax.swing.JLabel();
        numeroVentasMedioSpinner = new javax.swing.JSpinner();
        actualizarBoton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel2.setText("Cantidad:");

        campoCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCantidadActionPerformed(evt);
            }
        });

        jLabel3.setText("Precio por participación: ");

        jLabel4.setText("Comisión actual: ");

        jLabel1.setText("Venta de participaciones:");

        tablaEmpresas.setModel(new TablaEmpresasUsuario());
        tablaEmpresas.setToolTipText("");
        tablaEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEmpresasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaEmpresas);

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        botonVender.setText("Vender");
        botonVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVenderActionPerformed(evt);
            }
        });

        textoX.setText("X =");
        textoX.setToolTipText("");

        numeroVentasMedioSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));

        actualizarBoton.setText("Actualizar");
        actualizarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(campoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(campoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(precioComision, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(170, 170, 170))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(textoNombreEmpresa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(textoX)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(numeroVentasMedioSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(actualizarBoton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(precioMedio, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textoLargoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(botonSalir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonVender)
                        .addGap(46, 46, 46))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(campoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precioComision, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textoLargoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textoNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textoX)
                            .addComponent(numeroVentasMedioSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(actualizarBoton))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonSalir)
                            .addComponent(botonVender)))
                    .addComponent(precioMedio, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCantidadActionPerformed

    private void botonVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVenderActionPerformed
        //obtenemos todo lo necesario:
        TablaEmpresasUsuario tE = (TablaEmpresasUsuario) this.tablaEmpresas.getModel();
        Participacion pE = tE.obtenerParticipacion(this.tablaEmpresas.getSelectedRow());
        try{
            if(!this.campoCantidad.getText().isEmpty() && !this.campoPrecio.getText().isEmpty()){
                Integer cantidad = Integer.parseInt(this.campoCantidad.getText());
                Double precio = Double.parseDouble(this.campoPrecio.getText());
                Double comision = Double.parseDouble(this.precioComision.getText());
                if(pE.getNumero() >= cantidad){
                    FachadaDB.getFachada().venderParticipacion(this.usr,pE.getEmpresa(),cantidad,precio,comision);
                }
            }
        }catch(Exception e){
            VAviso x = new VAviso(this,true,e.getMessage());
            x.setVisible(true);
        }
        this.updateTabla();

    }//GEN-LAST:event_botonVenderActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void tablaEmpresasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEmpresasMouseClicked
        // TODO add your handling code here:
        TablaEmpresasUsuario tE = (TablaEmpresasUsuario) this.tablaEmpresas.getModel();
        if(tablaEmpresas.getSelectedRow()>0){
            UsuarioEmpresa emp = (UsuarioEmpresa)tE.getEmpresaAt(tablaEmpresas.getSelectedRow());
        }
        this.numeroVentasMedioSpinner.setValue(10);
        textoPrecioMedio();
    }//GEN-LAST:event_tablaEmpresasMouseClicked

    private void actualizarBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarBotonActionPerformed
        // TODO add your handling code here:
        this.textoPrecioMedio();
    }//GEN-LAST:event_actualizarBotonActionPerformed

    private void updateTabla(){
        try{
            TablaEmpresasUsuario tE = (TablaEmpresasUsuario) this.tablaEmpresas.getModel();
            Set<Participacion> p = this.usr.getParticipaciones();
            ArrayList<Participacion> pT = new ArrayList<>();


            for(Participacion pa : p){
                //modificamos el numero de participaciones para enseñar las disponibles
                int partEnVenta = FachadaDB.getFachada().getParticipacionesDeEmpresaALaVentaPorUsuario(this.usr,pa.getEmpresa());
                pa.setNumero(pa.getNumero()-partEnVenta);
                pT.add(pa);
            }

            tE.setFilas(pT);

            if(tE.getRowCount()>0){
                this.tablaEmpresas.setRowSelectionInterval(0, 0);
            }            
        }catch(Exception e){
            VAviso x = new VAviso(this,true,e.getMessage());
            x.setVisible(true);
        }

    }
    
    private void textoComision(){
        try{
            //muestra la comision del regulador
            Double comision = FachadaDB.getFachada().getUsuarioRegulador().getComision_actual();
            String comisionText = comision.toString();
            this.precioComision.setText(comisionText);            
        }catch(Exception e){
            VAviso x = new VAviso(this,true,e.getMessage());
            x.setVisible(true);
        }

    }
    
    private void textoPrecioMedio(){
        try{
            TablaEmpresasUsuario tE = (TablaEmpresasUsuario) this.tablaEmpresas.getModel();
            emp = (UsuarioEmpresa)tE.getEmpresaAt(tablaEmpresas.getSelectedRow());
            if(emp != null){
                Double precio = FachadaDB.getFachada().getPrecioMedioComprasEmpresa(emp, (int) this.numeroVentasMedioSpinner.getValue());
                if(precio!=null){
                    String precioText = precio.toString();
                    this.textoX.setVisible(true);
                    this.numeroVentasMedioSpinner.setVisible(true);
                    this.textoLargoPrecio.setVisible(true);
                    this.textoNombreEmpresa.setVisible(true);
                    this.precioMedio.setVisible(true);
                    this.actualizarBoton.setVisible(true);
                    this.textoLargoPrecio.setText("Precio medio de las últimas  X  ventas da empresa:");
                    this.textoNombreEmpresa.setText(emp.getNombreComercial());
                    this.precioMedio.setText(precioText + "€");
                    
                }
                else{
                    this.textoLargoPrecio.setText("Precio medio no disponible");
                    this.textoNombreEmpresa.setVisible(false);
                    this.precioMedio.setVisible(false);
                    this.textoX.setVisible(false);
                    this.numeroVentasMedioSpinner.setVisible(false);
                    this.actualizarBoton.setVisible(false);
                }
            }            
        }catch(Exception e){
            VAviso x = new VAviso(this,true,e.getMessage());
            x.setVisible(true);
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizarBoton;
    private javax.swing.JButton botonSalir;
    private javax.swing.JButton botonVender;
    private javax.swing.JTextField campoCantidad;
    private javax.swing.JTextField campoPrecio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner numeroVentasMedioSpinner;
    private javax.swing.JLabel precioComision;
    private javax.swing.JLabel precioMedio;
    private javax.swing.JTable tablaEmpresas;
    private javax.swing.JLabel textoLargoPrecio;
    private javax.swing.JLabel textoNombreEmpresa;
    private javax.swing.JLabel textoX;
    // End of variables declaration//GEN-END:variables
}
