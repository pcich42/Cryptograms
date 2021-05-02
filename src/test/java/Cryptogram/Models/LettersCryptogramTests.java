package Cryptogram.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LettersCryptogramTests {

    private static boolean anyCharactersAreDigits(String value) {
        return value.chars().mapToObj(cha -> (char) cha)
                .anyMatch(Character::isDigit);
    }

    private static boolean allCharactersAreLetters(String value) {
        return value.chars().mapToObj(cha -> (char) cha)
                .allMatch(Character::isLetter);
    }

    @Test
    void generatedAlphabetIsOnlyLettersAfterInit() {

        Cryptogram cryptogram = new LettersCryptogram("this is a test");

        assertTrue(cryptogram.getCryptogramAlphabet().values().stream()
                .allMatch(LettersCryptogramTests::allCharactersAreLetters));

        assertTrue(cryptogram.getCryptogramAlphabet().values().stream()
                        .noneMatch(LettersCryptogramTests::anyCharactersAreDigits));
    }

}
