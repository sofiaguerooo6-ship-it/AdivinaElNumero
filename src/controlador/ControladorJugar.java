package controlador;

import dao.JugadorDAO;
import dao.PartidaDAO;
import java.sql.SQLException;
import modelo.Jugador;
import modelo.Juego;
import modelo.Partida;
import modelo.Resultado;
import modelo.UsuarioJugador;
import vista.VistaInicio;
import vista.VistaJugar;
import vista.VistaPerfil;
import vista.VistaRanking;

/**
 * Maneja una sesión de juego: las partidas, los intentos
 * y el guardado del resultado.
 *
 * @author Sofi
 */
public class ControladorJugar {

    private final VistaJugar vista;
    private final UsuarioJugador usuario;
    private final PartidaDAO partidaDAO = new PartidaDAO();
    private final JugadorDAO jugadorDAO = new JugadorDAO();
    private Juego juego;

    public ControladorJugar(VistaJugar vista, UsuarioJugador usuario) {
        this.vista = vista;
        this.usuario = usuario;

        Jugador jugador = usuario.getJugador();
        vista.mostrarJugador(jugador.getNombre(), jugador.getAvatar());
        vista.setConsigna("Pensá un número del " + Juego.MINIMO + " al " + Juego.MAXIMO);

        vista.alProbar(e -> probar());
        vista.alNuevaPartida(e -> nuevaPartida());
        vista.alVerRanking(e -> verRanking());
        vista.alMiPerfil(e -> verPerfil());
        vista.alCerrarSesion(e -> cerrarSesion());

        nuevaPartida();
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    private void nuevaPartida() {
        juego = new Juego();
        vista.mostrarResultado(" ");
        vista.mostrarIntentos(0);
        vista.habilitarJuego(true);
        vista.limpiarNumero();
    }

    private void probar() {
        int numero;
        try {
            numero = Integer.parseInt(vista.getNumeroIngresado().trim());
        } catch (NumberFormatException ex) {
            vista.mostrarError("Escribí un número entero.");
            vista.limpiarNumero();
            return;
        }

        try {
            Resultado resultado = juego.probar(numero);
            vista.mostrarResultado(resultado.getMensaje());
            vista.mostrarIntentos(juego.getIntentos());
            vista.limpiarNumero();
            if (resultado == Resultado.ACERTASTE) {
                terminarPartida();
            }
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
            vista.limpiarNumero();
        }
    }

    private void terminarPartida() {
        vista.habilitarJuego(false);
        Partida partida = juego.crearPartida(usuario.getJugador().getId());
        try {
            partidaDAO.insertar(partida);
            String intentos = partida.getIntentos() == 1
                    ? "en el primer intento"
                    : "en " + partida.getIntentos() + " intentos";
            vista.mostrarMensaje("¡Lo adivinaste " + intentos + "!\n"
                    + "Sumaste " + partida.getPuntaje() + " puntos.");
        } catch (SQLException ex) {
            vista.mostrarError("Ganaste, pero no se pudo guardar la partida: " + ex.getMessage());
        }
    }

    private void verRanking() {
        new ControladorRanking(new VistaRanking(vista), usuario).mostrar();
        refrescarJugador();
    }

    private void verPerfil() {
        new ControladorPerfil(new VistaPerfil(vista), usuario, usuario.getJugador()).mostrar();
        refrescarJugador();
    }

    private void refrescarJugador() {
        Jugador jugador = usuario.getJugador();
        try {
            Jugador actualizado = jugadorDAO.buscarPorId(jugador.getId());
            if (actualizado != null) {
                jugador.setNombre(actualizado.getNombre());
                jugador.setAvatar(actualizado.getAvatar());
            }
        } catch (SQLException ex) {
            // Si no se puede releer, seguimos mostrando los datos que ya teníamos
        }
        vista.mostrarJugador(jugador.getNombre(), jugador.getAvatar());
    }

    private void cerrarSesion() {
        vista.cerrar();
        new ControladorInicio(new VistaInicio()).iniciar();
    }
}

        