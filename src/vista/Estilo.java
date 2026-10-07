package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Colores, fuentes y estilos comunes a todas las ventanas.
 *
 * @author Sofi
 */
public final class Estilo {

    public static final Color FONDO = new Color(0xF3E9DC);
    public static final Color TARJETA = new Color(0xFFFDF9);
    public static final Color ROSA = new Color(0xF4BFCB);
    public static final Color ROSA_BORDE = new Color(0xD98FA3);
    public static final Color ARENA = new Color(0xE6D5BF);
    public static final Color ARENA_BORDE = new Color(0xC9B49A);
    public static final Color TEXTO = new Color(0x2E2326);
    public static final Color ROSA_FUERTE = new Color(0x9C3F5C);

    public static final Font TITULO = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font SUBTITULO = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font NEGRITA = new Font("Segoe UI", Font.BOLD, 14);

    private Estilo() {
    }

    public static void botonPrincipal(JButton boton) {
        pintarBoton(boton, ROSA, ROSA_BORDE);
    }

    public static void botonSecundario(JButton boton) {
        pintarBoton(boton, TARJETA, ARENA_BORDE);
    }

    public static void tarjeta(JComponent panel) {
        panel.setBackground(TARJETA);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ARENA_BORDE),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
    }

    private static void pintarBoton(JButton boton, Color fondo, Color borde) {
        boton.setBackground(fondo);
        boton.setForeground(TEXTO);
        boton.setFont(NEGRITA);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
    }
        public static JLabel etiqueta(String texto, Font fuente) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(fuente);
        etiqueta.setForeground(TEXTO);
        return etiqueta;
    }
        public static void pantallaCompleta(JFrame ventana, JComponent contenido) {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(FONDO);
        fondo.add(contenido);
        ventana.setContentPane(fondo);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
      
        public static void tabla(JTable tabla) {
        tabla.setFont(NORMAL);
        tabla.setRowHeight(28);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setSelectionBackground(ROSA);
        tabla.setSelectionForeground(TEXTO);
        tabla.setGridColor(ARENA);
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setFont(NEGRITA);
        tabla.getTableHeader().setBackground(ARENA);
        tabla.getTableHeader().setReorderingAllowed(false);
    }

    public static JScrollPane conScroll(JTable tabla, int ancho, int alto) {
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(ancho, alto));
        scroll.getViewport().setBackground(TARJETA);
        return scroll;
    }
}
