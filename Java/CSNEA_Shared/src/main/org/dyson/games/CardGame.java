package org.dyson.games;

import java.io.*;
import java.util.*;

record Card(String colour, int number) {

	public String getColour() { return colour; }

	public int getNumber() { return number; }

	public String displayName() { return this.colour + " " + this.number; }
}

public class CardGame {

	static Player player1;
	static Player player2;

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
			// If the colours are equal, then the numbers must be different, so we don't have to worry about a bias
			if (card1.getNumber() > card2.getNumber()) {
				winHand(player1.getName(), p1Cards);
			} else {
				winHand(player2.getName(), p2Cards);
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
				winHand(player1.getName(), p1Cards);
			} else if (winColour.equals(card2.getColour())) {
				winHand(player2.getName(), p2Cards);
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

		player1 = GameMethods.authenticateReturnPlayer(1);
		player2 = GameMethods.authenticateReturnPlayer(2);

		// If same account, quit program
		if (player1.getName().equals(player2.getName())) {
			System.out.println("Sorry, " + player2.getName() + ", but that's the same account as player 1.");
			System.out.println("Press enter to exit");
			inputScanner.nextLine();
			System.exit(0);
		}

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
		for (int i = 0; i < deck.length; i += 2) {
			System.out.println("Hand: " + handNum);
			p1ActiveCard = deck[i];
			p2ActiveCard = deck[i + 1];

			System.out.println(player1.getName() + " drew a " + p1ActiveCard.displayName());
			System.out.println(player2.getName() + " drew a " + p2ActiveCard.displayName());
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
		Player winner;
		int winNum;
		ArrayList<Card> winCards;

		// Find winner from length of card stacks
		if (p1Cards.size() > p2Cards.size()) {
			winner = player1;
			winNum = p1Cards.size();
			winCards = p1Cards;
		} else {
			winner = player2;
			winNum = p2Cards.size();
			winCards = p2Cards;
		}

		winner.setScore(winNum); // Set the winner's score value to allow it to be written to a file properly

		System.out.println(winner.getName() + "! With " + winNum + " cards!");
		System.out.println();
		System.out.println("They had these cards:");
		inputScanner.nextLine();

		// Print all Cards
		for (Card card : winCards) {
			System.out.println(card.displayName());
		}

		System.out.println();

		GameMethods.writeScore("card_game_scores.csv", winner);

		GameMethods.displayHighScores("card_game_scores.csv");

		System.out.println();
		System.out.println("Press enter to finish.");
		inputScanner.nextLine();
		System.out.println("Thank you, " + player1.getName() + " and " + player2.getName() + ", for playing The Card Game!");
		inputScanner.nextLine();
	}

}
