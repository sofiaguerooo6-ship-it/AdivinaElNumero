package modelo;

/**
 * Las respuestas posibles al probar un número.
 *
 * @author Sofi
 */
public enum Resultado {

    MAS_ALTO("Más alto ↑"),
    MAS_BAJO("Más bajo ↓"),
    ACERTASTE("¡Acertaste!");

    private final String mensaje;

    Resultado(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
