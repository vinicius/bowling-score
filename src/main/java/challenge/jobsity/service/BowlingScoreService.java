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
                if(lastFrame) {
                    Frame frame = new Frame(frameCount, chances.get(i), chances.get(i+1), chances.get(i+2), score);
                    frameCount++;
                    game.addFrame(frame);
                    break;
                }
                Frame frame = new Frame(frameCount, chances.get(i), score);
                frameCount++;
                game.addFrame(frame);
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
                    frameCount++;
                    game.addFrame(frame);
                } else {
                    Frame frame = new Frame(frameCount, chances.get(i-1), chances.get(i), score);
                    frameCount++;
                    game.addFrame(frame);
                }
            } else {
                Frame frame = new Frame(frameCount, chances.get(i-1), chances.get(i), score);
                frameCount++;
                game.addFrame(frame);
            }
        }
    }

    public void printBowlingScore(List<Game> games) {
        System.out.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
        games.forEach(BowlingScoreService::printGameScore);
    }

    private static void printGameScore(Game game) {
        System.out.println(game.getPlayer());
        System.out.print("Pinfalls\t");
        game.getFrames().forEach(BowlingScoreService::printFramePinfalls);
        System.out.println();
        System.out.print("Score\t\t");
        game.getFrames().forEach(BowlingScoreService::printFramePartialScore);
        System.out.println();
    }

    private static void printFramePinfalls(Frame f) {
        String chance1 = f.getChance1();
        String chance2 = " " + f.getChance2();
        String extra = " " + f.getChanceExtra();
        if(f.getChance1().equals("10")) {
            chance1 = "  X";
            if(f.getNumber() != 10) {
                chance2 = "";
                extra = "";
            }
        } else if(!chance1.equals("F") && !chance2.trim().equals("F") && (Integer.parseInt(chance1) + Integer.parseInt(chance2.trim()) == 10)) {
            chance2 = " /";
        }
        if(f.getNumber() != 10) {
            extra = "";
        }
        System.out.print(chance1 + chance2 + extra + "\t\t");
    }

    private static void printFramePartialScore(Frame f) {
        System.out.print(f.getPartialScore() + "\t\t");
    }
}
