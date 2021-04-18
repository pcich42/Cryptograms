package Cryptogram.IntegrationTests;

import Cryptogram.*;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SaveACryptogramTests {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Application app;
    private InputPrompt prompt;
    private static final String path = "TestGameFiles";
    private static File file;

    private void testBody(String[] input, String expectedOutput) {

        prompt.injectInput(input);
        app.run();

        assertTrue(output.toString().contains(expectedOutput));
    }

    @BeforeAll
    static void beforeAll() {
            file = new File(path + "/PlayerGameFiles/test_user_saveGame.txt");
    }

    @BeforeEach
    void before () {
        System.setOut(new PrintStream(output));

        IPlayers players = new MockPlayers();
        ICryptogramManager manager = new CryptogramManager("TestGameFiles", "CryptogramPhrases");
        View view = new View();

        prompt = new InputPrompt(new Scanner(""));
        app = new Application(players, manager, view, prompt);

    }

    @AfterEach
    void after () {
        System.setOut(System.out);
    }

    @AfterAll
    static void afterAll() {
        file.delete();
    }

    /**
     * Tests if save cryptogram works
     */
    @Order(1)
    @Test
    public void saveTheCryptogram() {
//        // when player is playing a game and doesn't have a game stored:
//        // it is loaded
        String[] input = {
                "test_user_save",
                "new letters",
                "save",
                "exit",
                "no",
                "exit",
        };

        String outputString =
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                ">> Cryptogram saved.\n";

        assertFalse(file.exists());
        testBody(input, outputString);
        assertTrue(file.exists());
    }

    /**
     * Tests if saving cryptogram when cryptogram is already saved will prompt user to overwrite save
     */
    @Order(2)
    @Test
    public void playerAlreadyHasSavedCryptogram() {

        String[] input = {
                "test_user_save",
                "new letters",
                "save",
                "yes",
                "exit",
                "no",
                "exit",
        };

        String outputString = "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                ">> Do you wish to overwrite currently stored game?\n" +
                ">> Cryptogram saved.";

        assertTrue(file.exists());
        testBody(input, outputString);
        assertTrue(file.exists());
    }
}
