package entertainer.entertainments.functions;

import org.bukkit.Material;
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
}
