package gal.usc.mercadovalores.aplicacion;
import java.sql.Timestamp;
import java.util.Objects;

public class AnuncioVenta{
    private UsuarioEmpresa empresa;
    private UsuarioDeMercado vendedor;
    private Timestamp fecha;
    private double precio;
    private double comision_en_fecha;
    private int numero_participaciones;

    //Constructor

    public AnuncioVenta( UsuarioDeMercado vendedor,UsuarioEmpresa empresa, Timestamp fecha, double precio, double comision_en_fecha, int numero_participaciones) {
        this.empresa = empresa;
        this.vendedor = vendedor;
        this.fecha = fecha;
        this.precio = precio;
        this.comision_en_fecha = comision_en_fecha;
        this.numero_participaciones = numero_participaciones;
    }



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


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnuncioVenta other = (AnuncioVenta) obj;
        if (Double.doubleToLongBits(this.precio) != Double.doubleToLongBits(other.precio)) {
            return false;
        }
        if (Double.doubleToLongBits(this.comision_en_fecha) != Double.doubleToLongBits(other.comision_en_fecha)) {
            return false;
        }
        if (this.numero_participaciones != other.numero_participaciones) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (!Objects.equals(this.vendedor, other.vendedor)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

    
}