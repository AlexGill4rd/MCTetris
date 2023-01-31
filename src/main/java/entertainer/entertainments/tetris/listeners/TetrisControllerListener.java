package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.enums.RotateDirection;
import entertainer.entertainments.tetris.enums.TetrisDirection;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import static entertainer.entertainments.Entertainments.activeGames;
import static entertainer.entertainments.Entertainments.tetrisBoards;

public class TetrisControllerListener implements Listener {

    @EventHandler
    public void onClick(PlayerItemHeldEvent e){
        Player player = e.getPlayer();



        if (activeGames.get(player.getUniqueId()) == null)return;
        TetrisBoard tetrisBoard = tetrisBoards.get(activeGames.get(player.getUniqueId()));

        e.setCancelled(true);

        if (e.getNewSlot() == 3)
            tetrisBoard.currentBlock.move(TetrisDirection.LEFT, -3);
        else if (e.getNewSlot() == 4)
            tetrisBoard.currentBlock.move(TetrisDirection.DOWN, -3);
        else if (e.getNewSlot() == 5)
            tetrisBoard.currentBlock.move(TetrisDirection.RIGHT, 3);
        else if (e.getNewSlot() == 2)
            tetrisBoard.currentBlock.rotate(RotateDirection.LEFT);
        else if (e.getNewSlot() == 6)
            tetrisBoard.currentBlock.rotate(RotateDirection.RIGHT);
    }
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (activeGames.get(player.getUniqueId()) != null)
            e.setCancelled(true);
    }
}
