package Cryptogram.Interfaces;

import Cryptogram.Models.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

public interface IPlayers {

    void addPlayer(String username);

    Player getPlayer(String username);

    void savePlayerDetails();

    Map<Player, Integer> getAllPlayersCompletedGames();

    void loadPlayersFromFile() throws IOException;
}
