import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

class Card {
    String colour;
    int number;

    Card(String colour, int number) {
        this.colour = colour;
        this.number = number;
    }
}

//class Score {
//    String name;
//    int number;
//
//    Score(String name, int number) {
//        this.name = name;
//        this.number = number;
//    }
//}

public class CardGame {

    public static void authenticate(String name, String password) {
        Scanner inputScanner = new Scanner(System.in);

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
        catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public static void addPlayer() {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println();
        System.out.print("Please enter the name of the new player: ");
        String name = inputScanner.nextLine();
        System.out.print("Please enter the password: ");
        String password = inputScanner.nextLine();

        String newPlayer = name + "," + password;

        try {
            FileWriter fileWriter = new FileWriter("player_list.csv", true); // True enables append mode
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(newPlayer);
            printWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Press enter to log in.");
        inputScanner.nextLine();
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in); // Create scanner object to read input

        System.out.println("Welcome to The Card Game!");
        System.out.println("In this game, each player draws a card, the cards are compared and the winner takes both cards.");
        System.out.println();
        System.out.println("Press 1 to add a new player. Press enter to log in.");

        if (inputScanner.hasNextLine()) {
            if (inputScanner.nextLine().equals("1")) {
                addPlayer();
            }
        }

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

        Card[] deck = new Card[30]; // Array of 30 Card objects called deck

        try {
            File deckFile = new File("deck.csv");
            Scanner deckScanner = new Scanner(deckFile);
            int count = 0;

            while (deckScanner.hasNextLine()) {
                String line = deckScanner.nextLine();
                deck[count] = new Card(line.split(",")[0], Integer.parseInt(line.split(",")[1]));
                count++;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(deck));
    }

}
