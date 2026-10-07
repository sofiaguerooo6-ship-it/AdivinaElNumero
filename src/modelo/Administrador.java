package modelo;

/**
 * El administrador del juego: no juega, pero puede eliminar
 * jugadores y partidas, y reiniciar el ranking.
 *
 * @author Sofi
 */
public class Administrador extends Usuario {

    private static final String CONTRASENIA = "admin123";

    public static boolean contraseniaCorrecta(String intento) {
        return CONTRASENIA.equals(intento);
    }

    @Override
    public String getNombreParaMostrar() {
        return "Administrador";
    }

    @Override
    public boolean puedeJugar() {
        return false;
    }

    @Override
    public boolean puedeEditarPerfil(Jugador jugador) {
        return false;
    }

    @Override
    public boolean puedeEliminar() {
        return true;
    }
    
        @Override
    public boolean esElJugador(int jugadorId) {
        return false;
    }
}
