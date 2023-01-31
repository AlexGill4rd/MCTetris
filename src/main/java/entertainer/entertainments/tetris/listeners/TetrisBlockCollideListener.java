package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.events.TetrisBlockCollideEvent;
import entertainer.entertainments.tetris.objects.CopyBlock;
import entertainer.entertainments.tetris.objects.Sound;
import entertainer.entertainments.tetris.objects.TetrisBlock;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TetrisBlockCollideListener implements Listener {

    @EventHandler
    public void onTetrisBlockCollide(TetrisBlockCollideEvent e){

        //Collide sound :D
        Sound collideSound = new Sound(e.getTetrisBlock().getTetrisBoard().getPlayer(), "custom:blockcollide", 100);
        collideSound.play();

        TetrisBlock tetrisBlock = e.getTetrisBlock();
        TetrisBoard tetrisBoard = tetrisBlock.getTetrisBoard();

        tetrisBoard.blockLoopTask.cancel();

        tetrisBoard.checkRows();

        tetrisBoard.addScore(1);

        tetrisBoard.spawnTetrisBlock();
    }
}
