import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Scanner;

class Card {
    String colour;
    int number;

    Card(String colour, int number) {
        this.colour = colour;
        this.number = number;
    }

    public String getColour() {
        return colour;
    }
    public int getNumber() {
        return number;
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

    static String p1Name;
    static String p2Name;

    static Card p1ActiveCard;
    static Card p2ActiveCard;

    static Card[] p1Cards;
    static Card[] p2Cards;


    static Scanner inputScanner = new Scanner(System.in); // Create scanner object to read input

    public static void authenticate(String name, String password) {
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

    public static void winHand(String name, Card[] cardStack) {
        // TODO: Add active cards to cardStack

        System.out.println(name + " won that hand!");
        System.out.println();
        System.out.println("Press enter to continue.");
        inputScanner.nextLine();
        System.out.println();
    }

    public static void compare(Card card1, Card card2) {
        if (card1.getColour().equals(card2.getColour())) {
            if (card1.getNumber() > card2.getNumber()) {
                winHand(p1Name, p1Cards);

            } else {
                winHand(p2Name, p2Cards);
            }
        } else {
            String colour = card1.getColour() + " " + card2.getColour();
            String winColour = "";

            if (colour.equals("Red Black") | colour.equals("Black Red")) {
                winColour = "Red";

            } else if (colour.equals("Yellow Red") | colour.equals("Red Yellow")) {
                winColour = "Yellow";

            } else if (colour.equals("Black Yellow") | colour.equals("Yellow Black")) {
                winColour = "Black";
            }

            if (winColour.equals(card1.getColour())) {
                winHand(p1Name, p1Cards);

            } else if (winColour.equals(card2.getColour())) {
                winHand(p2Name, p2Cards);

            } else {
                System.out.println("Unhandled winColour exception in CardGame.compare()");
            }
        }
    }

    public static void main(String[] args) {
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
        p1Name = inputScanner.nextLine();

        System.out.print("Player 1, please enter your password: ");
        String p1Pass = inputScanner.nextLine();

        authenticate(p1Name, p1Pass);

        System.out.print("Player 2, please enter your username: ");
        p2Name = inputScanner.nextLine();

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

        System.out.println("Press enter to start.");
        inputScanner.nextLine();
        System.out.println("Let's begin the game!");
        System.out.println();
        
        int handNum = 1;

        for (int i = 0; i < 15; i++) {
            System.out.println("Hand: " + handNum);
            p1ActiveCard = deck[i];
            p2ActiveCard = deck[i + 1];

            System.out.println(p1Name + " drew a " + p1ActiveCard.getColour() + " " + p1ActiveCard.getNumber());
            System.out.println(p2Name + " drew a " + p2ActiveCard.getColour() + " " + p2ActiveCard.getNumber());
            System.out.println();
            System.out.println("Press enter to continue.");
            System.out.println();
            inputScanner.nextLine();

            compare(p1ActiveCard, p2ActiveCard);

            handNum++;
        }

        System.out.println("All cards have been drawn!");
        System.out.println("The winner is...");
        System.out.println();

        String winner;
        int winNum;
        Card[] winCards;

        if (p1Cards.length > p2Cards.length) {
            winner = p1Name;
            winNum = p1Cards.length;
            winCards = p1Cards;
        } else {
            winner = p2Name;
            winNum = p2Cards.length;
            winCards = p2Cards;
        }

        System.out.println(winner + "! With " + winNum + " cards!");
        System.out.println();
        System.out.println("They had these cards:");
        System.out.println();

        for (Card card : winCards) {
            System.out.println(card);
        }

        System.out.println();

        // TODO: Handle scores
    }

}
