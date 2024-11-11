package com.albincystudio.database;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    public static Boolean CheckDatabaseConnection(){
        try (Connection connection = SQLServerConnection.getConnection()){
            return connection != null;
        }catch (SQLException e){
            return false;
        }
    }
}
