package Cryptogram.UnitTests;//package Cryptogram;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.math.MathContext;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class SeeFrequenciesTests {
//
//    private Game game;
//    private final String username = "testUser";
//
//    @BeforeEach
//    public void setup() {
//        assertDoesNotThrow(() -> game = new Game(username));
//        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//    }
//
//    @Test
//    void allPhraseLettersPresentInFrequencies() {
//        String frequencies = game.getFrequencies();
//
//        boolean testFailed = false;
//        String phrase = game.getCryptogram().getPhrase();
//        for (int i = 0; i < phrase.length(); i++) {
//            if (frequencies.indexOf(phrase.toCharArray()[i]) == -1) {
//                testFailed = true;
//                break;
//            }
//        }
//
//        assertFalse(testFailed);
//    }
//
//    @Test
//    void frequenciesCorrect() {
//        String frequencies = game.getFrequencies();
//
//        String phrase = game.getCryptogram().getPhrase();
////        char firstLetter = phrase.toCharArray()[0];
//
//        double frequency;
//
//        String[] tokens = frequencies.split(", ");
//        String[] firstPair = tokens[0].split(":");
//
//        frequency = Double.parseDouble(String.valueOf(firstPair[1]));
//
//        int count = 0;
//        for (char letter : phrase.toCharArray()) {
//            if (letter == firstPair[0].charAt(0))
//                count++;
//        }
//
//        int size = game.getCryptogram().getPhrase().replaceAll("\\s+","").length();
//        assertEquals(count*100.0/size, frequency, 1);
//    }
//}
