package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.events.TetrisBlockCollideEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static entertainer.entertainments.tetris.listeners.TetrisZoneSeletionListener.tetrisBoard;

public class TetrisBlockCollideListener implements Listener {

    @EventHandler
    public void onTetrisBlockCollide(TetrisBlockCollideEvent e){
        Bukkit.getScheduler().cancelTask(tetrisBoard.downLoopTask);
        if (tetrisBoard.isStarted()){
            tetrisBoard.spawnTetrisBlock();
            tetrisBoard.addScore(1);
        }
    }
}
