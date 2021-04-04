package Cryptogram;

import java.io.*;
import java.util.HashMap;

public class Players implements IPlayers {

    protected HashMap<String, Player> allPlayers;
    final private File playersFile;

    public Players(String path) throws IOException {
        this.playersFile = new File(path);
        this.playersFile.createNewFile();
        this.allPlayers = getPlayersFromFile();
    }

    public Players() throws IOException {
        this("PlayerFiles/player_data.txt");
    }

    @Override
    public void addPlayer(String username) {
        if (!allPlayers.containsKey(username)) {
            Player player = new Player(username);
            allPlayers.put(username, player);
        }
    }

    @Override
    public Player getPlayer(String username) {
        return allPlayers.getOrDefault(username, null);
    }

    private HashMap<String, Player> getPlayersFromFile() throws IOException {
        HashMap<String, Player> players = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(playersFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length == 5) {
                Player player = new Player(tokens[0],   // username
                        Integer.parseInt(tokens[1]),     // correct guesses
                        Integer.parseInt(tokens[2]),    // total guesses
                        Integer.parseInt(tokens[3]),    // games played
                        Integer.parseInt(tokens[4])     // games completed
                );
                players.put(player.getUsername(), player);
            } else {
                throw new IOException("File format invalid");
            }
        }
        br.close();
        return players;
    }

    @Override
    public void savePlayers() {

        try {
            PrintWriter writer = new PrintWriter(playersFile);
            for (Player player : allPlayers.values()) {

                String str = player.getUsername() +
                        "," +
                        player.getCorrectGuesses() +
                        "," +
                        player.getTotalGuesses() +
                        "," +
                        player.getCryptogramsPlayed() +
                        "," +
                        player.getCryptogramsCompleted() +
                        "\n";
                writer.write(str);

            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Couldn't write to the file, sorry but all progress will be lost :( .");
        }

    }

    @Override
    public HashMap<Player, Integer> getAllPlayersCompletedGames() {
        HashMap<Player, Integer> gamesCompleted = new HashMap<>();
        for (Player player : allPlayers.values()) {
            if (player.getCryptogramsCompleted() != 0)
                gamesCompleted.put(player, player.getCryptogramsCompleted());
        }
        return gamesCompleted;
    }

}