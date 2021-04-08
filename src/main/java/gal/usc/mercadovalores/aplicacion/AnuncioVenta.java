package gal.usc.mercadovalores.aplicacion;
import java.sql.Timestamp;

public class AnuncioVenta{
    private UsuarioEmpresa empresa;
    private UsuarioDeMercado vendedor;
    private Timestamp fecha;
    private double precio;
    private double comision_en_fecha;
    private int numero_participaciones;

    //Constructor



    //Setters y Getters

    /**
     * @return the empresa
     */
    public UsuarioEmpresa getEmpresa() {
        return empresa;
    }

    /**
     * @return the vendedor
     */
    public UsuarioDeMercado getVendedor() {
        return vendedor;
    }

    /**
     * @return the fecha
     */
    public Timestamp getFecha() {
        return fecha;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @return the comision_en_fecha
     */
    public double getComision_en_fecha() {
        return comision_en_fecha;
    }

    /**
     * @return the numero_participaciones
     */
    public int getNumero_participaciones() {
        return numero_participaciones;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(UsuarioEmpresa empresa) {
        this.empresa = empresa;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(UsuarioDeMercado vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @param comision_en_fecha the comision_en_fecha to set
     */
    public void setComision_en_fecha(double comision_en_fecha) {
        this.comision_en_fecha = comision_en_fecha;
    }

    /**
     * @param numero_participaciones the numero_participaciones to set
     */
    public void setNumero_participaciones(int numero_participaciones) {
        this.numero_participaciones = numero_participaciones;
    }

}