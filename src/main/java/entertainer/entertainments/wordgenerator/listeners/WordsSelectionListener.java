package entertainer.entertainments.wordgenerator.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static entertainer.entertainments.Entertainments.wordGenerator;

public class WordsSelectionListener implements Listener {

    @EventHandler
    public void onSelection(PlayerInteractEvent e){

        Player player = e.getPlayer();
        if (e.getClickedBlock() == null ||
                e.getClickedBlock().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getItemMeta() == null || e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getHand() == EquipmentSlot.OFF_HAND)return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§7§l- §6§lWord §eZoner §7§l-")){
            Location corner = e.getClickedBlock().getLocation();
            wordGenerator.initialiseWords(corner);

            player.sendMessage("§6You have set the words pallet!");
        }
    }
}
