/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;

import java.util.ArrayList;
import gal.usc.mercadovalores.aplicacion.AnuncioVenta;

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
public class TablaAnunciosUsuario extends AbstractTableModel{
    private ArrayList<AnuncioVenta> anuncios;
    
    public TablaAnunciosUsuario(){
        this.anuncios = new ArrayList<AnuncioVenta>();
    }
    
    public int getColumnCount (){
        return 4;
    }

    public int getRowCount(){
        return anuncios.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Empresa"; break;
            case 1: nombre= "Participaciones"; break;
            case 2: nombre= "Precio Part"; break;
            case 3: nombre= "Comisión"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase= java.lang.Integer.class; break;
            case 2: clase= java.lang.Double.class; break;
            case 3: clase= java.lang.Double.class; break;
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
            case 0: resultado= this.anuncios.get(row).getEmpresa().getNombreComercial(); break;
            case 1: resultado= this.anuncios.get(row).getNumero_participaciones(); break;
            case 2: resultado= this.anuncios.get(row).getPrecio(); break;
            case 3: resultado= this.anuncios.get(row).getComision_en_fecha(); break;
        }
        return resultado;
    }

    public void setFilas(java.util.ArrayList<AnuncioVenta> anuncios){
        this.anuncios = anuncios;
        fireTableDataChanged();
    }

    public AnuncioVenta obtenerParticipacion(int i){
        return this.anuncios.get(i);
    }
}