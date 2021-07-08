package org.dyson.games;

import java.io.*;
import java.util.*;

public class GameMethods {

	// Scanner object for reading input
	static Scanner inputScanner = new Scanner(System.in);

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
		} catch (FileNotFoundException e) {
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

	public static Player authenticateReturnPlayer(int playerNum) {
		System.out.println("Player " + playerNum + ", please enter your name:");
		String name = inputScanner.nextLine();
		System.out.println("Player " + playerNum + ", please enter your password:");
		String password = inputScanner.nextLine();

		authenticate(name, password);

		return new Player(name);
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
		} catch (IOException e) {
			System.out.println("Failed to write to player_list.csv.");
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Press enter to log in.");
		inputScanner.nextLine();
	}

	public static void writeScore(String scoreFileName, Player player) {
		try {
			// True enables append mode
			PrintWriter printWriter = new PrintWriter(new FileWriter(scoreFileName, true));
			printWriter.println(player.getName() + "," + player.getScore());
			printWriter.close();
		}
		catch (IOException e) {
			System.out.println("Failed to write to " + scoreFileName + ".");
			e.printStackTrace();
		}
	}

	public static void displayHighScores(String scoreFileName) {
		// Create scoresList as ArrayList to allow for sorting later
		ArrayList<Score> scoresList = new ArrayList<>();

		// Read scores from file into scoresList
		try {
			File scoreFile = new File(scoreFileName);
			Scanner scoreScanner = new Scanner(scoreFile);

			while (scoreScanner.hasNextLine()) {
				String line = scoreScanner.nextLine();
				scoresList.add(new Score(line.split(",")[0], Integer.parseInt(line.split(",")[1])));
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(scoreFileName + " not found.");
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
	}
}
