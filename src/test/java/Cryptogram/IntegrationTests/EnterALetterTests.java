package Cryptogram.IntegrationTests;

import Cryptogram.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class EnterALetterTests {

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
     * Tests if an error message is displayed if a wrong mapping is chosen for Letter Cryptogram.Cryptogram *
     */
    @Test
    public void PlayersMapsALetterToAKeyThatDoesntExist() {

        String[] input = {
                "user_test",
                "load",
                "p !",
                "exit",
                "no",
                "exit"
        } ;

        String outputString =
                "t _ _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                ">> The value you're trying to map to, is not present in this cryptogram.\n" +
                ">> Try again with a different value.\n" +
                "\n" +
                "t _ _ t \n" +
                "a b c a ";

        testBody(input, outputString);
    }

    @Test
    public void playerEntersLetter() {
        String[] input = {
                "test_user",
                "load",
                "e b",
                "exit",
                "no",
                "exit"
        };

        String outputString = "t _ _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n\n" +
                "t e _ t \n" +
                "a b c a";

        testBody(input, outputString);
    }

    @Test
    public void playerPicksAlreadyMappedCryptoLetterAndDoesntRemap() {
        String[] input = {
                "test_user",
                "load",
                "p a",
                "no",
                "exit",
                "no",
                "exit"
        };

        String outputString = "t _ _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                ">> This value is already mapped to. Do you want to override this mapping?\n" +
                "\n" +
                "t _ _ t \n" +
                "a b c a";

        testBody(input, outputString);
    }

    @Test
    public void playerPicksAlreadyMappedCryptoLetterAndRemaps() {
        String[] input = {
                "test_user",
                "load",
                "p a",
                "yes",
                "exit",
                "no",
                "exit"
        };
        String outputString = "t _ _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                ">> This value is already mapped to. Do you want to override this mapping?\n" +
                "\n" +
                "p _ _ p \n" +
                "a b c a";

        testBody(input, outputString);
    }

    @Test
    public void playerPicksAlreadyMappedPlainLetter() {
        String[] input = {
                "test_user",
                "load",
                "t b",
                "exit",
                "no",
                "exit"
        };

        String outputString = "t _ _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                ">> The letter you are trying to use, has already been used.\n" +
                ">> Try a different one or erase that letter.\n" +
                "\n" +
                "t _ _ t \n" +
                "a b c a ";

        testBody(input, outputString);
    }

    @Test
    public void PlayerEnterAllLettersSuccessfully() {
        String[] input = {
                "test_user",
                "load",
                "e b",
                "s c",
                "exit"
        };

        String outputString = "t e s t \n" +
                "a b c a \n" +
                ">> Congratulations!!! You have successfully completed this cryptogram\n" +
                ">> Try again with a different one now.\n" +
                "Choose what do you want to do next.";

        testBody(input, outputString);
    }

    @Test
    public void PlayerEntersLastLetterUnsuccessfully() {
        String[] input = {
                "test_user",
                "load",
                "e b",
                "o c",
                "exit",
                "no",
                "exit",
        };

        String outputString =
                "t e _ t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:\n" +
                ">> Somethings is off here. Undo some letters to map them again.\n" +
                "\n" +
                "t e o t \n" +
                "a b c a \n" +
                "Type help to list all available commands.\n" +
                ">> Please enter a mapping in format <letter><space><cryptogram value>:";


        testBody(input, outputString);
    }

}
