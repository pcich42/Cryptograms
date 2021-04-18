package Cryptogram;

import java.util.Map;

public interface IPlayers {

    void addPlayer(String username);

    Player getPlayer(String username);

    void savePlayerDetails();

    Map<Player, Integer> getAllPlayersCompletedGames();

    boolean isInstantiated();
}
