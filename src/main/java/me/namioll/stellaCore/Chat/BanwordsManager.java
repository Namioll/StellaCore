package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.StellaCore;

import java.util.List;

public class BanwordsManager {

    private final StellaCore plugin;
    private final List<String> banwords;

    public BanwordsManager(StellaCore plugin) {
        this.plugin = plugin;
        this.banwords = plugin.getBanwordsConfig().getConfig().getStringList("words");
    }

    public List<String> getBanwords() {
        return banwords;
    }

    public boolean addWord(String word) {
        if (banwords.contains(word)) return false;

        banwords.add(word);
        save();
        return true;
    }

    public boolean removeWord(String word) {
        if (!banwords.contains(word)) return false;

        banwords.remove(word);
        save();
        return true;
    }

    private void save() {
        plugin.getBanwordsConfig().getConfig().set("words", banwords);
        plugin.getBanwordsConfig().saveConfig();
    }
}
