//package Cryptogram.UnitTests;
//
//import Cryptogram.*;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.util.Scanner;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class EnterALetterTests {
//
//    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
//    private Application app;
//    private InputPrompt prompt;
//
//
////    @BeforeAll
////    static void setup() {
//////        players = new MockPlayers();
//////        manager = new MockManager();
////
////    }
//
//    @BeforeEach
//    void before() {
//        System.setOut(new PrintStream(output));
//
//        prompt = new InputPrompt(new Scanner("user_test\n"));
//        IPlayers players = new MockPlayers();
//        ICryptogramManager manager = new MockManager();
//        View view = new View();
//        app = new Application(players, manager, view, prompt);
//
//    }
//
//    @AfterEach
//    void after() {
//        System.setOut(System.out);
//    }
//
//    /**
//     * Tests if an error message is displayed if a wrong mapping is chosen for Letter Cryptogram.Cryptogram *
//     */
//    @Test
//    public void PlayersMapsALetterToAKeyThatDoesntExist() {
//
//        String[] gameInput = {"p", "!"};
//
//        assertFalse(game.executeCommand(gameInput));
//        assertEquals(">> The value you're trying to map to, is not present in this cryptogram.\n" +
//                ">> Try again with a different value.", output.toString().trim());
////        assertNotEquals(game.getCryptogram().getLetterFromSolution(gameInput[0]), gameInput[1]);
//    }
////
////    @Test
////    public void playerEntersLetter() {
////        //Given a cryptogram has been generated and is being played
////        int totalGuesses = game.getPlayer().getTotalGuesses();
////        int correctGuesses = game.getPlayer().getCorrectGuesses();
////
////        //When the player identifies a value to replace with a letter
////        String[] input = {"e", "b"};
////        game.executeCommand(input);
////
////        //Then the letter is mapped to that value and is filled in for all instances in the cryptogram
////        assertEquals(game.getCryptogram().getSolution().get(input[0]), input[1]);
////
////        //the player’s statistics (numGuesses, numCorrectGuesses) are updated
////        assertTrue(game.getCryptogram().isGuessCorrect(input[0] , input[1]));
////        assertEquals(totalGuesses+1, game.getPlayer().getTotalGuesses());
////        assertEquals(correctGuesses+1, game.getPlayer().getCorrectGuesses());
////    }
////
////    @Test
////    public void playerPicksAlreadyMappedCryptoLetterAndDoesntRemap() {
////        // Given a cryptogram has been generated and is being played
////        int totalGuesses = game.getPlayer().getTotalGuesses();
////
////        // when a player select a value to which they already have mapped
////        String[] userInput = {"p", "a"};
////        prompt.injectInput("no");
////        game.executeCommand(userInput);
////
////        // they are asked if they want to remap a letter
////        assertEquals(">> This value is already mapped to. Do you want to override this mapping?",
////                output.toString().trim());
////
////        // if no, letters remain
////        assertNotEquals(game.getCryptogram().getLetterFromSolution(userInput[1]), userInput[0]);
////
////        // stats are not updated
////        assertEquals(game.getPlayer().getTotalGuesses(), totalGuesses);
////    }
////
////    @Test
////    public void playerPicksAlreadyMappedCryptoLetterAndRemaps() {
////        // Given a cryptogram has been generated and is being player
////        int totalGuesses = game.getPlayer().getTotalGuesses();
////
////        // when a player select a value to which they already have mapped
////        String[] userInput = {"p", "a"};
////        prompt.injectInput("yes");
////        game.executeCommand(userInput);
////
////        // they are asked if they want to remap a letter
////        assertEquals(">> This value is already mapped to. Do you want to override this mapping?",
////                output.toString().trim());
////
////        // if yes, letters remain
////        assertEquals(game.getCryptogram().getLetterFromSolution(userInput[1]), userInput[0]);
////
////        // stats are updated
////        assertEquals(game.getPlayer().getTotalGuesses(), totalGuesses+1);
////    }
////
////
////    @Test
////    public void playerPicksAlreadyMappedPlainLetter() {
////        //Given a cryptogram has been generated and is being played
////        String[] userInput = {"t", "b"};
////
////        // it shouldn't be a mapping already
////        game.executeCommand(userInput);
////        assertTrue(output.toString().contains("The letter you are trying to use, has already been used."));
////        assertNotEquals(game.getCryptogram().getLetterFromSolution(userInput[1]), userInput[0]);
////    }
////
////    @Test
////    public void PlayerEnterAllLettersSuccessfully() {
////        // when a player enters all letters correctly
////        assertFalse(game.executeCommand(new String[]{"e", "b"}));
////        assertTrue(game.executeCommand(new String[]{"s", "c"}));
////        // the game is finished
////        assertTrue(output.toString().contains(">> Congratulations!!! You have successfully completed this cryptogram\n" +
////                ">> Try again with a different one now."));
////    }
////
////    @Test
////    public void PlayerEntersLastLetterUnsuccessfully() {
////        // when a player enters all letters correctly
////        assertFalse(game.executeCommand(new String[]{"e", "b"}));
////        assertFalse(game.executeCommand(new String[]{"o", "c"}));
////        // the game is not finished
////        assertTrue(output.toString().contains(">> Somethings is off here. Undo some letters to map them again."));
////
////    }
//
//}
