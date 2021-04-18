//package Cryptogram;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ScoreboardTests {
//
//    private Game game;
//    private final String username = "testUser";
//
//    @Test
//    public void playersAvailable() {
//        assertDoesNotThrow(() -> game = new Game(username));
//        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//        ArrayList<String> a = game.getScoreboard();
//        int non_zero_successful_players = 0;
//        try {
//            BufferedReader bf = new BufferedReader(new FileReader("Resources/player_data.txt"));
//            String line;
//            while ((line = bf.readLine()) != null) {
//                String[] tokens = line.split(",");
//                if (!tokens[tokens.length - 1].equals("0")) {
//                    non_zero_successful_players++;
//                }
//            }
//            assertEquals(a.size(), non_zero_successful_players);
//            bf.close();
//        } catch (IOException fileNotFoundException) {
//            fail();
//        }
//    }
//
//    @Test
//    public void noPlayersHaveFinishedGame() {
//
//        String path = "Resources/player_data.txt";
//        List<String> contents = null;
//        try {
//            contents = Files.readAllLines(Path.of(path));
//        } catch (IOException e) {
//            fail();
//        }
//
//        try {
//            PrintWriter bw = new PrintWriter(path);
//            bw.write("a,0,0,0,0\nb,0,0,0,0\nc,0,0,0,0");
//            bw.close();
//        } catch (IOException e) {
//            fail();
//        }
//
//        assertDoesNotThrow(() -> game = new Game(username));
//        ArrayList<String> a = game.getScoreboard();
//        assertEquals(a.size(), 0);
//
//        try {
//            PrintWriter pw = new PrintWriter(path);
//            pw.write("");
//            pw.close();
//            if (contents.size() != 0) {
//                Files.write(Path.of(path), contents, StandardOpenOption.WRITE);
//            }
//        } catch (IOException e) {
//            fail();
//        }
//    }
//}
