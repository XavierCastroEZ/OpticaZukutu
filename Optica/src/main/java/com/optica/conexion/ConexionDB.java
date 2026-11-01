package com.optica.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:sqlserver://DESKTOP-O287UQH:1433;"
            + "databaseName=OpticaDB;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "Mortadela1";

    private static Connection conexion = null;

    private ConexionDB() {
    }

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("->Conexión exitosa a SQL");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error no se encontró el Driver de mssql-jdbc: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con SQL: " + e.getMessage());
        }

        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("->La conexión con SQL fue cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}