package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.objects.PalletHandler;
import entertainer.entertainments.tetris.objects.TetrisBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static entertainer.entertainments.tetris.listeners.PalletSelectListener.palletHandler;

public class TetrisBlockPlacer implements Listener {

    @EventHandler
    public void onSelectionClick(PlayerInteractEvent e){

        Player player = e.getPlayer();
        if (e.getClickedBlock() == null ||
                e.getClickedBlock().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getItemMeta() == null || e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getHand() == EquipmentSlot.OFF_HAND)return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("tetris placer")){
            TetrisBlock tetrisBlock = palletHandler.getTetrisBlock(6);
            tetrisBlock.place(e.getClickedBlock().getLocation(), 2);
            player.sendMessage("§6You have placed a tetris block!");
        }
    }

}
