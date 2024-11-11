package com.albincystudio.database.users;

import com.albincystudio.database.SQLServerConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertsUsers {
    public void createUser(String uuid, String username, String password, String rol){
        String sql = "INSERT INTO TBL_USERS (USERS_UUID, USERS_NAME, USERS_PASSWORD, USERS_ROL) VALUES (?,?,?,?)";
        Connection connection = null;

        try{
            connection = SQLServerConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, rol);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.err.println("Error al insertar datos" + e.getMessage());
        } finally {
            SQLServerConnection.closeConnection(connection);
        }
    }
}
