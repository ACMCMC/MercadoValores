package gal.usc.mercadovalores.aplicacion;

public enum EstadoUsuario {
    SOLICITANDO_ALTA, SOLICITANDO_BAJA, DADO_DE_ALTA;

    public static EstadoUsuario getByName(String name) {
        return EstadoUsuario.SOLICITANDO_ALTA;
    }
}
