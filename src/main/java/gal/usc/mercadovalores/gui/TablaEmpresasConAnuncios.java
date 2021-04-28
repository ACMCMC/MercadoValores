/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.gui;

import java.util.ArrayList;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author icaro
 */
public class TablaEmpresasConAnuncios extends AbstractTableModel{
    private ArrayList<UsuarioEmpresa> empresas;
    
    public TablaEmpresasConAnuncios(){
        this.empresas = new ArrayList<UsuarioEmpresa>();
    }
    
    public int getColumnCount(){
        return 1;
    }
    
    public int getRowCount(){
        return empresas.size();
    }
    
    @Override
    public String getColumnName(int col){
        String nombre = "";
        
        switch(col){
            case 0: nombre = "Empresa";
        }
        return nombre;
    }
    
    @Override
    public Class getColumnClass(int col){
        return gal.usc.mercadovalores.aplicacion.UsuarioEmpresa.class;
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }
    
    public Object getValueAt(int row, int col){
        return this.empresas.get(row).getNombreComercial();
    }
    
    public UsuarioEmpresa getEmpresaAt(int row, int col){
        return this.empresas.get(row);
    }
    
    public void setFilas(java.util.ArrayList<UsuarioEmpresa> empresas){
        this.empresas = empresas;
        fireTableDataChanged();
    }
    
    public UsuarioEmpresa obtenerEmpresa(int i){
        return this.empresas.get(i);
    }
}


