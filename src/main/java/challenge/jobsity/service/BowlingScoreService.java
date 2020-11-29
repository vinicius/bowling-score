package challenge.jobsity.service;

import challenge.jobsity.model.Frame;
import challenge.jobsity.model.Game;

import java.util.List;

public class BowlingScoreService {

    final int pins = 10;

    public void computeScore(Game game) {
        int frameCount = 1;
        int score = 0;
        List<String> chances = game.getChances();

        for(String chance: chances) {
            if (!chance.equals("F") && (Integer.parseInt(chance) < 0 || Integer.parseInt(chance) > pins)) {
                System.err.println("Invalid game! Chances can not be bigger than 10 or less than 0");
                return;
            }
        }

        boolean lastFrame = false;

        for(int i  = 0; i < chances.size(); i++) {

            if(game.numOfFrames() == 9) {
                lastFrame = true;
            }

            // First chance on frame

            int chance1 = chances.get(i).equals("F") ? 0 : Integer.parseInt(chances.get(i));
            score += chance1;

            if(chance1 == pins) {
                // Strike! Sum i+1 and i+2 pinfalls
                int next1 = chances.get(i+1).equals("F") ? 0 : Integer.parseInt(chances.get(i+1));
                int next2 = chances.get(i+2).equals("F") ? 0 : Integer.parseInt(chances.get(i+2));
                score = score + next1 + next2;
                Frame frame = new Frame(frameCount, chances.get(i), score);
                game.addFrame(frame);
                if(lastFrame) {
                    break;
                }
                continue;
            }
            i++;

            // Second chance on frame
            int chance2 = chances.get(i).equals("F") ? 0 : Integer.parseInt(chances.get(i));
            score += chance2;
            if(chance1 + chance2 > pins) {
                System.err.println("Invalid game! There can not be more than 10 pinfalls in a frame");
                return;
            } else if(chance1 + chance2 == pins) {
                // Spare! Sum i+1 pinfalls
                int next = chances.get(i+1).equals("F") ? 0 : Integer.parseInt(chances.get(i+1));
                score = score + next;
                if(lastFrame) {
                    // Extra chance on last frame
                    i++;
                    int extra = chances.get(i).equals("F") ? 0 : Integer.parseInt(chances.get(i));
                    score += extra;
                    Frame frame = new Frame(frameCount, chances.get(i-2), chances.get(i-1), chances.get(i), score);
                    game.addFrame(frame);
                } else {
                    Frame frame = new Frame(frameCount, chances.get(i-1), chances.get(i), score);
                    game.addFrame(frame);
                }
            } else {
                Frame frame = new Frame(frameCount, chances.get(i-1), chances.get(i), score);
                game.addFrame(frame);
            }
        }
        System.out.println(":: " + game.getPlayer() + "::");
        game.getFrames().forEach(BowlingScoreService::printFramePartialScore);
    }

    private static void printFramePartialScore(Frame f) {
        System.out.println(f.getPartialScore());
    }
}
