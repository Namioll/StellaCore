package me.namioll.stellaCore.MagicWand.Utils;

import dev.lone.itemsadder.api.CustomStack;
import me.namioll.stellaCore.ColorUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MagicStaff {

    public static ItemStack createMagicStaff(){
        CustomStack stack = CustomStack.getInstance("test2");
        if (stack == null) return null;

        ItemMeta meta = stack.getItemStack().getItemMeta();
        if (meta == null) return null;

        meta.getPersistentDataContainer().set(PDC.MAGIC_STAFF, PersistentDataType.BYTE, (byte) 1);
        meta.setDisplayName(ColorUtils.colorize("<#A82FD0>Огненный Посох</#FF035A>"));
        stack.getItemStack().setItemMeta(meta);
        return stack.getItemStack();
    }

    public static boolean isMagicStaff(ItemStack item){
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(PDC.MAGIC_STAFF, PersistentDataType.BYTE);
    }
}
