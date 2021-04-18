package Cryptogram.UnitTests;//package Cryptogram;
//
//import org.junit.jupiter.api.*;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.util.Scanner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UndoLetterMappingTests {
//
//    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
//    private static InputPrompt prompt;
//    private static Game game;
//    private static MockPlayers players;
//    private static MockManager manager;
//
//    @BeforeAll
//    static void setup() {
//        assertDoesNotThrow(() -> players = new MockPlayers());
//        assertDoesNotThrow(() -> manager = new MockManager());
//        prompt = new InputPrompt(new Scanner(""));
//    }
//
//    @BeforeEach
//    void before() {
//        prompt.injectInput("user_test\nload\n");
//        game = new Game(players, manager, prompt);
//        game.setupPlayer();
//        assertDoesNotThrow(() -> game.setupCryptogram());
//        System.setOut(new PrintStream(output));
//    }
//
//    @AfterEach
//    void after() {
//        System.setOut(System.out);
//    }
//
//    /**
//     * Tests if undo is deleting letters in Letter Cryptogram
//     */
//    @Test
//    public void undoLetterLetterCryptogram() {
//        // when a cryptogram is being played
//        Assertions.assertTrue(game.getCryptogram().isLetterAlreadyUsed("t"));
//        // when player requests to undo a letter
//        String[] input = {"undo", "t"};
//        game.executeCommand(input);
//        // it is removed from the mapping
//        Assertions.assertFalse(game.getCryptogram().isLetterAlreadyUsed("t"));
//
//    }
//
//    /**
//     * For Letter Cryptogram.Cryptogram:Tests if undo displays an error message when the letter to undo is not there *
//     */
//    @Test
//    public void undoFailLetterCryptogram() {
//        // when a cryptogram is being played
//        assertFalse(game.getCryptogram().isLetterAlreadyUsed("x"));
//        // when player requests to undo a letter
//        String[] input = {"undo", "x"};
//        game.executeCommand(input);
//        // it is removed from the mapping
//        assertTrue(output.toString().contains(">> The letter you are trying to delete is not in play"));
//        assertFalse(game.getCryptogram().isLetterAlreadyUsed("x"));
//
//    }
//
//}
