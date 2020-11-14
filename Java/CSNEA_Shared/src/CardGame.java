import java.io.*;
import java.util.*;

class Card {

    private final String colour;
    private final int number;

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

    public String displayName() {
        return this.colour + " " + this.number;
    }
}

public class CardGame {

    static String p1Name;
    static String p2Name;

    static Card p1ActiveCard;
    static Card p2ActiveCard;

    // Create ArrayLists for players' card stacks
    static ArrayList<Card> p1Cards = new ArrayList<>();
    static ArrayList<Card> p2Cards = new ArrayList<>();

    // Create scanner object to read input
    static Scanner inputScanner = new Scanner(System.in);


    public static void winHand(String name, ArrayList<Card> cardStack) {
        // Add active Cards to player's card stack
        cardStack.add(p1ActiveCard);
        cardStack.add(p2ActiveCard);

        System.out.println(name + " won that hand!");
        System.out.println();
        System.out.println("Press enter to continue.");
        inputScanner.nextLine();
    }

    public static void compare(Card card1, Card card2) {
        // If colours are equal, higher number wins
        if (card1.getColour().equals(card2.getColour())) {
            if (card1.getNumber() > card2.getNumber()) {
                winHand(p1Name, p1Cards);
            } else {
                winHand(p2Name, p2Cards);
            }
        } else {
            // If numbers are the same, compare colours to find winner
            String colour = card1.getColour() + " " + card2.getColour();
            String winColour = "";

            if (colour.equals("Red Black") | colour.equals("Black Red")) {
                winColour = "Red";
            } else if (colour.equals("Yellow Red") | colour.equals("Red Yellow")) {
                winColour = "Yellow";
            } else if (colour.equals("Black Yellow") | colour.equals("Yellow Black")) {
                winColour = "Black";
            }

            // Use winColour to find winning player
            if (winColour.equals(card1.getColour())) {
                winHand(p1Name, p1Cards);
            } else if (winColour.equals(card2.getColour())) {
                winHand(p2Name, p2Cards);
            } else {
                // If neither player wins, print error message
                System.out.println("Unhandled winColour exception in CardGame.compare().");
            }
        }
    }


    public static void main(String[] args) {
        System.out.println("Welcome to The Card Game!");
        System.out.println("In this game, each player draws a card, the cards are compared and the winner takes both cards.");
        System.out.println();
        System.out.println("Press 1 to add a new player. Press enter to log in.");

        // Call addPlayer() if input == "1"
        if (inputScanner.hasNextLine()) {
            if (inputScanner.nextLine().equals("1")) {
                GameMethods.addPlayer();
            }
        }

        System.out.print("Player 1, please enter your username: ");
        p1Name = inputScanner.nextLine();

        System.out.print("Player 1, please enter your password: ");
        String p1Pass = inputScanner.nextLine();

        GameMethods.authenticate(p1Name, p1Pass);

        System.out.print("Player 2, please enter your username: ");
        p2Name = inputScanner.nextLine();

        System.out.print("Player 2, please enter your password: ");
        String p2Pass = inputScanner.nextLine();

        // If same account, quit program
        if (p1Name.equals(p2Name)) {
            System.out.println("Sorry, " + p2Name + ", but that's the same account as player 1.");
            System.out.println("Press enter to exit");
            inputScanner.nextLine();
            System.exit(0);
        }

        GameMethods.authenticate(p2Name, p2Pass);

        // Create ArrayList of Cards to allow for shuffling later
        ArrayList<Card> deckList = new ArrayList<>();

        // Read deck.csv into deckList
        try {
            File deckFile = new File("deck.csv");
            Scanner deckScanner = new Scanner(deckFile);

            while (deckScanner.hasNextLine()) {
                String line = deckScanner.nextLine();
                deckList.add(new Card(line.split(",")[0], Integer.parseInt(line.split(",")[1])));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("deck.csv not found.");
            e.printStackTrace();
        }

        // Shuffle deckList and create normal array called deck
        Collections.shuffle(deckList);
        Card[] deck = deckList.toArray(new Card[0]);

        System.out.println("Press enter to start.");
        inputScanner.nextLine();
        System.out.println("Let's begin the game!");
        inputScanner.nextLine();

        int handNum = 1;

        // Loop over deck and compare top 2 Cards every time
        for (int i = 0; i < deck.length; i = i + 2) {
            System.out.println("Hand: " + handNum);
            p1ActiveCard = deck[i];
            p2ActiveCard = deck[i + 1];

            System.out.println(p1Name + " drew a " + p1ActiveCard.getColour() + " " + p1ActiveCard.getNumber());
            System.out.println(p2Name + " drew a " + p2ActiveCard.getColour() + " " + p2ActiveCard.getNumber());
            System.out.println();
            System.out.println("Press enter to continue.");
            inputScanner.nextLine();

            // Compare Cards to find winner
            compare(p1ActiveCard, p2ActiveCard);

            handNum++;
        }

        System.out.println("All cards have been drawn!");
        System.out.println("The winner is...");
        inputScanner.nextLine();

        // Create variables for winner
        String winner;
        int winNum;
        ArrayList<Card> winCards;

        // Find winner from length of card stacks
        if (p1Cards.size() > p2Cards.size()) {
            winner = p1Name;
            winNum = p1Cards.size();
            winCards = p1Cards;
        } else {
            winner = p2Name;
            winNum = p2Cards.size();
            winCards = p2Cards;
        }

        String winScore = winner + "," + winNum;

        System.out.println(winner + "! With " + winNum + " cards!");
        System.out.println();
        System.out.println("They had these cards:");
        inputScanner.nextLine();

        // Print all Cards
        for (Card card : winCards) {
            System.out.println(card.displayName());
        }

        System.out.println();

        GameMethods.writeScores("card_game_scores.csv", winScore);

        GameMethods.displayHighScores("card_game_scores.csv");

        System.out.println();
        System.out.println("Press enter to finish.");
        inputScanner.nextLine();
        System.out.println("Thank you, " + p1Name + " and " + p2Name + ", for playing The Card Game!");
        inputScanner.nextLine();
    }

}
