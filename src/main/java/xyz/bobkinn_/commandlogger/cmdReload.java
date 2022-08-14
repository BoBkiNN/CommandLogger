package xyz.bobkinn_.commandlogger;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class cmdReload extends Command{
    public cmdReload(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        configEngine.reload();
        Configuration configuration = configEngine.configuration;
        String reloadMsg = ChatColor.translateAlternateColorCodes('&',configuration.getString("reloadMsg"));
        sender.sendMessage(new TextComponent(reloadMsg));
    }
}
