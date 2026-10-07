package app;

import controlador.ControladorInicio;
import dao.BaseDeDatos;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import vista.VistaInicio;

/**
 * Punto de entrada del juego.
 *
 * @author Sofi
 */
public class Main {

    public static void main(String[] args) {
        try {
            BaseDeDatos.crearTablas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo preparar la base de datos:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            VistaInicio vista = new VistaInicio();
            ControladorInicio controlador = new ControladorInicio(vista);
            controlador.iniciar();
        });
    }
}