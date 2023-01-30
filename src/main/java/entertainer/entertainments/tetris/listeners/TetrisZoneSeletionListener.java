package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.objects.TetrisBoard;
import entertainer.entertainments.tetris.objects.TetrisSelection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static entertainer.entertainments.Entertainments.tetrisBoards;

public class TetrisZoneSeletionListener implements Listener {

    private HashMap<UUID, TetrisSelection> selections = new HashMap<>();

    @EventHandler
    public void onSelection(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (e.getClickedBlock() == null ||
                e.getClickedBlock().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getType() == Material.AIR ||
                player.getInventory().getItemInMainHand().getItemMeta() == null || (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getAction() != Action.LEFT_CLICK_BLOCK) || e.getHand() == EquipmentSlot.OFF_HAND)return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§7§l- §6§lTetris §eZoner §7§l-")){
            Location corner = e.getClickedBlock().getLocation();
            TetrisSelection tetrisSelection = selections.get(player.getUniqueId());
            if (tetrisSelection == null){
                TetrisSelection selection = new TetrisSelection();
                selections.put(player.getUniqueId(), selection);
                tetrisSelection = selection;
            }

            if (e.getAction() == Action.LEFT_CLICK_BLOCK){
                tetrisSelection.setLeftCorner(corner);
                player.sendMessage("§6Left bottom corner set! Please select the top right corner before beginning");
            }else if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
                tetrisSelection.setRightCorner(corner);
                player.sendMessage("§6Right top corner set!");
            }
            if (tetrisSelection.getRightCorner() != null && tetrisSelection.getRightCorner() != null){
                tetrisBoards.put(player.getUniqueId(), new TetrisBoard(player, tetrisSelection));
                player.sendMessage("§6All corners are set! Your tetris area is being made!");
            }
        }
    }
}
