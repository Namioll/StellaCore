package me.namioll.stellaCore.Professions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("stellacore.sudia")) return true;

        p.openInventory(Menus.createSudiaMenu());

        return true;
    }
}
