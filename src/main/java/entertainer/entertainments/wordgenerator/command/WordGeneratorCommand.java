package entertainer.entertainments.wordgenerator.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static entertainer.entertainments.Entertainments.wordGenerator;
import static entertainer.entertainments.functions.Functions.createItemstack;
import static entertainer.entertainments.functions.Functions.createLore;

public class WordGeneratorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("wordgenerator")){
            Player player = (Player) sender;

            if (args.length == 0){
                if (player.hasPermission("wordgenerator.help")){
                    player.sendMessage("§9/wordgenerator §fhelp             §7Get information about the commands");
                    player.sendMessage("§9/wordgenerator §ftool             §7Get the word pallet selection tool");
                }
            }else if (args.length == 1){
                if (args[0].equalsIgnoreCase("help")){
                    if (player.hasPermission("wordgenerator.help")){
                        player.sendMessage("§9/wordgenerator §fhelp             §7Get information about the commands");
                        player.sendMessage("§9/wordgenerator §ftool             §7Get the word pallet selection tool");
                    }
                }else if (args[0].equalsIgnoreCase("tool")){
                    if (player.hasPermission("wordgenerator.tool")){
                        ItemStack tool = createItemstack(Material.DIAMOND_AXE, "§7§l- §6§lWord §eZoner §7§l-", createLore("§7§l§m------", "§6Tool to make a selection for the words in the wordsgenerator pallet", "", "§7Usage:", "§fRight-Click-Block     §7Select the right front corner of the first letter", "§7§l§m------"));
                        player.getInventory().addItem(tool);
                        player.sendMessage("§9You have received the tool to select the words and numbers pallet");
                    }
                }
            }else {
                if (args[0].equalsIgnoreCase("write")){
                    if (player.hasPermission("wordgenerator.write")){
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 1; i < args.length; i++){
                            stringBuilder.append(args[i].toUpperCase()).append(" ");
                        }
                        stringBuilder.trimToSize();
                        wordGenerator.writeWord(player.getLocation(), stringBuilder.toString());
                    }
                }
            }
        }
        return false;
    }
}
