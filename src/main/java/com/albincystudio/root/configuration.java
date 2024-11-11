package com.albincystudio.root;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class configuration {
    //properties
    private final Properties properties = new Properties();
    private static final Logger LOGGER = Logger.getLogger(configuration.class.getName());

    //File
    private static final String confgData = "config.properties";

    //These options are internal and cannot be changed by the client
    private final String version=version(true); //SNAPSHOT=true, RELEASE=false

    //These options are customizable by the client
    private boolean newUseApp=true;
    private boolean savebackcupDB=false;
    private int theme_app=0; //dark:1 - Light:2

    public boolean isNewUseApp() {
        return newUseApp;
    }

    public void setNewUseApp(boolean newUseApp) {
        this.newUseApp = newUseApp;
    }

    public String getVersion() {
        return version;
    }

    public boolean isSavebackcupDB() {
        return savebackcupDB;
    }

    public void setSavebackcupDB(boolean savebackcupDB) {
        this.savebackcupDB = savebackcupDB;
    }

    public int getTheme_app() {
        return theme_app;
    }

    public void setTheme_app(int theme_app) {
        this.theme_app = theme_app;
    }

    //Return version
    public String version(boolean dev){
        if (dev){
            return "SNAPSHOT 24v01a";
        }else{
            return "VERSIÓN 1.0.0";
        }
    }

    //Method for saving settings
    public void saveSettings(){
        try (FileOutputStream out = new FileOutputStream("config.properties")){
            properties.setProperty("newUseApp", String.valueOf(newUseApp));
            properties.setProperty("savebackcupDB", String.valueOf(savebackcupDB));
            properties.setProperty("theme_app", String.valueOf(theme_app));
            properties.store(out, "User Configurations");
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving configuration", e);
        }
    }

    //Method to load the properties
    public void loadSettings(){
        try (FileInputStream in = new FileInputStream("config.properties")) {
            properties.load(in);
            newUseApp = Boolean.parseBoolean(properties.getProperty("newUseApp", "true"));
            savebackcupDB = Boolean.parseBoolean(properties.getProperty("savebackcupDB", "false"));
            theme_app = Integer.parseInt(properties.getProperty("theme_app", "0"));

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading configuration", e);
        }
    }

    //other method - existConfgData
    public boolean existUserdata(){
        File file_confgData = new File(confgData);
        boolean result = false;
        
        if(file_confgData.exists()){
            if(file_confgData.length() > 0){
                result=true;
            }else{
                saveSettings();
            }
        }else{
            createEmptyFile(file_confgData);
        }
        return result;
    }

    //other method - createConfgData-Empty
    public void createEmptyFile(File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created" + file.getName());
            } else {
                System.out.println("Failed to create file");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating file", e);
        }
    }
}