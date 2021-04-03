package Cryptogram;

import java.util.HashMap;

public interface IPlayers {
    void addPlayer(String username);

    Player getPlayer(String username);

    void savePlayers();

    HashMap<Player, Integer> getAllPlayersCompletedGames();
}
