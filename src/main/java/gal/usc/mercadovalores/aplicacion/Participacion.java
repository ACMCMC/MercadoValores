package gal.usc.mercadovalores.aplicacion;

public class Participacion {
    private UsuarioDeMercado usuarioMercado;
    private UsuarioEmpresa empresa;
    private Integer numero;

    Participacion(UsuarioDeMercado usuarioMercado, UsuarioEmpresa empresa, Integer num) {
        this.usuarioMercado = usuarioMercado;
        this.empresa = empresa;
        this.numero = num;
    }

    public UsuarioDeMercado getUsuarioMercado() {
        return usuarioMercado;
    }

    public void setUsuarioMercado(UsuarioDeMercado usuarioMercado) {
        this.usuarioMercado = usuarioMercado;
    }

    public UsuarioEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(UsuarioEmpresa empresa) {
        this.empresa = empresa;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
