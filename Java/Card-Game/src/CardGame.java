import java.io.*;
import java.util.*;

class Card {
    private final String colour;
    private final int number;

    Card(String colour, int number) {
        this.colour = colour;
        this.number = number;
    }

    public String getColour() { return colour; }
    public int getNumber() { return number; }
    public String displayName() { return this.colour + " " + this.number; }
}

class Score {
    private final String name;
    private final int number;

    Score(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public int getNumber() { return number; }
    public String displayName() { return this.name + " - " + this.number; }
}

public class CardGame {

    static String p1Name;
    static String p2Name;

    static Card p1ActiveCard;
    static Card p2ActiveCard;

    static ArrayList<Card> p1Cards = new ArrayList<>();
    static ArrayList<Card> p2Cards = new ArrayList<>();

    // Create scanner object to read input
    static Scanner inputScanner = new Scanner(System.in);


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
            System.out.println("player_list.csv not found.");
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
            // True enables append mode
            PrintWriter printWriter = new PrintWriter(new FileWriter("player_list.csv", true));
            printWriter.println(newPlayer);
            printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Failed to write to player_list.csv.");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Press enter to log in.");
        inputScanner.nextLine();
    }

    public static void winHand(String name, ArrayList<Card> cardStack) {
        cardStack.add(p1ActiveCard);
        cardStack.add(p2ActiveCard);

        System.out.println(name + " won that hand!");
        System.out.println();
        System.out.println("Press enter to continue.");
        inputScanner.nextLine();
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

        ArrayList<Card> deckList = new ArrayList<>();

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

        Collections.shuffle(deckList);
        Card[] deck = deckList.toArray(new Card[0]);

        System.out.println("Press enter to start.");
        inputScanner.nextLine();
        System.out.println("Let's begin the game!");
        inputScanner.nextLine();

        int handNum = 1;

        for (int i = 0; i < deck.length; i = i + 2) {
            System.out.println("Hand: " + handNum);
            p1ActiveCard = deck[i];
            p2ActiveCard = deck[i + 1];

            System.out.println(p1Name + " drew a " + p1ActiveCard.getColour() + " " + p1ActiveCard.getNumber());
            System.out.println(p2Name + " drew a " + p2ActiveCard.getColour() + " " + p2ActiveCard.getNumber());
            System.out.println();
            System.out.println("Press enter to continue.");
            inputScanner.nextLine();

            compare(p1ActiveCard, p2ActiveCard);

            handNum++;
        }

        System.out.println("All cards have been drawn!");
        System.out.println("The winner is...");
        inputScanner.nextLine();

        String winner;
        int winNum;
        ArrayList<Card> winCards;

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

        for (Card card : winCards) {
            System.out.println(card.displayName());
        }

        System.out.println();

        try {
            // True enables append mode
            PrintWriter printWriter = new PrintWriter(new FileWriter("scores.csv", true));
            printWriter.println(winScore);
            printWriter.close();
        }
        catch (IOException e) {
            System.out.println("Failed to write to player_list.csv.");
            e.printStackTrace();
        }

        ArrayList<Score> scoresList = new ArrayList<>();

        try {
            File scoreFile = new File("scores.csv");
            Scanner scoreScanner = new Scanner(scoreFile);

            while (scoreScanner.hasNextLine()) {
                String line = scoreScanner.nextLine();
                scoresList.add(new Score(line.split(",")[0], Integer.parseInt(line.split(",")[1])));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("scores.csv not found.");
            e.printStackTrace();
        }

        // Sort scoresList by number
        scoresList.sort(Comparator.comparing(Score::getNumber));
        Collections.reverse(scoresList);
        Score[] scores = scoresList.toArray(new Score[0]);

        System.out.println("These are the high scores:");
        inputScanner.nextLine();

        if (scores.length >= 5) {
            for (int i = 0; i < 5; i++) {
                System.out.println(scores[i].displayName());
            }
        } else {
            System.out.println("There are not enough scores to display.");
        }

        System.out.println();
        System.out.println("Press enter to finish.");
        inputScanner.nextLine();
        System.out.println("Thank you, " + p1Name + " and " + p2Name + ", for playing The Card Game!");
        inputScanner.nextLine();
    }

}
