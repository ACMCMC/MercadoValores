/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.aplicacion;


/**
 *
 * @author user
 */
public class UsuarioRegulador extends Usuario {
    private double comision_actual;

    /**
     * @return the comision_actual
     */
    public double getComision_actual() {
        return comision_actual;
    }

    /**
     * @param comision_actual the comision_actual to set
     */
    public void setComision_actual(double comision_actual) {
        this.comision_actual = comision_actual;
    }
}