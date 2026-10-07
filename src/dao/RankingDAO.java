package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.EntradaRanking;

/**
 * Mantiene la tabla de récords sincronizada con las partidas.
 *
 * @author Sofi
 */
public class RankingDAO {

    public void recalcular(int jugadorId) throws SQLException {
        String sqlMejor = """
            SELECT intentos, puntaje, fecha
            FROM partida
            WHERE jugador_id = ? AND vigente = 1
            ORDER BY puntaje DESC, fecha ASC
            LIMIT 1
            """;
        String sqlGuardar = """
            INSERT OR REPLACE INTO ranking (jugador_id, intentos, mejor_puntaje, fecha_record)
            VALUES (?, ?, ?, ?)
            """;
        String sqlBorrar = "DELETE FROM ranking WHERE jugador_id = ?";

        try (Connection con = Conexion.getConexion()) {
            boolean tienePartidas;
            int intentos = 0;
            int puntaje = 0;
            String fecha = null;

            try (PreparedStatement ps = con.prepareStatement(sqlMejor)) {
                ps.setInt(1, jugadorId);
                try (ResultSet rs = ps.executeQuery()) {
                    tienePartidas = rs.next();
                    if (tienePartidas) {
                        intentos = rs.getInt("intentos");
                        puntaje = rs.getInt("puntaje");
                        fecha = rs.getString("fecha");
                    }
                }
            }

            if (tienePartidas) {
                try (PreparedStatement ps = con.prepareStatement(sqlGuardar)) {
                    ps.setInt(1, jugadorId);
                    ps.setInt(2, intentos);
                    ps.setInt(3, puntaje);
                    ps.setString(4, fecha);
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement(sqlBorrar)) {
                    ps.setInt(1, jugadorId);
                    ps.executeUpdate();
                }
            }
        }
    }

    public List<EntradaRanking> listar() throws SQLException {
        String sql = """
            SELECT r.jugador_id, j.nombre, r.intentos, r.mejor_puntaje, r.fecha_record
            FROM ranking r
            JOIN jugador j ON r.jugador_id = j.id
            ORDER BY r.mejor_puntaje DESC, r.fecha_record ASC
            """;
        List<EntradaRanking> ranking = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ranking.add(new EntradaRanking(
                        rs.getInt("jugador_id"),
                        rs.getString("nombre"),
                        rs.getInt("intentos"),
                        rs.getInt("mejor_puntaje"),
                        rs.getString("fecha_record")));
            }
        }
        return ranking;
    }

    public void reiniciar() throws SQLException {
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement()) {
            con.setAutoCommit(false);
            try {
                st.executeUpdate("UPDATE partida SET vigente = 0");
                st.executeUpdate("DELETE FROM ranking");
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }
}
    

