package challenge.jobsity;

import challenge.jobsity.model.Score;
import challenge.jobsity.service.TraditionalBowlingScoreService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Bowling Score App main class
 *
 */
public class BowlingScoreApp {

    private static TraditionalBowlingScoreService traditionalBowlingScoreService = new TraditionalBowlingScoreService();

    public static void main( String[] args ) {
        if(args.length < 1) {
            System.out.println("Usage: java <bowling-jar-file> <input-file>");
            return;
        } else if(args.length > 1) {
            System.err.println("Too many arguments!");
            return;
        }

        try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
            Map<String, List<String>> playerRollsMap = new HashMap<>();
            List<Score> scores = new ArrayList<>();
            stream.forEach(line -> {
                String[] playerRoll = line.split("\\s+");
                if(playerRoll.length > 2 || playerRoll.length < 2) {
                    System.err.println("Input error: " + line + "\nEach line must contain two arguments and only two: the player's name and the pinfalls number (tab-separated)");
                    return;
                }

                String player = playerRoll[0];
                String roll = playerRoll[1];

                if(!roll.equals("F")) {
                    try {
                        Integer.parseInt(roll);
                    } catch (NumberFormatException nfe) {
                        System.err.println("Input error: " + line + "\nThe player roll should be a number (or F in case of foul)");
                        return;
                    }
                }

                playerRollsMap.putIfAbsent(player, new ArrayList<>());
                playerRollsMap.get(player).add(roll);
            });
            playerRollsMap.forEach((player, rolls) -> {
                scores.add(new Score(player, rolls));
            });

            if(traditionalBowlingScoreService.areValidScores(scores)) {
                scores.forEach(traditionalBowlingScoreService::computeScore);
                traditionalBowlingScoreService.printBowlingScore(scores);
            }
        } catch (NoSuchFileException nsfe) {
            System.err.println("File not found: " + nsfe.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
