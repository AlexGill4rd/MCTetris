package entertainer.entertainments.tetris.objects;

import entertainer.entertainments.Entertainments;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.swing.border.EtchedBorder;
import java.util.ArrayList;
import java.util.Random;

import static entertainer.entertainments.tetris.listeners.PalletSelectListener.palletHandler;

public class TetrisBoard {

    private Entertainments plugin = Entertainments.getPlugin(Entertainments.class);

    private Location leftBottomCorner = null;
    private Location rightTopCorner = null;

    //Delay till the game starts (s)
    private float startDelay = 10;

    //Delay till next tetris block will fall (ms)
    private int blockSpeed = 500;

    private Player host;
    private ArrayList<Player> players = new ArrayList<>();

    private TetrisBlock nextBlock;
    private boolean gameEnded = true;

    private TetrisBlock currentBlock;

    public TetrisBoard(Player host) {
        this.host = host;
    }

    public void setHost(Player host) {
        this.host = host;
    }
    public void addPlayer(Player player){
        this.players.add(player);
    }

    public Location getLeftBottomCorner() {
        return leftBottomCorner;
    }
    public void setLeftBottomCorner(Location leftBottomCorner) {
        this.leftBottomCorner = leftBottomCorner;
    }
    public Location getRightTopCorner() {
        return rightTopCorner;
    }
    public void setRightTopCorner(Location rightTopCorner) {
        this.rightTopCorner = rightTopCorner;
    }

    public void start(){
        if (leftBottomCorner == null || rightTopCorner == null){
            host.sendMessage("§cPlease first configure the tetris board!");
            return;
        }
        //Start delay timer for game start
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {

                //Task asynchronous for game running
                Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                    @Override
                    public void run() {

                        //Check if the game is over
                        //Each loop is a round of 1 tetris block
                        while (!gameEnded){
                            //Block move and spawn downwards loop
                            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (currentBlock == null)
                                        spawnNextTetris();
                                    else{
                                        gameEnded = !moveTetris();
                                    }
                                }
                            }, 0, blockSpeed);
                        }
                    }
                });
            }
        }, (long) (20 * startDelay));
    }
    private boolean moveTetris(){
        return currentBlock.move(-1);
    }
    private void spawnNextTetris(){
        Random r = new Random();
        int randomTetrisIndex = r.nextInt(6);
        currentBlock = palletHandler.getTetrisBlock(randomTetrisIndex);
        currentBlock.setCurrentVariant(0);
        currentBlock.setCurrentLocation(rightTopCorner.clone().add(20, 0, 0));
        currentBlock.place();
    }
}
