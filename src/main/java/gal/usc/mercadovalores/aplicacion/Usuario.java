package gal.usc.mercadovalores.aplicacion;

public abstract class Usuario {
    
    //tipos de datos temporales -> pendiente de diccionario de datos
    private String id;
    private String clave;
    private double saldo;

    public Usuario(String id, String clave, double saldo) {
        this.id = id;
        this.clave = clave;
        this.saldo = saldo;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
}