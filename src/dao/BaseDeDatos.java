package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Crea las tablas de la base de datos si todavía no existen.
 *
 * @author Sofi
 */
public class BaseDeDatos {

    private BaseDeDatos() {
    }

    public static void crearTablas() throws SQLException {
        String sqlJugador = """
            CREATE TABLE IF NOT EXISTS jugador (
                id      INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre  TEXT    NOT NULL UNIQUE,
                avatar  TEXT    NOT NULL
            )
            """;

        String sqlPartida = """
            CREATE TABLE IF NOT EXISTS partida (
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                jugador_id  INTEGER NOT NULL,
                intentos    INTEGER NOT NULL,
                puntaje     INTEGER NOT NULL,
                fecha       TEXT    NOT NULL,
                vigente     INTEGER NOT NULL DEFAULT 1,
                FOREIGN KEY (jugador_id) REFERENCES jugador(id) ON DELETE CASCADE
            )
            """;

        String sqlRanking = """
            CREATE TABLE IF NOT EXISTS ranking (
                id             INTEGER PRIMARY KEY AUTOINCREMENT,
                jugador_id     INTEGER NOT NULL UNIQUE,
                intentos       INTEGER NOT NULL,
                mejor_puntaje  INTEGER NOT NULL,
                fecha_record   TEXT    NOT NULL,
                FOREIGN KEY (jugador_id) REFERENCES jugador(id) ON DELETE CASCADE
            )
            """;

        try (Connection conexion = Conexion.getConexion();
             Statement st = conexion.createStatement()) {
            st.execute(sqlJugador);
            st.execute(sqlPartida);
            st.execute(sqlRanking);
        }
    }
}
