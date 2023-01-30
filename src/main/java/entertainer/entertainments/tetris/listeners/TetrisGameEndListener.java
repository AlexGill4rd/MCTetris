package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.events.TetrisGameEndEvent;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static entertainer.entertainments.Entertainments.activeGames;
import static entertainer.entertainments.functions.Functions.calculateTime;

public class TetrisGameEndListener implements Listener {

    @EventHandler
    public void onTetrisEnd(TetrisGameEndEvent e){
        Player player = e.getTetrisBoard().getPlayer();
        TetrisBoard tetrisBoard = e.getTetrisBoard();

        if (player != null){
            player.sendMessage("§6§l§m------------------------");
            player.sendMessage("§eTotal score: §f" + tetrisBoard.getScore());
            player.sendMessage("§eLines removed: §f" + tetrisBoard.getLines());
            player.sendMessage("§eTime played: §f" + calculateTime((long) ((System.currentTimeMillis() - tetrisBoard.getStartTime()) / 1000f)));
            player.sendMessage("§6§l§m------------------------");
            activeGames.remove(player.getUniqueId());
        }
        tetrisBoard.setScore(0);
        tetrisBoard.setLines(0);


        e.getTetrisBoard().blockLoopTask.cancel();
        e.getTetrisBoard().clearArea();
        e.getTetrisBoard().revertInventory();

        if (player != null)
            e.getTetrisBoard().getPlayer().teleport(e.getTetrisBoard().getPreviousPlayerLocation());
    }
}
