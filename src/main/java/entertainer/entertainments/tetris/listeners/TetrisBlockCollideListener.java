package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.events.TetrisBlockCollideEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static entertainer.entertainments.tetris.listeners.TetrisZoneSeletionListener.tetrisBoard;

public class TetrisBlockCollideListener implements Listener {

    @EventHandler
    public void onTetrisBlockCollide(TetrisBlockCollideEvent e){
        tetrisBoard.blockLoopTask.cancel();

        tetrisBoard.addScore(1);

        tetrisBoard.spawnTetrisBlock();
    }
}
