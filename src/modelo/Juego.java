package modelo;

import java.util.Random;

/**
 * Una partida en curso: guarda el número secreto y cuenta los intentos.
 *
 * @author Sofi
 */
public class Juego {

    public static final int MINIMO = 1;
    public static final int MAXIMO = 50;
    private static final Random AZAR = new Random();

    private final int numeroSecreto;
    private int intentos;
    private boolean terminado;

    public Juego() {
        numeroSecreto = AZAR.nextInt(MINIMO, MAXIMO + 1);
    }

    public Resultado probar(int numero) {
        if (terminado) {
            throw new IllegalStateException("La partida ya terminó.");
        }
        if (numero < MINIMO || numero > MAXIMO) {
            throw new IllegalArgumentException(
                    "El número tiene que estar entre " + MINIMO + " y " + MAXIMO + ".");
        }
        intentos++;
        if (numero < numeroSecreto) {
            return Resultado.MAS_ALTO;
        }
        if (numero > numeroSecreto) {
            return Resultado.MAS_BAJO;
        }
        terminado = true;
        return Resultado.ACERTASTE;
    }

    public Partida crearPartida(int jugadorId) {
        if (!terminado) {
            throw new IllegalStateException("Todavía no se adivinó el número.");
        }
        return new Partida(jugadorId, intentos);
    }

    public int getIntentos() {
        return intentos;
    }

    public boolean isTerminado() {
        return terminado;
    }
}
