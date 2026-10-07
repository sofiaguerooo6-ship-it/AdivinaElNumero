package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Pantalla donde el jugador adivina el número.
 *
 * @author Sofi
 */
public class VistaJugar extends JFrame {

    private static final int TAMANIO_AVATAR = 56;

    private final JLabel lblAvatar = new JLabel();
    private final JLabel lblJugador = Estilo.etiqueta("", Estilo.SUBTITULO);
    private final JLabel lblConsigna = Estilo.etiqueta("", Estilo.SUBTITULO);
    private final JTextField txtNumero = new JTextField(4);
    private final JButton btnProbar = new JButton("Probar");
    private final JLabel lblResultado = Estilo.etiqueta(" ", Estilo.TITULO);
    private final JLabel lblIntentos = Estilo.etiqueta("", Estilo.NORMAL);
    private final JButton btnNuevaPartida = new JButton("Nueva partida");
    private final JButton btnRanking = new JButton("Ver ranking");
    private final JButton btnPerfil = new JButton("Mi perfil");
    private final JButton btnCerrarSesion = new JButton("Cerrar sesión");

    public VistaJugar() {
        super("Adivina el número");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Estilo.botonPrincipal(btnProbar);
        Estilo.botonSecundario(btnNuevaPartida);
        Estilo.botonSecundario(btnRanking);
        Estilo.botonSecundario(btnPerfil);
        Estilo.botonSecundario(btnCerrarSesion);

        JPanel contenido = new JPanel(new BorderLayout(0, 20));
        contenido.setOpaque(false);
        contenido.add(crearEncabezado(), BorderLayout.NORTH);
        contenido.add(crearZonaJuego(), BorderLayout.CENTER);
        contenido.add(crearBotones(), BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnProbar);
        Estilo.pantallaCompleta(this, contenido);
    }

    // ----- Armado de la ventana -----

    private JPanel crearEncabezado() {
        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(Estilo.etiqueta("Jugando como", Estilo.NORMAL));
        textos.add(lblJugador);

        JPanel encabezado = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        Estilo.tarjeta(encabezado);
        encabezado.add(lblAvatar);
        encabezado.add(textos);
        return encabezado;
    }

    private JPanel crearZonaJuego() {
        txtNumero.setFont(Estilo.TITULO);
        txtNumero.setHorizontalAlignment(JTextField.CENTER);

        JPanel entrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        entrada.setOpaque(false);
        entrada.add(txtNumero);
        entrada.add(btnProbar);

        lblConsigna.setHorizontalAlignment(SwingConstants.CENTER);
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        lblResultado.setForeground(Estilo.ROSA_FUERTE);
        lblIntentos.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel zona = new JPanel(new GridLayout(4, 1, 0, 16));
        Estilo.tarjeta(zona);
        zona.add(lblConsigna);
        zona.add(entrada);
        zona.add(lblResultado);
        zona.add(lblIntentos);
        return zona;
    }

    private JPanel crearBotones() {
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botones.setOpaque(false);
        botones.add(btnNuevaPartida);
        botones.add(btnRanking);
        botones.add(btnPerfil);
        botones.add(btnCerrarSesion);
        return botones;
    }

    // ----- Lo que muestra -----

    public void mostrarJugador(String nombre, String archivoAvatar) {
        lblJugador.setText(nombre);
        lblAvatar.setIcon(Avatares.icono(archivoAvatar, TAMANIO_AVATAR));
    }

    public void setConsigna(String consigna) {
        lblConsigna.setText(consigna);
    }

    public void mostrarResultado(String resultado) {
        lblResultado.setText(resultado);
    }

    public void mostrarIntentos(int intentos) {
        lblIntentos.setText("Intentos: " + intentos);
    }

    public void habilitarJuego(boolean habilitado) {
        txtNumero.setEnabled(habilitado);
        btnProbar.setEnabled(habilitado);
    }

    public void limpiarNumero() {
        txtNumero.setText("");
        txtNumero.requestFocusInWindow();
    }

    // ----- Datos que escribió el usuario -----

    public String getNumeroIngresado() {
        return txtNumero.getText();
    }

    // ----- Avisos al controlador -----

    public void alProbar(ActionListener accion) {
        btnProbar.addActionListener(accion);
    }

    public void alNuevaPartida(ActionListener accion) {
        btnNuevaPartida.addActionListener(accion);
    }

    public void alVerRanking(ActionListener accion) {
        btnRanking.addActionListener(accion);
    }

    public void alMiPerfil(ActionListener accion) {
        btnPerfil.addActionListener(accion);
    }

    public void alCerrarSesion(ActionListener accion) {
        btnCerrarSesion.addActionListener(accion);
    }

    // ----- Mensajes -----

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.WARNING_MESSAGE);
    }

    public void cerrar() {
        dispose();
    }
}
