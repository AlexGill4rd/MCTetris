package entertainer.entertainments;

import entertainer.entertainments.configuration.Configs;
import entertainer.entertainments.groundlifter.listener.GroundLiftListener;
import entertainer.entertainments.tetris.command.TetrisCommand;
import entertainer.entertainments.tetris.inventories.InventoryClickListener;
import entertainer.entertainments.tetris.listeners.*;
import entertainer.entertainments.tetris.objects.PalletHandler;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import entertainer.entertainments.tetris.objects.TetrisPlayer;
import entertainer.entertainments.tetris.objects.TetrisSelection;
import entertainer.entertainments.tntbow.listeners.BowShootListener;
import entertainer.entertainments.wordgenerator.WordGenerator;
import entertainer.entertainments.wordgenerator.command.WordGeneratorCommand;
import entertainer.entertainments.wordgenerator.listeners.WordsSelectionListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static entertainer.entertainments.configuration.Configs.*;
import static entertainer.entertainments.functions.Functions.convertLocationToString;
import static entertainer.entertainments.functions.Functions.convertStringToLocation;

public final class Entertainments extends JavaPlugin {

    public static FeatureManager featureManager = new FeatureManager();

    public static WordGenerator wordGenerator;
    public static PalletHandler palletHandler;
    public static HashMap<Integer, TetrisBoard> tetrisBoards = new HashMap<>();
    public static HashMap<UUID, TetrisPlayer> tetrisPlayers = new HashMap<>();
    //The integer is the ID for the board
    public static HashMap<UUID, Integer> activeGames = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        createCustomConfig1();
        createCustomConfig2();
        createCustomConfig3();
        createCustomConfig4();
        createCustomConfig5();

        getServer().getPluginManager().registerEvents(new BowShootListener(), this);
        getServer().getPluginManager().registerEvents(new GroundLiftListener(), this);

        //Tetris listeners
        getServer().getPluginManager().registerEvents(new PalletSelectListener(), this);
        getServer().getPluginManager().registerEvents(new TetrisBlockPlacer(), this);
        getServer().getPluginManager().registerEvents(new TetrisZoneSeletionListener(), this);
        getServer().getPluginManager().registerEvents(new TetrisControllerListener(), this);
        //Custom Events
        getServer().getPluginManager().registerEvents(new TetrisBlockCollideListener(), this);
        getServer().getPluginManager().registerEvents(new TetrisGameEndListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInitJoinListener(), this);
        getCommand("tetris").setExecutor(new TetrisCommand());

        palletHandler = new PalletHandler();
        wordGenerator = new WordGenerator();

        //Load all tetris boards
        if (Configs.getCustomConfig3().contains("locations")){
            for (String idString : Configs.getCustomConfig3().getConfigurationSection("locations").getKeys(false)){
                Location leftCorner = convertStringToLocation(getCustomConfig3().getString("locations." + idString + ".lc"));
                Location rightCorner = convertStringToLocation(getCustomConfig3().getString("locations." + idString + ".rc"));
                Location spawnLocation = null;
                if (getCustomConfig3().contains("locations." + idString + ".spawn"))
                    spawnLocation = convertStringToLocation(getCustomConfig3().getString("locations." + idString + ".spawn"));

                TetrisSelection tetrisSelection = new TetrisSelection();
                tetrisSelection.setLeftCorner(leftCorner);
                tetrisSelection.setRightCorner(rightCorner);

                TetrisBoard tetrisBoard = new TetrisBoard(Integer.parseInt(idString), tetrisSelection);
                if (spawnLocation != null)
                 tetrisBoard.setSpawnLocation(spawnLocation);

                tetrisBoards.put(tetrisBoard.getID(), tetrisBoard);
            }
        }

        //Word generator
        getServer().getPluginManager().registerEvents(new WordsSelectionListener(), this);
        getCommand("wordgenerator").setExecutor(new WordGeneratorCommand());


        getCommand("tntbow").setExecutor(new FeatureCommand());
        getCommand("groundlifter").setExecutor(new FeatureCommand());

        for (Player player : Bukkit.getOnlinePlayers()){
            TetrisPlayer tetrisPlayer = new TetrisPlayer(player);
            tetrisPlayer.initialise();
            tetrisPlayers.put(player.getUniqueId(), tetrisPlayer);
        }

        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //Save all tetris boards
        int counter = 0;
        for (TetrisBoard tetrisBoard : tetrisBoards.values()){
            tetrisBoard.stop();

            Configs.getCustomConfig3().set("locations." + counter + ".lc", convertLocationToString(tetrisBoard.getLeftBottomCorner()));
            Configs.getCustomConfig3().set("locations." + counter + ".rc", convertLocationToString(tetrisBoard.getRightTopCorner()));
            if (tetrisBoard.getSpawnLocation() != null)
                Configs.getCustomConfig3().set("locations." + counter + ".spawn", convertLocationToString(tetrisBoard.getSpawnLocation()));

            saveTetrisConfig();
            counter++;
        }
        for (Player player : Bukkit.getOnlinePlayers()){
            TetrisPlayer tetrisPlayer = tetrisPlayers.get(player.getUniqueId());
            if (tetrisPlayer != null)
                tetrisPlayer.savePlayerData();
        }
    }
    private void saveTetrisConfig(){
        try {
            Configs.getCustomConfig3().save(customConfigFile3);
        } catch (IOException ignored) {}
    }
}
