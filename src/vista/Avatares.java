package vista;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Sabe dónde están las imágenes de los avatares y cómo cargarlas.
 *
 * @author Sofi
 */
public final class Avatares {

    public static final int CANTIDAD = 5;
    private static final String CARPETA = "/recursos/avatares/";
    private static final String EXTENSION = ".jpeg";

    private Avatares() {
    }

    public static String nombreArchivo(int numero) {
        return "avatar" + numero + EXTENSION;
    }

    public static ImageIcon icono(String archivo, int tamanio) {
        URL ubicacion = Avatares.class.getResource(CARPETA + archivo);
        if (ubicacion == null) {
            return null;
        }
        Image imagen = new ImageIcon(ubicacion).getImage()
                .getScaledInstance(tamanio, tamanio, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
}
