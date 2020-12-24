import java.util.*;

public class DiceGame {

//    static int p1Score = 0;
//    static int p2Score = 0;

    static Player p1;
    static Player p2;

    // Scanner object for reading input
    static Scanner inputScanner = new Scanner(System.in);
    // Random object for generating random integers within bounds
    static Random random = new Random();


    public static int diceRoll(String name) {
        // Generate 2 random numbers from 1-6
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        int both = die1 + die2;
        int score;

        // Check state of dice to determine score
        if (die1 == die2) {
            System.out.println(name + " rolled a double! (" + die1 + ")");
            System.out.println("That means they get to roll an extra die!");
            int doubleDie = random.nextInt(6) + 1;
            System.out.println("Their extra die rolled a " + doubleDie + "!");
            score = both + doubleDie;

        } else if (both % 2 == 0) {
            System.out.println(name + " rolled an even number ! (" + both + ")");
            System.out.println("That means they gain 10 points!");
            score = both + 10;

        } else {
            System.out.println(name + " rolled an odd number ! (" + both + ")");
            System.out.println("That means they lose 5 points!");
            score = both - 5;
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

        p1 = GameMethods.authenticateReturnPlayer(1);
        p2 = GameMethods.authenticateReturnPlayer(2);

        // If same account, quit program
        if (p1.getName().equals(p2.getName())) {
            System.out.println("Sorry, " + p2.getName() + ", but that's the same account as player 1.");
            System.out.println("Press enter to exit");
            inputScanner.nextLine();
            System.exit(0);
        }

        // Do 5 rounds of dice rolling
        for (int i = 0; i < 5; i++) {
            p1.incrementScore(diceRoll(p1.getName()));
            p2.incrementScore(diceRoll(p2.getName()));
            System.out.println(p1.getName() + "'s score is " + p1.getScore() + " and " + p2.getName() + "'s score is " + p2.getScore());
            System.out.println("Press enter to continue.");
            inputScanner.nextLine();
            System.out.println();
        }

        // Break ties
        while (p1.getScore() == p2.getScore()) {
            System.out.println("It's a tie! Let's roll; another die to determine the winner!");
            p1.incrementScore(random.nextInt(6) + 1);
            p2.incrementScore(random.nextInt(6) + 1);
            System.out.println(p1.getName() + "'s score is " + p1.getScore() + " and " + p2.getName() + "'s score is " + p2.getScore());
            System.out.println("That means...");
        }

        String winner;
        int winNum;

        // Decide winner
        if (p1.getScore() > p2.getScore()) {
            winner = p1.getName();
            winNum = p1.getScore();
        } else {
            winner = p2.getName();
            winNum = p2.getScore();
        }

        System.out.println("The winner is " + winner + "!");

        String winScore = winner + "," + winNum;

        GameMethods.writeScore("dice_game_scores.csv", winScore);

        GameMethods.displayHighScores("dice_game_scores.csv");

        System.out.println();
        System.out.println("Press enter to finish.");
        inputScanner.nextLine();
        System.out.println("Thank you, " + p1.getName() + " and " + p2.getName() + ", for playing The Dice Game!");
        inputScanner.nextLine();
    }
}
