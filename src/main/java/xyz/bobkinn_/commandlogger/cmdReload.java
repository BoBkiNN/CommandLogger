package xyz.bobkinn_.commandlogger;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;

public class cmdReload extends Command{

    public cmdReload(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String reloadMsg = CommandLogger.configuration.getString("reloadMsg").replace("&","§");
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).load(CommandLogger.configFile);
            sender.sendMessage(new ComponentBuilder (reloadMsg).create());
        } catch (IOException e) {
            e.printStackTrace();
            sender.sendMessage(new ComponentBuilder("Error occurred, check console for details").color(ChatColor.RED).create());
        }
    }
}
