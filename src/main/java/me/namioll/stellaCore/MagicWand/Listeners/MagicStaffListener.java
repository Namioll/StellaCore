package me.namioll.stellaCore.MagicWand.Listeners;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.StellaCore;
import me.namioll.stellaCore.MagicWand.Utils.MagicStaff;
import org.bukkit.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MagicStaffListener implements Listener {

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private StellaCore plugin;

    public MagicStaffListener(StellaCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK &&
                e.getAction() != Action.RIGHT_CLICK_AIR) return;

        Player p = e.getPlayer();
        UUID playerId = p.getUniqueId();
        long now = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && now - cooldowns.get(playerId) < 2000) {
            return;
        }
        cooldowns.put(playerId, now);

        ItemStack item = p.getInventory().getItemInMainHand();

        if (!MagicStaff.isMagicStaff(item)) return;

        Location loc = p.getEyeLocation();
        Vector dir = loc.getDirection();
        Fireball fireball = p.getWorld().spawn(loc, Fireball.class);

        fireball.setDirection(dir);
        fireball.setYield(0);
        fireball.setIsIncendiary(false);
        fireball.setShooter(p);

        Vector recoil = dir.clone().multiply(-3.0);

        p.setVelocity(recoil);
        startFireTrail(p);

        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1f, 1f);
    }

    @EventHandler
    public void onFireballHit(EntityDamageByEntityEvent e){
        if (!(e.getDamager() instanceof Fireball fb)) return;
        if (!(e.getEntity() instanceof Player)) {
            e.setDamage(e.getDamage()*5);
            return;
        }
        if (!(fb.getShooter() instanceof Player p)) return;

        ItemStack item = p.getInventory().getItemInMainHand();
        if (!MagicStaff.isMagicStaff(item)) return;

        e.setDamage(0);
    }

    private void startFireTrail(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getVelocity().length() < 0.1) {
                    this.cancel();
                    return;
                }
                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 5, 0.2, 0.5, 0.2, 0);
            }
        }.runTaskTimer(StellaCore.getPlugin(), 0L, 2L);
    }
}
