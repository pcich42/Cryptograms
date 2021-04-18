package Cryptogram.IntegrationTests;

import Cryptogram.*;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateACryptogramTests {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Application app;
    private InputPrompt prompt;

    private void testBody(String[] input, String expectedOutput) {

        prompt.injectInput(input);
        app.run();

        assertTrue(output.toString().contains(expectedOutput));
    }

    @Nested
    class FilesExist {
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


        @Test
        public void playerRequestsLettersCryptogramAtStart () {

            String[] input = {
                    "test_user",
                    "new letters",
                    "exit",
                    "no",
                    "exit",
            };

            String outputString = ">> Please enter a mapping in format <letter><space><cryptogram value>:";

            testBody(input, outputString);
        }

        @Test
        public void playerRequestsNumbersCryptogram () {

            String[] input = {
                    "test_user",
                    "new numbers",
                    "exit",
                    "no",
                    "exit",
            };

            String outputString = ">> Please enter a mapping in format <letter><space><cryptogram value>:";

            testBody(input, outputString);
        }
    }

    @Test
    public void playerRequestsCryptogramButNoPhrasesStored() {

        System.setOut(new PrintStream(output));

        IPlayers players = new MockPlayers();
        CryptogramManager newManager = new CryptogramManager("PlayerFiles", "NoPhrasesHere");
        View view = new View();

        prompt = new InputPrompt(new Scanner(""));
        app = new Application(players, newManager, view, prompt);

        String[] input = {
                "test_user",
                "new",
                "exit",
                "no",
                "exit",
        };

        String outputString = "Cryptograms file doesn't exists, Exiting...";

        testBody(input, outputString);
        System.setOut(System.out);

    }

}
