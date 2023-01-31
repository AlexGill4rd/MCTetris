package entertainer.entertainments.tetris.events;

import entertainer.entertainments.tetris.objects.CopyBlock;
import entertainer.entertainments.tetris.objects.TetrisBlock;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class TetrisBlockCollideEvent extends Event {

    private TetrisBlock tetrisBlock;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private CopyBlock[][] collidingBlocks;

    public TetrisBlockCollideEvent(TetrisBlock tetrisBlock, CopyBlock[][] collidingBlocks){
        this.tetrisBlock = tetrisBlock;
        this.collidingBlocks = collidingBlocks;
    }

    public CopyBlock[][] getCollidingMatrix() {
        return collidingBlocks;
    }
    public ArrayList<CopyBlock> getCollidingBlocks() {
        ArrayList<CopyBlock> collidingBlockList = new ArrayList<>();
        for (int x = 0; x < 12; x++){
            for (int z = 0; z < 3; z++){
                if (collidingBlocks[x][z] != null){
                    collidingBlockList.add(collidingBlocks[x][z]);
                }
            }
        }
        return collidingBlockList;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public TetrisBlock getTetrisBlock() {
        return tetrisBlock;
    }

    public void setTetrisBlock(TetrisBlock tetrisBlock) {
        this.tetrisBlock = tetrisBlock;
    }
}
