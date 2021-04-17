package Cryptogram;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateACryptogramTests {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Game game;
    private static MockPlayers players;
    private static CryptogramManager manager;
    private static InputPrompt prompt;

    @BeforeAll
    static void setup() {
//        try {
//            players = new MockPlayers();
//            manager = new CryptogramManager();
//            prompt = new InputPrompt(new Scanner(""));
//        } catch (IOException e) {
//            System.out.println("Players File invalid, Exiting...");
//        }
        assertDoesNotThrow(() -> players = new MockPlayers());
        assertDoesNotThrow(() -> manager = new CryptogramManager());
        prompt = new InputPrompt(new Scanner(""));
    }

    @BeforeEach
    void before() {
        System.setOut(new PrintStream(output));
        game = new Game(players, manager, prompt);
    }

    @AfterEach
    void after() {
        System.setOut(System.out);
    }

    @Test
    public void playerRequestsLettersCryptogramAtStart() {
        // when there are phrases stored
        //if player requests letters cryptogram
        prompt.injectInput("user_test\nnew letters\n");
        game.setupPlayer();
        assertFalse(game.setupCryptogram());

        // they get a cryptogram based on letters
        assertEquals(LettersCryptogram.class, game.getCryptogram().getClass());
    }

    @Test
    public void playerRequestsNumbersCryptogram() {
        // when there are phrases stored
        // if player requests letters cryptogram
        prompt.injectInput("user_test\nnew numbers\n");
        game.setupPlayer();
        assertFalse(game.setupCryptogram());

        // they get a cryptogram based on letters
        assertEquals(NumbersCryptogram.class, game.getCryptogram().getClass());
    }

    @Test
    public void playerRequestsCryptogramButNoPhrasesStored() {

        CryptogramManager newManager = new CryptogramManager("PlayerFiles", "NoPhrasesHere");

        // when there are no phrases stored
        // if player requests any cryptogram
        game = new Game(players, newManager, prompt);
        prompt.injectInput("user_test\nnew letters\n");
        game.setupPlayer();
        assertTrue(game.setupCryptogram());

        // error message is displayed and the game quits
        assertTrue(output.toString().contains("Cryptograms file doesn't exists, Exiting..."));

    }

}
