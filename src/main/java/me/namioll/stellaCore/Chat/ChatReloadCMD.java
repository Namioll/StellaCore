package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.StellaCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChatReloadCMD implements CommandExecutor {

    private final StellaCore plugin;

    public ChatReloadCMD(StellaCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("stellacore.chatreload")) {
            sender.sendMessage(ColorUtils.STELLA() + "§cУ вас нет прав.");
            return true;
        }

        plugin.getChatConfig().loadConfig();
        plugin.getChatConfig().saveConfig();
        plugin.getPlayerDataManager().reloadAll(plugin);

        sender.sendMessage(ColorUtils.STELLA() + "§aКонфиг чата успешно перезагружен.");
        return true;
    }
}
