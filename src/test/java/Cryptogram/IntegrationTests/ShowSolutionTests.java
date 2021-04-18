package Cryptogram.IntegrationTests;

import Cryptogram.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ShowSolutionTests {

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
    public void playerRequestsCorrectSolution() {
        String[] input = {
                "user_test",
                "load",
                "solution",
                "exit"
        } ;

        String outputString =
                "t _ _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                "\n" +
                "t e s t \n" +
                "a b c a \n" +
                "Game finished. Try again.";

        testBody(input, outputString);
    }
}
