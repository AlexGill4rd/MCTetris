package entertainer.entertainments.tetris.command;

import entertainer.entertainments.tetris.inventories.Inventories;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import entertainer.entertainments.tetris.objects.TetrisPlayer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static entertainer.entertainments.Entertainments.*;
import static entertainer.entertainments.functions.Functions.createItemstack;
import static entertainer.entertainments.functions.Functions.hasPerm;

public class TetrisCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("tetris")){

            Player player = (Player) sender;

            if (args.length == 0){
                if (hasPerm(player,"tetris.help")){
                    player.sendMessage("§6/tetris §7tool                        §8Start with selecting a tetris board");
                    player.sendMessage("§6/tetris §7placer                      §8Get a tetris block placer");
                    player.sendMessage("§6/tetris §7find                        §8Start the tetris game");
                    player.sendMessage("§6/tetris §7clear <id>                  §8Clear het bord");
                    player.sendMessage("§6/tetris §7stop <id>                   §8Stop de game");
                    player.sendMessage("§6/tetris §7setspawn <id>               §8Set the spawnpoint for  the tetris game");
                }
            }else if (args.length == 1){
                if (args[0].equalsIgnoreCase("tool")){
                    if (hasPerm(player, "tetris.tool")){
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add("§8§l---------");
                        lore.add("§6Tool for selecting the tetris area");
                        player.getInventory().addItem(createItemstack(Material.DIAMOND_AXE, "§7§l- §6§lTetris §eZoner §7§l-", lore));
                        player.sendMessage("§6A tool has been added to your inventory! Please select the area! §4Left bottom corner first!");
                    }
                }else if (args[0].equalsIgnoreCase("find")){
                    if (hasPerm(player,"tetris.find")){
                        if (activeGames.get(player.getUniqueId()) != null){
                            player.sendMessage("§cYou can't play multiple games at once!");
                            return true;
                        }
                        player.openInventory(Inventories.tetrisFinder());
                    }
                }else if (args[0].equalsIgnoreCase("stats")){
                    if (hasPerm(player,"tetris.stats")){
                        TetrisPlayer tetrisPlayer = tetrisPlayers.get(player.getUniqueId());
                        if (tetrisPlayer == null)
                            player.kickPlayer("§cAn error occured. Please rejoin for the error to be solved.");
                        else
                            player.openInventory(Inventories.playerStats(tetrisPlayer));
                    }
                }
            }else if (args.length == 2){
                if (args[0].equalsIgnoreCase("clear")){
                    if (hasPerm(player,"tetris.clear")){
                        int tetrisID = Integer.parseInt(args[1]);
                        TetrisBoard tetrisBoard = tetrisBoards.get(tetrisID);
                        tetrisBoard.clearArea();
                    }
                }else if (args[0].equalsIgnoreCase("stop")){
                    if (hasPerm(player,"tetris.stop")){
                        int tetrisID = Integer.parseInt(args[1]);
                        TetrisBoard tetrisBoard = tetrisBoards.get(tetrisID);

                        tetrisBoard.getPlayer().sendTitle("§4Game Ended!", "§7The game was forcefully ended!", 20, 40, 20);

                        tetrisBoard.stop();
                    }
                }else if (args[0].equalsIgnoreCase("setspawn")){
                    if (hasPerm(player,"tetris.setspawn")){
                        int tetrisID = Integer.parseInt(args[1]);
                        TetrisBoard tetrisBoard = tetrisBoards.get(tetrisID);
                        if (tetrisBoard != null){
                            tetrisBoard.setSpawnLocation(player.getLocation());
                            player.sendMessage("§6You have set the spawnpoint for this tetris game!");
                        }else{
                            player.sendMessage("§cInvalid tetris game id!");
                        }
                    }
                }
            }
        }
        return false;
    }
}
