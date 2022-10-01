package xyz.bobkinn_.commandlogger;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ConfigEngine {

    static File dataFolder = CommandLogger.plugin.getDataFolder();
    static Configuration configuration;

    public static void reload(){
        File configFile = new File(dataFolder,"config.yml");
        boolean modified = false;

        if (!dataFolder.exists()){
            if (!dataFolder.mkdir()){
                CommandLogger.logger.severe("Failed to create data folder: "+dataFolder.getAbsolutePath());
                return;
            }
        }

        try {
            if(!configFile.exists()) {
                if (!configFile.createNewFile()){
                    CommandLogger.logger.severe("Failed to create config file");
                    return;
                }
            }

            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e){
            e.printStackTrace();
            return;
        }


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
        if (!configuration.contains("addPrefix")){
            configuration.set("addPrefix",false);
            modified=true;
        }

        if (modified){
            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e){
            e.printStackTrace();
        }
        if (!configuration.getBoolean("addPrefix")) CommandLogger.logger = CommandLogger.plugin.getProxy().getLogger();
        else CommandLogger.logger = CommandLogger.plugin.getLogger();

    }

}
