package xyz.bobkinn_.commandlogger;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ProxyReloadEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;


public final class CommandLogger extends Plugin implements Listener {

    public static File configFile;
    public static String configFilePath;
    public File dataFolder = getDataFolder();


    @Override
    public void onEnable() {
        getLogger().info("Enabling...");
        getProxy().getPluginManager().registerListener(this, this);
        getProxy().getPluginManager().registerCommand(this, new cmdReload("commandloggerreload","commandlogger.reload", "clreload", "clr"));
        new configEngine(getDataFolder());
        setconfigFilePath();
        try {
            configEngine.configLoad();
        } catch (IOException e) {
            e.printStackTrace();
        }
        configEngine.setDataFolder(getDataFolder());
        getLogger().info("Plugin enabled!");
    }

    private void setconfigFilePath() {
        configFilePath = new File(getDataFolder()+"config.yml").getAbsolutePath();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin disabled!");
    }

    @EventHandler
    public void rlListener(ProxyReloadEvent e){
        new configEngine(getDataFolder());
        try {
            configEngine.configLoad();
            Configuration configuration = configEngine.configuration;
            String reloadMsg = configuration.getString("reloadMsg").replace("&","§");
            getLogger().info(reloadMsg);
        } catch (IOException ex) {
            ex.printStackTrace();
            getLogger().warning("Reload error occurred see stacktrace upper");
        }
    }



    @EventHandler
    public void cmdListener(ChatEvent e){
        new configEngine(getDataFolder());
        Configuration configuration = configEngine.configuration;
        String msg = e.getMessage();
        ProxiedPlayer p = (ProxiedPlayer)e.getSender();
        String nick = p.getName();
        String sName = p.getServer().getInfo().getName();
        String omsg = configuration.getString("msg");
        List<String> hiddenCmds = configuration.getStringList("hiddenCmds");
        String dmsg = omsg.replace("%player%",nick).replace("%cmd%",msg).replace("&","§").replace("%server%",sName);
        dmsg = dmsg + "§r";

        boolean showmsg = true;
        if(msg.startsWith("/")){
            for (String cmd : hiddenCmds){
                if (StringUtils.containsIgnoreCase(msg,cmd) && configuration.getBoolean("ignoreCase")){
                    showmsg = false;
                    break;
                } else if (msg.contains(cmd)){
                    showmsg = false;
                    break;
                }
            }
            if (showmsg) {getLogger().info(dmsg);}
        }
    }
}