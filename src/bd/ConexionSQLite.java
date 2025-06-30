package db;

import java.sql.*;

public class ConexionSQLite {

    private static final String URL = "jdbc:sqlite:usuarios.db";

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            crearTablaSiNoExiste(conn);
            return conn;
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    private static void crearTablaSiNoExiste(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "correo TEXT NOT NULL UNIQUE," +
                "clave TEXT NOT NULL)";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }
}
