package me.namioll.stellaCore.Chat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AlarmCMD implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender s) {
            if (args.length == 0) {
                s.sendMessage("§cОшибка! Ваше сообщение должно состоять хотябы из 1 слова.");
            }
            else {
                String message = String.join(" ", args);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage("§8§l[§cАЛЯРМ§8§l] §fCONSOLE: §a" + message);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                }
            }
        }
        else if (sender instanceof Player p) {
            if (p.hasPermission("stellacore.alarm")) {
                if (args.length == 0) {
                    p.sendMessage("§cОшибка! Ваше сообщение должно состоять хотябы из 1 слова.");
                }
                else {
                    String message = String.join(" ", args);
                    for (Player p1 : Bukkit.getOnlinePlayers()) {
                        p1.sendMessage("§8§l[§cАЛЯРМ§8§l] §f" + p.getName() + ": " + "§a" + message);
                        p1.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                    }
                }
            }
        }
        return true;
    }
}
