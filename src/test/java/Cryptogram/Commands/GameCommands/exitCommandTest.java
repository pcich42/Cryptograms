package Cryptogram.Commands.GameCommands;

import Cryptogram.Interfaces.GameCommand;
import Cryptogram.Mocks.MockUnitTestsManager;
import Cryptogram.Models.LettersCryptogram;
import Cryptogram.Models.Player;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class exitCommandTest {

    private GameCommand exitCommand;
    private InputPrompt prompt;

    @BeforeEach
    void setUp() {
        prompt = new InputPrompt(new Scanner(""));
         exitCommand = new exitCommand(
                new LettersCryptogram("test phrase"),
                new Player("abc"),
                new MockUnitTestsManager(),
                new View(),
                prompt);
    }

    @Test
    void gameQuits_PlayerDoesntSave() {
        prompt.injectInput(new String[] {
                "no"
        });
        assertTrue(exitCommand.execute());
    }
}