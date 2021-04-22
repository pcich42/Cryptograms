package Cryptogram.IntegrationTests;

import Cryptogram.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HintTests {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Application app;
    private InputPrompt prompt;

    private void testBody(String[] input, String expectedOutput) {

        prompt.injectInput(input);
        app.run();

        assertTrue(output.toString().contains(expectedOutput));
    }

    @BeforeEach
    void before() {
        System.setOut(new PrintStream(output));

        IPlayers players = new MockPlayers();
        ICryptogramManager manager = new MockManager();
        View view = new View();

        prompt = new InputPrompt(new Scanner(""));
        app = new Application(players, manager, view, prompt);

    }

    @AfterEach
    void after() {
        System.setOut(System.out);
    }


    /**
     * Tests if the letter hasnt been input then hint will reveal the letter to the user and put it in
     */
    @Test
    void notMappedHint() {
        String[] input = {
                "test_user",
                "load",
                "hint",
                "exit",
                "no",
                "exit",
        };
        String outputString =
                "t _ _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                "The letter s is revealed.\n" +
                "\n" +
                "t _ s t \n" +
                "a b c a ";
        testBody(input, outputString);
    }

    /**
     * Tests if the hint replaces a wrong letter and puts it where it belongs
     * then displays a message.
     */
    @Test
    void mappedHint() {
        String[] input = {
                "test_user",
                "load",
                "s b",
                "hint",
                "exit",
                "no",
                "exit",
        };
        String outputString =
                "t s _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                "The letter s is revealed.\n" +
                "\n" +
                "t _ s t \n" +
                "a b c a ";
        testBody(input, outputString);
    }
}