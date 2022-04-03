package xyz.bobkinn_.commandlogger;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class CommandLogger extends Plugin implements Listener {

    public static File configFile;
    public static Configuration configuration;

    @Override
    public void onEnable() {
        getLogger().info("Enabling...");
        getProxy().getPluginManager().registerListener(this, this);
        getProxy().registerChannel("cmdlogger:logger");

        configLoad();
        getLogger().info("Plugin enabled!");
    }

    private void configLoad() {
        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        configFile = new File(getDataFolder(), "config.yml");

        try {
            if(!configFile.exists()) {
                configFile.createNewFile();
            }
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!configuration.contains("msg")) {
            configuration.set("msg","&a[%server%] &b%player%&r executed &e%cmd%");
        }
        if (!configuration.contains("hiddenCmds")){
            List<String> defList = new ArrayList<>();
            defList.add("/l ");
            defList.add("/log ");
            defList.add("/login ");
            configuration.set("hiddenCmds", defList);

            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin disabled!");
    }



    @EventHandler
    public void cmdListener(ChatEvent e){
        String msg = e.getMessage();
        ProxiedPlayer p = (ProxiedPlayer)e.getSender();
        String nick = p.getName();
        String sName = p.getServer().getInfo().getName();
        String omsg = configuration.getString("msg");
        List<String> hiddenCmds = configuration.getStringList("hiddenCmds");
        String dmsg = omsg.replace("%player%",nick).replace("%cmd%",msg).replace("&","§").replace("%server%",sName);
        dmsg = dmsg + "§r";
        //String dmsg = "§b"+nick+"§r executed: "+"§e"+ msg;

        boolean showmsg = true;
        if(msg.startsWith("/")){
            for (String cmd : hiddenCmds){
                if (msg.contains(cmd)){
                    showmsg = false;
                    break;
                }
            }
            if (showmsg) {getLogger().info(dmsg);}
        }
    }
}