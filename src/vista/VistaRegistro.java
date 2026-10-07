package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Ventana para crear un jugador nuevo eligiendo nombre y avatar.
 *
 * @author Sofi
 */
public class VistaRegistro extends JDialog {

    private final JTextField txtNombre = new JTextField(20);
    private final PanelAvatares panelAvatares = new PanelAvatares();
    private final JButton btnCancelar = new JButton("Cancelar");
    private final JButton btnCrear = new JButton("Crear y jugar");

    public VistaRegistro(JFrame duenio) {
        super(duenio, "Registrarme", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        txtNombre.setFont(Estilo.NORMAL);
        Estilo.botonSecundario(btnCancelar);
        Estilo.botonPrincipal(btnCrear);

        JPanel datos = new JPanel(new GridLayout(4, 1, 0, 8));
        datos.setOpaque(false);
        datos.add(Estilo.etiqueta("Creá tu jugador", Estilo.SUBTITULO));
        datos.add(Estilo.etiqueta("Nombre de usuario", Estilo.NEGRITA));
        datos.add(txtNombre);
        datos.add(Estilo.etiqueta("Elegí tu avatar", Estilo.NEGRITA));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botones.setOpaque(false);
        botones.add(btnCancelar);
        botones.add(btnCrear);

        JPanel tarjeta = new JPanel(new BorderLayout(0, 16));
        Estilo.tarjeta(tarjeta);
        tarjeta.add(datos, BorderLayout.NORTH);
        tarjeta.add(panelAvatares, BorderLayout.CENTER);
        tarjeta.add(botones, BorderLayout.SOUTH);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(Estilo.FONDO);
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        fondo.add(tarjeta);
        setContentPane(fondo);

        getRootPane().setDefaultButton(btnCrear);
        pack();
        setResizable(false);
        setLocationRelativeTo(duenio);
    }

    public String getNombreIngresado() {
        return txtNombre.getText();
    }

    public void setNombre(String nombre) {
        txtNombre.setText(nombre);
    }

    public String getAvatarElegido() {
        return panelAvatares.getAvatarSeleccionado();
    }

    public void alCrear(ActionListener accion) {
        btnCrear.addActionListener(accion);
    }

    public void alCancelar(ActionListener accion) {
        btnCancelar.addActionListener(accion);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.WARNING_MESSAGE);
    }

    public void cerrar() {
        dispose();
    }
}
