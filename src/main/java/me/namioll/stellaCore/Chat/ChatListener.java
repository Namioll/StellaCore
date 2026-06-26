package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.StellaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {

    private final StellaCore plugin;
    private ChatFilter chatFilter;

    public ChatListener(StellaCore plugin) {
        this.plugin = plugin;
        this.chatFilter = new ChatFilter(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String name = e.getPlayer().getName();
        if (!plugin.getChatConfig().getConfig().contains("players." + name)) {
            plugin.getChatConfig().getConfig().set("players." + name + ".prefix", "Ç");
            plugin.getChatConfig().getConfig().set("players." + name + ".title", "");
            plugin.getChatConfig().getConfig().set("players." + name + ".nick-color", "&f");
            plugin.getChatConfig().getConfig().set("players." + name + ".message-color", "&f");
            plugin.getChatConfig().getConfig().set("players." + name + ".link", "");
            plugin.getChatConfig().getConfig().set("players." + name + ".streamer", false);
            plugin.getChatConfig().getConfig().set("players." + name + ".colors", false);
            plugin.getChatConfig().getConfig().set("players." + name + ".hex", false);
            plugin.getChatConfig().saveConfig();
        }

        String prefix = plugin.getChatConfig().getConfig().getString("players." + name + ".prefix", "Ç");
        String title = plugin.getChatConfig().getConfig().getString("players." + name + ".title", "");
        String nickcolor = plugin.getChatConfig().getConfig().getString("players." + name + ".nick-color", "&f");
        String messagecolor = plugin.getChatConfig().getConfig().getString("players." + name + ".message-color", "&f");
        String link = plugin.getChatConfig().getConfig().getString("players." + name + ".link", "");
        boolean streamer = plugin.getChatConfig().getConfig().getBoolean("players." + name + ".streamer", false);
        boolean colors = plugin.getChatConfig().getConfig().getBoolean("players." + name + ".colors", false);
        boolean hex = plugin.getChatConfig().getConfig().getBoolean("players." + name + ".hex", false);

        plugin.getPlayerDataManager().put(e.getPlayer(), new PlayerData(prefix, title, nickcolor, messagecolor, link, streamer, colors, hex));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.getPlayerDataManager().remove(e.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();

        PlayerData data = plugin.getPlayerDataManager().get(p);
        String name = p.getName();
        String prefix = data.getPrefix();
        String title = data.getTitle();
        String nickcolor = data.getNickcolor();
        String messagecolor = data.getMessagecolor();
        boolean colors = data.isColors();
        boolean hex = data.isHex();

        prefix = ColorUtils.colorize(prefix);
        title = ColorUtils.colorize(title);
        nickcolor = ColorUtils.colorize(nickcolor);
        messagecolor = ColorUtils.colorize(messagecolor);

        String raw = e.getMessage();
        boolean isGlobal = raw.startsWith("!");

        if (isGlobal && raw.length() > 1) {
            raw = raw.substring(1);
        }
        String filteredMsg = chatFilter.filter(raw);

        StringBuilder sb = new StringBuilder();

        if (isGlobal) {
            sb.append("§6Ⓖ§f ");
        }

        else {
            sb.append("§7Ⓛ§f ");
        }
            sb.append(prefix)
            .append(" ")
            .append(nickcolor)
            .append(name)
            .append(" ")
            .append(title)
            .append("§8§l: ")
            .append(messagecolor);

        StringBuilder filtered = new StringBuilder();
        filtered.append(sb);

        if (colors && !hex) {
            sb.append(ChatColor.translateAlternateColorCodes('&', raw));
            filtered.append(ChatColor.translateAlternateColorCodes('&', filteredMsg));
        }

        else if (hex){
            sb.append(ColorUtils.colorize(raw));
            filtered.append(ColorUtils.colorize(filteredMsg));
        }

        else {
            sb.append(raw);
            filtered.append(filteredMsg);
        }

        String message = sb.toString();
        String filteredMessage = filtered.toString();
        if (isGlobal) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {

                    PlayerData data1 = plugin.getPlayerDataManager().get(player);

                    if (data1.isStreamer()) {
                        player.sendMessage(filteredMessage);
                    }
                    else {
                        player.sendMessage(message);
                    }


                }
            });
        }
        else {
            Bukkit.getScheduler().runTask(plugin, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (p.getWorld() != player.getWorld()) continue;
                    if (p.getLocation().distanceSquared(player.getLocation()) <= 10000) {

                        PlayerData data1 = plugin.getPlayerDataManager().get(player);

                        if (data1.isStreamer()) {
                            player.sendMessage(filteredMessage);
                        }
                        else {
                            player.sendMessage(message);
                        }
                    }
                }
            });
        }
        Bukkit.getConsoleSender().sendMessage(message);
    }
}

