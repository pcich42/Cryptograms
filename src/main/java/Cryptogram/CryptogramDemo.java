package Cryptogram;

import java.io.IOException;

class CryptogramDemo {

    public static void main(String[] args) {

        try {
            Players players = new Players();
            CryptogramManager manager = new CryptogramManager();
            View view = new View();
            Game game = new Game(players, manager, view);
            game.setup();
            game.play();
        } catch (IOException e) {
            System.out.println("Players File invalid, Exiting...");
        }
    }

}
