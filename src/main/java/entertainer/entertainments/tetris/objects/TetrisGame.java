package entertainer.entertainments.tetris.objects;

import java.util.Date;

public class TetrisGame {

    private int score = 0;
    private int lines = 0;
    private long duration = 0;
    private Date date = new Date();

    public TetrisGame() {
    }

    public TetrisGame(int score, int lines, long duration, Date date) {
        this.score = score;
        this.lines = lines;
        this.duration = duration;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void addScore(int score) {
        this.score += score;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }
    public void addLines(int lines) {
        this.lines += lines;
    }

    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
}
