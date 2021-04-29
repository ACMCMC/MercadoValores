/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;

import java.util.ArrayList;
import gal.usc.mercadovalores.aplicacion.ParteCompra;
import javax.swing.table.AbstractTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class TablaCompra extends AbstractTableModel{
    private ArrayList<ParteCompra> partes;
    
    public TablaCompra(){
        this.partes = new ArrayList<ParteCompra>();
    }
    
    public int getColumnCount (){
        return 3;
    }

    public int getRowCount(){
        return partes.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre = "Cantidad"; break;
            case 1: nombre = "Precio"; break;
            case 2: nombre = "Vendedor"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.Integer.class; break;
            case 1: clase=java.lang.Double.class; break;
            case 2: clase=java.lang.String.class; break;

        }
        return clase;
    }

    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    public Object getValueAt(int row, int col){
        Object resultado=null;

        switch (col){
            case 0: resultado= partes.get(row).getCantidad(); break;
            case 1: resultado= partes.get(row).getPrecio(); break;
            case 2: resultado= partes.get(row).getVendedor().getId(); break;
        }
        return resultado;
    }

    public void setFilas(java.util.ArrayList<ParteCompra> partes){
        this.partes = partes;
        fireTableDataChanged();
    }

    public ParteCompra obtenerBeneficios(int i){
        return this.partes.get(i);
    }
}