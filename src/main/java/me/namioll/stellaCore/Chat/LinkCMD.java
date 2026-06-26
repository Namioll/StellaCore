package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.StellaCore;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LinkCMD implements CommandExecutor {
    private StellaCore plugin;

    public LinkCMD(StellaCore plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("stellacore.stream")) {
            p.sendMessage(ColorUtils.STELLA() + "§cИзвините! Но Вы не можете использовать эту команду, так как не числитесь нашим стримером. Если это не так, свяжитесь с администрацией! Извините :с");
            return true;
        }
        PlayerData data = plugin.getPlayerDataManager().get(p);
        if (data == null) return true;

        if (args.length == 0) {
            if (data.getLink() == null || data.getLink().isEmpty()) {
                p.sendMessage(ColorUtils.STELLA() + "§cУ вас не установлена ссылка на стрим. Используйте /link <URL>, чтобы установить ссылку.");
            }
            else {
                BaseComponent[] base = TextComponent.fromLegacyText(ColorUtils.STELLA() + "§aВот ваша установленная ссылка: §f§n");
                TextComponent msg = new TextComponent(base);

                TextComponent click = new TextComponent(data.getLink());
                click.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, data.getLink()));
                msg.addExtra(click);

                p.spigot().sendMessage(msg);
            }
            return true;
        }
        else {
            String link = args[0];
            if (!link.startsWith("https://") && !link.startsWith("http://")) {
                p.sendMessage(ColorUtils.STELLA() + "§cЭто не ссылка.");
                return true;
            }
            plugin.getPlayerDataManager().saveLink(p, data, link, plugin);
            p.sendMessage(ColorUtils.STELLA() + "§aВы успешно установили ссылку! Вы можете изменить её в любой момент.");
            return true;
        }
    }
}
