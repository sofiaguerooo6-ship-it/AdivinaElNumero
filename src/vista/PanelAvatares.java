package vista;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Una fila de avatares donde se puede elegir uno solo.
 *
 * @author Sofi
 */
public class PanelAvatares extends JPanel {

    private static final int TAMANIO = 72;

    private final JToggleButton[] botones = new JToggleButton[Avatares.CANTIDAD];

    public PanelAvatares() {
        super(new GridLayout(1, Avatares.CANTIDAD, 10, 0));
        setOpaque(false);

        ButtonGroup grupo = new ButtonGroup();
        for (int i = 0; i < botones.length; i++) {
            String archivo = Avatares.nombreArchivo(i + 1);
            JToggleButton boton = new JToggleButton(Avatares.icono(archivo, TAMANIO));
            boton.setActionCommand(archivo);
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            boton.addItemListener(e -> actualizarBordes());
            grupo.add(boton);
            botones[i] = boton;
            add(boton);
        }
        botones[0].setSelected(true);
        actualizarBordes();
    }

    public String getAvatarSeleccionado() {
        for (JToggleButton boton : botones) {
            if (boton.isSelected()) {
                return boton.getActionCommand();
            }
        }
        return botones[0].getActionCommand();
    }

    public void setAvatarSeleccionado(String archivo) {
        for (JToggleButton boton : botones) {
            if (boton.getActionCommand().equals(archivo)) {
                boton.setSelected(true);
            }
        }
    }

    private void actualizarBordes() {
        for (JToggleButton boton : botones) {
            if (boton.isSelected()) {
                boton.setBorder(BorderFactory.createLineBorder(Estilo.ROSA_FUERTE, 4));
            } else {
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Estilo.ARENA_BORDE, 1),
                        BorderFactory.createEmptyBorder(3, 3, 3, 3)));
            }
        }
    }
}
