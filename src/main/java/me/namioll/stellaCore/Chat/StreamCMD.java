package me.namioll.stellaCore.Chat;

import me.namioll.stellaCore.ColorUtils;
import me.namioll.stellaCore.StellaCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class StreamCMD implements CommandExecutor {
    private StellaCore plugin;

    public StreamCMD(StellaCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("stellacore.stream")){
            p.sendMessage(ColorUtils.STELLA() + "§cИзвините! Но Вы не можете использовать эту команду, так как не числитесь нашим стримером. Если это не так, свяжитесь с администрацией! Извините :с");
            return true;
        }

        p.openInventory(createStreamerMenu(p));
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1f, 1f);

        return true;
    }

    private Inventory createStreamerMenu(Player p){
        Inventory inv = Bukkit.createInventory(null, 54, ColorUtils.colorize("<#9200FF>&lМеню Стримера</#E60EC1>"));

        ItemStack glass_purple = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta meta = glass_purple.getItemMeta();
        meta.setDisplayName(" ");
        glass_purple.setItemMeta(meta);

        ItemStack glass_gray = new  ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta1 = glass_gray.getItemMeta();
        meta1.setDisplayName(" ");
        glass_gray.setItemMeta(meta1);

        for (int i = 0; i < 54; i++){
            inv.setItem(i, glass_gray);
        }

        int[] purple_slots = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
        for (int i : purple_slots){
            inv.setItem(i,glass_purple);
        }

        PlayerData data = plugin.getPlayerDataManager().get(p);
        if (data.isStreamer()){
            ItemStack yes = yes();
            inv.setItem(20,yes);
        }
        else {
            ItemStack no = no();
            inv.setItem(20,no);
        }

        ItemStack stream = new ItemStack(Material.FIREWORK_ROCKET);
        ItemMeta meta3 = stream.getItemMeta();
        meta3.setDisplayName("§d§lНачать стрим");
        meta3.setLore(List.of("", "§7Нажмите, чтобы оповестить сервер о том, что Вы начали стрим.",
                "§7Игроки смогут зайти на Ваш стрим, §fнаведясь §7на сообщение в чате.", "",
                "§7Если у вас не установлена ссылка на §5Twitch§7, §nВам высветится, как её установить."));
        stream.setItemMeta(meta3);
        inv.setItem(22,stream);

        ItemStack banword = new ItemStack(Material.CREEPER_SPAWN_EGG);
        ItemMeta meta4 = banword.getItemMeta();
        meta4.setDisplayName("§c§cПоказать список банвордов");
        meta4.setLore(List.of("", "§c§l§nВНИМАНИЕ! §fНи в коем случае не нажимайте сюда, если ведёте стрим. Я предупредил."));
        banword.setItemMeta(meta4);
        inv.setItem(24,banword);

        ItemStack FAQ = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta5 = FAQ.getItemMeta();
        meta5.setDisplayName("§c§cFAQ");
        meta5.setLore(List.of(
                "", "§fДобро пожаловать в меню стримера! §4❤", "",
                "§c▪ §e/stream §f- меню стримера.",
                "§c▪ §e/banwords add §f- добавить новый банворд в список.",
                "§c▪ §e/banwords remove §f- удалить банворд из списка.",
                "§c▪ §e/link §f- посмотреть текущую ссылку.",
                "§c▪ §e/link <URL> §f- установить ссылку на ваш §5Twitch."
        ));
        FAQ.setItemMeta(meta5);
        inv.setItem(40,FAQ);

        return inv;
    }

    public static ItemStack yes(){
        ItemStack yes  = new  ItemStack(Material.AXOLOTL_BUCKET);
        ItemMeta meta2 = yes.getItemMeta();
        meta2.setDisplayName("§fРежим стримера §a§l§nВКЛЮЧЕН");
        meta2.setLore(List.of("", "§7Нажмите, чтобы переключить режим."));
        yes.setItemMeta(meta2);
        return yes;
    }

    public static ItemStack no(){
        ItemStack no  = new  ItemStack(Material.BUCKET);
        ItemMeta meta2 = no.getItemMeta();
        meta2.setDisplayName("§fРежим стримера §c§l§nВЫКЛЮЧЕН");
        meta2.setLore(List.of("", "§7Нажмите, чтобы переключить режим."));
        no.setItemMeta(meta2);
        return no;
    }
}
