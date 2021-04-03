package Cryptogram;

import java.io.IOException;
import java.util.Scanner;

class CryptogramDemo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        try {
            IPlayers Players = new Players();
            ICryptogramManager manager = new CryptogramManager();
            Game game = new Game(Players, manager, scanner);
            game.play();
        } catch (IOException e) {
            System.out.println("Players File invalid, Exiting...");
        }
    }

}
