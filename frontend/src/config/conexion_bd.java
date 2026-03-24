package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion_bd {
    private static final String URL = "jdbc:mysql://localhost:3306/presupuesto_personal";
    private static final String USER = "root";
    private static final String PASSWORD = "JosMad_2026";

    public static Connection obtener_conexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void probar_conexion() {
        try (Connection conexion = obtener_conexion()) {
            System.out.println("La conexion fue exitosa y se conecto a mysql");
        } catch (SQLException e) {
            System.out.println("Lo siento, error de conexion:");
            System.out.println(e.getMessage());
        }
    }
}