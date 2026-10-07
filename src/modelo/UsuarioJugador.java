package modelo;

/**
 * Un jugador que inició sesión.
 *
 * @author Sofi
 */
public class UsuarioJugador extends Usuario {

    private final Jugador jugador;

    public UsuarioJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    @Override
    public String getNombreParaMostrar() {
        return jugador.getNombre();
    }

    @Override
    public boolean puedeJugar() {
        return true;
    }

    @Override
    public boolean puedeEditarPerfil(Jugador otro) {
        return esElJugador(otro.getId());
    }

    @Override
    public boolean puedeEliminar() {
        return false;
    }
    
    @Override
    public boolean esElJugador(int jugadorId) {
        return jugador.getId() == jugadorId;
    }
}
