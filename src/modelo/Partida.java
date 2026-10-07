package modelo;

import java.time.LocalDate;

/**
 * Representa una partida jugada por un jugador.
 *
 * @author Sofi
 */
public class Partida {

    public static final int PUNTAJE_MAXIMO = 100;
    public static final int DESCUENTO_POR_INTENTO = 10;

    private int id;
    private int jugadorId;
    private int intentos;
    private int puntaje;
    private String fecha;
    private boolean vigente;

    // Para una partida recién terminada
    public Partida(int jugadorId, int intentos) {
        this.jugadorId = jugadorId;
        setIntentos(intentos);
        this.puntaje = calcularPuntaje(intentos);
        this.fecha = LocalDate.now().toString();
        this.vigente = true;
    }

    // Para una partida que se leyó de la base
    public Partida(int id, int jugadorId, int intentos, int puntaje,
                   String fecha, boolean vigente) {
        this.id = id;
        this.jugadorId = jugadorId;
        setIntentos(intentos);
        this.puntaje = puntaje;
        this.fecha = fecha;
        this.vigente = vigente;
    }

    public static int calcularPuntaje(int intentos) {
        return Math.max(0, PUNTAJE_MAXIMO - (intentos - 1) * DESCUENTO_POR_INTENTO);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJugadorId() {
        return jugadorId;
    }

    public int getIntentos() {
        return intentos;
    }

    private void setIntentos(int intentos) {
        if (intentos < 1) {
            throw new IllegalArgumentException("Los intentos deben ser al menos 1");
        }
        this.intentos = intentos;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getFecha() {
        return fecha;
    }

    public boolean isVigente() {
        return vigente;
    }
}
