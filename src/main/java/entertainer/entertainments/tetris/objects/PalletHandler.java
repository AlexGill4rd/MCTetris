package entertainer.entertainments.tetris.objects;

import entertainer.entertainments.Entertainments;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class PalletHandler {

    private Location cornerLocation;
    private HashMap<Integer, TetrisBlock> tetrisBlocks = new HashMap<>();
    private Entertainments plugin = Entertainments.getPlugin(Entertainments.class);

    public PalletHandler(Location cornerLocation){
        this.cornerLocation = cornerLocation;
        initialiseBlocks();
    }
    public PalletHandler(){
        initialiseBlocks();
    }
    private void initialiseBlocks(){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            TetrisBlock lineBlock = new TetrisBlock(cornerLocation, 0);
            TetrisBlock reverseLBlock = new TetrisBlock(cornerLocation, 1);
            TetrisBlock lBlock = new TetrisBlock(cornerLocation, 2);
            TetrisBlock squarreBlock = new TetrisBlock(cornerLocation, 3);
            TetrisBlock lightningBlock = new TetrisBlock(cornerLocation, 4);
            TetrisBlock tBlock = new TetrisBlock(cornerLocation, 5);
            TetrisBlock reverseLightningBlock = new TetrisBlock(cornerLocation, 6);
            tetrisBlocks.put(0, lineBlock);
            tetrisBlocks.put(1, reverseLBlock);
            tetrisBlocks.put(2, lBlock);
            tetrisBlocks.put(3, squarreBlock);
            tetrisBlocks.put(4, lightningBlock);
            tetrisBlocks.put(5, tBlock);
            tetrisBlocks.put(6, reverseLightningBlock);
        });

    }
    public TetrisBlock getTetrisBlock(int index){
        return tetrisBlocks.get(index);
    }

}
