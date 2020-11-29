package challenge.jobsity.service;

import challenge.jobsity.model.Score;

import java.util.List;

public interface BowlingScoreService {

    /**
     * Validates the bowling score and return true or false whether is valid or not.
     *
     * @param scores
     * @return true of false
     */
    boolean areValidScores(List<Score> scores);

    /**
     * Computes the bowling score. The score points of each frame must be added to the score parameter.
     *
     * @param score
     */
    void computeScore(Score score);

    /**
     * Prints the bowling score.
     * @param scores
     */
    void printBowlingScore(List<Score> scores);
}
