package Cryptogram;

import java.io.IOException;
import java.util.HashMap;

public class MockPlayers extends Players implements IPlayers {

    public MockPlayers() throws IOException {
        allPlayers = new HashMap<>();
        allPlayers.put("test_user", new Player("test_user"));
    }

    @Override
    public void savePlayers() {}
}
