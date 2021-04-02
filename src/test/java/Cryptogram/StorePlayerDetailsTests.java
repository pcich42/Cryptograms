package Cryptogram;

import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorePlayerDetailsTests {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Game game;
    private final String username = "def";


    @Test
    public void storePlayerDetails() {
        assertDoesNotThrow(() -> game = new Game(username));
        assertDoesNotThrow(() -> game.generateCryptogram("letters"));

        String path = "Resources/player_data.txt";
        List<String> contents = null;
        try {
            contents = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            fail();
        }

        game.updatePlayersFile();

        try {
            PrintWriter pw = new PrintWriter(path);
            pw.write("");
            pw.close();
            if (contents.size() != 0) {
                Files.write(Path.of(path), contents, StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            fail();
        }

        System.setOut(new PrintStream(outputStreamCaptor));
        assertNotEquals("Couldn't write to the file, sorry but all progress will be lost :( .", outputStreamCaptor.toString().trim());
        System.setOut(standardOut);


    }

}
