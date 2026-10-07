package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Se encarga de abrir conexiones con la base de datos SQLite.
 *
 * @author Sofi
 */
public class Conexion {

    private static final String URL = "jdbc:sqlite:adivina.db";

    private Conexion() {
    }

    public static Connection getConexion() throws SQLException {
        Connection conexion = DriverManager.getConnection(URL);
        try (Statement st = conexion.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return conexion;
    }
}
