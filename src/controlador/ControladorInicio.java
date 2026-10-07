package controlador;

import dao.JugadorDAO;
import java.sql.SQLException;
import modelo.Administrador;
import modelo.Jugador;
import modelo.UsuarioJugador;
import vista.VistaInicio;
import vista.VistaJugadores;
import vista.VistaJugar;
import vista.VistaRegistro;

/**
 * Maneja el inicio de sesión de jugadores y del administrador.
 *
 * @author Sofi
 */
public class ControladorInicio {

    private final VistaInicio vista;
    private final JugadorDAO jugadorDAO = new JugadorDAO();

    public ControladorInicio(VistaInicio vista) {
        this.vista = vista;
        vista.alIngresarJugador(e -> ingresarComoJugador());
        vista.alRegistrarse(e -> registrarse());
        vista.alIngresarAdmin(e -> ingresarComoAdmin());
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    private void ingresarComoJugador() {
        String nombre = vista.getNombreIngresado();
        if (nombre.isBlank()) {
            vista.mostrarError("Escribí tu nombre de usuario.");
            return;
        }
        try {
            Jugador jugador = jugadorDAO.buscarPorNombre(nombre);
            if (jugador == null) {
                vista.mostrarError("No existe ningún jugador con ese nombre.\n"
                        + "Si es tu primera vez, apretá Registrarme.");
                return;
            }
            abrirJuego(new UsuarioJugador(jugador));
        } catch (SQLException ex) {
            vista.mostrarError("Error con la base de datos: " + ex.getMessage());
        }
    }

    private void registrarse() {
        VistaRegistro vistaRegistro = new VistaRegistro(vista);
        vistaRegistro.setNombre(vista.getNombreIngresado());
        ControladorRegistro controladorRegistro = new ControladorRegistro(vistaRegistro);

        Jugador nuevo = controladorRegistro.mostrar();
        if (nuevo != null) {
            abrirJuego(new UsuarioJugador(nuevo));
        }
    }

    private void ingresarComoAdmin() {
        if (Administrador.contraseniaCorrecta(vista.getContraseniaIngresada())) {
            abrirAdministracion(new Administrador());
        } else {
            vista.mostrarError("Contraseña incorrecta.");
            vista.limpiarContrasenia();
        }
    }

    private void abrirJuego(UsuarioJugador usuario) {
        vista.cerrar();
        new ControladorJugar(new VistaJugar(), usuario).iniciar();
    }

        private void abrirAdministracion(Administrador administrador) {
        vista.cerrar();
        new ControladorJugadores(new VistaJugadores(), administrador).iniciar();
    }
}
