package challenge.jobsity.service;

import challenge.jobsity.model.Frame;
import challenge.jobsity.model.Score;

import java.util.List;

public class WorldBowlingScoreService implements BowlingScoreService {


    @Override
    public boolean areValidScores(List<Score> scores) {
        // TODO score validation for world bowling score
        return true;
    }

    @Override
    public void computeScore(Score score) {
        // TODO score computation for world bowling score
    }

    @Override
    public void printBowlingScore(List<Score> scores) {
        System.out.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
        scores.forEach(WorldBowlingScoreService::printGameScore);
    }

    /**
     * Prints the game score using tab-separated format.
     *
     * @param score
     */
    private static void printGameScore(Score score) {
        System.out.println(score.getPlayer());
        System.out.print("Pinfalls\t");
        score.getFrames().forEach(WorldBowlingScoreService::printFramePinfalls);
        System.out.println();
        System.out.print("Score\t\t");
        score.getFrames().forEach(WorldBowlingScoreService::printFramePartialScore);
        System.out.println();
    }

    /**
     * Prints the frame pinfalls using 'X' for strike, 'F' for fouls and '/' for spares.
     *
     * @param f score frame
     */
    private static void printFramePinfalls(Frame f) {
        String roll1 = f.getRoll1();
        String roll2 = " " + f.getRoll2();
        if(f.getRoll1().equals("10")) {
            roll1 = "  X";
            if(f.getNumber() != 10) {
                roll2 = "";
            }
        } else if(!roll1.equals("F") && !roll2.trim().equals("F") && (Integer.parseInt(roll1) + Integer.parseInt(roll2.trim()) == 10)) {
            roll2 = " /";
        }
        System.out.print(roll1 + roll2 + "\t\t");
    }

    /**
     * Prints the frame (partial) score.
     * @param f
     */
    private static void printFramePartialScore(Frame f) {
        System.out.print(f.getPartialScore() + "\t\t");
    }
}
