package me.namioll.stellaCore.Crafts;

import me.namioll.stellaCore.StellaCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class LightBlock {

    private StellaCore plugin;

    public void register(StellaCore plugin) {
        ItemStack light = new ItemStack(Material.LIGHT);
        NamespacedKey key = new NamespacedKey(plugin, "light_block");
        ShapedRecipe recipe = new ShapedRecipe(key, light);
        recipe.shape(
                "WWW",
                "WTW",
                "WWW");
        recipe.setIngredient('W', Material.GLASS_PANE);
        recipe.setIngredient('T', Material.TORCH);
        Bukkit.addRecipe(recipe);
    }
    
}
