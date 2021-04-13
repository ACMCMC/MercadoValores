/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;

/**
 *
 * @author icaro
 */
public class VPrincipalEmpresa extends javax.swing.JFrame {
    private UsuarioEmpresa usr;
    /**
     * Creates new form VPrincipalMercado
     */
    public VPrincipalEmpresa(UsuarioEmpresa usr) {
        initComponents();
        this.usr = usr;
        this.ActualizarTablaDatos();
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
        Menu = new javax.swing.JMenuBar();
        CuentaMenu = new javax.swing.JMenu();
        ModificarMenuItem = new javax.swing.JMenuItem();
        BajaMenuItem = new javax.swing.JMenuItem();
        ParticipacionesMenu = new javax.swing.JMenu();
        AltaParticipacionMenuItem = new javax.swing.JMenuItem();
        BajaParticipacionMenuItem = new javax.swing.JMenuItem();
        VenderMenuItem = new javax.swing.JMenuItem();
        ComprarMenuItem = new javax.swing.JMenuItem();
        BajaVentaMenuItem = new javax.swing.JMenuItem();
        BeneficiosMenu = new javax.swing.JMenu();
        AltaPagoMenuItem = new javax.swing.JMenuItem();
        BajaPagoMenuItem = new javax.swing.JMenuItem();
        PagarMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ventana  Empresa");

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
                {"Participaciones creadas", null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        TablaDatos.setCellSelectionEnabled(true);
        TablaDatos.setTableHeader(null);
        jScrollPane2.setViewportView(TablaDatos);

        SalirBoton.setText("Salir");

        jLabel1.setText("Información del usuario:");

        CuentaMenu.setText("Cuenta");

        ModificarMenuItem.setText("Modificar Datos");
        CuentaMenu.add(ModificarMenuItem);

        BajaMenuItem.setText("Solicitar Baja");
        CuentaMenu.add(BajaMenuItem);

        Menu.add(CuentaMenu);

        ParticipacionesMenu.setText("Participaciones");

        AltaParticipacionMenuItem.setText("Dar de alta");
        AltaParticipacionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AltaParticipacionMenuItemActionPerformed(evt);
            }
        });
        ParticipacionesMenu.add(AltaParticipacionMenuItem);

        BajaParticipacionMenuItem.setText("Dar de baja");
        ParticipacionesMenu.add(BajaParticipacionMenuItem);

        VenderMenuItem.setText("Vender");
        VenderMenuItem.setToolTipText("");
        ParticipacionesMenu.add(VenderMenuItem);

        ComprarMenuItem.setText("Comprar");
        ParticipacionesMenu.add(ComprarMenuItem);

        BajaVentaMenuItem.setText("Dar de baja anuncio");
        ParticipacionesMenu.add(BajaVentaMenuItem);

        Menu.add(ParticipacionesMenu);

        BeneficiosMenu.setText("Beneficios");

        AltaPagoMenuItem.setText("Alta de pago");
        AltaPagoMenuItem.setToolTipText("");
        BeneficiosMenu.add(AltaPagoMenuItem);

        BajaPagoMenuItem.setText("Baja de pago");
        BeneficiosMenu.add(BajaPagoMenuItem);

        PagarMenuItem.setText("Pagar");
        BeneficiosMenu.add(PagarMenuItem);

        Menu.add(BeneficiosMenu);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(SalirBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 258, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(SalirBoton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AltaParticipacionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AltaParticipacionMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AltaParticipacionMenuItemActionPerformed

    private void ActualizarTablaDatos(){
        this.TablaDatos.setValueAt(usr.getId(), 0, 1);
        this.TablaDatos.setValueAt(usr.getSaldo(), 1, 1);
        this.TablaDatos.setValueAt(usr.getDireccion(), 2, 1);
        this.TablaDatos.setValueAt(usr.getTelefono(), 3, 1);
        this.TablaDatos.setValueAt(usr.getCif(), 4, 1);
        this.TablaDatos.setValueAt(usr.getNombreComercial(), 5, 1);
        this.TablaDatos.setValueAt(usr.getImporteBloqueado(), 6, 1);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AltaPagoMenuItem;
    private javax.swing.JMenuItem AltaParticipacionMenuItem;
    private javax.swing.JMenuItem BajaMenuItem;
    private javax.swing.JMenuItem BajaPagoMenuItem;
    private javax.swing.JMenuItem BajaParticipacionMenuItem;
    private javax.swing.JMenuItem BajaVentaMenuItem;
    private javax.swing.JMenu BeneficiosMenu;
    private javax.swing.JMenuItem ComprarMenuItem;
    private javax.swing.JMenu CuentaMenu;
    private javax.swing.JMenuBar Menu;
    private javax.swing.JMenuItem ModificarMenuItem;
    private javax.swing.JMenuItem PagarMenuItem;
    private javax.swing.JMenu ParticipacionesMenu;
    private javax.swing.JButton SalirBoton;
    private javax.swing.JTable TablaDatos;
    private javax.swing.JMenuItem VenderMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
