package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.events.TetrisGameEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TetrisGameEndListener implements Listener {

    @EventHandler
    public void onTetrisEnd(TetrisGameEndEvent e){
        e.getTetrisBoard().blockLoopTask.cancel();
        e.getTetrisBoard().clearArea();
        e.getTetrisBoard().revertInventory();
    }
}
