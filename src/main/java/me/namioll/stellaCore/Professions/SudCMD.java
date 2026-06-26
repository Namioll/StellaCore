package me.namioll.stellaCore.Professions;

import me.namioll.stellaCore.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SudCMD implements CommandExecutor, Listener {

    private final Map<String, String> pending = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("stellacore.sudia")) return true;

        if (args.length < 2){
            p.sendMessage(ColorUtils.STELLA() + "§cУважаемый Судья. Данная команда используется чтобы вызвать игрока на суд. Использование: /sud [Ник] [Причина].");
            return true;
        }

        String targetName = args[0];
        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        if (!target.hasPlayedBefore() && !target.isOnline()) {
            p.sendMessage(ColorUtils.STELLA() + "§cТакого игрока никогда не было на сервере!");
            return true;
        }

        if (target.isOnline()) {
            for (Player player : Bukkit.getOnlinePlayers()){
                player.sendMessage(ColorUtils.STELLA() + "§cИгрок " + targetName +  "§c§l вызывается в суд!");
                player.sendMessage(ColorUtils.STELLA() + "§cПричина: §f" + reason);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }
        }
        else {
            pending.put(targetName, reason);
            p.sendMessage(ColorUtils.STELLA() + "§cИгрок оффлайн. Вызов будет объявлен при его заходе.");
        }
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String name = e.getPlayer().getName();
        if (pending.containsKey(name)) {
            String reason = pending.remove(name);
            for (Player player : Bukkit.getOnlinePlayers()){
                player.sendMessage(ColorUtils.STELLA() + "§cИгрок " + name +  "§c§l вызывается в суд!");
                player.sendMessage(ColorUtils.STELLA() + "§cПричина: §f" + reason);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }
        }
    }
}
