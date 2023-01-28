package entertainer.entertainments;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static entertainer.entertainments.Entertainments.featureManager;

public class FeatureCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("groundlifter")) {
            Player player = (Player) sender;
            featureManager.groundLifter = !featureManager.groundLifter;
            if (featureManager.groundLifter)
                player.sendMessage("§6Ground Lifter is turned §aON");
            else
                player.sendMessage("§6Ground Lifter is turned §4OFF");
        } else if (command.getName().equalsIgnoreCase("tntbow")) {
            Player player = (Player) sender;
            featureManager.tntBow = !featureManager.tntBow;
            if (featureManager.tntBow)
                player.sendMessage("§6TNT Bow is turned §aON");
            else
                player.sendMessage("§6TNT Bow is turned §4OFF");
        }
        return false;
    }
}