package xyz.bobkinn_.commandlogger;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ProxyReloadEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;


public final class CommandLogger extends Plugin implements Listener {

    public static File configFile;
    public static CommandLogger plugin;
    public static Logger logger;

    @Override
    public void onEnable() {
        plugin=this;
        logger=getLogger();
        getLogger().info("Enabling...");
        configFile = new File(getDataFolder()+"config.yml");
        ConfigEngine.reload();
        getProxy().getPluginManager().registerListener(this, this);
        getProxy().getPluginManager().registerCommand(this, new CmdReload("commandloggerreload","commandlogger.reload", "clreload", "clr"));
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getProxy().getPluginManager().unregisterCommands(this);
        getLogger().info("Plugin disabled!");
    }

    @EventHandler
    public void rlListener(ProxyReloadEvent e){
        ConfigEngine.reload();
        Configuration configuration = ConfigEngine.configuration;
        String reloadMsg = configuration.getString("reloadMsg").replace("&","§");
        getLogger().info(reloadMsg);
    }



    @EventHandler
    public void cmdListener(ChatEvent e){
        Configuration configuration = ConfigEngine.configuration;
        String msg = e.getMessage();
        ProxiedPlayer p = (ProxiedPlayer)e.getSender();
        String nick = p.getName();
        String sName = p.getServer().getInfo().getName();
        String oMsg = configuration.getString("msg");
        List<String> hiddenCmds = configuration.getStringList("hiddenCmds");
        String dMsg = oMsg.replace("%player%",nick)
                        .replace("%cmd%",msg)
                        .replace("&","§")
                        .replace("%server%",sName)
                + ChatColor.RESET;

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
            if (showmsg) {logger.info(ChatColor.translateAlternateColorCodes('&',dMsg));}
        }
    }
}