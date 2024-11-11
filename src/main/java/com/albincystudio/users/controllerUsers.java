package com.albincystudio.users;

import com.albincystudio.root.configuration;
import com.albincystudio.ui.CustomErrorDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class controllerUsers {

    private final Properties properties = new Properties();
    private static final Logger LOGGER = Logger.getLogger(configuration.class.getName());
    private static final String userData = "auth.properties";

    private String uuid;
    private String userName;
    private int session_id;
    private String rol;

    //User permissions
    private int add_new_user;
    private int edit_exist_user;
    private int add_new_product;
    private int edit_exist_product;
    private int show_exist_product;
    private int change_settings_app;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getAdd_new_user() {
        return add_new_user;
    }

    public void setAdd_new_user(int add_new_user) {
        this.add_new_user = add_new_user;
    }

    public int getEdit_exist_user() {
        return edit_exist_user;
    }

    public void setEdit_exist_user(int edit_exist_user) {
        this.edit_exist_user = edit_exist_user;
    }

    public int getAdd_new_product() {
        return add_new_product;
    }

    public void setAdd_new_product(int add_new_product) {
        this.add_new_product = add_new_product;
    }

    public int getEdit_exist_product() {
        return edit_exist_product;
    }

    public void setEdit_exist_product(int edit_exist_product) {
        this.edit_exist_product = edit_exist_product;
    }

    public int getShow_exist_product() {
        return show_exist_product;
    }

    public void setShow_exist_product(int show_exist_product) {
        this.show_exist_product = show_exist_product;
    }

    public int getChange_settings_app() {
        return change_settings_app;
    }

    public void setChange_settings_app(int change_settings_app) {
        this.change_settings_app = change_settings_app;
    }

    //generate uuid users
    public static String hexadecimalConverter(String user){
        StringBuilder sb = new StringBuilder();
        char[] ch = user.toCharArray();
        for (char c : ch){
            String hexString = Integer.toHexString(c);
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static String randomNumbers(){
        Random r = new Random();
        String result;
        int number = 10000 + r.nextInt(90000);
        result = Integer.toString(number);
        return result;
    }

    public static String generatorUuid(String user){
        String hexName = hexadecimalConverter(user);
        hexName += randomNumbers();
        return hexName;
    }

    public void save_uuid_local(String uuid){
        try (FileOutputStream out = new FileOutputStream("auth.properties")){
            properties.setProperty("uuid", String.valueOf(uuid));
            properties.store(out,"session user local");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving user", e);
            CustomErrorDialog.showWindows("Error guardar usuario",e.getMessage());
        }
    }

    public void load_auth_local(){
        try (FileInputStream in = new FileInputStream("auth.properties")) {
            properties.load(in);
            uuid = (properties.getProperty("uuid", uuid));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading configuration", e);
            CustomErrorDialog.showWindows("Error cargar configuración",e.getMessage());
        }
    }

    public boolean existAuth_local(){
        File file_userData = new File(userData);
        boolean result = false;

        if (file_userData.exists()){
            if(file_userData.length() > 0){
                result=true;
            }
        }else{
            createEmptyFile(file_userData);
        }
        return result;
    }

    public void createEmptyFile(File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created" + file.getName());
            } else {
                System.out.println("Failed to create file");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating file", e);
            CustomErrorDialog.showWindows("Error al crear un archivo",e.getMessage());
        }
    }
}


