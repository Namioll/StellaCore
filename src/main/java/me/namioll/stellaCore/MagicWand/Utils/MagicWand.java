package me.namioll.stellaCore.MagicWand.Utils;

import dev.lone.itemsadder.api.CustomStack;
import me.namioll.stellaCore.ColorUtils;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class MagicWand {

    public static ItemStack createMagicWand(){
        CustomStack stack = CustomStack.getInstance("stick");
        if (stack == null) return null;

        ItemMeta meta = stack.getItemStack().getItemMeta();
        if (meta == null) return null;

        meta.getPersistentDataContainer().set(PDC.MAGIC_WAND, PersistentDataType.BYTE, (byte) 1);
        meta.getPersistentDataContainer().set(PDC.WAND_LEVEL, PersistentDataType.INTEGER, 0);
        stack.getItemStack().setItemMeta(meta);

        return stack.getItemStack();
    }

    public static boolean isMagicWand(ItemStack wand){
        if (wand == null || !wand.hasItemMeta()) return false;
        return (wand.getItemMeta().getPersistentDataContainer().has(PDC.MAGIC_WAND, PersistentDataType.BYTE));
    }

    public static ItemStack createMagicWand(int level) {
        CustomStack stack = CustomStack.getInstance("stick");
        if (stack == null) return null;

        ItemMeta meta = stack.getItemStack().getItemMeta();
        meta.setDisplayName("§5Тронутая тьмой палка §c+" + level);
        meta.getPersistentDataContainer().set(PDC.MAGIC_WAND, PersistentDataType.BYTE, (byte) 1);
        meta.getPersistentDataContainer().set(PDC.WAND_LEVEL, PersistentDataType.INTEGER, level);

        int sharpness = level/12;
        int looting = level/5;
        int knockback = level/10;
        int sweeeping = level/20;
        int fire = level/33;

        meta.addEnchant(Enchantment.SHARPNESS, sharpness, true);
        meta.addEnchant(Enchantment.LOOTING, looting, true);
        meta.addEnchant(Enchantment.KNOCKBACK, knockback, true);
        meta.addEnchant(Enchantment.SWEEPING_EDGE, sweeeping, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, fire, true);

        stack.getItemStack().setItemMeta(meta);
        return stack.getItemStack();
    }

    public static int getWandLVL(ItemStack wand){
        if (!isMagicWand(wand)) return 0;

        ItemMeta meta = wand.getItemMeta();
        if (meta == null) return 0;

        Integer lvl = meta.getPersistentDataContainer().get(PDC.WAND_LEVEL, PersistentDataType.INTEGER);
        return lvl == null ? 0 : lvl;
    }

    public static void deleteMagicWand(Player p, ItemStack wand){
        wand.setAmount(0);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        p.sendMessage(ColorUtils.STELLA() +"§cФатальное невезение!");
    }

    public static void increaseWandLVL(Player p, ItemStack wand, boolean isMinoBone){
        if (!isMagicWand(wand)) return;

        ItemMeta meta = wand.getItemMeta();
        int lvl = getWandLVL(wand);

        Random random = new Random();
        double chance = random.nextDouble(100) + 1;
        double burnChance = 0;

        if (lvl >= 15) {
            burnChance = (double) (lvl - 15) / 5.5;
            if (burnChance > 20.0) burnChance = 20.0;
        }

        if (isMinoBone){
            burnChance = 0.0;
            p.sendMessage(ColorUtils.STELLA() + "§aТепло Миношуры позволило Вам прокачать палку без риска её сломать!");
        }

        if (chance <= burnChance) {
            deleteMagicWand(p, wand);
            String formattedChance = String.format("%.2f", burnChance);
            p.sendMessage(ColorUtils.STELLA() + "§cФатальное невезение! Шанс неудачи был: §4" + formattedChance + "§c%. §c(LVL: §4" + (lvl+1) + "§c)");
            return;
        }

        for (Enchantment ench : meta.getEnchants().keySet()) {
            meta.removeEnchant(ench);
        }

        lvl++;
        meta.getPersistentDataContainer().set(PDC.WAND_LEVEL, PersistentDataType.INTEGER, lvl);

        int sharpness = lvl/12;
        int looting = lvl/5;
        int knockback = lvl/10;
        int sweeeping = lvl/20;
        int fire = lvl/33;

        meta.addEnchant(Enchantment.SHARPNESS, sharpness, true);
        meta.addEnchant(Enchantment.LOOTING, looting, true);
        meta.addEnchant(Enchantment.KNOCKBACK, knockback, true);
        meta.addEnchant(Enchantment.SWEEPING_EDGE, sweeeping, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, fire, true);

        meta.setDisplayName("§5Тронутая тьмой палка §c+" + lvl);
        wand.setItemMeta(meta);
        p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1.0f, 1.0f);
    }
}