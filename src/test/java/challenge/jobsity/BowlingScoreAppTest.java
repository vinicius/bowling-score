package challenge.jobsity;

import challenge.jobsity.model.Score;
import challenge.jobsity.service.BowlingScoreService;
import challenge.jobsity.service.TraditionalBowlingScoreService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Unit test for Bowling Score App
 */
public class BowlingScoreAppTest {

    BowlingScoreService service = new TraditionalBowlingScoreService();
    BowlingScoreApp app;
    List<Score> validScores;
    List<Score> missingScores;
    List<Score> perfectGameScores;
    List<Score> foulGameScores;
    List<Score> zeroGameScores;
    List<Score> manyRollsScores;
    List<Score> invalidScores;

    @Before
    public void prepareInputs() {
        app = new BowlingScoreApp();
        String validInput = "src/test/resources/score-sample.txt";
        String missingRollsInput = "src/test/resources/score-missing-rolls.txt";
        String perfectGameInput = "src/test/resources/perfect-game.txt";
        String foulGameInput = "src/test/resources/foul-game.txt";
        String zeroGameInput = "src/test/resources/zero-game.txt";
        String manyRollsInput = "src/test/resources/score-many.txt";
        String invalidInput = "src/test/resources/invalid-sample.txt";
        try {
            Stream<String> stream = Files.lines(Paths.get(validInput));
            Map<String, List<String>> playerRollsMap = app.buildingPlayerRollsMap(stream);

            validScores = app.buildingScoresFromRollsMaps(playerRollsMap);

            stream = Files.lines(Paths.get(missingRollsInput));
            playerRollsMap = app.buildingPlayerRollsMap(stream);

            missingScores = app.buildingScoresFromRollsMaps(playerRollsMap);

            stream = Files.lines(Paths.get(perfectGameInput));
            playerRollsMap = app.buildingPlayerRollsMap(stream);

            perfectGameScores = app.buildingScoresFromRollsMaps(playerRollsMap);

            stream = Files.lines(Paths.get(foulGameInput));
            playerRollsMap = app.buildingPlayerRollsMap(stream);

            foulGameScores = app.buildingScoresFromRollsMaps(playerRollsMap);

            stream = Files.lines(Paths.get(zeroGameInput));
            playerRollsMap = app.buildingPlayerRollsMap(stream);

            zeroGameScores = app.buildingScoresFromRollsMaps(playerRollsMap);

            stream = Files.lines(Paths.get(manyRollsInput));
            playerRollsMap = app.buildingPlayerRollsMap(stream);

            manyRollsScores = app.buildingScoresFromRollsMaps(playerRollsMap);

            stream = Files.lines(Paths.get(invalidInput));
            playerRollsMap = app.buildingPlayerRollsMap(stream);

            invalidScores = app.buildingScoresFromRollsMaps(playerRollsMap);

        } catch (NoSuchFileException nsfe) {
            System.err.println("File not found: " + nsfe.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Test valid scores
     */
    @Test
    public void testValidScores() {
        assertTrue(service.areValidScores(validScores));
    }

    /**
     * Test invalid scores
     */
    @Test
    public void testInvalidScores() {
        assertFalse(service.areValidScores(invalidScores));
    }

    /**
     * Test perfect game
     */
    @Test
    public void testPerfectGame() {
        assertTrue(service.areValidScores(perfectGameScores));
        service.computeScore(perfectGameScores.get(0));
        assertEquals(perfectGameScores.get(0).getFrames().get(9).getPartialScore(), 300);
    }

    /**
     * Test foul game
     */
    @Test
    public void testFoulGame() {
        assertTrue(service.areValidScores(foulGameScores));
        service.computeScore(foulGameScores.get(0));
        assertEquals(foulGameScores.get(0).getFrames().get(9).getPartialScore(), 0);
    }

    /**
     * Test zero game
     */
    @Test
    public void testZeroGame() {
        assertTrue(service.areValidScores(zeroGameScores));
        service.computeScore(zeroGameScores.get(0));
        assertEquals(zeroGameScores.get(0).getFrames().get(9).getPartialScore(), 0);
    }

    /**
     * Test missing score
     */
    @Test
    public void testMissingRolls() {
        assertFalse(service.areValidScores(missingScores));
    }

    /**
     * Test too many rolls
     */
    @Test
    public void testTooManyRolls() {
        assertFalse(service.areValidScores(manyRollsScores));
    }
}
