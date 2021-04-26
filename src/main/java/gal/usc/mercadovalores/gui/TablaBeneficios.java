/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;

import java.util.ArrayList;
import gal.usc.mercadovalores.aplicacion.Beneficios;

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
public class TablaBeneficios extends AbstractTableModel{
    private ArrayList<Beneficios> beneficios;
    
    public TablaBeneficios(){
        this.beneficios = new ArrayList<Beneficios>();
    }
    
    public int getColumnCount (){
        return 3;
    }

    public int getRowCount(){
        return beneficios.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre = "Empresa"; break;
            case 1: nombre = "Fecha"; break;
            case 2: nombre = "Importe"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase=java.sql.Timestamp.class; break;
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
            case 0: resultado= beneficios.get(row).getEmpresa().getNombreComercial(); break;
            case 1: resultado= beneficios.get(row).getFecha(); break;
            case 2: resultado= beneficios.get(row).getImporteParticipacion(); break;


        }
        return resultado;
    }

    public void setFilas(java.util.ArrayList<Beneficios> beneficios){
        this.beneficios = beneficios;
        fireTableDataChanged();
    }

    public Beneficios obtenerBeneficios(int i){
        return this.beneficios.get(i);
    }
}