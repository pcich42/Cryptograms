package Cryptogram.Models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LetterCryptogramTests {

    Cryptogram cryptogram;

    @BeforeEach
    void setUp() {
        String phrase = "this is a test";
//        Map<String, String> alphabet = new HashMap<>();
//        alphabet.put("t", "a");
//        alphabet.put("h", "b");
//        alphabet.put("i", "c");
//        alphabet.put("s", "d");
//        alphabet.put("a", "e");
//        alphabet.put("e", "f");
//        Map<String, String> solution = new HashMap<>();
        cryptogram = new LettersCryptogram(phrase);
    }

    @Test
    void newCryptogramHasEmptySolution() {
        assertTrue(cryptogram.getSolution().isEmpty());
    }

    @Test
    void afterOneLetterMapping_solutionSizeIsOne() {
        cryptogram.mapLetters("h", "i");
        assertEquals(1, cryptogram.getSolution().size());
    }

    @Test
    void afterOneLetterMappingAndRemove_solutionSizeIsZero() {
        cryptogram.mapLetters("h", "i");
        cryptogram.removeMapping("h");
        assertTrue(cryptogram.getSolution().isEmpty());
    }

    @Test
    void remapAlreadyExitingLetterWorks() {
        cryptogram.mapLetters("h", "i");
        cryptogram.remapLetters("o", "i");
        assertEquals(cryptogram.getSolution().get("o"), "i");
    }

}
