package entertainer.entertainments.tetris.objects;

import entertainer.entertainments.Entertainments;
import entertainer.entertainments.tetris.events.TetrisGameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static entertainer.entertainments.functions.Functions.createItemstack;
import static entertainer.entertainments.functions.Functions.createLore;
import static entertainer.entertainments.tetris.listeners.PalletSelectListener.palletHandler;

public class TetrisBoard {

    private Entertainments plugin = Entertainments.getPlugin(Entertainments.class);

    private Location leftBottomCorner = null;
    private Location rightTopCorner = null;

    private int score = 0;

    //Delay till the game starts (s)
    private float startDelay = 0;

    //Delay till next tetris block will fall (ticks)
    private int blockSpeed = 5;

    private Player host;
    private ArrayList<Player> players = new ArrayList<>();

    private TetrisBlock nextBlock;

    public TetrisBlock currentBlock;
    private ArrayList<TetrisBlock> tetrisBlocks = new ArrayList<>();

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

    private boolean started = false;


    //START AND STOP
    public boolean start(){
        if (leftBottomCorner == null || rightTopCorner == null){
            host.sendMessage("§cPlease first configure the tetris board!");
            return false;
        }
        started = true;
        //Start delay timer for game start
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnTetrisBlock, (long) (20 * startDelay));
        showControls();
        return true;
    }
    private HashMap<Integer, ItemStack> hostInventory = new HashMap<>();
    public void showControls(){
        hostInventory.clear();
        for (int i = 0; i < host.getInventory().getSize(); i++){
            ItemStack itemStack = host.getInventory().getItem(i);
            hostInventory.put(i, itemStack);
        }
        host.getInventory().clear();

        ItemStack leftStick = createItemstack(Material.ARROW, "§7§l<", createLore("§6§l§m------", "§7Tool to change the position of the tetris block on the screen", "", "§7Usage:", "§fRight-Click §7To go to the left" ,"§6§l§m------"));
        ItemStack goFaster = createItemstack(Material.STICK, "§6§lGo §eFaster", createLore("§6§l§m------", "§7Make the block go down faster", "", "§7Usage:", "§fRight-Click §8§lHOLD §7To make faster" ,"§6§l§m------"));
        ItemStack rightStick = createItemstack(Material.ARROW, "§7§l>", createLore("§6§l§m------", "§7Tool to change the position of the tetris block on the screen", "", "§7Usage:", "§fRight-Click §7To go to the right" ,"§6§l§m------"));

        host.getInventory().setItem(3, leftStick);
        host.getInventory().setItem(4, goFaster);
        host.getInventory().setItem(5, rightStick);
    }
    public void revertInventory(){
        for (int i : hostInventory.keySet()){
            host.getInventory().setItem(i, hostInventory.get(i));
        }
    }
    public void stop(){
        started = false;
        TetrisGameEndEvent event = new TetrisGameEndEvent(this);
        Bukkit.getPluginManager().callEvent(event);
    }

    //PROPERTIES
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void addScore(int score) {
        this.score+=score;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public Player getHost() {
        return host;
    }

    public boolean isStarted() {
        return started;
    }

    //FUNCTIONS
    public int downLoopTask;
    public void spawnTetrisBlock(){
        Random r = new Random();
        int randomTetrisIndex = r.nextInt(6);
        currentBlock = palletHandler.getTetrisBlock(randomTetrisIndex);
        currentBlock.setCurrentVariant(0);
        currentBlock.setCurrentLocation(rightTopCorner.clone().add(-17, 0, 0));
        currentBlock.place();

        downLoopTask = new BukkitRunnable() {
            @Override
            public void run() {
                currentBlock.move(TetrisBlock.TetrisDirection.DOWN,-3);
                if (!started){
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, blockSpeed).getTaskId();
    }
    public void clearArea(){
        Cuboid cuboid = new Cuboid(leftBottomCorner, rightTopCorner);
        for (Block block : cuboid.getBlocks()){
            block.setType(Material.AIR);
        }
    }
}
