package challenge.jobsity.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String player;
    private List<String> chances;
    private List<Frame> frames;

    public Game(String player, List<String> chances) {
        this.player = player;
        this.chances = chances;
        frames = new ArrayList<>();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public List<String> getChances() {
        return chances;
    }

    public void setChances(List<String> chances) {
        this.chances = chances;
    }

    public void addFrame(Frame frame) {
        this.frames.add(frame);
    }

    public int numOfFrames() {
        return frames.size();
    }

    public List<Frame> getFrames() {
        return frames;
    }
}
