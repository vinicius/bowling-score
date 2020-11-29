package challenge.jobsity.service;

import challenge.jobsity.model.Score;

import java.util.List;

public interface BowlingScoreService {

    boolean areValidScores(List<Score> scores);

    void computeScore(Score score);

    void printBowlingScore(List<Score> scores);
}
