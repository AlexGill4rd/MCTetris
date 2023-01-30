package entertainer.entertainments.functions;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

    public static void applyNBTTag(ItemStack itemStack, String key, Object value) {
        ItemStack is = NBTEditor.set(itemStack, value, key);
        ItemMeta itemMeta = is.getItemMeta();
        itemStack.setItemMeta(itemMeta);
    }

    public static void removeNBTTag(ItemStack stack, String obj) {
        ItemStack is = NBTEditor.set(stack, null, obj);
        ItemMeta meta = is.getItemMeta();
        stack.setItemMeta(meta);
    }

    public static int getNBTint(ItemStack item, String object) {
        return NBTEditor.getInt(item, object);
    }

    public static boolean getNBTboolean(ItemStack item, Object object) {
        return NBTEditor.getBoolean(item, object);
    }

    public static double getNBTdouble(ItemStack item, Object object) {
        return NBTEditor.getDouble(item, object);
    }

    public static String getNBTString(ItemStack item, String object) {
        return NBTEditor.getString(item, object);
    }
}