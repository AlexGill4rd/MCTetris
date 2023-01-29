package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.objects.TetrisBoard;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class TetrisZoneSeletionListener implements Listener {

    public static TetrisBoard tetrisBoard = null;

    @EventHandler
    public void onSelection(PlayerInteractEvent e){

        Player player = e.getPlayer();
        if (e.getClickedBlock() == null ||
                e.getClickedBlock().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getItemMeta() == null || e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getHand() == EquipmentSlot.OFF_HAND)return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§7§l- §6§lTetris §eZoner §7§l-")){
            Location corner = e.getClickedBlock().getLocation();
            if (tetrisBoard == null){
                tetrisBoard = new TetrisBoard(player);
                tetrisBoard.setLeftBottomCorner(corner);
                player.sendMessage("§6Left bottom corner set! Please select the top right corner before beginning");
            }else{
                tetrisBoard.setRightTopCorner(corner);
                player.sendMessage("§6Right top corner set! §e§lYou are ready to play tetris!");
            }
        }
    }
}
