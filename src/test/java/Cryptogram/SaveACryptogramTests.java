package Cryptogram;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SaveACryptogramTests {
    private Game game;
    private String username = "abc";
    File file = new File("Resources/PlayerGameFiles/" + username + "Game.txt");


    @BeforeEach
    public void setup() {
        // create a game before each test
        assertDoesNotThrow(() -> game = new Game(username));
        assertDoesNotThrow(() -> game.generateCryptogram("letters"));
    }

    @AfterEach
    public void tearDown() {
        file.delete();
    }

    /**
     * Tests if save cryptogram works
     */
    @Test
    public void saveTheCryptogram() {
        //tests that save game works
        assertDoesNotThrow(() -> game.saveGameToFile(true));
        assertTrue(file.exists());
    }

    /**
     * Tests if saving cryptogram when cryptogram is already saved will prompt user to overwrite save
     */
    @Test
    public void playerAlreadyHasSavedCryptogram() {
        //tests that RunTime exception will show as game has already been saved
        assertDoesNotThrow(() -> game.saveGameToFile(true));
        assertTrue(file.exists());
        // if they do not want to overwrite the file:
        assertThrows(java.lang.RuntimeException.class, () -> game.saveGameToFile(false));
        // if they do want to overwrite it:
        assertDoesNotThrow(() -> game.saveGameToFile(true));
        assertTrue(file.exists());
    }
}
