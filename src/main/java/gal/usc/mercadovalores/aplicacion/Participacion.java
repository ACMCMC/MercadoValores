package gal.usc.mercadovalores.aplicacion;

import java.util.Objects;

public class Participacion {
    private UsuarioDeMercado usuarioMercado;
    private UsuarioEmpresa empresa;
    private Integer numero;

    public Participacion(UsuarioDeMercado usuarioMercado, UsuarioEmpresa empresa, Integer num) {
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
        final Participacion other = (Participacion) obj;
        if (!Objects.equals(this.usuarioMercado, other.usuarioMercado)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        return true;
    }

    

}
