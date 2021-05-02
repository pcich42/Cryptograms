package Cryptogram.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumbersCryptogramTests {

    private static boolean allCharactersAreDigits(String value) {
        return value.chars().mapToObj(cha -> (char) cha)
                .allMatch(Character::isDigit);
    }

    private static boolean anyCharactersAreLetters(String value) {
        return value.chars().mapToObj(cha -> (char) cha)
                .anyMatch(Character::isLetter);
    }

    @Test
    void generatedAlphabetIsOnlyLettersAfterInit() {

        Cryptogram cryptogram = new NumbersCryptogram("this is a test");

        assertTrue(cryptogram.getCryptogramAlphabet().values().stream()
                .allMatch(NumbersCryptogramTests::allCharactersAreDigits));

        assertTrue(cryptogram.getCryptogramAlphabet().values().stream()
                        .noneMatch(NumbersCryptogramTests::anyCharactersAreLetters));
    }

}
