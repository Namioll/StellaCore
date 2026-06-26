package me.namioll.stellaCore.Professions;

import me.namioll.stellaCore.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SudiaMenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (!(ChatColor.stripColor(e.getView().getTitle()).equals("Меню Судьи"))) return;
        if (!(e.getWhoClicked() instanceof Player p)) return;

        e.setCancelled(true);
        if (e.getRawSlot() == 3){
            for (Player p1 : Bukkit.getOnlinePlayers()) {
                p1.sendMessage(ColorUtils.STELLA() + "§aСуд начнётся через 5 минут! Все желающие могут войти в зал суда для участия в судебном процессе.");
                p1.playSound(p1.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            p.closeInventory();
        }
        else if (e.getRawSlot() == 4){
            for (Player p1 : Bukkit.getOnlinePlayers()) {
                p1.sendMessage(ColorUtils.STELLA() + "§cСудебное дело началось! Двери суда закрываются до окончания судебного процесса.");
                p1.playSound(p1.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            }
            p.closeInventory();
        }
        else if (e.getRawSlot() == 5){
            for (Player p1 : Bukkit.getOnlinePlayers()) {
                p1.sendMessage(ColorUtils.STELLA() + "§eСуд завершен! Двери суда снова свободны для новых дел.");
                p1.playSound(p1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }
            p.closeInventory();
        }
    }
}
