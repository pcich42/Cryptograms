package Cryptogram.Models;

import Cryptogram.Exceptions.GuessAlreadyUsedException;
import Cryptogram.Exceptions.ValueAlreadyMappedException;
import Cryptogram.Exceptions.ValueNotInCryptogramException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LetterCryptogramTests {

    Cryptogram cryptogram;

    @BeforeEach
    void setUp() {
        String phrase = "this is a test";
        Map<String, String> alphabet = new HashMap<>();
        alphabet.put("t", "a");
        alphabet.put("h", "b");
        alphabet.put("i", "c");
        alphabet.put("s", "d");
        alphabet.put("a", "e");
        alphabet.put("e", "f");
        Map<String, String> solution = new HashMap<>();
        cryptogram = new Cryptogram(phrase, alphabet, solution);
    }

    @Test
    void newCryptogramHasEmptySolution() {
        assertTrue(cryptogram.getSolution().isEmpty());
    }

    @Test
    void afterOneLetterMapping_solutionSizeIsOne() {
        cryptogram.mapLetters("h", "b");
        assertEquals(1, cryptogram.getSolution().size());
    }

    @Test
    void afterOneLetterMappingAndRemove_solutionSizeIsZero() {
        cryptogram.mapLetters("h", "b");
        cryptogram.removeMapping("h");
        assertTrue(cryptogram.getSolution().isEmpty());
    }

    @Test
    void remapAlreadyExitingLetterWorks() {
        cryptogram.mapLetters("h", "b");
        cryptogram.remapLetters("o", "b");
        assertEquals(cryptogram.getSolution().get("o"), "b");
    }

    @Test
    void validMappingDoesNotThrowExceptions() {
        assertDoesNotThrow(() -> cryptogram.mapLetters("h", "b"));
        assertDoesNotThrow(() -> cryptogram.mapLetters("t", "e"));
        assertDoesNotThrow(() -> cryptogram.mapLetters("o", "c"));
        assertDoesNotThrow(() -> cryptogram.mapLetters("i", "f"));
        assertDoesNotThrow(() -> cryptogram.mapLetters("s", "a"));
    }

    @Test
    void mappingToAlreadyMappedLetter_throws_GuessAlreadyUsed() {
        cryptogram.mapLetters("h", "b");
        cryptogram.mapLetters("u", "a");
        assertThrows(GuessAlreadyUsedException.class, () -> cryptogram.mapLetters("h", "c"));
        assertThrows(GuessAlreadyUsedException.class, () -> cryptogram.mapLetters("u", "c"));
        assertThrows(GuessAlreadyUsedException.class, () -> cryptogram.mapLetters("u", "e"));
    }

    @Test
    void mappingToValueNotInAlphabet_throws_ValueNotInCryptogram() {
        assertThrows(ValueNotInCryptogramException.class, () -> cryptogram.mapLetters("h", "i"));
        assertThrows(ValueNotInCryptogramException.class, () -> cryptogram.mapLetters("h", "o"));
        assertThrows(ValueNotInCryptogramException.class, () -> cryptogram.mapLetters("p", "03"));
    }

    @Test
    void mappingToAlreadyMappedValue_throws_ValueAlreadyMapped() {
        cryptogram.mapLetters("h", "e");
        assertThrows(ValueAlreadyMappedException.class, () -> cryptogram.mapLetters("i", "e"));
        assertThrows(ValueAlreadyMappedException.class, () -> cryptogram.mapLetters("o", "e"));
    }

}
