package controlador;

import dao.JugadorDAO;
import dao.PartidaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Administrador;
import modelo.Jugador;
import modelo.Partida;
import vista.VistaInicio;
import vista.VistaJugadores;
import vista.VistaRanking;

/**
 * Pantalla del administrador: maestro (jugadores) y detalle (partidas).
 *
 * @author Sofi
 */
public class ControladorJugadores {

    private final VistaJugadores vista;
    private final Administrador administrador;
    private final JugadorDAO jugadorDAO = new JugadorDAO();
    private final PartidaDAO partidaDAO = new PartidaDAO();
    private List<Jugador> jugadores = new ArrayList<>();
    private List<Partida> partidas = new ArrayList<>();
    private Jugador seleccionado;

    public ControladorJugadores(VistaJugadores vista, Administrador administrador) {
        this.vista = vista;
        this.administrador = administrador;

        vista.alSeleccionarJugador(fila -> seleccionarJugador(fila));
        vista.alEliminarJugador(e -> eliminarJugador());
        vista.alEliminarPartida(e -> eliminarPartida());
        vista.alVerRanking(e -> verRanking());
        vista.alCerrarSesion(e -> cerrarSesion());

        cargarJugadores();
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    // ----- Maestro -----

    private void cargarJugadores() {
        try {
            jugadores = jugadorDAO.listarTodos();
        } catch (SQLException ex) {
            vista.mostrarError("No se pudieron cargar los jugadores: " + ex.getMessage());
            jugadores = new ArrayList<>();
        }
        vista.limpiarJugadores();
        for (Jugador jugador : jugadores) {
            vista.agregarJugador(jugador.getId(), jugador.getNombre(), jugador.getAvatar());
        }
        seleccionarJugador(-1);
    }

    private void seleccionarJugador(int fila) {
        if (fila >= 0 && fila < jugadores.size()) {
            seleccionado = jugadores.get(fila);
        } else {
            seleccionado = null;
        }
        cargarPartidas();
    }

    // ----- Detalle -----

    private void cargarPartidas() {
        vista.limpiarPartidas();
        partidas = new ArrayList<>();

        if (seleccionado == null) {
            vista.mostrarDetalle("Seleccioná un jugador", null);
            return;
        }

        vista.mostrarDetalle("Partidas de " + seleccionado.getNombre(), seleccionado.getAvatar());
        try {
            partidas = partidaDAO.listarPorJugador(seleccionado.getId());
        } catch (SQLException ex) {
            vista.mostrarError("No se pudieron cargar las partidas: " + ex.getMessage());
        }
        for (Partida partida : partidas) {
            vista.agregarPartida(partida.getId(), partida.getFecha(), partida.getIntentos(),
                    partida.getPuntaje(), partida.isVigente());
        }
    }

    // ----- Acciones -----

    private void eliminarJugador() {
        if (seleccionado == null) {
            vista.mostrarError("Seleccioná un jugador de la tabla.");
            return;
        }
        String pregunta = "¿Eliminar a " + seleccionado.getNombre() + "?\n"
                + "Se van a borrar también todas sus partidas y su récord.";
        if (!vista.confirmar(pregunta)) {
            return;
        }
        try {
            jugadorDAO.eliminar(seleccionado.getId());
            cargarJugadores();
            vista.mostrarMensaje("Jugador eliminado.");
        } catch (SQLException ex) {
            vista.mostrarError("No se pudo eliminar el jugador: " + ex.getMessage());
        }
    }

    private void eliminarPartida() {
        int fila = vista.getPartidaSeleccionada();
        if (fila < 0) {
            vista.mostrarError("Seleccioná una partida de la tabla.");
            return;
        }
        Partida partida = partidas.get(fila);
        String pregunta = "¿Eliminar la partida del " + partida.getFecha()
                + " (" + partida.getPuntaje() + " puntos)?";
        if (!vista.confirmar(pregunta)) {
            return;
        }
        try {
            partidaDAO.eliminar(partida);
            cargarPartidas();
            vista.mostrarMensaje("Partida eliminada.");
        } catch (SQLException ex) {
            vista.mostrarError("No se pudo eliminar la partida: " + ex.getMessage());
        }
    }

    private void verRanking() {
        new ControladorRanking(new VistaRanking(vista), administrador).mostrar();
        cargarJugadores();
    }

    private void cerrarSesion() {
        vista.cerrar();
        new ControladorInicio(new VistaInicio()).iniciar();
    }
}
