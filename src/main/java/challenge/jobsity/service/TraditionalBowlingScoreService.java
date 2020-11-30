package challenge.jobsity.service;

import challenge.jobsity.model.Frame;
import challenge.jobsity.model.Score;

import java.util.List;

/**
 * Service responsible for validating, computing and printing bowling scores
 */
public class TraditionalBowlingScoreService implements BowlingScoreService {

    final int totalPins = 10;
    final int totalFrames = 10;


    /**
     * {@inheritDoc}
     *
     * @param scores
     * @return
     */
    @Override
    public boolean areValidScores(List<Score> scores) {
        for(Score score : scores) {
            int frameCount = 0;
            boolean firstRoll = true;
            boolean hasExtra = false;

            List<String> rolls = score.getRolls();
            for(int i  = 0; i < rolls.size(); i++) {
                if(frameCount == totalFrames) {
                    System.err.println("Invalid score! Too many rolls (the score can not exceed more than 10 frames)");
                    return false;
                }
                int roll = rolls.get(i).equals("F") ? 0 : Integer.parseInt(rolls.get(i));
                if (roll < 0 || roll > totalPins) {
                    System.err.println("Invalid score! Rolls can not be bigger than 10 or less than 0");
                    return false;
                }
                if(frameCount < 9) {
                    if(firstRoll && roll == 10) {
                        frameCount++;
                        continue;
                    } else if (firstRoll){
                        if(i >= rolls.size() - 1) {
                            System.err.println("Invalid score! Some rolls are missing");
                            return false;
                        }
                        int next = rolls.get(i+1).equals("F") ? 0 : Integer.parseInt(rolls.get(i+1));
                        if(roll + next > 10) {
                            System.err.println("Invalid score for " + score.getPlayer() + "! It's not possible to have more than 10 pinfalls in one frame");
                            return false;
                        }
                        firstRoll = false;
                    } else {
                        frameCount++;
                        firstRoll = true;
                    }
                } else {
                    if(firstRoll) {
                        if(i >= rolls.size() - 1) {
                            System.err.println("Invalid score! Some rolls are missing");
                            return false;
                        }
                        int next = rolls.get(i+1).equals("F") ? 0 : Integer.parseInt(rolls.get(i+1));
                        if(roll == 10) {
                            if(i + 1 >= rolls.size() - 1) {
                                System.err.println("Invalid score! Extra roll is missing");
                                return false;
                            }
                            hasExtra = true;
                            firstRoll = false;
                            int extra = rolls.get(i+2).equals("F") ? 0 : Integer.parseInt(rolls.get(i+2));
                            if(next + extra > 10) {
                                System.err.println("Invalid score! Last rolls after strike can not be greater than 10");
                                return false;
                            }
                            continue;
                        }
                        if(roll + next == 10) {
                            if(i + 1 >= rolls.size() - 1) {
                                System.err.println("Invalid score! Extra roll is missing");
                                return false;
                            }
                            hasExtra = true;
                            firstRoll = false;
                            int extra = rolls.get(i+2).equals("F") ? 0 : Integer.parseInt(rolls.get(i+2));
                            if(extra > 10) {
                                System.err.println("Last roll after spare can not be greater than 10");
                                return false;
                            }
                            continue;
                        }
                        if(roll + next > 10) {
                            System.err.println("Invalid score! It's not possible to have more than 10 pinfalls in a single frame");
                            return false;
                        }
                        firstRoll = false;
                    } else if (hasExtra){
                        hasExtra = false;
                    } else {
                        frameCount++;
                    }
                }
            }
            if(frameCount < 10) {
                System.err.println("Invalid score! Some frames are missing");
                return false;
            }
        }
        return true;
    }

    @Override
    public void computeScore(Score score) {

        int partialScore = 0;
        List<String> rolls = score.getRolls();

        int frameCount = 0;

        boolean lastFrame = false;

        for(int i  = 0; i < rolls.size(); i++) {

            if(score.numOfFrames() == 9) {
                lastFrame = true;
            }

            // First roll on frame

            int roll1 = rolls.get(i).equals("F") ? 0 : Integer.parseInt(rolls.get(i));
            partialScore += roll1;

            if(roll1 == totalPins) {
                // Strike! Sum i+1 and i+2 pinfalls
                int next1 = rolls.get(i+1).equals("F") ? 0 : Integer.parseInt(rolls.get(i+1));
                int next2 = rolls.get(i+2).equals("F") ? 0 : Integer.parseInt(rolls.get(i+2));
                partialScore = partialScore + next1 + next2;
                if(lastFrame) {
                    frameCount++;
                    Frame frame = new Frame(frameCount, rolls.get(i), rolls.get(i+1), rolls.get(i+2), partialScore);
                    score.addFrame(frame);
                    break;
                }
                frameCount++;
                Frame frame = new Frame(frameCount, rolls.get(i), partialScore);
                score.addFrame(frame);
                continue;
            }
            i++;

            // Second roll on frame
            int roll2 = rolls.get(i).equals("F") ? 0 : Integer.parseInt(rolls.get(i));
            partialScore += roll2;
            if(roll1 + roll2 > totalPins) {
                System.err.println("Invalid score! There can not be more than 10 pinfalls in a frame");
                return;
            } else if(roll1 + roll2 == totalPins) {
                // Spare! Sum i+1 pinfalls
                int next = rolls.get(i+1).equals("F") ? 0 : Integer.parseInt(rolls.get(i+1));
                partialScore = partialScore + next;
                if(lastFrame) {
                    // Extra roll on last frame
                    i++;
                    int extra = rolls.get(i).equals("F") ? 0 : Integer.parseInt(rolls.get(i));
                    partialScore += extra;
                    frameCount++;
                    Frame frame = new Frame(frameCount, rolls.get(i-2), rolls.get(i-1), rolls.get(i), partialScore);
                    score.addFrame(frame);
                } else {
                    frameCount++;
                    Frame frame = new Frame(frameCount, rolls.get(i-1), rolls.get(i), partialScore);
                    score.addFrame(frame);
                }
            } else {
                frameCount++;
                Frame frame = new Frame(frameCount, rolls.get(i-1), rolls.get(i), partialScore);
                score.addFrame(frame);
            }
        }
    }

    @Override
    public void printBowlingScore(List<Score> scores) {
        System.out.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
        scores.forEach(TraditionalBowlingScoreService::printGameScore);
    }

    private static void printGameScore(Score score) {
        System.out.println(score.getPlayer());
        System.out.print("Pinfalls\t");
        score.getFrames().forEach(TraditionalBowlingScoreService::printFramePinfalls);
        System.out.println();
        System.out.print("Score\t\t");
        score.getFrames().forEach(TraditionalBowlingScoreService::printFramePartialScore);
        System.out.println();
    }

    private static void printFramePinfalls(Frame f) {
        String roll1 = f.getRoll1();
        String roll2 = " " + f.getRoll2();
        String extra = f.getRollExtra() != null ? " " + f.getRollExtra() : "";
        if(f.getRoll1().equals("10")) {
            roll1 = "  X";
            if(f.getNumber() != 10) {
                roll2 = "";
            }
        } else if(!roll1.equals("F") && !roll2.trim().equals("F") && (Integer.parseInt(roll1) + Integer.parseInt(roll2.trim()) == 10)) {
            roll2 = " /";
        }
        System.out.print(roll1 + roll2 + extra + "\t\t");
    }

    private static void printFramePartialScore(Frame f) {
        System.out.print(f.getPartialScore() + "\t\t");
    }
}
