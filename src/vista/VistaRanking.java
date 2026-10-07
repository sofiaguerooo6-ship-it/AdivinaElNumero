package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.IntConsumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Muestra los récords de la temporada actual.
 *
 * @author Sofi
 */
public class VistaRanking extends JDialog {

    private static final String[] COLUMNAS = {"Puesto", "Jugador", "Intentos", "Mejor puntaje", "Fecha"};

    private final DefaultTableModel modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modeloTabla);
    private final JLabel lblResumen = Estilo.etiqueta(" ", Estilo.NEGRITA);
    private final JButton btnReiniciar = new JButton("Reiniciar ranking");
    private final JButton btnVolver = new JButton("Volver");

    public VistaRanking(JFrame duenio) {
        super(duenio, "Ranking", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Estilo.botonPrincipal(btnReiniciar);
        Estilo.botonSecundario(btnVolver);
        configurarTabla();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(640, 260));
        scroll.getViewport().setBackground(Estilo.TARJETA);

        JPanel arriba = new JPanel(new GridLayout(2, 1, 0, 4));
        arriba.setOpaque(false);
        arriba.add(Estilo.etiqueta("Mejores puntajes", Estilo.SUBTITULO));
        arriba.add(Estilo.etiqueta("Doble clic en un jugador para ver su perfil", Estilo.NORMAL));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botones.setOpaque(false);
        botones.add(btnReiniciar);
        botones.add(btnVolver);

        JPanel abajo = new JPanel(new BorderLayout(0, 12));
        abajo.setOpaque(false);
        abajo.add(lblResumen, BorderLayout.NORTH);
        abajo.add(botones, BorderLayout.SOUTH);

        JPanel tarjeta = new JPanel(new BorderLayout(0, 14));
        Estilo.tarjeta(tarjeta);
        tarjeta.add(arriba, BorderLayout.NORTH);
        tarjeta.add(scroll, BorderLayout.CENTER);
        tarjeta.add(abajo, BorderLayout.SOUTH);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(Estilo.FONDO);
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        fondo.add(tarjeta);
        setContentPane(fondo);

        pack();
        setLocationRelativeTo(duenio);
    }

    private void configurarTabla() {
        tabla.setFont(Estilo.NORMAL);
        tabla.setRowHeight(28);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setSelectionBackground(Estilo.ROSA);
        tabla.setSelectionForeground(Estilo.TEXTO);
        tabla.setGridColor(Estilo.ARENA);
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setFont(Estilo.NEGRITA);
        tabla.getTableHeader().setBackground(Estilo.ARENA);
        tabla.getTableHeader().setReorderingAllowed(false);
    }

    // ----- Lo que muestra -----

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public void agregarFila(int puesto, String jugador, int intentos, int puntaje, String fecha) {
        modeloTabla.addRow(new Object[]{puesto, jugador, intentos, puntaje, fecha});
    }

    public void resaltarFila(int fila) {
        tabla.setRowSelectionInterval(fila, fila);
    }

    public void mostrarResumen(String texto) {
        lblResumen.setText(texto);
    }

    public void mostrarReiniciar(boolean visible) {
        btnReiniciar.setVisible(visible);
    }

    // ----- Avisos al controlador -----

    public void alReiniciar(ActionListener accion) {
        btnReiniciar.addActionListener(accion);
    }

    public void alVolver(ActionListener accion) {
        btnVolver.addActionListener(accion);
    }

    public void alHacerDobleClic(IntConsumer accion) {
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tabla.rowAtPoint(e.getPoint());
                    if (fila >= 0) {
                        accion.accept(fila);
                    }
                }
            }
        });
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
