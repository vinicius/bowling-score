package challenge.jobsity;

import challenge.jobsity.model.Game;
import challenge.jobsity.service.BowlingScoreService;

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

    private static BowlingScoreService bowlingScoreService = new BowlingScoreService();

    public static void main( String[] args ) {
        if(args.length < 1) {
            System.out.println("Usage: java <bowling-jar-file> <input-file>");
            return;
        } else if(args.length > 1) {
            System.err.println("Too many arguments!");
            return;
        }

        try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
            Map<String, List<String>> playerChancesMap = new HashMap<>();
            List<Game> games = new ArrayList<>();
            stream.forEach(line -> {
                String[] playerChance = line.split("\\s+");
                if(playerChance.length > 2) {
                    System.err.println("Input error: " + line + "\nEach line should contain only the player's name and the pinfalls number (tab-separated)");
                    return;
                }

                String player = playerChance[0];
                String chance = playerChance[1];

                if(!chance.equals("F")) {
                    try {
                        Integer.parseInt(chance);
                    } catch (NumberFormatException nfe) {
                        System.err.println("Input error: " + line + "\nThe player chance should be a number (or F in case of foul)");
                    }
                }

                playerChancesMap.putIfAbsent(player, new ArrayList<>());
                playerChancesMap.get(player).add(chance);
            });
            playerChancesMap.forEach((player, chances) -> {
                games.add(new Game(player, chances));
            });
            games.forEach(bowlingScoreService::computeScore);
            bowlingScoreService.printBowlingScore(games);
        } catch (NoSuchFileException nsfe) {
            System.err.println("File not found: " + nsfe.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
