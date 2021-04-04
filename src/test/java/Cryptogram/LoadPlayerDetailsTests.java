//package Cryptogram;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.io.PrintWriter;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LoadPlayerDetailsTests {
//
//    private final PrintStream standardOut = System.out;
//    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//    private Game game;
//    private final String username = "def";
//
//
//    @BeforeEach
//    void setUp() {
//        assertDoesNotThrow(() -> game = new Game(username));
//        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//    }
//
//    /**
//     * Tests if a user is loaded in if they already started a cryptogram
//     */
//    @Test
//    public void loadPlayerDetails(){
//
//        String path = "Resources/player_data.txt";
//        List<String> contents = null;
//        try {
//            contents = Files.readAllLines(Path.of(path));
//        } catch (IOException e) {
//            fail();
//        }
//
//        int totalGuesses = game.getPlayer().getTotalGuesses();
//        int correctGuesses = game.getPlayer().getCorrectGuesses();
//        int cryptPlayed = game.getPlayer().getCryptogramsPlayed();
//        int cryptCompleted = game.getPlayer().getCryptogramsCompleted();
//        game.updatePlayersFile();
//
//        assertDoesNotThrow(() -> game = new Game(username));
//        Assertions.assertEquals(totalGuesses, game.getPlayer().getTotalGuesses());
//        Assertions.assertEquals(correctGuesses, game.getPlayer().getCorrectGuesses());
//        Assertions.assertEquals(cryptPlayed, game.getPlayer().getCryptogramsPlayed());
//        Assertions.assertEquals(cryptCompleted, game.getPlayer().getCryptogramsCompleted());
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
//
//
//    /**
//     * Display an error if there is a problem with players file
//     */
//    @Test
//    public void errorLoadingDetails(){
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
//            bw.write("alsjbdasljdbalsjdasljdalsjdb");
//            bw.close();
//        } catch (IOException e) {
//            fail();
//        }
//
//        assertThrows(IOException.class ,() -> game = new Game("abc"));
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
//
//
//    /**
//     * Tests if a player does not exist and creates a new player if they do not.
//     * Checks if an error message is given.
//     */
//    @Test
//    public void playerDoesNotExist() {
//        System.setOut(new PrintStream(outputStreamCaptor));
//        assertDoesNotThrow(() -> game = new Game("abc"));
//        assertEquals("Cryptogram.Player doesn't yet exist, creating a new player profile.", outputStreamCaptor.toString().trim());
//        System.setOut(standardOut);
//        Assertions.assertEquals("abc", game.getPlayer().getUsername());
//    }
//}