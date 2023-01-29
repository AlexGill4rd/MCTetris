package entertainer.entertainments.tetris.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static entertainer.entertainments.functions.Functions.createItemstack;

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
                }
            }
        }
        return false;
    }
}
