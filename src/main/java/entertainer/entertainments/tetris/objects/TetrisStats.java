package entertainer.entertainments.tetris.objects;

import entertainer.entertainments.configuration.Configs;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static entertainer.entertainments.configuration.Configs.customConfigFile5;

public class TetrisStats {

    private int totalScore;
    private int totalLines;
    private int highscore;
    private Date joinDate = new Date();
    private ArrayList<TetrisGame> tetrisGames = new ArrayList<>();
    private final Player player;

    public TetrisStats(Player player) {
        this.player = player.getPlayer();
        if (Configs.getCustomConfig5().contains("players." + player.getUniqueId()))
            loadStats();
        else
            saveStats();
    }
    private void loadStats(){
        //Global info
        this.joinDate = new Date(Configs.getCustomConfig5().getLong("players." + player.getUniqueId() + ".stats.join_date"));
        this.totalScore = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".stats.total_score");
        this.totalLines = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".stats.total_lines");
        this.highscore = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".stats.highscore");

        //Match history info
        if (Configs.getCustomConfig5().contains("players." + player.getUniqueId() + ".stats.history")){
            for (String matchID : Configs.getCustomConfig5().getConfigurationSection("players." + player.getUniqueId() + ".stats.history").getKeys(false)){
                int score = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".stats.history." + matchID + ".score");
                int lines = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".stats.history." + matchID + ".lines");
                int duration = Configs.getCustomConfig5().getInt("players." + player.getUniqueId() + ".stats.history." + matchID + ".duration");
                long millis = Configs.getCustomConfig5().getLong("players." + player.getUniqueId() + ".stats.history." + matchID + ".date");
                TetrisGame tetrisGame = new TetrisGame(score, lines, duration, new Date(millis));
                tetrisGames.add(tetrisGame);
            }
        }
    }
    public void saveStats(){
        //Global info
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".stats.join_date", joinDate.getTime());
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".stats.total_score", totalScore);
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".stats.total_lines", totalLines);
        Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".stats.highscore", highscore);

        //Match history info
        int counter = 0;
        for (TetrisGame tetrisGame : tetrisGames){
            Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".stats.history." + counter + ".score", tetrisGame.getScore());
            Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".stats.history." + counter + ".lines", tetrisGame.getLines());
            Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".stats.history." + counter + ".duration", tetrisGame.getDuration());
            Configs.getCustomConfig5().set("players." + player.getUniqueId() + ".stats.history." + counter + ".date", tetrisGame.getDate().getTime());
            counter++;
        }

        try {
            Configs.getCustomConfig5().save(customConfigFile5);
        } catch (IOException ignored) {}
    }
    @Nullable
    public TetrisGame getLastGame(){
        if (tetrisGames.size() > 0){
            return tetrisGames.get(tetrisGames.size() - 1);
        }else return null;
    }


    public long getTotalGamesDuration(){
        return tetrisGames.stream().mapToLong(TetrisGame::getDuration).sum();
    }

    public int getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalLines() {
        return totalLines;
    }
    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getHighscore() {
        return highscore;
    }
    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public Date getJoinDate() {
        return joinDate;
    }
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public ArrayList<TetrisGame> getTetrisGames() {
        return tetrisGames;
    }
    public void setTetrisGames(ArrayList<TetrisGame> tetrisGames) {
        this.tetrisGames = tetrisGames;
    }
    public void addTetrisGame(TetrisGame tetrisGame) {
        if (tetrisGame.getScore() > highscore)
            this.highscore = tetrisGame.getScore();
        this.totalScore += tetrisGame.getScore();
        this.totalLines += tetrisGame.getLines();

        this.tetrisGames.add(tetrisGame);
    }

    public Player getPlayer() {
        return player;
    }
}
