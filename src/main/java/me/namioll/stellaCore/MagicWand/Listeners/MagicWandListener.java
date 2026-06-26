package me.namioll.stellaCore.MagicWand.Listeners;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.MagicWand.Utils.MagicBone;
import me.namioll.stellaCore.MagicWand.Utils.MagicMinoBone;
import me.namioll.stellaCore.MagicWand.Utils.MagicWand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.*;

public class MagicWandListener implements Listener {

    private final Random random = new Random();
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final Set<Material> WOOD_TYPES = Set.of(
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.BIRCH_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.DARK_OAK_LOG,
            Material.MANGROVE_LOG,
            Material.CHERRY_LOG,
            Material.CRIMSON_STEM,
            Material.WARPED_STEM
    );

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Block block = e.getBlock();
        if (WOOD_TYPES.contains(block.getType())) {
            Random rand = new Random();

            int chance = 500;
            int lucklvl = 0;

            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasEnchant(Enchantment.FORTUNE)) {
                    lucklvl = meta.getEnchantLevel(Enchantment.FORTUNE);
                }
            }

            int drop = rand.nextInt(chance - (lucklvl*60));
            if (drop == 0){
                Location loc = e.getBlock().getLocation();
                loc.getWorld().dropItem(loc, MagicWand.createMagicWand());
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack cursorItem = e.getCursor();
        ItemStack clickedItem = e.getCurrentItem();

        if (cursorItem != null && clickedItem != null &&
                MagicBone.isMagicBone(cursorItem) &&
                MagicWand.isMagicWand(clickedItem)) {
            e.setCancelled(true);

            if (clickedItem.getAmount() > 1) {
                player.sendMessage(ColorUtils.STELLA() + "§cНельзя улучшать несколько палок одновременно!");
                return;
            }

            useBoneOnWand(player, cursorItem, clickedItem, false);
        }
        else if (cursorItem != null && clickedItem != null &&
                MagicMinoBone.isMinoBone(cursorItem) &&
                MagicWand.isMagicWand(clickedItem)) {
            e.setCancelled(true);
            if (clickedItem.getAmount() > 1) {
                player.sendMessage(ColorUtils.STELLA() + "§cНельзя улучшать несколько палок одновременно!");
                return;
            }

            useBoneOnWand(player, cursorItem, clickedItem, true);
        }
    }

    private void useBoneOnWand(Player player, ItemStack bone, ItemStack wand, boolean isMinoBone) {
        if (player.getGameMode() != org.bukkit.GameMode.CREATIVE) {
            bone.setAmount(bone.getAmount() - 1);
        }
        MagicWand.increaseWandLVL(player, wand, isMinoBone);
    }

    @EventHandler
    public void onWandUse(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR &&
                e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!MagicWand.isMagicWand(item)) return;

        UUID playerId = player.getUniqueId();
        long now = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && now - cooldowns.get(playerId) < 700) {
            return;
        }
        cooldowns.put(playerId, now);

        int wandLevel = MagicWand.getWandLVL(item);
        int chance = random.nextInt(100) + 1;

        if (chance <= wandLevel) {
            launchPlayer(player, wandLevel);
        } else {
            player.getWorld().playSound(player.getLocation(),
                    Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 1.0f);
        }
    }

    private void launchPlayer(Player p, int wandLevel) {
        Vector dir = p.getEyeLocation().getDirection();
        dir.multiply(0.8 + (double) wandLevel/50);
        dir.setY(dir.getY()+0.2 + (wandLevel * 0.003));
        p.setVelocity(dir);

        float pitch = 1.5f - (float)(wandLevel * 0.005);
        if (pitch < 0.8f) pitch = 0.8f;

        p.getWorld().playSound(p.getLocation(),
                Sound.ENTITY_BLAZE_SHOOT, 1.0f, pitch);
    }
}
