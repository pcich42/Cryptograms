package Cryptogram.AcceptanceTests;

import Cryptogram.Controllers.Application;
import Cryptogram.Mocks.MockAcceptanceTestsManager;
import Cryptogram.Mocks.MockPlayers;
import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SeeFrequenciesTests {

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
        ICryptogramManager manager = new MockAcceptanceTestsManager();
        View view = new View();

        prompt = new InputPrompt(new Scanner(""));
        app = new Application(players, manager, view, prompt);

    }

    @AfterEach
    void after() {
        System.setOut(System.out);
    }


    @Test
    void frequenciesAreDisplayedTest() {
        String[] input = {
                "user_test",
                "load",
                "frequencies",
                "exit",
                "no",
                "exit"
        } ;

        String outputString = "Current cryptogram has the following frequencies:\n" +
                "e:25.0, s:25.0, t:50.0, ";

        testBody(input, outputString);
    }

}
