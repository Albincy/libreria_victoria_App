package com.albincystudio.database;

import com.albincystudio.ui.CustomErrorDialog;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnection {
    private static final Logger LOGGER = Logger.getLogger(SQLServerConnection.class.getName());
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=db_admin_library;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "240715";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error al conectar a la base de datos", e);
            CustomErrorDialog.showWindows("Error al conectar a la base de datos", e.getMessage());
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error cerrando la conexión", e);
            }
        }
    }
}
