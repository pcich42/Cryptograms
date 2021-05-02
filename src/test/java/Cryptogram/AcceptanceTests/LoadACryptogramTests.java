package Cryptogram.AcceptanceTests;

import Cryptogram.Controllers.Application;
import Cryptogram.Controllers.CryptogramManager;
import Cryptogram.Mocks.MockPlayers;
import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class LoadACryptogramTests {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Application app;
    private InputPrompt prompt;
    private static final String path = "TestGameFiles";

    private void testBody(String[] input, String expectedOutput) {

        prompt.injectInput(input);
        app.run();

        assertTrue(output.toString().contains(expectedOutput));
    }

    @BeforeEach
    void before() {
        System.setOut(new PrintStream(output));

        IPlayers players = new MockPlayers();
        ICryptogramManager manager = new CryptogramManager(path, "CryptogramPhrasesAcceptanceTests");
        View view = new View();

        prompt = new InputPrompt(new Scanner(""));
        app = new Application(players, manager, view, prompt);

    }

    @AfterEach
    void after() {
        System.setOut(System.out);
    }


    /**
     * Tests if loading cryptogram works
     */
    @Test
    public void loadTheCryptogram() {
        // when player has already saved a game:
        File file = new File(path + "/PlayerGameFiles/test_userGame.txt");
        assertTrue(file.exists());

        String[] input = {
                "test_user",
                "load",
                "exit",
                "no",
                "exit",
        };

        String outputString = ">> Please enter a mapping in format <letter><space><cryptogram value>:";

        testBody(input, outputString);

    }

    /**
     * Tests if loading cryptogram returns exception when there is no saved game to be loaded
     */
    @Test
    public void loadCryptogramWithNoSavedGame() {
        // when player doesn't have already saved a game:
        File file = new File(path + "/PlayerGameFiles/test_user1Game.txt");
        assertFalse(file.exists());

        String[] input = {
                "test_user1",
                "load",
                "exit",
        };

        String outputString = ">> No game saved. Save a game using 'save' before loading or type: new <[letters]/numbers> - to create a new cryptogram.";

        testBody(input, outputString);
    }
//
//    @Test
//    public void corruptedFileCryptogram() {
//        // when a player game file has been corrupted
//        // when they request to load a game
//        File file = new File("Resources/PlayerGameFiles/" + username + "GameTests.txt");
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
}
