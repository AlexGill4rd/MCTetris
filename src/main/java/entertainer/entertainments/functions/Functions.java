package entertainer.entertainments.functions;

import entertainer.entertainments.configuration.Configs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Functions {

    public static ItemStack createItemstack(Material material, String title, ArrayList<String> lore){
        ItemStack stack = new ItemStack(material);
        ItemMeta stack_meta = stack.getItemMeta();
        if (title != null)stack_meta.setDisplayName(title);
        if (lore != null)stack_meta.setLore(lore);
        stack.setItemMeta(stack_meta);
        return stack;
    }
    public static ArrayList<String> createLore(String... args){
        ArrayList<String> lines = new ArrayList<>();
        for (String arg : args)
            lines.addAll(splitString(arg, 40));
        return lines;
    }
    public static ArrayList<String> splitString(String s, int length){
        ArrayList<String> list = new ArrayList<>();
        StringBuilder sentence = new StringBuilder();
        String[] words = s.split(" ");
        String latestColor = "§7";
        for (String word : words) {
            if (word.contains("§"))
                latestColor = "§" + word.charAt(word.indexOf("§") + 1);
            if (sentence.length() + word.length() + 1 > length) {
                list.add(sentence.toString().trim());
                sentence = new StringBuilder();
                sentence.append(latestColor).append(word).append(" ");
            } else sentence.append(latestColor).append(word).append(" ");
        }
        list.add(sentence.toString().trim());
        return list;
    }
    public static boolean hasPerm(Player player, String permission){
        if (player.hasPermission(permission)) return true;
        player.sendMessage(getMessage("No Permissions").replace("<permission>", permission));
        return false;
    }
    public static String getMessage(String path){
        if (Configs.getCustomConfig1().contains(path)){
            return color(Configs.getCustomConfig1().getString(path));
        }
        return "§cInvalid Message";
    }
    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
