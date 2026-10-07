package vista;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Perfil de un jugador: sus datos y su historial de partidas.
 *
 * @author Sofi
 */
public class VistaPerfil extends JDialog {

    private static final String[] COLUMNAS = {"Fecha", "Intentos", "Puntaje", "Temporada actual"};
    private static final int TAMANIO_AVATAR = 96;

    private final JLabel lblTitulo = Estilo.etiqueta("", Estilo.SUBTITULO);
    private final JTextField txtNombre = new JTextField(20);
    private final PanelAvatares panelAvatares = new PanelAvatares();
    private final JLabel lblAvatarGrande = new JLabel();
    private final JButton btnGuardar = new JButton("Guardar cambios");

    private final DefaultTableModel modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modeloTabla);
    private final JButton btnEliminarPartida = new JButton("Eliminar partida");
    private final JButton btnVolver = new JButton("Volver");

    public VistaPerfil(Window duenio) {
        super(duenio, "Perfil", Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        txtNombre.setFont(Estilo.NORMAL);
        Estilo.botonPrincipal(btnGuardar);
        Estilo.botonPrincipal(btnEliminarPartida);
        Estilo.botonSecundario(btnVolver);
        Estilo.tabla(tabla);

        JPanel tarjeta = new JPanel(new BorderLayout(0, 18));
        Estilo.tarjeta(tarjeta);
        tarjeta.add(crearDatos(), BorderLayout.NORTH);
        tarjeta.add(crearHistorial(), BorderLayout.CENTER);
        tarjeta.add(crearBotones(), BorderLayout.SOUTH);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(Estilo.FONDO);
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        fondo.add(tarjeta);
        setContentPane(fondo);

        pack();
        setLocationRelativeTo(duenio);
    }

    // ----- Armado de la ventana -----

    private JPanel crearDatos() {
        JPanel nombre = new JPanel(new GridLayout(3, 1, 0, 6));
        nombre.setOpaque(false);
        nombre.add(lblTitulo);
        nombre.add(Estilo.etiqueta("Nombre", Estilo.NEGRITA));
        nombre.add(txtNombre);

        JPanel avatares = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        avatares.setOpaque(false);
        avatares.add(lblAvatarGrande);
        avatares.add(panelAvatares);

        JPanel avatar = new JPanel(new BorderLayout(0, 6));
        avatar.setOpaque(false);
        avatar.add(Estilo.etiqueta("Avatar", Estilo.NEGRITA), BorderLayout.NORTH);
        avatar.add(avatares, BorderLayout.CENTER);

        JPanel guardar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        guardar.setOpaque(false);
        guardar.add(btnGuardar);

        JPanel datos = new JPanel(new BorderLayout(0, 12));
        datos.setOpaque(false);
        datos.add(nombre, BorderLayout.NORTH);
        datos.add(avatar, BorderLayout.CENTER);
        datos.add(guardar, BorderLayout.SOUTH);
        return datos;
    }

    private JPanel crearHistorial() {
        JPanel historial = new JPanel(new BorderLayout(0, 6));
        historial.setOpaque(false);
        historial.add(Estilo.etiqueta("Historial de partidas", Estilo.NEGRITA), BorderLayout.NORTH);
        historial.add(Estilo.conScroll(tabla, 560, 180), BorderLayout.CENTER);
        return historial;
    }

    private JPanel crearBotones() {
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botones.setOpaque(false);
        botones.add(btnEliminarPartida);
        botones.add(btnVolver);
        return botones;
    }

    // ----- Lo que muestra -----

    public void mostrarDatos(String nombre, String archivoAvatar) {
        setTitle("Perfil de " + nombre);
        lblTitulo.setText("Perfil de " + nombre);
        txtNombre.setText(nombre);
        panelAvatares.setAvatarSeleccionado(archivoAvatar);
        lblAvatarGrande.setIcon(Avatares.icono(archivoAvatar, TAMANIO_AVATAR));
    }

    public void setEditable(boolean editable) {
        txtNombre.setEditable(editable);
        panelAvatares.setVisible(editable);
        lblAvatarGrande.setVisible(!editable);
        btnGuardar.setVisible(editable);
        pack();
        setLocationRelativeTo(getOwner());
    }

    public void mostrarEliminarPartida(boolean visible) {
        btnEliminarPartida.setVisible(visible);
    }

    public void limpiarHistorial() {
        modeloTabla.setRowCount(0);
    }

    public void agregarPartida(String fecha, int intentos, int puntaje, boolean vigente) {
        modeloTabla.addRow(new Object[]{fecha, intentos, puntaje, vigente ? "Sí" : "No"});
    }

    // ----- Datos que eligió el usuario -----

    public String getNombreIngresado() {
        return txtNombre.getText();
    }

    public String getAvatarElegido() {
        return panelAvatares.getAvatarSeleccionado();
    }

    public int getFilaSeleccionada() {
        return tabla.getSelectedRow();
    }

    // ----- Avisos al controlador -----

    public void alGuardar(ActionListener accion) {
        btnGuardar.addActionListener(accion);
    }

    public void alEliminarPartida(ActionListener accion) {
        btnEliminarPartida.addActionListener(accion);
    }

    public void alVolver(ActionListener accion) {
        btnVolver.addActionListener(accion);
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
