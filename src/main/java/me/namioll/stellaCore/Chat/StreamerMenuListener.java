package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.StellaCore;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class StreamerMenuListener implements Listener {
    private StellaCore plugin;

    public StreamerMenuListener(StellaCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (!e.getView().getTitle().equals(ColorUtils.colorize("<#9200FF>&lМеню Стримера</#E60EC1>"))) return;
        if (!(e.getWhoClicked() instanceof Player p)) return;

        e.setCancelled(true);
        PlayerData data = plugin.getPlayerDataManager().get(p);
        if (data == null) return;

        if (e.getRawSlot() == 20){
            if (!data.isStreamer()){
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f);
                plugin.getPlayerDataManager().saveStreamer(p, data, true, plugin);
                e.getView().getTopInventory().setItem(20, StreamCMD.yes());
            }
            else {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f);
                plugin.getPlayerDataManager().saveStreamer(p, data, false, plugin);
                e.getView().getTopInventory().setItem(20, StreamCMD.no());
            }
        }
        else if (e.getRawSlot() == 22){
            if (data.getLink() == null || data.getLink().isEmpty()){
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                p.closeInventory();
                p.sendMessage(ColorUtils.STELLA() + "§cУ вас не установлена ссылка на стрим! Используйте /link <URL>, чтобы установить ссылку.");
            }
            else {
                p.closeInventory();
                TextComponent msg = new TextComponent(TextComponent.fromLegacyText(ColorUtils.STELLA() + "§aХей! Тут §e" + p.getName() + " §aначал стрим! "));

                TextComponent click = new TextComponent("§c§lНАЖМИ СЮДА");
                click.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, data.getLink()));
                click.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fПерейти на §5Twitch§f.")));

                TextComponent msg1 = new TextComponent("§a, чтобы начать просмотр!");
                msg.addExtra(click);
                msg.addExtra(msg1);

                for (Player pl : Bukkit.getOnlinePlayers()){
                    pl.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1f, 1f);
                    pl.spigot().sendMessage(msg);
                }
            }
        }
        else if (e.getRawSlot() == 24){
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.6f, 0.8f);
            p.closeInventory();
            p.sendMessage(ColorUtils.STELLA() +"§c§lВНИМАНИЕ! §aВы нажали на список §c§nбанвордов§a. У вас есть 5 секунд, чтобы скрыть экран, если вы случайно нажали сюда на стриме.");
            new BukkitRunnable() {
                public void run() {
                    List<String> banwords = plugin.getBanwordsConfig().getConfig().getStringList("words");
                    String joined = String.join("§7, §f", banwords);
                    p.sendMessage("§cБанворды: §f" + joined);
                }
            }.runTaskLater(plugin, 100L);
        }
    }
}
