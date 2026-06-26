package me.namioll.stellaCore.Professions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Menus {

    public static Inventory createSudiaMenu(){
        Inventory sudia_inventory = Bukkit.createInventory(null, InventoryType.DISPENSER, "§l§eМеню Судьи");
        ArrayList<String> clickme = new  ArrayList<>();
        clickme.add("");
        clickme.add("§7Нажми, чтобы вызвать это событие.");

        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta infometa = info.getItemMeta();
        infometa.setDisplayName("§l§8[§l§c⚠§l§8] §aИнформация о меню судьи");
        ArrayList<String> infolore = new  ArrayList<>();
        infolore.add("");
        infolore.add("§7Привет, дорогой Судья! Здесь кратко написаны все твои возможности.");
        infolore.add("");
        infolore.add("");
        infolore.add("§f/sud [Ник] [Причина] §7 — вызвать игрока в суд по указанной причине.");
        infolore.add("§fОповестить о начале суда §7 —  все игроки на сервере получат информацию, что судебное дело начинается через 5 минут.");
        infolore.add("§fСуд начинается! §7 — все игроки на сервере получат информацию, что двери суда закрываются для начала судебного процесса.");
        infolore.add("§fСуд завершен! §7 —  все игроки на сервере получат информацию, что двери суда снова свободны для новых дел.");
        infometa.setLore(infolore);
        info.setItemMeta(infometa);
        sudia_inventory.setItem(1, info);

        ItemStack sudnach = new ItemStack(Material.BOOK);
        ItemMeta sudnachmeta = sudnach.getItemMeta();
        sudnachmeta.setDisplayName("§fОповестить о начале суда");
        sudnachmeta.setLore(clickme);
        sudnach.setItemMeta(sudnachmeta);
        sudia_inventory.setItem(3, sudnach);

        ItemStack sudidet = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta sudidetmeta = sudidet.getItemMeta();
        sudidetmeta.setDisplayName("§fСуд начинается!");
        sudidetmeta.setLore(clickme);
        sudidet.setItemMeta(sudidetmeta);
        sudia_inventory.setItem(4, sudidet);

        ItemStack sudkonec = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta sudkonecmeta = sudkonec.getItemMeta();
        sudkonecmeta.setDisplayName("§fСуд завершен!");
        sudkonecmeta.setLore(clickme);
        sudkonec.setItemMeta(sudkonecmeta);
        sudia_inventory.setItem(5, sudkonec);
        return sudia_inventory;
    }
}
