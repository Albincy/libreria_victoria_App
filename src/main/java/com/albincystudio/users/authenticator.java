package com.albincystudio.users;

import com.albincystudio.database.SQLServerConnection;
import com.albincystudio.root.configuration;
import com.albincystudio.ui.CustomErrorDialog;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class authenticator {

    private static final Logger LOGGER = Logger.getLogger(configuration.class.getName());

    public controllerUsers auth_uuid(String uuid){
        String sql = "SELECT u.USERS_UUID, u.USERS_NAME, r.ROL_ADDUSER, r.ROL_EDITUSER, r.ROL_ADDPROD, r.ROL_EDITPROD, r.ROL_SHOWPROD, r.ROL_SETTING " +
                     "FROM TBL_USERS u " +
                     "JOIN TBL_ROLS r ON u.USERS_ROL = r.ROL_ID " +
                     "WHERE u.USERS_UUID = '"+uuid+"'";
        controllerUsers userdata = new controllerUsers();

        try(Connection connection = SQLServerConnection.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            if (rs.next()){
                userdata.setUuid(rs.getString("USERS_UUID"));
                userdata.setUserName(rs.getString("USERS_NAME"));
                userdata.setAdd_new_product(rs.getInt("ROL_ADDUSER"));
                userdata.setEdit_exist_user(rs.getInt("ROL_EDITUSER"));
                userdata.setAdd_new_product(rs.getInt("ROL_ADDPROD"));
                userdata.setEdit_exist_product(rs.getInt("ROL_EDITPROD"));
                userdata.setShow_exist_product(rs.getInt("ROL_SHOWPROD"));
                userdata.setChange_settings_app(rs.getInt("ROL_SETTING"));
            }
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error al autenticar datos", e);
            CustomErrorDialog.showWindows("Error al Autenticar usuario",e.getMessage());
        }
        return userdata;
    }

    public void auth_permission(controllerUsers controllerUsers){
        String sql = "SELECT ROL_ADDUSER, ROL_EDITUSER, ROL_ADDPROD, ROL_EDITPROD, ROL_SHOWPROD, ROL_SETTING " +
                "FROM TBL_ROLS " +
                "WHERE ROL_ID = ?";

        try (Connection connection = SQLServerConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, controllerUsers.getRol());
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                controllerUsers.setAdd_new_user(rs.getInt("ROL_ADDUSER"));
                controllerUsers.setEdit_exist_user(rs.getInt("ROL_EDITUSER"));
                controllerUsers.setAdd_new_product(rs.getInt("ROL_ADDPROD"));
                controllerUsers.setEdit_exist_product(rs.getInt("ROL_EDITPROD"));
                controllerUsers.setShow_exist_product(rs.getInt("ROL_SHOWPROD"));
                controllerUsers.setChange_settings_app(rs.getInt("ROL_SETTING"));
            }

        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error al obtener permisos", e);
            CustomErrorDialog.showWindows("Error al obtener los permisos",e.getMessage());
        }
    }

    public boolean session_user(String uuid, int Status){
        String sql = "SELECT * FROM TBL_LOGGED_IN_USERS WHERE LOGGED_IN_USERS_STATUS = ? AND LOGGED_IN_USERS_USERUUID = ?";
        boolean session_in_use = false;
        //Status= 1-user logged 2-exist logged

        try (Connection connection = SQLServerConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
             ps.setInt(1,Status);
             ps.setString(2,uuid);
             ResultSet rs = ps.executeQuery();

             if (rs.next()){
                 session_in_use = true;
             }
        }catch (SQLException e){
            System.err.println("ERROR: " + e);
            CustomErrorDialog.showWindows("Error al consultar sessión",e.getMessage());
        }
        return session_in_use;
    }

    public void sessionCreate(controllerUsers co){
        String insertSql = "INSERT INTO TBL_LOGGED_IN_USERS (LOGGED_IN_USERS_USERUUID) VALUES (?)";
        String consultSql = "SELECT current_value FROM sys.sequences WHERE name = 'SQ_ID_INCREMENTAL_LOGGED_IN_USERS'";

        try (Connection connection = SQLServerConnection.getConnection();
             PreparedStatement psInsert = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psConsult = connection.prepareStatement(consultSql)) {

            psInsert.setString(1, co.getUuid());
            psInsert.executeUpdate();

            try (ResultSet rsConsult = psConsult.executeQuery()){
                if(rsConsult.next()){
                    int idSession = rsConsult.getInt(1);
                    co.setSession_id(idSession);
                }
            }

        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error al crear sesión", e);
            CustomErrorDialog.showWindows("Error al crear sesión",e.getMessage());
        }
    }

    public void accessSession(controllerUsers co){
        if (session_user(co.getUuid(),0)){
            String queryUpdate = "UPDATE TBL_LOGGED_IN_USERS " +
                    "SET LOGGED_IN_USERS_STATUS = 1 " +
                    "OUTPUT INSERTED.LOGGED_IN_USERS_ID " +
                    "WHERE LOGGED_IN_USERS_USERUUID = ? " +
                    "AND LOGGED_IN_USERS_STATUS = 0";

            try (Connection connection = SQLServerConnection.getConnection();
                 PreparedStatement ps = connection.prepareStatement(queryUpdate)){

                ps.setString(1,co.getUuid());
                ResultSet rs = ps.executeQuery();

                if (rs.next()){
                    int idSession = rs.getInt("LOGGED_IN_USERS_ID");
                    co.setSession_id(idSession);
                }

            }catch (SQLException e){
                LOGGER.log(Level.SEVERE, "Error al modificar sesión", e);
                CustomErrorDialog.showWindows("Error al modificar sesión",e.getMessage());
            }
        }else{
            LOGGER.log(Level.SEVERE, "Error al encontrar sesión");
        }
    }

    public void closetSession(int idSession){
        String queryUpdate = "UPDATE TBL_LOGGED_IN_USERS SET LOGGED_IN_USERS_STATUS = 0 WHERE LOGGED_IN_USERS_ID = ?";

        try (Connection connection = SQLServerConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(queryUpdate)) {

            ps.setInt(1, idSession);
            ps.executeUpdate();

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al cerrar sesión", ex);
            CustomErrorDialog.showWindows("Error al cerrar sesión",ex.getMessage());
        }
    }

    public static boolean startSession(String username, String password, controllerUsers co){
        boolean loginDone = false;
        String Jquery = "SELECT USERS_UUID, USERS_NAME, USERS_PASSWORD, USERS_ROL FROM TBL_USERS WHERE USERS_STATUS=1 AND USERS_NAME = ? AND USERS_PASSWORD = ?";

        try (Connection connection = SQLServerConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(Jquery)){

            ps.setString(1,username);
            ps.setString(2,password);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    co.setUuid(rs.getString("USERS_UUID"));
                    co.setUserName(rs.getString("USERS_NAME"));
                    co.setRol(rs.getString("USERS_ROL"));
                    loginDone = true;
                }
            }
        }catch (SQLException e){
            System.out.println("ERROR: " + e);
            CustomErrorDialog.showWindows("Error al iniciar sesión",e.getMessage());
        }
        return loginDone;
    }
}
