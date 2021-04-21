/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;

import java.util.ArrayList;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;

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
public class TablaSaldos extends AbstractTableModel{
    private ArrayList<UsuarioDeMercado> usuarios;
    
    public TablaSaldos(){
        this.usuarios = new ArrayList<UsuarioDeMercado>();
    }
    
    public int getColumnCount (){
        return 2;
    }

    public int getRowCount(){
        return usuarios.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Id"; break;
            case 1: nombre= "Saldo"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase=java.lang.String.class; break;
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
            case 0: resultado= usuarios.get(row).getId(); break;
            case 1: resultado= usuarios.get(row).getSaldo(); break;

        }
        return resultado;
    }

    public void setFilas(java.util.ArrayList<UsuarioDeMercado> users){
        this.usuarios = users;
        fireTableDataChanged();
    }

    public UsuarioDeMercado obtenerUsuario(int i){
        return this.usuarios.get(i);
    }
}