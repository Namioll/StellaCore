package me.namioll.stellaCore.MagicWand.Commands;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.MagicWand.Utils.MagicBone;
import me.namioll.stellaCore.MagicWand.Utils.MagicMinoBone;
import me.namioll.stellaCore.MagicWand.Utils.MagicStaff;
import me.namioll.stellaCore.MagicWand.Utils.MagicWand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetMagicsCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cТолько игроки могут использовать эту команду!");
            return true;
        }

        if (!p.hasPermission("stellacore.getmagics")) {
            p.sendMessage(ColorUtils.STELLA() + "§cУ вас нет разрешения!");
            return true;
        }


        if (args.length == 0) {
            p.getInventory().addItem(MagicWand.createMagicWand());
            p.getInventory().addItem(MagicBone.createMagicBone());
            p.getInventory().addItem(MagicStaff.createMagicStaff());
            p.getInventory().addItem(MagicMinoBone.createMinoBone());
            p.sendMessage(ColorUtils.STELLA() + "§aВы получили магический набор!");
            return true;
        }

        try {
            int level = Integer.parseInt(args[0]);
            if (level < 0) {
                p.sendMessage(ColorUtils.STELLA() + "§cУровень не может быть отрицательным!");
                return true;
            }

            if (level > 1000) {
                p.sendMessage(ColorUtils.STELLA() + "§cСлишком высокий уровень! Максимум 1000.");
                return true;
            }

            p.getInventory().addItem(MagicWand.createMagicWand(level));
            p.sendMessage(ColorUtils.STELLA() + "§aВы получили магическую палку уровня §e+" + level);

        } catch (NumberFormatException e) {
            p.sendMessage(ColorUtils.STELLA() + "§cНеверный уровень! Используйте число.");
            p.sendMessage(ColorUtils.STELLA() + "§7Использование: /getmagics [уровень] [количество_костей]");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("0");
            completions.add("10");
            completions.add("20");
            completions.add("30");
            completions.add("50");
            completions.add("100");
        }

        return completions;
    }
}

