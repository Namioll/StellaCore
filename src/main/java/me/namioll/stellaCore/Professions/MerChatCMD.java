package me.namioll.stellaCore.Professions;

import me.namioll.stellaCore.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MerChatCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("stellacore.mer")) return true;
        if (args.length == 0) {
            p.sendMessage(ColorUtils.STELLA() + "§cМэр! Данная команда используется чтобы оповестить весь сервер о каком-то событии. Использование: /mc [Сообщение].");
            return true;
        }

        String message = String.join(" ", args);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ColorUtils.STELLA() + "§fСообщение от Мэра: §a" + message);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);

        }

        return true;
    }
}
