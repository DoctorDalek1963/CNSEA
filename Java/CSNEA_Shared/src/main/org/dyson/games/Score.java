package org.dyson.games;

public record Score(String name, int number) {

	public int getNumber() { return number; }

	public String displayName() { return name + " - " + number; }
}
