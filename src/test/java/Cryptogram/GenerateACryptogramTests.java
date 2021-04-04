//package Cryptogram;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GenerateACryptogramTests {
//
//    private Game game;
//    private final List<String> lettersAlphabet = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));
//    private final String[] possibleIntsArray = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
//            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
//            "21", "22", "23", "24", "25", "26"};
//    private final List<String> numbersAlphabet = Arrays.asList(possibleIntsArray);
//
//    @Nested
//    class PhraseFileExists {
//        @BeforeEach
//        public void setup () {
//        assertDoesNotThrow(() -> game = new Game("abc"));
//        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
//    }
//
//
//        @Test
//        public void playerRequestsLettersCryptogram () {
//
//            //if player requests letters cryptogram
//            assertDoesNotThrow(() -> game.generateCryptogram("lettres"));
//
//            List<String> actual = Arrays.asList(game.getCryptogram().getCryptogramAlphabet().values().toArray(new String[0]));
//            // they get a cryptogram which contains only letters
//            assertFalse(Collections.disjoint(actual, lettersAlphabet));
//            assertTrue(Collections.disjoint(actual, numbersAlphabet));
//
//        }
//
//        @Test
//        public void playerRequestsNumbersCryptogram () {
//
//            // when the player request numbers cryptogram
//            assertDoesNotThrow(() -> game.generateCryptogram("numbers"));
//
//            List<String> actual = Arrays.asList(game.getCryptogram().getCryptogramAlphabet().values().toArray(new String[0]));
//            // they get a cryptogram which contains only numbers
//            assertFalse(Collections.disjoint(actual, numbersAlphabet));
//            assertTrue(Collections.disjoint(actual, lettersAlphabet));
//        }
//    }
//
//    @Test
//    public void playerRequestsCryptogramButNoPhrasesStored() {
//        assertDoesNotThrow(() -> game = new Game("abc"));
//        File file = new File("Resources/CryptogramPhrases");
//        if (file.isDirectory()){
//            if (file.list().length == 0) {
//                assertThrows(IOException.class, () -> game.generateCryptogram("letters"));
//            }
//        } else {
//            assertThrows(IOException.class, () -> game.generateCryptogram("letters"));
//        }
//    }
//
//}
