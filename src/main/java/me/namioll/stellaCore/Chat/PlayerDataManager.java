package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.StellaCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {

    private final Map<String, PlayerData> players = new ConcurrentHashMap<>();

    public PlayerData get(Player p){
        return players.get(p.getName());
    }

    public void put(Player p, PlayerData data){
        players.put(p.getName(), data);
    }

    public void remove(Player p){
        players.remove(p.getName());
    }

    public void saveLink(Player p, PlayerData data, String link, StellaCore plugin){
        data.setLink(link);
        plugin.getChatConfig().getConfig().set("players." + p.getName() + ".link", link);
        plugin.getChatConfig().saveConfig();
    }

    public void saveStreamer(Player p, PlayerData data, boolean streamer, StellaCore plugin){
        data.setStreamer(streamer);
        plugin.getChatConfig().getConfig().set("players." + p.getName() + ".streamer", streamer);
        plugin.getChatConfig().saveConfig();
    }

    public void reloadAll(StellaCore plugin) {
        players.clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
            String name = p.getName();

            String prefix = plugin.getChatConfig().getConfig()
                    .getString("players." + name + ".prefix", "Ç");
            String title = plugin.getChatConfig().getConfig()
                    .getString("players." + name + ".title", "");
            String nickcolor = plugin.getChatConfig().getConfig()
                    .getString("players." + name + ".nick-color", "&f");
            String messagecolor = plugin.getChatConfig().getConfig()
                    .getString("players." + name + ".message-color", "&f");
            String link = plugin.getChatConfig().getConfig()
                    .getString("players." + name + ".link", "");
            boolean streamer = plugin.getChatConfig().getConfig()
                    .getBoolean("players." + name + ".streamer", false);
            boolean colors = plugin.getChatConfig().getConfig()
                    .getBoolean("players." + name + ".colors", false);
            boolean hex = plugin.getChatConfig().getConfig()
                    .getBoolean("players." + name + ".hex", false);

            players.put(name, new PlayerData(
                    prefix, title, nickcolor, messagecolor, link, streamer, colors, hex
            ));
        }
    }
}
