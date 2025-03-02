package com.example.demoa4.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private String repoType;
    private String fileComanda;
    private String fileTort;

    public Settings(){
    }

    public String getRepoType() {
        return repoType;
    }

    public String getFileTort() {
        return fileTort;
    }

    public String getFileComanda() {
        return fileComanda;
    }

    private static Settings instance;

    public static Settings getInstance(String fileName){
        if(instance == null){
            Properties settings = new Properties();
            try{
                settings.load(new FileReader(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            instance = new Settings();
            instance.repoType = settings.getProperty("Repository");
            instance.fileTort = settings.getProperty("TortsFile");
            instance.fileComanda = settings.getProperty("OrdersFile");
        }
        return instance;
    }
}
