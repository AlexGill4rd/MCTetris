package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.enums.TetrisDirection;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class TetrisControllerListener implements Listener {

    @EventHandler
    public void onClick(PlayerItemHeldEvent e){
        Player player = e.getPlayer();

        TetrisBoard tetrisBoard = TetrisBoard.getPlayerTetrisboard(player.getUniqueId());

        if (tetrisBoard == null || !tetrisBoard.isStarted())return;
        e.setCancelled(true);

        if (e.getNewSlot() == 0)
            tetrisBoard.currentBlock.move(TetrisDirection.LEFT, -3);
        else if (e.getNewSlot() == 1)
            tetrisBoard.currentBlock.move(TetrisDirection.DOWN, -3);
        else if (e.getNewSlot() == 2)
            tetrisBoard.currentBlock.move(TetrisDirection.RIGHT, 3);
        else if (e.getNewSlot() == 3)
            tetrisBoard.currentBlock.rotateLeft();
        else if (e.getNewSlot() == 4)
            tetrisBoard.currentBlock.rotateRight();
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
