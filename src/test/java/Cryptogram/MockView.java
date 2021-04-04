package Cryptogram;

import java.util.Scanner;

public class MockView extends View {

    public MockView(String input){
        this.scanner = new Scanner(input);
    }

    public void injectInput(String input){
        this.scanner = new Scanner(input);
    }
}
