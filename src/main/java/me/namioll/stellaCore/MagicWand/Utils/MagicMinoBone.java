package me.namioll.stellaCore.MagicWand.Utils;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;

public class MagicMinoBone {

    public static ItemStack createMinoBone(){
        CustomStack stack = CustomStack.getInstance("minobone");
        if (stack == null) return null;
        return stack.getItemStack();
    }

    public static boolean isMinoBone(ItemStack bone){
        if (bone == null) return false;
        CustomStack stack = CustomStack.byItemStack(bone);
        return stack != null && stack.getNamespacedID().equals("souls:minobone");
    }
}
