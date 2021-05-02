package Cryptogram.Controllers;

import Cryptogram.Interfaces.GameCommand;
import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Mocks.MockPlayers;
import Cryptogram.Mocks.MockUnitTestsManager;
import Cryptogram.Models.Cryptogram;
import Cryptogram.Models.LettersCryptogram;
import Cryptogram.Models.Player;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    static private Player player;
    static private Cryptogram cryptogram;
    static private ICryptogramManager manager;
    static private IPlayers players;
    static private InputPrompt prompt;
    static private View view;
    private Game game;

    @BeforeAll
    static void setUpAll() {
        player = new Player("Player1");
        cryptogram = new LettersCryptogram("test phrase");
        manager = new MockUnitTestsManager();
        players = new MockPlayers();
        prompt = new InputPrompt(new Scanner(""));
        view = new View();
    }

    @BeforeEach
    void setUp() {
        game = new Game(player,
                cryptogram,
                manager,
                players,
                prompt,
                view,
                new GameCommandSupplier() {
                    @Override
                    public GameCommand fetchCommand(String[] input,
                                                    Cryptogram cryptogram,
                                                    Player player,
                                                    ICryptogramManager manager,
                                                    IPlayers playerList,
                                                    InputPrompt prompt,
                                                    View view) {
                        return () -> true;
                    }
                });
    }

    @Test
    void initTest(){
        assertNotNull(game.getPlayer());
        assertNotNull(game.getCryptogram());
    }

    @Test
    void gameLoopWorks() {
        assertTrue(game.executeCommand(new String[] {
                "exit",
        }));
    }
}
