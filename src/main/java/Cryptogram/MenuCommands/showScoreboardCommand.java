package Cryptogram.MenuCommands;

import Cryptogram.*;

import java.util.*;

public class showScoreboardCommand implements GameCommand, MenuCommand {

    private final IPlayers playerList;
    private final View view;

    public showScoreboardCommand(IPlayers playerList, View view) {
        this.playerList = playerList;
        this.view = view;
    }

    @Override
    public void perform() {
        showScoreboard();
    }
    
    @Override
    public boolean execute() {
        showScoreboard();
        return false;
    }
    
    private void showScoreboard() {
        Map<Player, Integer> gamesCompleted = playerList.getAllPlayersCompletedGames();
        ArrayList<Map.Entry<Player, Integer>> scoreboardToSort = new ArrayList<>(gamesCompleted.entrySet());
        scoreboardToSort.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        ArrayList<String> scoreboard = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : scoreboardToSort) {
            if (entry.getValue() != 0) {
                scoreboard.add(entry.getKey().getUsername() + " - " + entry.getValue());
            }
        }
        if (scoreboard.size() != 0) {
            view.displayMessage("Top 10 players by number of successfully completed cryptograms: ");
            Iterator<String> it = scoreboard.iterator();
            int i = 1;
            while (i < 11) {
                String username = "";
                if (it.hasNext()) {
                    username = it.next();
                }
                view.displayMessage(i + ": " + username);
                i++;
            }
        } else {
            view.displayMessage("No players in the scoreboard.");
        }
    }

    public Game getGame() {
        return null;
    }
}
