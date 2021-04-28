package gal.usc.mercadovalores.aplicacion;

public class ParteCompra {
    private int id;
    private double precio;
    private int cantidad;
    private UsuarioDeMercado vendedor;

    public ParteCompra(int id, double precio, int cantidad, UsuarioDeMercado vendedor) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
        this.vendedor = vendedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public UsuarioDeMercado getVendedor() {
        return vendedor;
    }

    public void setVendedor(UsuarioDeMercado vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        return hash;
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
        final ParteCompra other = (ParteCompra) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ParteCompra{id=" + id + '}';
    }
    
    
}
