package Cryptogram.UnitTests;//package Cryptogram;
//
//import org.junit.jupiter.api.*;
//
//import java.io.*;
//import java.util.Scanner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class SaveACryptogramTests {
//    private static final ByteArrayOutputStream output = new ByteArrayOutputStream();
//    private static InputPrompt prompt;
//    private static Game game;
//    private static final String path = "TestGameFiles";
//    private static File file;
//    private static MockPlayers players;
//
//    @BeforeAll
//    static void setup() {
//            System.setOut(new PrintStream(output));
//            assertDoesNotThrow(() -> players = new MockPlayers());
//            CryptogramManager manager = new CryptogramManager(path, "CryptogramPhrases");
//            prompt = new InputPrompt(new Scanner("test_user_save\nnew\n"));
//            game = new Game(players, manager, prompt);
//            game.setupPlayer();
//            assertFalse(game.setupCryptogram());
//            file = new File(path + "/PlayerGameFiles/test_user_saveGame.txt");
//    }
//
//    @AfterAll
//    static void after() {
//        System.setOut(System.out);
//        file.delete();
//    }
//
//    /**
//     * Tests if save cryptogram works
//     */
//    @Order(1)
//    @Test
//    public void saveTheCryptogram() {
//        // when player is playing a game and doesn't have a game stored:
//        assertFalse(file.exists());
//
//        // when the player wants to save the game
//        game.executeCommand(new String[]{"save"});
//
//        // it is loaded
//        assertTrue(file.exists());
//    }
//
//    /**
//     * Tests if saving cryptogram when cryptogram is already saved will prompt user to overwrite save
//     */
//    @Order(2)
//    @Test
//    public void playerAlreadyHasSavedCryptogram() {
//        // when player is playing a game and has a game stored:
//        assertTrue(file.exists());
//
//        // when the player wants to save the game
//        prompt.injectInput("yes");
//        game.executeCommand(new String[]{"save"});
//        assertTrue(output.toString().contains(">> Do you wish to overwrite currently stored game?"));
//
//        // it is loaded
//        assertTrue(file.exists());
//    }
//}
