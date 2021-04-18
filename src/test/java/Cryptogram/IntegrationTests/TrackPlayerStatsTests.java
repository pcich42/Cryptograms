//package Cryptogram;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TrackPlayerStatsTests {
//
//    private Game game;
//    private final String username = "abc";
//
//    @BeforeEach
//    public void setUp() {
//            assertDoesNotThrow(() -> game = new Game(username));
//            assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//    }
//
//
//    /**
//     * Tests if the number of successfully completed cryptograms
//     * is updated after a cryptogram is successfully completed
//     */
//    @Test
//    public void successfulCryptogram() {
//        HashMap<String, String> alphabet = game.getCryptogram().getCryptogramAlphabet();
//        int cryptogramsComplete = game.getPlayer().getCryptogramsCompleted();
//        String[] input;
//        for (Map.Entry<String, String> entry : alphabet.entrySet()) {
//            input = new String[]{entry.getKey(), entry.getValue()};
//            game.enterLetter(input, false);
//        }
//
//        Assertions.assertEquals(1, game.isSuccessfullyFinished());
//        Assertions.assertEquals(cryptogramsComplete + 1, game.getPlayer().getCryptogramsCompleted());
//
//    }
//
//    /**
//     * Tests if the number of successfully completed cryptograms
//     * is not updated after a cryptogram is unsuccessfully completed
//     */
//    @Test
//    public void unsuccessfulCryptogram() {
//        HashMap<String, String> alphabet = game.getCryptogram().getCryptogramAlphabet();
//        Assertions.assertEquals(0, game.getPlayer().getCryptogramsCompleted());
//        String[] input;
//        String previous = "x";
//        for (Map.Entry<String, String> entry : alphabet.entrySet()) {
//            input = new String[]{previous, entry.getValue()};
//            game.enterLetter(input, false);
//            previous = entry.getKey();
//        }
//        Assertions.assertEquals(-1, game.isSuccessfullyFinished());
//        Assertions.assertEquals(0, game.getPlayer().getCryptogramsCompleted());
//
//    }
//
//
//
//    /**
//     * Tests if the number of cryptograms completed is updated when a
//     * cryptogram is successfully finished
//     */
//    @Test
//    public void numberOfCryptogramsPlayed()  {
//        Assertions.assertEquals(1, game.getPlayer().getCryptogramsPlayed());
//        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//        Assertions.assertEquals(2, game.getPlayer().getCryptogramsPlayed());
//    }
//
//
//
//    /**
//     * Tests if the number of cryptograms completed stays the same when a
//     * cryptogram is unsuccessfully finished
//     *
//     */
//    @Test
//    public void noChangesToCryptogramsPlayed(){
//        Assertions.assertEquals(1, game.getPlayer().getCryptogramsPlayed());
//        assertDoesNotThrow(() -> game.saveGameToFile(true));
//        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//        Assertions.assertEquals(2, game.getPlayer().getCryptogramsPlayed());
//        assertDoesNotThrow(() -> game.loadGame());
//        Assertions.assertEquals(2, game.getPlayer().getCryptogramsPlayed());
//
//        File file = new File("Resources/PlayerGameFiles/"+username+"Game.txt");
//        if (file.exists()){
//            file.delete();
//        }
//
//    }
//
//    /**
//     * Tests if the number of guesses and number of correct guesses are
//     * updated when a letter is entered correctly
//     **/
//    @Test
//    public void correctGuess() {
//        HashMap<String, String> alphabet = game.getCryptogram().getCryptogramAlphabet();
//        String[] input;
//
//        Assertions.assertEquals(0, game.getPlayer().getCorrectGuesses());
//        Assertions.assertEquals(0, game.getPlayer().getTotalGuesses());
//
//        for (Map.Entry<String, String> entry : alphabet.entrySet()) {
//            input = new String[]{entry.getKey(), entry.getValue()};
//            game.enterLetter(input, false);
//            break;
//        }
//
//        Assertions.assertEquals(1, game.getPlayer().getCorrectGuesses());
//        Assertions.assertEquals(1, game.getPlayer().getTotalGuesses());
//    }
//
//
//
//    /**
//     * Tests if the number of guesses and not number of correct guesses is
//     * updated when a letter is entered incorrectly
//     **/
//    @Test
//    public void wrongGuess() {
//        HashMap<String, String> alphabet = game.getCryptogram().getCryptogramAlphabet();
//        String[] input;
//
//        Assertions.assertEquals(0, game.getPlayer().getCorrectGuesses());
//        Assertions.assertEquals(0, game.getPlayer().getTotalGuesses());
//
//        for (Map.Entry<String, String> entry : alphabet.entrySet()) {
//            input = new String[]{"z", entry.getValue()};
//            game.enterLetter(input, false);
//            break;
//        }
//
//        Assertions.assertEquals(0, game.getPlayer().getCorrectGuesses());
//        Assertions.assertEquals(1, game.getPlayer().getTotalGuesses());
//    }
//
//}