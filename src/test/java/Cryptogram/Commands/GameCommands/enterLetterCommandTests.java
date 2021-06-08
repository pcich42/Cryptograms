//package Cryptogram.Commands.GameCommands;
//
//import Cryptogram.Interfaces.GameCommand;
//import Cryptogram.Interfaces.ICryptogramManager;
//import Cryptogram.Mocks.MockUnitTestsManager;
//import Cryptogram.Models.Cryptogram;
//import Cryptogram.Models.Player;
//import Cryptogram.Views.InputPrompt;
//import Cryptogram.Views.View;
//import org.junit.jupiter.api.BeforeEach;
//
//import java.util.Scanner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class enterLetterCommandTests {
//
//    private Cryptogram cryptogram;
//    private GameCommand command;
//    private InputPrompt prompt;
//
//    @BeforeEach
//    void setUp() {
//        ICryptogramManager manager = new MockUnitTestsManager();
//        assertDoesNotThrow(() -> cryptogram = manager.loadCryptogram(null));
//        prompt = new InputPrompt(new Scanner(""));
//
//    }
//
//    void letterIsPutIn() {
//        String[] input = new String[]{
//                "a o",
//        };
//        command = new enterLetterCommand(
//                input,
//                cryptogram,
//                new Player("player2"),
//                prompt,
//                new View()
//        );
//
//    }
//}
