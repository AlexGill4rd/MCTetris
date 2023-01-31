package entertainer.entertainments.tetris.objects;

import entertainer.entertainments.configuration.Configs;
import org.bukkit.entity.Player;

import java.io.IOException;

import static entertainer.entertainments.configuration.Configs.customConfigFile5;

public class TetrisPlayer {

    private Player player;
    private int totalScore = 0;
    private int totalLines = 0;
    private long firstJoined;

    private int lastScore = 0;
    private long lastGameDuration = 0;
    private long lastLines = 0;

    public TetrisPlayer(Player player) {
        this.player = player;
        this.firstJoined = System.currentTimeMillis();
    }

    public TetrisPlayer(Player player, int totalScore, int totalLines, int lastScore, int lastGameDuration, int lastLines, long firstJoined) {
        this.player = player;
        this.totalScore = totalScore;
        this.totalLines = totalLines;
        this.lastScore = lastScore;
        this.lastGameDuration = lastGameDuration;
        this.lastLines = lastLines;
        this.firstJoined = firstJoined;
    }
    public void initialise(){
        if (Configs.getCustomConfig5().contains("players." + player.getUniqueId())){
            this.totalScore = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".TotalScore");
            this.firstJoined = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".FirstJoined");
            this.totalLines = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".TotalLines");
            this.lastScore = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".LastGame.LastScore");
            this.lastGameDuration = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".LastGame.LastGameDuration");
            this.lastLines = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".LastGame.LastLines");
        }else
            savePlayerData();
    }
    public void savePlayerData(){
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".TotalScore", totalScore);
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".FirstJoined", firstJoined);
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".TotalLines", totalLines);
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".LastGame.LastScore", lastScore);
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".LastGame.LastGameDuration", lastGameDuration);
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".LastGame.LastLines", lastLines);

        try {
            Configs.getCustomConfig5().save(customConfigFile5);
        } catch (IOException ignored) {}
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public void addTotalScore(int score) {
        this.totalScore += score;
    }

    public int getTotalLines() {
        return totalLines;
    }
    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }
    public void addTotalLines(int lines) {
        this.totalLines += lines;
    }

    public int getLastScore() {
        return lastScore;
    }
    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    public long getLastLines() {
        return lastLines;
    }
    public void setLastLines(long lastLines) {
        this.lastLines = lastLines;
    }

    public long getLastGameDuration() {
        return lastGameDuration;
    }
    public void setLastGameDuration(long lastGameDuration) {
        this.lastGameDuration = lastGameDuration;
    }

    public long getFirstJoined() {
        return firstJoined;
    }
    public void setFirstJoined(long firstJoined) {
        this.firstJoined = firstJoined;
    }
}
