package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.objects.PalletHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PalletSelectListener implements Listener {

    public static PalletHandler palletHandler;

    @EventHandler
    public void onSelectionClick(PlayerInteractEvent e){

        Player player = e.getPlayer();
        if (e.getClickedBlock() == null ||
                e.getClickedBlock().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getItemMeta() == null || e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getHand() == EquipmentSlot.OFF_HAND)return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("tetris selector")){
            Location rightForwardCorner = e.getClickedBlock().getLocation();
            palletHandler = new PalletHandler(rightForwardCorner);
            player.sendMessage("§6You have made the pallet for tetris!");
        }
    }
}
