import java.io.File;
// import java.io.FileReader;
import java.util.Scanner;

public class CardGame {

    static void authenticate(String name, String password) {
        // Take a name and password and authenticate them if they're in player_list.csv

        Scanner inputScanner = new Scanner(System.in); // Create dummy scanner object to read input

        String playerDetails = name + "," + password;
        boolean match = false;

        try {
            File playerList = new File("player_list.csv");
            Scanner playerListReader = new Scanner(playerList);

            while (playerListReader.hasNextLine()) {
                // Search file for a match
                String fileDetails = playerListReader.nextLine();
                // If match is found, set flag var
                if (fileDetails.equals(playerDetails)) {
                    match = true;
                }
            }
        }
        catch (Exception e) {
            System.out.println("player_list.csv was not found.");
        }

        // If no match was found
        if (!match) {
            System.out.println();
            System.out.println("Sorry, " + name + ", your username or password was incorrect.");
            System.out.println("Press enter to exit");
            inputScanner.nextLine();
            System.exit(0);
        }

        System.out.println();
        System.out.println("Welcome, " + name + "!");
        System.out.println();

    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in); // Create dummy scanner object to read input

        System.out.println("Welcome to The Card Game!");
        System.out.println("In this game, each player draws a card, the cards are compared and the winner takes both cards.");
        System.out.println();
        System.out.println("Press enter to continue");
        inputScanner.nextLine();

        System.out.print("Player 1, please enter your username: ");
        String p1Name = inputScanner.nextLine();

        System.out.print("Player 1, please enter your password: ");
        String p1Pass = inputScanner.nextLine();

        authenticate(p1Name, p1Pass);

        System.out.print("Player 2, please enter your username: ");
        String p2Name = inputScanner.nextLine();

        System.out.print("Player 2, please enter your password: ");
        String p2Pass = inputScanner.nextLine();

        // If same account, disallow it
        if (p1Name.equals(p2Name)) {
            System.out.println("Sorry, " + p2Name + ", but that's the same account as player 1.");
            System.out.println("Press enter to exit");
            inputScanner.nextLine();
            System.exit(0);
        }

        authenticate(p2Name, p2Pass);


    }

}
