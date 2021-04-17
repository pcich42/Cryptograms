//package Cryptogram;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class StorePlayerDetailsTests {
//    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
//    private static Players players;
//    private static final String path = "TestGameFiles/player_data.txt";
//
//    @Test
//    public void storePlayerDetails() {
//
//        assertDoesNotThrow(() -> players = new Players());
//
//        players.savePlayers();
//        File file = new File(path);
//
//    }
//
//}
