package Cryptogram.IntegrationTests;

import Cryptogram.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class UndoLetterMappingTests {

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

    @Test
    public void undoLetterLetterCryptogram() {
        String[] input = {
                "test_user",
                "load",
                "undo t",
                "exit",
                "no",
                "exit",
        };
        String outputString = "t _ _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                "\n" +
                "_ _ _ _ \n" +
                "a b c a ";
        testBody(input, outputString);

    }

    @Test
    public void undoFailLetterCryptogram() {
        String[] input = {
                "test_user",
                "load",
                "undo x",
                "exit",
                "no",
                "exit",
        };
        String outputString = "t _ _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                ">> The letter you are trying to delete is not in play\n" +
                "\n" +
                "t _ _ t \n" +
                "a b c a ";
        testBody(input, outputString);

    }

}
