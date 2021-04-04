//package Cryptogram;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UndoLetterMappingTests {
//
//    private final PrintStream standardOut = System.out;
//    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//    private Game game;
//
//    private String[] generateUserInputFromCurrentCryptogram(Game game) {
//        String CypherLetter = game.getCryptogram().getCryptogramAlphabet().keySet().toArray(new String[0])[0];
//        return new String[]{CypherLetter, game.getCryptogram().getCryptogramAlphabet().get(CypherLetter)};
//    }
//
//    @BeforeEach
//    public void setup () {
//        assertDoesNotThrow(() -> game = new Game("abc"));
//        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//    }
//
//    /**
//     * Tests if undo is deleting letters in Letter Cryptogram.Cryptogram*
//     */
//    @Test
//    public void undoLetterLetterCryptogram() {
//
//        String[] input = generateUserInputFromCurrentCryptogram(game);
//        assertDoesNotThrow(() ->game.enterLetter(input, false));
//        Assertions.assertTrue(game.isLetterAlreadyUsed(input[0]));
//
//        String[] undoInput = {"undo", input[0]};
//
//        assertDoesNotThrow(() -> game.undoLetter(undoInput));
//        Assertions.assertFalse(game.isLetterAlreadyUsed(input[0]));
//
//    }
//
//    /**
//     * For Letter Cryptogram.Cryptogram:Tests if undo displays an error message when the letter to undo is not there *
//     */
//    @Test
//    public void undoFailLetterCryptogram() {
//        String[] input = generateUserInputFromCurrentCryptogram(game);
//        String[] undoInput = {"undo", input[0]};
//        assertThrows(IllegalStateException.class, () -> game.undoLetter(undoInput));
//    }
//
//}
