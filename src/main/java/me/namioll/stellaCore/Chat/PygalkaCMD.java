package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.StellaCore;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PygalkaCMD implements CommandExecutor {

    private final String[] PHRASES = {
            "Тени начинают шептать забытые имена...",
            "Воздух холодеет, дыхание смерти ощутимо...",
            "Печати древних гробниц трескаются...",
            "Эхо проклятий разносится по ветру...",
            "Мана искажается, реальность дрожит...",
            "Кости в земле стучат в такт невидимому сердцу...",
            "Завеса между мирами истончается...",
            "Призраки прошлого пробуждаются от вечного сна...",
            "Руны на камнях начинают светиться тусклым светом...",
            "Власть некромантов просыпается ото сна...",
            "Тлен и разложение наполняют воздух сладковатым запахом...",
            "Древние алтари жаждут новых жертвоприношений...",
            "Тьма в сердцах живых находит отклик...",
            "Лунный свет окрашивается в багровый оттенок...",
            "Земля содрогается от шагов нежити...",
            "Последние искры жизни готовы угаснуть...",
            "Проклятые души тянутся к миру живых...",
            "Книги запретных знаний открывают свои страницы...",
            "Время замирает в ожидании темного часа...",
            "Вечность смотрит на этот мир пустыми глазницами..."
    };

    public void pygalka(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cОшибка! Ваше сообщение должно состоять хотябы из 1 слова.");
        }
        else {
            Random random = new Random();
            String randomPhrase = PHRASES[random.nextInt(PHRASES.length)];
            String message = String.join(" ", args);

            Bukkit.broadcastMessage(ColorUtils.colorize("<#2ECD37>" + randomPhrase + "</#2ECD37>"));

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_AMBIENT, 0.8f, 0.7f);
                p.playSound(p.getLocation(), Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, 0.5f, 1.0f);
                p.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.8f, 0.9f);
            }

            World world = Bukkit.getWorlds().get(0);
            long time = world.getTime();
            world.setTime(18000);
            world.setStorm(true);
            world.setThundering(true);


            Bukkit.getScheduler().runTaskLater(StellaCore.getPlugin(), () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 1));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 1));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 60, 1));
                    p.sendMessage(ColorUtils.colorize("<#894EFF>&k&l123213213213123212131322112321312312313113112</#894EFF>"));
                    p.sendMessage("");
                    p.sendMessage(ColorUtils.colorize("<#3DA9FA>✟ " + message.toUpperCase() + " ✟</#3DA9FA>"));
                    p.sendMessage("");
                    p.sendMessage(ColorUtils.colorize("<#894EFF>&k&l123213213213123212131322112321312312313113112</#894EFF>"));
                    p.playSound(p.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.2f, 0.6f);
                    p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_PREPARE_WOLOLO, 1.0f, 0.5f);
                    p.playSound(p.getLocation(), Sound.ENTITY_VEX_AMBIENT, 0.8f, 0.8f);
                }
            }, 200L);

            Bukkit.getScheduler().runTaskLater(StellaCore.getPlugin(), () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                }

                world.setTime(time);
                world.setStorm(false);
                world.setThundering(false);

            }, 600L);
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender s) {
            pygalka(sender, args);
        }
        else if (sender instanceof Player p) {
            if (p.hasPermission("stellacore.pygalka")) { pygalka(p, args);}
        }
        return true;
    }
}

