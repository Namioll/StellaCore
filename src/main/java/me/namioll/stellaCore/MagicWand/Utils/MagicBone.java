package me.namioll.stellaCore.MagicWand.Utils;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;


public class MagicBone {

    public static ItemStack createMagicBone(){
        CustomStack stack = CustomStack.getInstance("bone");
        if (stack == null) return null;
        return stack.getItemStack();
    }

    public static boolean isMagicBone(ItemStack bone){
        if (bone == null) return false;
        CustomStack stack = CustomStack.byItemStack(bone);
        return stack != null && stack.getNamespacedID().equals("souls:bone");
    }
}

