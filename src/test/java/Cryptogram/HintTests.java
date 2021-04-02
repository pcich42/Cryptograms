package Cryptogram;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HintTests {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Game game;
    private final String username = "abc";

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> game = new Game(username));
        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
    }

    /**
     * Tests if the letter hasnt been input then hint will reveal the letter to the user and put it in
     */
    @Test
    void notMappedHint() {
        String letter = game.getCryptogram().getCryptogramAlphabet().keySet().iterator().next();
        Assertions.assertFalse(game.isLetterAlreadyUsed(letter));
        game.hint();
        Assertions.assertTrue(game.isLetterAlreadyUsed(letter));
    }

    /**
     * Tests if the hint replaces a wrong letter and puts it where it belongs
     * then displays a message.
     */
    @Test
    void mappedHint() {
        HashMap<String, String> alphabet = game.getCryptogram().getCryptogramAlphabet();
        String oldMapping;
        String newMapping;
        String[] input;

        Iterator<Map.Entry<String, String>> entryIterator = alphabet.entrySet().iterator();
        String letter = entryIterator.next().getKey(); // get first letter
        String value =  entryIterator.next().getValue(); // get second value
        game.enterLetter(new String[]{letter, value}, false);

        oldMapping = game.getCurrentSolution().get(letter);
        Assertions.assertTrue(game.isLetterAlreadyUsed(letter));
        System.setOut(new PrintStream(outputStreamCaptor));
        game.hint();
        assertEquals("The letter " + letter + " replaced your wrong guess.", outputStreamCaptor.toString().trim());
        System.setOut(standardOut);
        newMapping = game.getCurrentSolution().get(letter);
        Assertions.assertTrue(game.isLetterAlreadyUsed(letter));
        assertNotEquals(oldMapping, newMapping);
    }
}