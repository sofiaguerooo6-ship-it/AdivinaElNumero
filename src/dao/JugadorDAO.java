package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Jugador;

/**
 * Guarda y lee jugadores de la base de datos.
 *
 * @author Sofi
 */
public class JugadorDAO {

    public void insertar(Jugador jugador) throws SQLException {
        String sql = "INSERT INTO jugador (nombre, avatar) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getAvatar());
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    jugador.setId(claves.getInt(1));
                }
            }
        }
    }

    public void modificar(Jugador jugador) throws SQLException {
        String sql = "UPDATE jugador SET nombre = ?, avatar = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getAvatar());
            ps.setInt(3, jugador.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM jugador WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Jugador buscarPorNombre(String nombre) throws SQLException {
        String sql = "SELECT id, nombre, avatar FROM jugador WHERE nombre = ? COLLATE NOCASE";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearJugador(rs);
                }
            }
        }
        return null;
    }

    public Jugador buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, avatar FROM jugador WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearJugador(rs);
                }
            }
        }
        return null;
    }

    public List<Jugador> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, avatar FROM jugador ORDER BY nombre";
        List<Jugador> jugadores = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                jugadores.add(crearJugador(rs));
            }
        }
        return jugadores;
    }

    private Jugador crearJugador(ResultSet rs) throws SQLException {
        return new Jugador(rs.getInt("id"), rs.getString("nombre"), rs.getString("avatar"));
    }
}
