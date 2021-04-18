package Cryptogram.UnitTests;//package Cryptogram;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.util.Scanner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class LoadACryptogramTests {
//    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
//    private static InputPrompt prompt;
//    private static Game game;
//    private static MockPlayers players;
//    private static CryptogramManager manager;
//    private static final String path = "TestGameFiles";
//
//    @BeforeAll
//    static void setup() {
//        assertDoesNotThrow(() -> players = new MockPlayers());
//        assertDoesNotThrow(() -> manager = new CryptogramManager(path, "CryptogramPhrases"));
//        prompt = new InputPrompt(new Scanner(""));
//    }
//
//    @BeforeEach
//    void before() {
//        System.setOut(new PrintStream(output));
//        game = new Game(players, manager, prompt);
//    }
//
//    @AfterEach
//    void after() {
//        System.setOut(System.out);
//    }
//
//    /**
//     * Tests if loading cryptogram works
//     */
//    @Test
//    public void loadTheCryptogram() {
//        // when player has already saved a game:
//        File file = new File(path + "/PlayerGameFiles/test_userGame.txt");
//        assertTrue(file.exists());
//
//        // when the player wants to load the game
//        prompt.injectInput("test_user\nload\n");
//
//        // it is loaded
//        game.setupPlayer();
//        assertFalse(game.setupCryptogram());
//        assertNotNull(game.getCryptogram());
//
//    }
//
//    /**
//     * Tests if loading cryptogram returns exception when there is no saved game to be loaded
//     */
//    @Test
//    public void loadCryptogramWithNoSavedGame() {
//        // when player doesn't have already saved a game:
//        File file = new File(path + "/PlayerGameFiles/test_user1Game.txt");
//        assertFalse(file.exists());
//
//        // when the player wants to load the game
//        prompt.injectInput("test_user1\nload\nexit");
//
//        // no game is loaded
//        game.setupPlayer();
//        assertTrue(game.setupCryptogram());
//        assertNull(game.getCryptogram());
//        assertTrue(output.toString().contains(">> No game saved. Save a game using 'save' before loading or type: new <[letters]/numbers> - to create a new cryptogram."));
//    }
////
////    @Test
////    public void corruptedFileCryptogram() {
////        // when a player game file has been corrupted
////        // when they request to load a game
////        File file = new File("Resources/PlayerGameFiles/" + username + "Game.txt");
////        assertDoesNotThrow(() -> game.saveGameToFile(true));
////        assertTrue(file.exists());
////        try {
////            PrintWriter bw = new PrintWriter(file.getPath());
////            bw.write("alsjbdasljdbalsjdasljdalsjdb");
////            bw.close();
////        } catch (IOException e) {
////            fail();
////        }
////
////        assertThrows(IllegalStateException.class, () -> game.loadGame());
////
////        if (file.exists()) {
////            file.delete();
////        }
////    }
//}
