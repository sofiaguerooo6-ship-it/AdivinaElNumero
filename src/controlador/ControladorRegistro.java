package controlador;

import dao.JugadorDAO;
import java.sql.SQLException;
import modelo.Jugador;
import vista.VistaRegistro;

/**
 * Registra un jugador nuevo.
 *
 * @author Sofi
 */
public class ControladorRegistro {

    private final VistaRegistro vista;
    private final JugadorDAO jugadorDAO = new JugadorDAO();
    private Jugador jugadorRegistrado;

    public ControladorRegistro(VistaRegistro vista) {
        this.vista = vista;
        vista.alCrear(e -> crear());
        vista.alCancelar(e -> vista.cerrar());
    }

    public Jugador mostrar() {
        vista.setVisible(true);
        return jugadorRegistrado;
    }

    private void crear() {
        try {
            Jugador nuevo = new Jugador(vista.getNombreIngresado(), vista.getAvatarElegido());
            if (jugadorDAO.buscarPorNombre(nuevo.getNombre()) != null) {
                vista.mostrarError("Ese nombre ya está en uso. Probá con otro.");
                return;
            }
            jugadorDAO.insertar(nuevo);
            jugadorRegistrado = nuevo;
            vista.cerrar();
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            vista.mostrarError("Error con la base de datos: " + ex.getMessage());
        }
    }
}
