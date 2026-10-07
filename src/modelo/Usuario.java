package modelo;

/**
 * Representa a quien inició sesión. Cada tipo de usuario
 * define qué puede hacer.
 *
 * @author Sofi
 */
public abstract class Usuario {

    public abstract String getNombreParaMostrar();

    public abstract boolean puedeJugar();

    public abstract boolean puedeEditarPerfil(Jugador jugador);

    public abstract boolean puedeEliminar();
    
    public abstract boolean esElJugador(int jugadorId);
}
