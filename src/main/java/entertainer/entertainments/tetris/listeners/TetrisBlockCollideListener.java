package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.events.TetrisBlockCollideEvent;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TetrisBlockCollideListener implements Listener {

    @EventHandler
    public void onTetrisBlockCollide(TetrisBlockCollideEvent e){
        TetrisBoard tetrisBoard = e.getTetrisBlock().getTetrisBoard();

        tetrisBoard.blockLoopTask.cancel();

        tetrisBoard.checkRows();

        tetrisBoard.addScore(1);

        tetrisBoard.spawnTetrisBlock();
    }
}
