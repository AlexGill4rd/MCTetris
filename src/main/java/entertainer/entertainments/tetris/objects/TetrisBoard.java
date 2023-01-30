package entertainer.entertainments.tetris.objects;

import entertainer.entertainments.Entertainments;
import entertainer.entertainments.tetris.enums.TetrisDirection;
import entertainer.entertainments.tetris.events.TetrisGameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static entertainer.entertainments.Entertainments.*;
import static entertainer.entertainments.functions.Functions.*;

public class TetrisBoard {

    private Entertainments plugin = Entertainments.getPlugin(Entertainments.class);

    private Location leftBottomCorner = null;
    private Location rightTopCorner = null;
    private final int ID;

    private int score = 0;
    private int lines = 0;
    private long startTime = 0;

    //Delay till the game starts (s)
    private float startDelay = 0;

    //Delay till next tetris block will fall (ticks)
    private int blockSpeed = 10;

    private Player player;

    private TetrisBlock nextBlock;

    public TetrisBlock currentBlock;

    private Location spawnLocation;
    private Location previousPlayerLocation;

    public TetrisBoard(int ID, TetrisSelection tetrisSelection) {
        this.setRightTopCorner(tetrisSelection.getRightCorner());
        this.setLeftBottomCorner(tetrisSelection.getLeftCorner());
        this.ID = ID;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getID() {
        return ID;
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
    public boolean start(Player player){
        this.player = player;
        activeGames.put(player.getUniqueId(), this.getID());
        previousPlayerLocation = player.getLocation().clone();
        player.teleport(spawnLocation);
        player.sendMessage("§6The game is about to start!");

        setScore(0);
        setLines(0);
        started = true;
        //Start delay timer for game start
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            startTime = System.currentTimeMillis();
            spawnTetrisBlock();
        }, (long) (20 * startDelay));
        showControls();
        return true;
    }
    private HashMap<Integer, ItemStack> hostInventory = new HashMap<>();
    public void showControls(){
        player.getInventory().setHeldItemSlot(8);
        hostInventory.clear();
        for (int i = 0; i < player.getInventory().getSize(); i++){
            ItemStack itemStack = player.getInventory().getItem(i);
            hostInventory.put(i, itemStack);
        }
        player.getInventory().clear();

        ItemStack leftStick = createItemstack(Material.ARROW, "§7§l<", createLore("§6§l§m------", "§7Tool to change the position of the tetris block on the screen", "", "§7Usage:", "§fRight-Click §7To go to the left" ,"§6§l§m------"));
        ItemStack goFaster = createItemstack(Material.STICK, "§6§lGo §eFaster", createLore("§6§l§m------", "§7Make the block go down faster", "", "§7Usage:", "§fRight-Click §8§lHOLD §7To make faster" ,"§6§l§m------"));
        ItemStack rightStick = createItemstack(Material.ARROW, "§7§l>", createLore("§6§l§m------", "§7Tool to change the position of the tetris block on the screen", "", "§7Usage:", "§fRight-Click §7To go to the right" ,"§6§l§m------"));

        player.getInventory().setItem(3, leftStick);
        player.getInventory().setItem(4, goFaster);
        player.getInventory().setItem(5, rightStick);
    }
    public void revertInventory(){
        for (int i : hostInventory.keySet()){
            player.getInventory().setItem(i, hostInventory.get(i));
        }
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }
    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Location getPreviousPlayerLocation() {
        return previousPlayerLocation;
    }

    public void stop(){
        started = false;
        if (player != null){
            player.sendMessage("§6§l§m------------------------");
            player.sendMessage("§eTotal score: §f" + getScore());
            player.sendMessage("§eLines removed: §f" + getLines());
            player.sendMessage("§eTime played: §f" + calculateTime((long) ((System.currentTimeMillis() - getStartTime()) / 1000f)));
            player.sendMessage("§6§l§m------------------------");
            activeGames.remove(player.getUniqueId());
        }
        setScore(0);
        setLines(0);

        TetrisGameEndEvent event = new TetrisGameEndEvent(this);
        Bukkit.getPluginManager().callEvent(event);
    }

