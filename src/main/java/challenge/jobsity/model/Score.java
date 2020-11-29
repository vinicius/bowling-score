package challenge.jobsity.model;

import java.util.ArrayList;
import java.util.List;

public class Score {

    private String player;
    private List<String> rolls;
    private List<Frame> frames;

    public Score(String player, List<String> rolls) {
        this.player = player;
        this.rolls = rolls;
        frames = new ArrayList<>();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public List<String> getRolls() {
        return rolls;
    }

    public void setRolls(List<String> rolls) {
        this.rolls = rolls;
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
