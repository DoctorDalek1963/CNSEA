import java.io.File;
import java.io.FileReader;

public class Main {

    static void authenticate(String name, String password) {
        // File deck = new File("deck.txt");
        try {
        FileReader deckRead = new FileReader("deck.txt");
        }
        catch (Exception e) {
            System.out.println("deck.txt was not found.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to The Card Game!");
        System.out.println("In this game, each player draws a card, the cards are compared and the winner takes both cards.");
        System.out.println();
        System.out.println("Press enter to continue");
    }

}
