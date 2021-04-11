package gal.usc.mercadovalores.aplicacion;

import java.util.Arrays;

public enum EstadoUsuario {
    SOLICITANDO_ALTA, SOLICITANDO_BAJA, DADO_DE_ALTA;

    public static EstadoUsuario getByName(String name) throws EnumConstantNotPresentException {
        return Arrays.asList(EstadoUsuario.values()).stream().filter(e -> e.toString().compareToIgnoreCase(name)==0).findFirst().orElseThrow(() -> new EnumConstantNotPresentException(EstadoUsuario.class, name));
    }
}
