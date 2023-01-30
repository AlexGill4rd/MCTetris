package entertainer.entertainments.tetris.inventories;

import entertainer.entertainments.functions.ItemManager;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import entertainer.entertainments.wordgenerator.WordGenerator;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static entertainer.entertainments.Entertainments.tetrisBoards;
import static entertainer.entertainments.Entertainments.wordGenerator;
import static entertainer.entertainments.functions.Functions.createItemstack;
import static entertainer.entertainments.functions.Functions.createLore;

public class Inventories {

    public static Inventory tetrisFinder(){
        Inventory inventory = Bukkit.createInventory(null, 27, "§8§l|       §6Tetris finder       §8§l|");


        int counter = 0;
        for (TetrisBoard tetrisBoard : tetrisBoards.values()){

            ItemStack itemStack;
            if (tetrisBoard.isStarted()){
                itemStack = createItemstack(Material.RED_STAINED_GLASS_PANE, "§6Matchmaking: §e" + counter,
                        createLore("§7§l§m----",
                                "§6This game has already begun! Wait till the game is over!",
                                "",
                                "§fInformation:",
                                "§7Current Player: " + tetrisBoard.getPlayer().getDisplayName(),
                                "§7Start Delay: " + tetrisBoard.getStartDelay(),
                                "§7Block Speed: " + tetrisBoard.getBlockSpeed(),
                                "§7§l§m----"));
            }else if (tetrisBoard.getSpawnLocation() == null ||
                    tetrisBoard.getRightTopCorner() == null ||
                    tetrisBoard.getLeftBottomCorner() == null ||
                    !wordGenerator.isConfigured){
                itemStack = createItemstack(Material.ORANGE_STAINED_GLASS_PANE, "§6Matchmaking: §e" + counter,
                        createLore("§7§l§m----",
                                "§6This game can't start because the tetris game is not correctly configured!",
                                "",
                                "§fInformation:",
                                "§7Start Delay: " + tetrisBoard.getStartDelay(),
                                "§7Block Speed: " + tetrisBoard.getBlockSpeed(),
                                "§7§l§m----"));
            } else{
                itemStack = createItemstack(Material.LIME_STAINED_GLASS_PANE, "§6Matchmaking: §e" + counter,
                        createLore("§7§l§m----",
                                "§6Click on the item to go to the game. When clicked, the game wil automatically start after " + tetrisBoard.getStartDelay() + " seconds!",
                                "",
                                "§fInformation:",
                                "§7Start Delay: " + tetrisBoard.getStartDelay(),
                                "§7Block Speed: " + tetrisBoard.getBlockSpeed(),
                                "§7§l§m----"));
            }
            ItemManager.applyNBTTag(itemStack, "id", tetrisBoard.getID());
            inventory.setItem(counter, itemStack);

            inventory.setItem(26, createItemstack(Material.ARROW, "§4§lClose menu ➜", createLore("§7§l----", "§6Close the tetris finder menu by clicking on this item.", "§7§l----")));
            counter++;
        }
        return inventory;
    }

}
