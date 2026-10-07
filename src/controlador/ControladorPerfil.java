package controlador;

import dao.JugadorDAO;
import dao.PartidaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Jugador;
import modelo.Partida;
import modelo.Usuario;
import vista.VistaPerfil;

/**
 * Muestra el perfil de un jugador. Según quién lo abra, permite
 * editar los datos o eliminar partidas.
 *
 * @author Sofi
 */
public class ControladorPerfil {

    private final VistaPerfil vista;
    private final Usuario usuario;
    private final Jugador jugador;
    private final JugadorDAO jugadorDAO = new JugadorDAO();
    private final PartidaDAO partidaDAO = new PartidaDAO();
    private List<Partida> partidas = new ArrayList<>();

    public ControladorPerfil(VistaPerfil vista, Usuario usuario, Jugador jugador) {
        this.vista = vista;
        this.usuario = usuario;
        this.jugador = jugador;

        vista.mostrarDatos(jugador.getNombre(), jugador.getAvatar());
        vista.setEditable(usuario.puedeEditarPerfil(jugador));
        vista.mostrarEliminarPartida(usuario.puedeEliminar());

        vista.alGuardar(e -> guardar());
        vista.alEliminarPartida(e -> eliminarPartida());
        vista.alVolver(e -> vista.cerrar());

        cargarHistorial();
    }

    public void mostrar() {
        vista.setVisible(true);
    }

    private void cargarHistorial() {
        try {
            partidas = partidaDAO.listarPorJugador(jugador.getId());
        } catch (SQLException ex) {
            vista.mostrarError("No se pudo cargar el historial: " + ex.getMessage());
            partidas = new ArrayList<>();
        }
        vista.limpiarHistorial();
        for (Partida partida : partidas) {
            vista.agregarPartida(partida.getFecha(), partida.getIntentos(),
                    partida.getPuntaje(), partida.isVigente());
        }
    }

    private void guardar() {
        String nombreAnterior = jugador.getNombre();
        String avatarAnterior = jugador.getAvatar();
        try {
            jugador.setNombre(vista.getNombreIngresado());
            jugador.setAvatar(vista.getAvatarElegido());

            Jugador otro = jugadorDAO.buscarPorNombre(jugador.getNombre());
            if (otro != null && otro.getId() != jugador.getId()) {
                throw new IllegalArgumentException("Ese nombre ya está en uso. Probá con otro.");
            }

            jugadorDAO.modificar(jugador);
            vista.mostrarDatos(jugador.getNombre(), jugador.getAvatar());
            vista.mostrarMensaje("¡Cambios guardados!");
        } catch (IllegalArgumentException ex) {
            restaurar(nombreAnterior, avatarAnterior);
            vista.mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            restaurar(nombreAnterior, avatarAnterior);
            vista.mostrarError("No se pudieron guardar los cambios: " + ex.getMessage());
        }
    }

    private void restaurar(String nombre, String avatar) {
        jugador.setNombre(nombre);
        jugador.setAvatar(avatar);
    }

    private void eliminarPartida() {
        int fila = vista.getFilaSeleccionada();
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
            cargarHistorial();
            vista.mostrarMensaje("Partida eliminada.");
        } catch (SQLException ex) {
            vista.mostrarError("No se pudo eliminar la partida: " + ex.getMessage());
        }
    }
}
