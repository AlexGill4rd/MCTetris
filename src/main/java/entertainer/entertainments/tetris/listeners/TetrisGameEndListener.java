package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.events.TetrisGameEndEvent;
import entertainer.entertainments.tetris.objects.Sound;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import entertainer.entertainments.tetris.objects.TetrisPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Date;
import java.util.Random;

import static entertainer.entertainments.Entertainments.activeGames;
import static entertainer.entertainments.functions.Functions.calculateTime;

public class TetrisGameEndListener implements Listener {

    @EventHandler
    public void onTetrisEnd(TetrisGameEndEvent e){
        Player player = e.getTetrisBoard().getPlayer();
        TetrisBoard tetrisBoard = e.getTetrisBoard();
        TetrisPlayer tetrisPlayer = tetrisBoard.getTetrisPlayer();
        if (player != null){
            player.sendMessage("§6§l§m------------------------");
            player.sendMessage("§eTotal score: §f" + tetrisBoard.getScore());
            player.sendMessage("§eLines removed: §f" + tetrisBoard.getLines());
            player.sendMessage("§eTime played: §f" + calculateTime((long) ((System.currentTimeMillis() - tetrisBoard.getStartTime()) / 1000f)));
            player.sendMessage("§6§l§m------------------------");
            activeGames.remove(player.getUniqueId());
        }
        e.getTetrisBoard().getTetrisGame().setDuration(System.currentTimeMillis() - tetrisBoard.getStartTime());
        tetrisPlayer.getTetrisStats().addTetrisGame(e.getTetrisBoard().getTetrisGame());

        tetrisBoard.setScore(0);
        tetrisBoard.setLines(0);
        tetrisBoard.setStartTime(0);
        tetrisBoard.setTetrisGame(null);

        tetrisBoard.blockLoopTask.cancel();
        tetrisBoard.clearArea();
        tetrisBoard.revertInventory();

        if (player != null)
            tetrisBoard.getPlayer().teleport(tetrisBoard.getPreviousPlayerLocation());
    }
}
