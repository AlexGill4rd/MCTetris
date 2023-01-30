package entertainer.entertainments;

import entertainer.entertainments.groundlifter.listener.GroundLiftListener;
import entertainer.entertainments.tetris.command.TetrisCommand;
import entertainer.entertainments.tetris.listeners.*;
import entertainer.entertainments.tetris.objects.PalletHandler;
import entertainer.entertainments.tetris.objects.TetrisBoard;
import entertainer.entertainments.tntbow.listeners.BowShootListener;
import entertainer.entertainments.wordgenerator.WordGenerator;
import entertainer.entertainments.wordgenerator.command.WordGeneratorCommand;
import entertainer.entertainments.wordgenerator.listeners.WordsSelectionListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static entertainer.entertainments.configuration.Configs.createCustomConfig1;
import static entertainer.entertainments.configuration.Configs.createCustomConfig2;

public final class Entertainments extends JavaPlugin {

    public static FeatureManager featureManager = new FeatureManager();

    public static WordGenerator wordGenerator = new WordGenerator();
    public static PalletHandler palletHandler;
    public static HashMap<UUID, TetrisBoard> tetrisBoards = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        createCustomConfig1();
        createCustomConfig2();

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
        getCommand("tetris").setExecutor(new TetrisCommand());

        palletHandler = new PalletHandler();

        //Word generator
        getServer().getPluginManager().registerEvents(new WordsSelectionListener(), this);
        getCommand("wordgenerator").setExecutor(new WordGeneratorCommand());


        getCommand("tntbow").setExecutor(new FeatureCommand());
        getCommand("groundlifter").setExecutor(new FeatureCommand());

        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
