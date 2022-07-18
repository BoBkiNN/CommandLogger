package xyz.bobkinn_.commandlogger;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class configEngine {

    static String configFilePath = CommandLogger.configFilePath;
    static File dataFolder;
    static Configuration configuration;

    public configEngine(File dataFolder){
        configEngine.dataFolder = dataFolder;
    }

    public static void setDataFolder(File df){
        dataFolder = df;
    }

    public static void configLoad() throws IOException {

        File configFile = new File(dataFolder,"config.yml");
        boolean modified = false;

        if (!dataFolder.exists()){
            dataFolder.mkdir();
        }

        if(!configFile.exists()) {
            configFile.createNewFile();
        }

        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);

        if (!configuration.contains("msg")) {
            configuration.set("msg","&a[%server%] &b%player%&r executed &e%cmd%");
            modified=true;
        }

        if (!configuration.contains("reloadMsg")) {
            configuration.set("reloadMsg","&aConfig reloaded!");
            modified=true;
        }

        if (!configuration.contains("hiddenCmds")){
            String[] defList = {"/l ","/log ","/login ","/reg","/changepass","/cp","/tell ","/msg ","/pm","/pmsg","/w ","/m","/whisper"};
            configuration.set("hiddenCmds", defList);
            modified=true;
        }
        if (!configuration.contains("ignoreCase")){
            configuration.set("ignoreCase",true);
            modified=true;
        }

        if (modified){
            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);

    }

}
