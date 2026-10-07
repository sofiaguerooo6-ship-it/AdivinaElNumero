package controlador;

import dao.JugadorDAO;
import dao.RankingDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.EntradaRanking;
import modelo.Jugador;
import modelo.Usuario;
import vista.VistaPerfil;
import vista.VistaRanking;

/**
 * Muestra el ranking. Se comporta distinto según quién lo abra:
 * el jugador ve su fila resaltada, el administrador puede reiniciarlo.
 *
 * @author Sofi
 */
public class ControladorRanking {

    private final VistaRanking vista;
    private final Usuario usuario;
    private final RankingDAO rankingDAO = new RankingDAO();
    private final JugadorDAO jugadorDAO = new JugadorDAO();
    private List<EntradaRanking> ranking = new ArrayList<>();

    public ControladorRanking(VistaRanking vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;

        vista.mostrarReiniciar(usuario.puedeEliminar());
        vista.alReiniciar(e -> reiniciar());
        vista.alVolver(e -> vista.cerrar());
        vista.alHacerDobleClic(fila -> abrirPerfil(fila));

        cargar();
    }

    public void mostrar() {
        vista.setVisible(true);
    }

    private void cargar() {
        try {
            ranking = rankingDAO.listar();
        } catch (SQLException ex) {
            vista.mostrarError("No se pudo cargar el ranking: " + ex.getMessage());
            ranking = new ArrayList<>();
        }

        vista.limpiarTabla();
        for (int i = 0; i < ranking.size(); i++) {
            EntradaRanking entrada = ranking.get(i);
            vista.agregarFila(i + 1, entrada.getNombreJugador(), entrada.getIntentos(),
                    entrada.getMejorPuntaje(), entrada.getFecha());
            if (usuario.esElJugador(entrada.getJugadorId())) {
                vista.resaltarFila(i);
            }
        }
        vista.mostrarResumen(armarResumen());
    }

    private String armarResumen() {
        if (ranking.isEmpty()) {
            return "Todavía no hay récords en esta temporada.";
        }
        for (int i = 0; i < ranking.size(); i++) {
            EntradaRanking entrada = ranking.get(i);
            if (usuario.esElJugador(entrada.getJugadorId())) {
                if (i == 0) {
                    return "¡Vas en el primer puesto con " + entrada.getMejorPuntaje() + " puntos!";
                }
                EntradaRanking anterior = ranking.get(i - 1);
                int faltan = anterior.getMejorPuntaje() - entrada.getMejorPuntaje();
                String comparacion = faltan > 0
                        ? "¡Te faltan " + faltan + " para alcanzar a " + anterior.getNombreJugador() + "!"
                        : "¡Empatás con " + anterior.getNombreJugador() + ", pero llegó primero!";
                return "Estás en el puesto " + (i + 1) + " con "
                        + entrada.getMejorPuntaje() + " puntos. " + comparacion;
            }
        }
        return usuario.puedeJugar()
                ? "Todavía no tenés un récord en esta temporada. ¡A jugar!"
                : " ";
    }

    private void reiniciar() {
        if (!vista.confirmar("¿Seguro que querés reiniciar el ranking?\nEsto no se puede deshacer.")) {
            return;
        }
        try {
            rankingDAO.reiniciar();
            cargar();
            vista.mostrarMensaje("El ranking se reinició. ¡Empieza una nueva temporada!");
        } catch (SQLException ex) {
            vista.mostrarError("No se pudo reiniciar el ranking: " + ex.getMessage());
        }
    }

    private void abrirPerfil(int fila) {
        EntradaRanking entrada = ranking.get(fila);
        try {
            Jugador jugador = jugadorDAO.buscarPorId(entrada.getJugadorId());
            if (jugador == null) {
                vista.mostrarError("Ese jugador ya no existe.");
                cargar();
                return;
            }
            new ControladorPerfil(new VistaPerfil(vista), usuario, jugador).mostrar();
            cargar();
        } catch (SQLException ex) {
            vista.mostrarError("No se pudo abrir el perfil: " + ex.getMessage());
        }
    }
}
