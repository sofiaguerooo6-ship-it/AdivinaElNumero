package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Partida;

/**
 * Guarda, lista y elimina partidas. Después de cada cambio,
 * recalcula el ranking del jugador.
 *
 * @author Sofi
 */
public class PartidaDAO {

    private final RankingDAO rankingDAO = new RankingDAO();

    public void insertar(Partida partida) throws SQLException {
        String sql = """
            INSERT INTO partida (jugador_id, intentos, puntaje, fecha, vigente)
            VALUES (?, ?, ?, ?, ?)
            """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, partida.getJugadorId());
            ps.setInt(2, partida.getIntentos());
            ps.setInt(3, partida.getPuntaje());
            ps.setString(4, partida.getFecha());
            ps.setInt(5, partida.isVigente() ? 1 : 0);
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    partida.setId(claves.getInt(1));
                }
            }
        }
        rankingDAO.recalcular(partida.getJugadorId());
    }

    public void eliminar(Partida partida) throws SQLException {
        String sql = "DELETE FROM partida WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, partida.getId());
            ps.executeUpdate();
        }
        rankingDAO.recalcular(partida.getJugadorId());
    }

    public List<Partida> listarPorJugador(int jugadorId) throws SQLException {
        String sql = """
            SELECT id, jugador_id, intentos, puntaje, fecha, vigente
            FROM partida
            WHERE jugador_id = ?
            ORDER BY fecha DESC, id DESC
            """;
        List<Partida> partidas = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, jugadorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    partidas.add(new Partida(
                            rs.getInt("id"),
                            rs.getInt("jugador_id"),
                            rs.getInt("intentos"),
                            rs.getInt("puntaje"),
                            rs.getString("fecha"),
                            rs.getInt("vigente") == 1));
                }
            }
        }
        return partidas;
    }
}
    

