package Cryptogram;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EnterALetterTests {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Game game;

    private String[] generateUserInputFromCurrentCryptogram(Game game) {
        String CypherLetter = game.getCryptogram().getCryptogramAlphabet().keySet().toArray(new String[0])[0];
        return new String[]{CypherLetter, game.getCryptogram().getCryptogramAlphabet().get(CypherLetter)};
    }

    @BeforeEach
    public void setup() {
        assertDoesNotThrow(() -> game = new Game("abc"));
        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
    }

    /**
     * Tests if an error message is displayed if a wrong mapping is chosen for Letter Cryptogram.Cryptogram *
     */
    @Test
    public void PlayersMapsALetterToAKeyThatDoesntExist() {
        String[] input = {"p", "!"};
        System.setOut(new PrintStream(outputStreamCaptor));

        game.enterLetter(input, false);
        assertEquals(">> The value you're trying to map to, is not present in this cryptogram.\n" +
                ">> Try again with a different value.", outputStreamCaptor.toString().trim());
        Assertions.assertFalse(game.valueInAlphabet(input[1]));
        System.setOut(standardOut);
    }

    @Test
    public void playerEntersLetter() {
        //Given a cryptogram has been generated and is being played

        //When the player identifies a value to replace with a letter
        String[] testInput = generateUserInputFromCurrentCryptogram(game);
        int totalGuesses = game.getPlayer().getTotalGuesses();
        int correctGuesses = game.getPlayer().getCorrectGuesses();

        assertDoesNotThrow(() -> game.enterLetter(testInput, false));

        //Then the letter is mapped to that value and is filled in for all instances in the cryptogram
        Assertions.assertEquals(game.getCurrentSolution().get(testInput[0]), testInput[1]);

        //the player’s statistics (numGuesses, numCorrectGuesses) are updated
        Assertions.assertTrue(game.isGuessCorrect(testInput[0], testInput[1]));
        Assertions.assertEquals(totalGuesses+1, game.getPlayer().getTotalGuesses());
        Assertions.assertEquals(correctGuesses+1, game.getPlayer().getCorrectGuesses());
    }

    @Test
    public void playerPicksAlreadyMappedCryptoLetter() {
        // Given a cryptogram has been generated and is being played
        String[] userInput = generateUserInputFromCurrentCryptogram(game);
        assertDoesNotThrow(() -> game.enterLetter(userInput, false));
        String[] originalInput = new String[]{userInput[0], userInput[1]};
        int totalGuesses = game.getPlayer().getTotalGuesses();

        //map plain letter to different than before, cypher remains same
        userInput[0] = game.getCryptogram().getCryptogramAlphabet().keySet().toArray(new String[0])[1];

        // When the player selects a cryptogram value which they have already mapped Then the player is asked if they want to overwrite the mapping
        assertThrows(IllegalStateException.class, () -> game.enterLetter(userInput, false));

        //if not the original mapping remains
        Assertions.assertEquals(game.getCurrentSolution().get(originalInput[0]), originalInput[1]);
        Assertions.assertEquals(totalGuesses, game.getPlayer().getTotalGuesses());

        //if they do it’s overwritten and stats updated,
        assertDoesNotThrow(() -> game.enterLetter(userInput, true));

        Assertions.assertEquals(game.getCurrentSolution().get(userInput[0]), userInput[1]);
        Assertions.assertEquals(totalGuesses+1, game.getPlayer().getTotalGuesses());
    }

    @Test
    public void playerPicksAlreadyMappedPlainLetter() {
        //Given a cryptogram has been generated and is being played
        String[] userInput = generateUserInputFromCurrentCryptogram(game);

        assertDoesNotThrow(() -> game.enterLetter(userInput, false));

        //map cypher letter to different than before, plain remains same
        userInput[1] = game.getCryptogram().getCryptogramAlphabet().values().toArray(new String[0])[2];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        assertDoesNotThrow(() -> game.enterLetter(userInput, false));

        assertTrue(baos.toString().contains("The letter you are trying to use, has already been used."));
        System.setOut(old);
    }

    @Test
    public void PlayerEnterAllLettersSuccessfully() {
        HashMap<String, String> alphabet = game.getCryptogram().getCryptogramAlphabet();

        String[] input;
        for (Map.Entry<String, String> entry : alphabet.entrySet()) {
            input = new String[]{entry.getKey(), entry.getValue()};
            game.enterLetter(input, false);
        }
        Assertions.assertEquals(1, game.isSuccessfullyFinished());
    }

    @Test
    public void PlayerEntersLastLetterUnsuccessfully() {
        HashMap<String, String> alphabet = game.getCryptogram().getCryptogramAlphabet();
        String[] input;
        String previous = "x";
        for (Map.Entry<String, String> entry : alphabet.entrySet()) {
            input = new String[]{previous, entry.getValue()};
            game.enterLetter(input, false);
            previous = entry.getKey();
        }
        Assertions.assertEquals(-1, game.isSuccessfullyFinished());

    }

}
