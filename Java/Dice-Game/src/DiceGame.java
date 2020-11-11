import java.io.*;
import java.util.*;

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

public class DiceGame {

    static String p1Name;
    static String p2Name;

    static int p1Score = 0;
    static int p2Score = 0;

    // Scanner object for reading input
    static Scanner inputScanner = new Scanner(System.in);
    // Random object for generating random integers within bounds
    static Random random = new Random();

    public static void authenticate(String name, String password) {
        String playerDetails = name + "," + password;
        boolean match = false;

        // Search through player_list.csv to find playerDetails
        try {
            File playerList = new File("player_list.csv");
            Scanner playerListReader = new Scanner(playerList);

            while (playerListReader.hasNextLine()) {
                String fileDetails = playerListReader.nextLine();
                if (fileDetails.equals(playerDetails)) {
                    match = true;
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("player_list.csv not found.");
            e.printStackTrace();
        }

        // If no match was found, quit program
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

        // Add newPlayer to player_list.csv
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

        // If same account, quit program
        if (p1Name.equals(p2Name)) {
            System.out.println("Sorry, " + p2Name + ", but that's the same account as player 1.");
            System.out.println("Press enter to exit");
            inputScanner.nextLine();
            System.exit(0);
        }

        authenticate(p2Name, p2Pass);

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

        // Write score to file
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

        // Create scoresList as ArrayList to allow for sorting later
        ArrayList<Score> scoresList = new ArrayList<>();

        // Read scores from file into scoresList
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
        Score[] scoresArray = scoresList.toArray(new Score[0]);

        System.out.println("These are the high scores:");
        inputScanner.nextLine();

        // Print top 5 high scores (or the top n if there's less than 5 available)
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(scoresArray[i].displayName());
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        System.out.println();
        System.out.println("Press enter to finish.");
        inputScanner.nextLine();
        System.out.println("Thank you, " + p1Name + " and " + p2Name + ", for playing The Dice Game!");
        inputScanner.nextLine();
    }
}
