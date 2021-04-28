/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;

import java.util.ArrayList;
import gal.usc.mercadovalores.aplicacion.Participacion;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;

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
public class TablaEmpresasUsuario extends AbstractTableModel{
    private ArrayList<Participacion> participaciones;
    
    public TablaEmpresasUsuario(){
        this.participaciones = new ArrayList<Participacion>();
    }
    
    public int getColumnCount (){
        return 2;
    }

    public int getRowCount(){
        return participaciones.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Empresa"; break;
            case 1: nombre= "Participaciones"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase= java.lang.Integer.class; break;
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
            case 0: resultado= this.participaciones.get(row).getEmpresa().getNombreComercial(); break;
            case 1: resultado= this.participaciones.get(row).getNumero(); break;

        }
        return resultado;
    }
    
    public UsuarioEmpresa getEmpresaAt(int row){
        return  this.participaciones.get(row).getEmpresa();
    }

    public void setFilas(java.util.ArrayList<Participacion> participaciones){
        this.participaciones = participaciones;
        fireTableDataChanged();
    }

    public Participacion obtenerParticipacion(int i){
        return this.participaciones.get(i);
    }
}