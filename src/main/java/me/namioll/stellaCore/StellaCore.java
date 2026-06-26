package me.namioll.stellaCore;

import me.namioll.stellaCore.Chat.*;
import me.namioll.stellaCore.Crafts.LightBlock;
import me.namioll.stellaCore.MagicWand.Commands.GetMagicsCMD;
import me.namioll.stellaCore.MagicWand.Listeners.MagicBoneListener;
import me.namioll.stellaCore.MagicWand.Listeners.MagicStaffListener;
import me.namioll.stellaCore.MagicWand.Listeners.MagicWandListener;
import me.namioll.stellaCore.Professions.MenuCMD;
import me.namioll.stellaCore.Professions.MerChatCMD;
import me.namioll.stellaCore.Professions.SudCMD;
import me.namioll.stellaCore.Professions.SudiaMenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class StellaCore extends JavaPlugin {

    private static StellaCore plugin;
    private PlayerDataManager playerDataManager;
    private BanwordsManager banwordsManager;
    private ConfigManager chatconfig;
    private ConfigManager banwords;


    //                                    P E R M I S S I O N S
    // stellacore.getmagics - /getmagics получить набор из 1 кости, палки и посоха огня (тронутый тьмой контент)
    // stellacore.stream - /stream, /link, /banwords
    // stellacore.sudia - /sud и /menu (судьи)
    // stellacore.mer - /mc (сообщение от мэра на весь сервер)
    // stellacore.pygalka - /pygalka (Мир окутала тьма...) Напугать игроков через 10 секунд
    // stellacore.alarm - /alarm (системное уведомление, например рестарт)
    // stellacore.chatreload - /chatreload (перезагрузка конфига чата)
    //

    @Override
    public void onEnable() {
        plugin = this;

        chatconfig = new ConfigManager(this, "chat.yml");
        chatconfig.loadConfig();
        banwords = new ConfigManager(this, "banwords.yml");
        banwords.loadConfig();

        playerDataManager = new PlayerDataManager();
        banwordsManager = new BanwordsManager(this);

        SudCMD sudCMD = new SudCMD();

        new LightBlock().register(plugin);

        getCommand("getmagics").setExecutor(new GetMagicsCMD());
        getCommand("stream").setExecutor(new StreamCMD(this));
        getCommand("link").setExecutor(new LinkCMD(this));
        getCommand("banwords").setExecutor(new BanwordsCMD(this));
        getCommand("sud").setExecutor(sudCMD);
        getCommand("menu").setExecutor(new MenuCMD());
        getCommand("mc").setExecutor(new MerChatCMD());
        getCommand("pygalka").setExecutor(new PygalkaCMD());
        getCommand("alarm").setExecutor(new AlarmCMD());
        getCommand("chatreload").setExecutor(new ChatReloadCMD(this));
        getServer().getPluginManager().registerEvents(new MagicBoneListener(), this);
        getServer().getPluginManager().registerEvents(new MagicStaffListener(this), this);
        getServer().getPluginManager().registerEvents(new MagicWandListener(), this);
        getServer().getPluginManager().registerEvents(new StreamerMenuListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(sudCMD, this);
        getServer().getPluginManager().registerEvents(new SudiaMenuListener(), this);
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public BanwordsManager getBanwordsManager() {
        return banwordsManager;
    }

    public ConfigManager getChatConfig() {
        return chatconfig;
    }

    public ConfigManager getBanwordsConfig() {
        return banwords;
    }

    public static StellaCore getPlugin() {
        return plugin;
    }
}
