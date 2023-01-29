package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.enums.TetrisDirection;
import entertainer.entertainments.tetris.objects.TetrisBlock;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static entertainer.entertainments.tetris.listeners.TetrisZoneSeletionListener.tetrisBoard;

public class TetrisControllerListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getItemMeta() == null || e.getAction() != Action.RIGHT_CLICK_AIR || e.getHand() == EquipmentSlot.OFF_HAND)return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§7§l<")){
            tetrisBoard.currentBlock.move(TetrisDirection.LEFT, -3);
        }else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§7§l>")){
            tetrisBoard.currentBlock.move(TetrisDirection.RIGHT, 3);
        }else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§6§lGo §eFaster")){
            tetrisBoard.currentBlock.move(TetrisDirection.DOWN, -3);
        }
    }
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (player.getInventory().getItem(4) == null ||
                player.getInventory().getItem(4).getType() == Material.AIR ||
                player.getInventory().getItem(4).getItemMeta() == null)return;

        if (player.getInventory().getItem(4).getItemMeta().getDisplayName().equals("§6§lGo §eFaster")){
            e.setCancelled(true);
        }
    }

}
