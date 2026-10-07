package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Ventana de inicio: el jugador entra con su nombre
 * y el administrador con su contraseña.
 *
 * @author Sofi
 */
public class VistaInicio extends JFrame {

    private final JTextField txtNombre = new JTextField(18);
    private final JPasswordField txtContrasenia = new JPasswordField(18);
    private final JButton btnIngresarJugador = new JButton("Ingresar");
    private final JButton btnRegistrarme = new JButton("Registrarme");
    private final JButton btnIngresarAdmin = new JButton("Ingresar");

    public VistaInicio() {
        super("Adiviná el número");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contenido = new JPanel(new BorderLayout(0, 24));
        contenido.setBackground(Estilo.FONDO);
        contenido.setBorder(BorderFactory.createEmptyBorder(32, 40, 40, 40));
        contenido.add(crearEncabezado(), BorderLayout.NORTH);
        contenido.add(crearTarjetas(), BorderLayout.CENTER);
        Estilo.pantallaCompleta(this, contenido);
    }

    // ----- Armado de la ventana -----

    private JPanel crearEncabezado() {
        JLabel titulo = Estilo.etiqueta("Adivina el número", Estilo.TITULO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel subtitulo = Estilo.etiqueta("¿Cómo querés entrar?", Estilo.NORMAL);
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 0, 6));
        encabezado.setOpaque(false);
        encabezado.add(titulo);
        encabezado.add(subtitulo);
        return encabezado;
    }

    private JPanel crearTarjetas() {
        JPanel tarjetas = new JPanel(new GridLayout(1, 2, 20, 0));
        tarjetas.setOpaque(false);
        tarjetas.add(crearTarjetaJugador());
        tarjetas.add(crearTarjetaAdmin());
        return tarjetas;
    }

    private JPanel crearTarjetaJugador() {
        Estilo.botonPrincipal(btnIngresarJugador);
        Estilo.botonSecundario(btnRegistrarme);

        JPanel botones = new JPanel(new GridLayout(1, 2, 8, 0));
        botones.setOpaque(false);
        botones.add(btnIngresarJugador);
        botones.add(btnRegistrarme);

        return crearTarjeta("Soy jugador", "Nombre de usuario", txtNombre, botones);
    }

    private JPanel crearTarjetaAdmin() {
        Estilo.botonPrincipal(btnIngresarAdmin);
        return crearTarjeta("Soy administrador", "Contraseña", txtContrasenia, btnIngresarAdmin);
    }

    private JPanel crearTarjeta(String titulo, String etiqueta,
                                JComponent campo, JComponent abajo) {
        campo.setFont(Estilo.NORMAL);

        JPanel tarjeta = new JPanel(new GridLayout(4, 1, 0, 8));
        Estilo.tarjeta(tarjeta);
        tarjeta.add(Estilo.etiqueta(titulo, Estilo.SUBTITULO));
        tarjeta.add(Estilo.etiqueta(etiqueta, Estilo.NEGRITA));
        tarjeta.add(campo);
        tarjeta.add(abajo);
        return tarjeta;
    }

    // ----- Datos que escribió el usuario -----

    public String getNombreIngresado() {
        return txtNombre.getText();
    }

    public String getContraseniaIngresada() {
        return new String(txtContrasenia.getPassword());
    }

    // ----- Avisos al controlador -----

    public void alIngresarJugador(ActionListener accion) {
        btnIngresarJugador.addActionListener(accion);
    }

    public void alRegistrarse(ActionListener accion) {
        btnRegistrarme.addActionListener(accion);
    }

    public void alIngresarAdmin(ActionListener accion) {
        btnIngresarAdmin.addActionListener(accion);
    }

    // ----- Mensajes -----

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.WARNING_MESSAGE);
    }

    public void limpiarContrasenia() {
        txtContrasenia.setText("");
    }
        public void cerrar() {
        dispose();
    }
}
