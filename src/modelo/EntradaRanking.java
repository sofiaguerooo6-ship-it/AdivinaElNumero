package modelo;

/**
 * Una fila del ranking: el récord de un jugador.
 *
 * @author Sofi
 */
public class EntradaRanking {

    private final int jugadorId;
    private final String nombreJugador;
    private final int intentos;
    private final int mejorPuntaje;
    private final String fecha;

    public EntradaRanking(int jugadorId, String nombreJugador, int intentos,
                          int mejorPuntaje, String fecha) {
        this.jugadorId = jugadorId;
        this.nombreJugador = nombreJugador;
        this.intentos = intentos;
        this.mejorPuntaje = mejorPuntaje;
        this.fecha = fecha;
    }

    public int getJugadorId() {
        return jugadorId;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getIntentos() {
        return intentos;
    }

    public int getMejorPuntaje() {
        return mejorPuntaje;
    }

    public String getFecha() {
        return fecha;
    }
}