    //PROPERTIES

    public float getStartDelay() {
        return startDelay;
    }
    public void setStartDelay(float startDelay) {
        this.startDelay = startDelay;
    }

    public int getBlockSpeed() {
        return blockSpeed;
    }
    public void setBlockSpeed(int blockSpeed) {
        this.blockSpeed = blockSpeed;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;

        String scoreString = String.format("%05d", this.score);
        Location scoreLocation = rightTopCorner.clone().add(3, -12, 0);
        wordGenerator.writeWord(scoreLocation, scoreString);
    }
    public void addScore(int score) {
        setScore(this.score + score);
    }
    public int getLines() {
        return lines;
    }
    public void setLines(int lines) {
        this.lines = lines;

        String linesString = String.format("%05d", this.lines);
        Location scoreLocation = rightTopCorner.clone().add(3, -31, 0);
        wordGenerator.writeWord(scoreLocation, linesString);
    }
    public void addLines(int lines) {
        setLines(this.lines + lines);
    }

    public boolean isStarted() {
        return started;
    }

    public Player getPlayer() {
        return player;
    }

    //FUNCTIONS
    public BukkitTask blockLoopTask = null;
    public void spawnTetrisBlock(){
        if (blockLoopTask != null){
            blockLoopTask.cancel();
            blockLoopTask = null;
        }
        Random r = new Random();
        int randomTetrisIndex = r.nextInt(6);
        currentBlock = palletHandler.getTetrisBlock(randomTetrisIndex);
        currentBlock.setCurrentVariant(0);
        currentBlock.setTetrisBoard(this);
        currentBlock.setCurrentLocation(rightTopCorner.clone().add(-17, 0, 0));
        currentBlock.place();

        blockLoopTask = new BukkitRunnable() {
            @Override
            public void run() {
                currentBlock.move(TetrisDirection.DOWN,-3);
            }
        }.runTaskTimer(plugin, blockSpeed, blockSpeed);
    }
    public void clearArea(){
        Cuboid cuboid = new Cuboid(leftBottomCorner, rightTopCorner);
        for (Block block : cuboid.getBlocks()){
            block.setType(Material.AIR);
        }
    }
    private final int arenaWidth = 30;
    private final int arenaHeight = 60;
    public void checkRows(){
        boolean foundRow;
        do {
            foundRow = false;
            for (int y = 0; y < arenaHeight; y+=3){
                int counter = 0;
                for (int x = 0; x < arenaWidth; x++){
                    Location newBlockLoc = leftBottomCorner.clone().add(x, y, 0);
                    if (newBlockLoc.getBlock().getType() != Material.AIR){
                        counter++;
                    }
                }
                if (counter >= arenaWidth){
                    foundRow = true;
                    removeRow(y/3);
                    break;
                }
            }
        }while (foundRow);
    }
    public void removeRow(int row){
        //Remove full line on screen
        for (int x = 0; x < arenaWidth; x++) {
            //((row+1)*3) - 3           Begin at bottom of the full line and not bottom of screen
            for (int y = ((row+1)*3) - 3; y < (row*3) + 3; y++) {
                for (int z = 0; z < 3; z++) {
                    Location blockLoc = leftBottomCorner.clone().add(x, y, -z);
                    blockLoc.getBlock().setType(Material.AIR);
                }
            }
        }
        //Shuffle everything 3 to the bottom
        Location lineLocation = leftBottomCorner.clone().add(0, ((row+1)*3), 0);
        for (int x = 0; x < arenaWidth; x++) {
            for (int y = 0; y < arenaHeight - ((row+1)*3); y++) {
                for (int z = 0; z < 3; z++) {
                    Location blockLoc = lineLocation.clone().add(x, y, -z);
                    Location newBlockLoc = lineLocation.clone().add(x, y - 3, -z);
                    Block block = blockLoc.getBlock();

                    if (block.getType() == Material.AIR) continue;

                    newBlockLoc.getBlock().setType(block.getType());
                    newBlockLoc.getBlock().setBlockData(block.getBlockData());

                    block.setType(Material.AIR);
                }
            }
        }
        addLines(1);
    }
}
