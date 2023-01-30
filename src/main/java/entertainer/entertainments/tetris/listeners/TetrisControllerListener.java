package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.enums.TetrisDirection;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;

import static entertainer.entertainments.tetris.listeners.TetrisZoneSeletionListener.tetrisBoard;

public class TetrisControllerListener implements Listener {

    @EventHandler
    public void onClick(PlayerItemHeldEvent e){
        Player player = e.getPlayer();

        if (!tetrisBoard.isStarted())return;
        e.setCancelled(true);

        if (e.getNewSlot() == 0){
            tetrisBoard.currentBlock.move(TetrisDirection.LEFT, -3);
        }else if (e.getNewSlot() == 1){
            tetrisBoard.currentBlock.move(TetrisDirection.DOWN, -3);
        }else if (e.getNewSlot() == 2){
            tetrisBoard.currentBlock.move(TetrisDirection.RIGHT, 3);
        }else if (e.getNewSlot() == 3){
            tetrisBoard.currentBlock.rotateLeft();
        }else if (e.getNewSlot() == 4){
            tetrisBoard.currentBlock.rotateRight();
        }

        /*
        if (tetrisBoard.isStarted() && player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            if (e.getAction() == Action.LEFT_CLICK_AIR)
                tetrisBoard.currentBlock.rotateLeft();
            if (e.getAction() == Action.RIGHT_CLICK_AIR){
                tetrisBoard.currentBlock.rotateRight();
            }
            return;
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getItemMeta() == null || e.getAction() != Action.RIGHT_CLICK_AIR || e.getHand() == EquipmentSlot.OFF_HAND)return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§7§l<")){
            tetrisBoard.currentBlock.move(TetrisDirection.LEFT, -3);
        }else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§7§l>")){
            tetrisBoard.currentBlock.move(TetrisDirection.RIGHT, 3);
        }else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§6§lGo §eFaster")){
            tetrisBoard.currentBlock.move(TetrisDirection.DOWN, -3);
        }*/
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
