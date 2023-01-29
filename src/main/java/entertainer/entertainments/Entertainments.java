package entertainer.entertainments;

import entertainer.entertainments.groundlifter.listener.GroundLiftListener;
import entertainer.entertainments.tetris.command.TetrisCommand;
import entertainer.entertainments.tetris.listeners.*;
import entertainer.entertainments.tntbow.listeners.BowShootListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Entertainments extends JavaPlugin {

    public static FeatureManager featureManager = new FeatureManager();

    @Override
    public void onEnable() {
        // Plugin startup logic

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

        getCommand("tntbow").setExecutor(new FeatureCommand());
        getCommand("groundlifter").setExecutor(new FeatureCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
