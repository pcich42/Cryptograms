package Cryptogram.AcceptanceTests;

import Cryptogram.Controllers.Application;
import Cryptogram.Controllers.CryptogramManager;
import Cryptogram.Controllers.Players;
import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LoadPlayerDetailsTests {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Application app;
    private InputPrompt prompt;
    private ICryptogramManager manager;
    private View view;

    private void testBody(String[] input, String expectedOutput) {

        prompt.injectInput(input);
        app.run();

        assertTrue(output.toString().contains(expectedOutput));
    }

    public void removeLine(String lineContent) throws IOException {
        File file = new File("TestGameFiles/player_data.txt");
        List<String> out = Files.lines(file.toPath())
                .filter(line -> !line.contains(lineContent))
                .collect(Collectors.toList());
        Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @BeforeEach
    void before() {
        System.setOut(new PrintStream(output));
        manager = new CryptogramManager("TestGameFiles", "CryptogramPhrasesAcceptanceTests");
        view = new View();
    }

    @AfterEach
    void after() {
        System.setOut(System.out);
    }

    @org.junit.jupiter.api.Nested
    class Nested {

        @BeforeEach
        void Before() {
            IPlayers players = new Players("TestGameFiles/player_data.txt");
            prompt = new InputPrompt(new Scanner(""));
            app = new Application(players, manager, view, prompt);
        }

        @Test
        public void playerDetailsAreLoadedCorrectly () {
            String[] input = {
                    "test_user3",
                    "exit",
            };

            String outputString = "Welcome test_user3!";

            assertFalse(output.toString().contains("Player doesn't yet exist, creating a new player profile."));
            testBody(input, outputString);
        }

        @Test
        public void playerDoesntExist () {

            try {
                removeLine("test_user4");
            } catch (IOException ioException) {
                fail();
            }

            String[] input = {
                    "test_user4",
                    "exit"
            };

            String outputString =
                    "Player doesn't yet exist, creating a new player profile.\n" +
                    "Welcome test_user4!";

            testBody(input, outputString);
        }
    }

    @Test
    public void fileDoesntExist_AppQuits(){
        IPlayers players = new Players("TestGameFiles/invalid_player_data1.txt");
        prompt = new InputPrompt(new Scanner(""));
        app = new Application(players, manager, view, prompt);

        String[] input = {
                ""
        };

        String outputString = "Players file not found. Exiting...";

        testBody(input, outputString);
    }

    @Test
    public void playerFileInvalidFormat(){
        IPlayers players = new Players("TestGameFiles/invalid_player_data2.txt");
        prompt = new InputPrompt(new Scanner(""));
        app = new Application(players, manager, view, prompt);

        String[] input = {
                ""
        };

        String outputString = "File format invalid. Exiting...";

        testBody(input, outputString);
    }
}