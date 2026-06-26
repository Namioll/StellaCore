package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.StellaCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanwordsCMD implements CommandExecutor {
    private StellaCore plugin;
    private BanwordsManager banwordsManager;

    public BanwordsCMD(StellaCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
            if (!(sender instanceof Player p)) return true;
            if (!p.hasPermission("stellacore.stream")) {
                p.sendMessage(ColorUtils.STELLA() + "§cИзвините! Но Вы не можете использовать эту команду, так как не числитесь нашим стримером. Если это не так, свяжитесь с администрацией! Извините :с");
                return true;
            }

            if (args.length != 2) {
                p.sendMessage(ColorUtils.STELLA() + "§cИспользование: §e/banwords add [Слово] §cили §e/banwords remove [Слово]§c.");
                return true;
            }

            banwordsManager = plugin.getBanwordsManager();
            if (args[0].equalsIgnoreCase("add")){
                if (!banwordsManager.addWord(args[1].toLowerCase())) {
                    p.sendMessage(ColorUtils.STELLA() + "§cЭто слово уже есть в списке.");
                    return true;
                }
                p.sendMessage(ColorUtils.STELLA() + "§aВы успешно §nдобавили §aбанворд.");
            }

            else if (args[0].equalsIgnoreCase("remove")){
                if (!banwordsManager.removeWord(args[1].toLowerCase())) {
                    p.sendMessage(ColorUtils.STELLA() + "§cТакого слова нет.");
                    return true;
                }
                p.sendMessage(ColorUtils.STELLA() + "§aВы успешно §nудалили §aбанворд.");
            }
        return true;
    }
}
