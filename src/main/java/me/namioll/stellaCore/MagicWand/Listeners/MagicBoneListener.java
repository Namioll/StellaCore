package me.namioll.stellaCore.MagicWand.Listeners;

import me.namioll.stellaCore.MagicWand.Utils.MagicBone;
import me.namioll.stellaCore.MagicWand.Utils.MagicMinoBone;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class MagicBoneListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if (e.getEntity().getKiller() == null) return;

        Player killer = e.getEntity().getKiller();
        ItemStack weapon = killer.getInventory().getItemInMainHand();

        int lootingLevel = 0;
        if (weapon.hasItemMeta()) {
            ItemMeta meta = weapon.getItemMeta();
            if (meta.hasEnchant(Enchantment.LOOTING)) {
                lootingLevel = meta.getEnchantLevel(Enchantment.LOOTING);
            }
        }

        boolean isSilverFish = false;
        int baseChance = 1000;
        int reducedChance = baseChance - (lootingLevel * 50);

        if (reducedChance < 50) {
            reducedChance = 25;
        }

        if (e.getEntityType() == EntityType.SILVERFISH) {
            reducedChance *= 20;
            isSilverFish = true;
        }

        Random rand = new Random();
        int drop = rand.nextInt(reducedChance);
        int dropMinoBone = rand.nextInt(5000);
        if (isSilverFish) {
            dropMinoBone = 1;
        }

        if (drop == 0){
            ItemStack bone = MagicBone.createMagicBone();
            e.getDrops().add(bone);
        }

        if (dropMinoBone == 0){
            ItemStack minobone = MagicMinoBone.createMinoBone();
            e.getDrops().add(minobone);
        }
    }
}
