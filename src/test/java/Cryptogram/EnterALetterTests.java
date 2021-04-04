package Cryptogram;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class EnterALetterTests {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private static MockView view;
    private static Game game;
    private static MockPlayers players;
    private static MockManager manager;

    @BeforeAll
    static void setup() {
        try {
            players = new MockPlayers();
            manager = new MockManager();
            view = new MockView("");
        } catch (IOException e) {
            System.out.println("Players File invalid, Exiting...");
        }
    }

    @BeforeEach
    void before() {
        view.injectInput("user_test\nload\n");
        game = new Game(players, manager, view);
        game.setup();
        System.setOut(new PrintStream(output));
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
        String[] input = {"p", "!"};

        game.enterLetter(input);
        assertEquals(">> The value you're trying to map to, is not present in this cryptogram.\n" +
                ">> Try again with a different value.", output.toString().trim());
        assertNotEquals(game.getCryptogram().getLetter(input[0]), input[1]);
    }

    @Test
    public void playerEntersLetter() {
        //Given a cryptogram has been generated and is being played
        int totalGuesses = game.getPlayer().getTotalGuesses();
        int correctGuesses = game.getPlayer().getCorrectGuesses();

        //When the player identifies a value to replace with a letter
        String[] input = {"e", "b"};
        game.enterLetter(input);

        //Then the letter is mapped to that value and is filled in for all instances in the cryptogram
        assertEquals(game.getCryptogram().getSolution().get(input[0]), input[1]);

        //the player’s statistics (numGuesses, numCorrectGuesses) are updated
        assertTrue(game.getCryptogram().isGuessCorrect(input[0] , input[1]));
        assertEquals(totalGuesses+1, game.getPlayer().getTotalGuesses());
        assertEquals(correctGuesses+1, game.getPlayer().getCorrectGuesses());
    }

    @Test
    public void playerPicksAlreadyMappedCryptoLetterAndDoesntRemap() {
        // Given a cryptogram has been generated and is being played
        int totalGuesses = game.getPlayer().getTotalGuesses();

        // when a player select a value to which they already have mapped
        String[] userInput = {"p", "a"};
        view.injectInput("no");
        game.enterLetter(userInput);

        // they are asked if they want to remap a letter
        assertEquals(">> This value is already mapped to. Do you want to override this mapping?",
                output.toString().trim());

        // if no, letters remain
        assertNotEquals(game.getCryptogram().getLetter(userInput[1]), userInput[0]);

        // stats are not updated
        assertEquals(game.getPlayer().getTotalGuesses(), totalGuesses);
    }

    @Test
    public void playerPicksAlreadyMappedCryptoLetterAndRemaps() {
        // Given a cryptogram has been generated and is being player
        int totalGuesses = game.getPlayer().getTotalGuesses();

        // when a player select a value to which they already have mapped
        String[] userInput = {"p", "a"};
        view.injectInput("yes");
        game.enterLetter(userInput);

        // they are asked if they want to remap a letter
        assertEquals(">> This value is already mapped to. Do you want to override this mapping?",
                output.toString().trim());

        // if yes, letters remain
        assertEquals(game.getCryptogram().getLetter(userInput[1]), userInput[0]);

        // stats are updated
        assertEquals(game.getPlayer().getTotalGuesses(), totalGuesses+1);
    }


    @Test
    public void playerPicksAlreadyMappedPlainLetter() {
        //Given a cryptogram has been generated and is being played
        String[] userInput = {"t", "b"};

        // it shouldn't be a mapping already
        game.enterLetter(userInput);
        assertTrue(output.toString().contains("The letter you are trying to use, has already been used."));
        assertNotEquals(game.getCryptogram().getLetter(userInput[1]), userInput[0]);
    }

    @Test
    public void PlayerEnterAllLettersSuccessfully() {
        // when a player enters all letters correctly
        game.enterLetter(new String[]{"e", "b"});
        game.enterLetter(new String[]{"s", "c"});
        // the game is finished
        assertTrue(game.isGameFinishedRoutine());
        assertTrue(output.toString().contains(">> Congratulations!!! You have successfully completed this cryptogram\n" +
                ">> Try again with a different one now."));
    }

    @Test
    public void PlayerEntersLastLetterUnsuccessfully() {
        game.enterLetter(new String[]{"e", "b"});
        game.enterLetter(new String[]{"o", "c"});
        assertFalse(game.isGameFinishedRoutine());
        assertTrue(output.toString().contains(">> Somethings is off here. Undo some letters to map them again."));

    }

}
