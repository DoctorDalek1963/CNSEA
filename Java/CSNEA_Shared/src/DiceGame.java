import java.io.*;
import java.util.*;

public class DiceGame {

    static String p1Name;
    static String p2Name;

    static int p1Score = 0;
    static int p2Score = 0;

    // Scanner object for reading input
    static Scanner inputScanner = new Scanner(System.in);
    // Random object for generating random integers within bounds
    static Random random = new Random();


    public static int diceRoll(String name, int score) {
        // Generate 2 random numbers from 1-6
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        int both = die1 + die2;

        // Check state of dice to determine score
        if (die1 == die2) {
            System.out.println(name + " rolled a double! (" + die1 + ")");
            System.out.println("That means they get to roll an extra die!");
            int doubleDie = random.nextInt(6) + 1;
            System.out.println("Their extra die rolled a " + doubleDie + "!");
            score += both + doubleDie;

        } else if (both % 2 == 0) {
            System.out.println(name + " rolled an even number ! (" + both + ")");
            System.out.println("That means they gain 10 points!");
            score += both + 10;

        } else {
            System.out.println(name + " rolled an odd number ! (" + both + ")");
            System.out.println("That means they lose 5 points!");
            score += both - 5;
        }

        // Reset score if necessary
        if (score < 0) {
            System.out.println(name + "'s score went below 0, so it's been reset to 0.");
            score = 0;
        }

        System.out.println();
        return score;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to The Dice Game!");
        System.out.println("Each player rolls a dice and the winner is decided by a set of rules.");
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

        // Do 5 rounds of dice rolling
        for (int i = 0; i < 5; i++) {
            p1Score = diceRoll(p1Name, p1Score);
            p2Score = diceRoll(p2Name, p2Score);
            System.out.println(p1Name + "'s score is " + p1Score + " and " + p2Name + "'s score is " + p2Score);
            System.out.println("Press enter to continue.");
            inputScanner.nextLine();
            System.out.println();
        }

        // Break ties
        while (p1Score == p2Score) {
            System.out.println("It's a tie! Let's roll; another die to determine the winner!");
            p1Score += random.nextInt(6) + 1;
            p2Score += random.nextInt(6) + 1;
            System.out.println(p1Name + "'s score is " + p1Score + " and " + p2Name + "'s score is " + p2Score);
            System.out.println("That means...");
        }

        String winner;
        int winNum;

        // Decide winner
        if (p1Score > p2Score) {
            winner = p1Name;
            winNum = p1Score;
        } else {
            winner = p2Name;
            winNum = p2Score;
        }

        System.out.println("The winner is " + winner + "!");

        String winScore = winner + "," + winNum;

        GameMethods.writeScores("dice_game_scores.csv", winScore);

        GameMethods.displayHighScores("dice_game_scores.csv");

        System.out.println();
        System.out.println("Press enter to finish.");
        inputScanner.nextLine();
        System.out.println("Thank you, " + p1Name + " and " + p2Name + ", for playing The Dice Game!");
        inputScanner.nextLine();
    }
}
