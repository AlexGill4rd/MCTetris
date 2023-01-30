package entertainer.entertainments.tetris.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static entertainer.entertainments.functions.Functions.createItemstack;
import static entertainer.entertainments.tetris.listeners.TetrisZoneSeletionListener.tetrisBoard;

public class TetrisCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("tetris")){

            Player player = (Player) sender;

            if (args.length == 0){
                if (player.hasPermission("tetris.help")){
                    player.sendMessage("§6/tetris §7selection       §8Start with selecting a tetris board");
                    player.sendMessage("§6/tetris §7placer          §8Get a tetris block placer");
                    player.sendMessage("§6/tetris §7start           §8Start the tetris game");
                    player.sendMessage("§6/tetris §7clear           §8Clear het bord");
                    player.sendMessage("§6/tetris §7stop            §8Stop de game");
                }
            }else if (args.length == 1){
                if (args[0].equalsIgnoreCase("selection")){
                    if (player.hasPermission("tetris.selection")){
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add("§8§l---------");
                        lore.add("§6Tool for selecting the tetris area");
                        player.getInventory().addItem(createItemstack(Material.DIAMOND_AXE, "§7§l- §6§lTetris §eZoner §7§l-", lore));
                        player.sendMessage("§6A tool has been added to your inventory! Please select the area! §4Left bottom corner first!");
                    }
                }else if (args[0].equalsIgnoreCase("start")){
                    if (player.hasPermission("tetris.start")){
                        if (tetrisBoard == null){
                            player.sendMessage("§cPlease make a tetris screen first woth the tool before starting a game!");
                            return true;
                        }
                        if (tetrisBoard.start()){
                            player.sendMessage("§6The game has started!");
                        }
                    }
                }else if (args[0].equalsIgnoreCase("clear")){
                    if (player.hasPermission("tetris.clear")){
                        tetrisBoard.clearArea();
                    }
                }else if (args[0].equalsIgnoreCase("stop")){
                    if (player.hasPermission("tetris.stop")){
                        for (Player target : tetrisBoard.getPlayers())
                            target.sendTitle("§4Game Ended!", "§7The game has ended!", 20, 40, 20);
                        tetrisBoard.getHost().sendTitle("§4Game Ended!", "§7The game has ended!", 20, 40, 20);
                        tetrisBoard.stop();
                    }
                }
            }
        }
        return false;
    }
}
