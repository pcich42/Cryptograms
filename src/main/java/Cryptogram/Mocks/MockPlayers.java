package Cryptogram.Mocks;

import Cryptogram.Controllers.Players;
import Cryptogram.Models.Player;

import java.util.HashMap;

public class MockPlayers extends Players {
    
    public MockPlayers() {
        allPlayers = new HashMap<>();
        allPlayers.put("test_user", new Player("test_user"));
    }

    @Override
    public void savePlayerDetails() {}
}
