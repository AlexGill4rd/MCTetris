package entertainer.entertainments.tetris.inventories;

import entertainer.entertainments.functions.ItemManager;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import entertainer.entertainments.tetris.objects.TetrisPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static entertainer.entertainments.Entertainments.tetrisBoards;
import static entertainer.entertainments.Entertainments.wordGenerator;
import static entertainer.entertainments.functions.Functions.*;

public class Inventories {

    public static Inventory playerStats(TetrisPlayer tetrisPlayer){
        Inventory inventory = Bukkit.createInventory(null, 9, "§8§l|       §6Tetris Player Stats       §8§l|");

        ItemStack head = createHead(tetrisPlayer.getPlayer(), "§7§l- §6§l" + tetrisPlayer.getPlayer().getName() + " §7§l-", createLore(
                "§8§l§m----",
                "§7Last game score: §f" + tetrisPlayer.getLastScore(),
                "§7Last game duration: §f" + calculateTime((long) ((System.currentTimeMillis() - tetrisPlayer.getLastGameDuration()) / 1000f)),
                "§7Last games lines: §f" + tetrisPlayer.getLastLines(),
                "",
                "§cNote!",
                "§7Al the above data wil change when you play a new game! This are changing results.",
                "§8§l§m----"
        ));
        ItemStack totalScore = createItemstack(Material.PAPER, "§7§l- §6§lTotal Score §f" + tetrisPlayer.getTotalScore() + " §7§l-", createLore(
                "§8§l§m----",
                "§7In the name of this item you can see how much you have achieved in total over your entire playing experience.",
                "§8§l§m----"
        ));
        ItemStack totalLines = createItemstack(Material.PAPER, "§7§l- §6§lTotal Lines §f" + tetrisPlayer.getTotalLines() + " §7§l-", createLore(
                "§8§l§m----",
                "§7At the top you can see how many horizontal lines of tetris have already been cleared by you.",
                "§8§l§m----"
        ));
        ItemStack totalPlayTime = createItemstack(Material.PAPER, "§7§l- §6§lPlaytime §7§l-", createLore(
                "§8§l§m----",
                "§7Current Playtime: §f" + calculateTime(tetrisPlayer.getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE) * 60L),
                "§8§l§m----"
        ));
        inventory.setItem(2, head);
        inventory.setItem(4, totalScore);
        inventory.setItem(5, totalLines);
        inventory.setItem(6, totalPlayTime);

        fillInv(inventory, createItemstack(Material.BLACK_STAINED_GLASS_PANE, "", null));

        return inventory;
    }

    public static Inventory tetrisFinder(){
        Inventory inventory = Bukkit.createInventory(null, 27, "§8§l|       §6Tetris finder       §8§l|");


        int counter = 0;
        for (TetrisBoard tetrisBoard : tetrisBoards.values()){

            ItemStack itemStack;
            if (tetrisBoard.isStarted()){
                itemStack = createItemstack(Material.RED_STAINED_GLASS_PANE, "§4Matchmaking: §c" + counter,
                        createLore("§7§l§m----",
                                "§6This game has already begun! Wait till the game is over!",
                                "",
                                "§fInformation:",
                                "§7Current Player: " + tetrisBoard.getPlayer().getDisplayName(),
                                "§7Start Delay: " + tetrisBoard.getStartDelay(),
                                "§7Block Speed: " + tetrisBoard.getBlockSpeed(),
                                "§7§l§m----"));
                ItemManager.applyNBTTag(itemStack, "tetcustom", "playing");
            }else if (tetrisBoard.getSpawnLocation() == null ||
                    tetrisBoard.getRightTopCorner() == null ||
                    tetrisBoard.getLeftBottomCorner() == null ||
                    !wordGenerator.isConfigured){
                itemStack = createItemstack(Material.ORANGE_STAINED_GLASS_PANE, "§6ERROR IN CREATION!",
                        createLore("§7§l§m----",
                                "§6This game can't start because the tetris game is not correctly configured!",
                                "",
                                "§fInformation:",
                                "§7Start Delay: " + tetrisBoard.getStartDelay(),
                                "§7Block Speed: " + tetrisBoard.getBlockSpeed(),
                                "§7§l§m----"));
                ItemManager.applyNBTTag(itemStack, "tetcustom", "invalid");
            } else{
                itemStack = createItemstack(Material.LIME_DYE, "§6Matchmaking: §e" + counter,
                        createLore("§7§l§m----",
                                "§6Click on the item to go to the game. When clicked, the game wil automatically start after " + tetrisBoard.getStartDelay() + " seconds!",
                                "",
                                "§fInformation:",
                                "§7Start Delay: " + tetrisBoard.getStartDelay(),
                                "§7Block Speed: " + tetrisBoard.getBlockSpeed(),
                                "§7§l§m----"));
                ItemManager.applyNBTTag(itemStack, "tetcustom", "playable");
            }
            ItemManager.applyNBTTag(itemStack, "id", tetrisBoard.getID());
            inventory.setItem(counter, itemStack);

            inventory.setItem(26, createItemstack(Material.ARROW, "§4§lClose menu ➜", createLore("§7§l----", "§6Close the tetris finder menu by clicking on this item.", "§7§l----")));
            counter++;
        }
        return inventory;
    }

}
