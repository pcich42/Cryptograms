//package Cryptogram;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class LoadACryptogramTests {
//    private Game game;
//    private String username = "abc";
//
//    @BeforeEach
//    public void setup() {
//        assertDoesNotThrow(() -> game = new Game(username));
//        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//    }
//
//    /**
//     * Tests if loading cryptogram works
//     */
//    @Test
//    public void loadTheCryptogram() {
//        // when player has already saved a game:
//        File file = new File("Resources/PlayerGameFiles/" + username + "Game.txt");
//        assertDoesNotThrow(() -> game.saveGameToFile(true));
//        assertTrue(file.exists());
//        // when the player wants to load the game it is loaded
//        assertDoesNotThrow(() -> game.loadGame());
//
//        if (file.exists()) {
//            file.delete();
//        }
//    }
//
//    /**
//     * Tests if loading cryptogram returns exception when there is no saved game to be loaded
//     */
//    @Test
//    public void loadCryptogramWithNoSavedGame() {
//        // when a player hasn't saved any game yet
//        // when they want to load a game
//        // error message is show (exception handled in view)
//        assertThrows(java.io.IOException.class, () -> game.loadGame());
//    }
//
//    @Test
//    public void corruptedFileCryptogram() {
//        // when a player game file has been corrupted
//        // when they request to load a game
//        File file = new File("Resources/PlayerGameFiles/" + username + "Game.txt");
//        assertDoesNotThrow(() -> game.saveGameToFile(true));
//        assertTrue(file.exists());
//        try {
//            PrintWriter bw = new PrintWriter(file.getPath());
//            bw.write("alsjbdasljdbalsjdasljdalsjdb");
//            bw.close();
//        } catch (IOException e) {
//            fail();
//        }
//
//        assertThrows(IllegalStateException.class, () -> game.loadGame());
//
//        if (file.exists()) {
//            file.delete();
//        }
//    }
//}
