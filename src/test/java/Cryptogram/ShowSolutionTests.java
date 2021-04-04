//package Cryptogram;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ShowSolutionTests {
//
//    private Game game;
//
//    @Test
//    public void playerRequestsCorrectSolution() {
//        assertDoesNotThrow(() -> game = new Game("abc"));
//        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//
//        game.fillSolution();
//        Assertions.assertEquals(game.getCurrentSolution(), game.getCryptogram().getCryptogramAlphabet());
//
//    }
//}
