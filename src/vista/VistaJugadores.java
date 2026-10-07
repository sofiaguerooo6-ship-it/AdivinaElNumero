package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.function.IntConsumer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla del administrador: lista de jugadores (maestro)
 * y partidas del jugador seleccionado (detalle).
 *
 * @author Sofi
 */
public class VistaJugadores extends JFrame {

    private static final int TAMANIO_AVATAR = 64;

    private final DefaultTableModel modeloJugadores =
            new DefaultTableModel(new String[]{"ID", "Nombre", "Avatar"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tablaJugadores = new JTable(modeloJugadores);

    private final DefaultTableModel modeloPartidas =
            new DefaultTableModel(new String[]{"ID", "Fecha", "Intentos", "Puntaje", "Temporada actual"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tablaPartidas = new JTable(modeloPartidas);

    private final JLabel lblAvatar = new JLabel();
    private final JLabel lblDetalle = Estilo.etiqueta("Seleccioná un jugador", Estilo.SUBTITULO);
    private final JButton btnEliminarJugador = new JButton("Eliminar jugador");
    private final JButton btnEliminarPartida = new JButton("Eliminar partida");
    private final JButton btnRanking = new JButton("Ver ranking");
    private final JButton btnCerrarSesion = new JButton("Cerrar sesión");

    public VistaJugadores() {
        super("Adiviná el Número - Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Estilo.tabla(tablaJugadores);
        Estilo.tabla(tablaPartidas);
        Estilo.botonPrincipal(btnEliminarJugador);
        Estilo.botonPrincipal(btnEliminarPartida);
        Estilo.botonSecundario(btnRanking);
        Estilo.botonSecundario(btnCerrarSesion);

        JPanel centro = new JPanel(new GridLayout(1, 2, 20, 0));
        centro.setOpaque(false);
        centro.add(crearMaestro());
        centro.add(crearDetalle());

        JPanel contenido = new JPanel(new BorderLayout(0, 20));
        contenido.setOpaque(false);
        contenido.add(crearEncabezado(), BorderLayout.NORTH);
        contenido.add(centro, BorderLayout.CENTER);
        contenido.add(crearBotones(), BorderLayout.SOUTH);

        Estilo.pantallaCompleta(this, contenido);
    }

    // ----- Armado de la ventana -----

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new GridLayout(2, 1, 0, 4));
        encabezado.setOpaque(false);
        encabezado.add(Estilo.etiqueta("Panel de administración", Estilo.TITULO));
        encabezado.add(Estilo.etiqueta("Seleccioná un jugador para ver sus partidas", Estilo.NORMAL));
        return encabezado;
    }

    private JPanel crearMaestro() {
        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        abajo.setOpaque(false);
        abajo.add(btnEliminarJugador);

        JPanel maestro = new JPanel(new BorderLayout(0, 10));
        Estilo.tarjeta(maestro);
        maestro.add(Estilo.etiqueta("Jugadores", Estilo.SUBTITULO), BorderLayout.NORTH);
        maestro.add(Estilo.conScroll(tablaJugadores, 420, 320), BorderLayout.CENTER);
        maestro.add(abajo, BorderLayout.SOUTH);
        return maestro;
    }

    private JPanel crearDetalle() {
        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        arriba.setOpaque(false);
        arriba.add(lblAvatar);
        arriba.add(lblDetalle);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        abajo.setOpaque(false);
        abajo.add(btnEliminarPartida);

        JPanel detalle = new JPanel(new BorderLayout(0, 10));
        Estilo.tarjeta(detalle);
        detalle.add(arriba, BorderLayout.NORTH);
        detalle.add(Estilo.conScroll(tablaPartidas, 480, 320), BorderLayout.CENTER);
        detalle.add(abajo, BorderLayout.SOUTH);
        return detalle;
    }

    private JPanel crearBotones() {
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botones.setOpaque(false);
        botones.add(btnRanking);
        botones.add(btnCerrarSesion);
        return botones;
    }

    // ----- Lo que muestra -----

    public void limpiarJugadores() {
        modeloJugadores.setRowCount(0);
    }

    public void agregarJugador(int id, String nombre, String avatar) {
        modeloJugadores.addRow(new Object[]{id, nombre, avatar});
    }

    public void limpiarPartidas() {
        modeloPartidas.setRowCount(0);
    }

    public void agregarPartida(int id, String fecha, int intentos, int puntaje, boolean vigente) {
        modeloPartidas.addRow(new Object[]{id, fecha, intentos, puntaje, vigente ? "Sí" : "No"});
    }

    public void mostrarDetalle(String titulo, String archivoAvatar) {
        lblDetalle.setText(titulo);
        lblAvatar.setIcon(archivoAvatar == null ? null : Avatares.icono(archivoAvatar, TAMANIO_AVATAR));
    }

    // ----- Lo que eligió el usuario -----

    public int getPartidaSeleccionada() {
        return tablaPartidas.getSelectedRow();
    }

    // ----- Avisos al controlador -----

    public void alSeleccionarJugador(IntConsumer accion) {
        tablaJugadores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                accion.accept(tablaJugadores.getSelectedRow());
            }
        });
    }

    public void alEliminarJugador(ActionListener accion) {
        btnEliminarJugador.addActionListener(accion);
    }

    public void alEliminarPartida(ActionListener accion) {
        btnEliminarPartida.addActionListener(accion);
    }

    public void alVerRanking(ActionListener accion) {
        btnRanking.addActionListener(accion);
    }

    public void alCerrarSesion(ActionListener accion) {
        btnCerrarSesion.addActionListener(accion);
    }

    // ----- Mensajes -----

    public boolean confirmar(String pregunta) {
        int respuesta = JOptionPane.showConfirmDialog(this, pregunta, "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

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
