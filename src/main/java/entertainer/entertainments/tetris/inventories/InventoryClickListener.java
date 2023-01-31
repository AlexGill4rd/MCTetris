package entertainer.entertainments.tetris.inventories;

import entertainer.entertainments.functions.ItemManager;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static entertainer.entertainments.Entertainments.tetrisBoards;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){

        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null)return;
        if (e.getView().getTitle().equals("§8§l|       §6Tetris finder       §8§l|")){
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.LIME_DYE || e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE){
                int tetrisboardID = ItemManager.getNBTint(e.getCurrentItem(), "id");
                TetrisBoard tetrisBoard = tetrisBoards.get(tetrisboardID);
                if (!tetrisBoard.isStarted()){
                    player.closeInventory();
                    player.updateInventory();
                    tetrisBoard.start(player);
                }else
                    player.sendMessage("§cThis game has already began!");
            }
        }
    }
}
