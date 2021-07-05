package org.dyson.games;

import java.util.Scanner;

public class SelectGame {
	public static void main(String[] args) {
		Scanner inputScanner = new Scanner(System.in);
		System.out.println("Please select a game:");
		System.out.println("1) Music Quiz");
		System.out.println("2) Dice Game");
		System.out.println("3) Card Game");

		switch (inputScanner.nextLine()) {
			case "1" -> MusicQuiz.main(args);
			case "2" -> DiceGame.main(args);
			case "3" -> CardGame.main(args);
			default -> System.out.println("Unrecognised choice. Please enter '1', '2', or '3'.");
		}
	}
}
