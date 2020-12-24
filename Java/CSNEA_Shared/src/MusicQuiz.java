import java.io.*;
import java.util.*;

class Song {

    private final String songName;
    private final String artistName;
    private final String albumName;

    Song(String song, String artist, String album) {
        songName = song;
        artistName = artist;
        albumName = album;
    }

    public String getSongName() {
        return songName;
    }

    private String firstLetters() {
        String[] wordList = songName.split(" ");
        String uppers = "";
        String letter;

        // Loop over each word in songName, get the first letter, make it uppercase, and add to a string
        for (String word : wordList) {
            letter = word.substring(0, 1).toUpperCase();
            uppers = uppers.concat(letter);
        }

        return uppers;
    }

    public String displayFirstLetters() {
        return firstLetters() + " by " + artistName + " from " + albumName;
    }
}

public class MusicQuiz {

    // Scanner object for reading input
    static Scanner inputScanner = new Scanner(System.in);
    // Random object for generating random integers within bounds
    static Random random = new Random();

    static boolean failFlag = false;
    static Song[] songsList = generateSongArray("songs.csv");


    public static String guessSong(Song song) {
        System.out.println();
        System.out.println(song.displayFirstLetters());
        System.out.println();
        return inputScanner.nextLine(); // Takes input and returns it
    }

    public static void playRound(Player player) {
        Song randomSong = songsList[random.nextInt(songsList.length)];

        String guessedSong = guessSong(randomSong);
        System.out.println();

        if (guessedSong.equalsIgnoreCase(randomSong.getSongName())) {
            System.out.println("That's correct! You get 3 points!");
            player.incrementScore(3);
            return;
        }

        // Only if failed first guess
        System.out.println("Incorrect. Try again.");

        String guessedSong2 = guessSong(randomSong);
        System.out.println();

        if (guessedSong2.equals(randomSong.getSongName())) {
            System.out.println("That's correct! You get 1 points!");
            player.incrementScore(1);
            return;
        }

        // Only if failed both
        System.out.println("Sorry, " + player.getName() + ", but you failed to guess the song.");
        System.out.println("It was " + randomSong.getSongName());
        System.out.println();

        failFlag = true;
    }

    public static Song[] generateSongArray(String filename) {
        ArrayList<Song> songsArrayList = new ArrayList<>();

        try {
            File songFile = new File(filename);
            Scanner songFileReader = new Scanner(songFile);

            // Loop through file and create Song object from each line
            while (songFileReader.hasNextLine()) {
                String line = songFileReader.nextLine();
                Song song = new Song(line.split(",")[0], line.split(",")[1], line.split(",")[2]);
                songsArrayList.add(song);
            }

            return songsArrayList.toArray(new Song[0]); // Cast ArrayList to array for easier use
        } catch (FileNotFoundException e) {
            System.out.println(filename + " was not found.");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to The Music Quiz!");
        System.out.println("You must guess a song from a given hint.");
        System.out.println("The hint is the first letter of every word in the name of the song, the artist, and the album.");
        System.out.println();
        System.out.println("Press 1 to add a new player. Press enter to log in.");

        // Call addPlayer() if input == "1"
        if (inputScanner.hasNextLine()) {
            if (inputScanner.nextLine().equals("1")) {
                GameMethods.addPlayer();
            }
        }

        System.out.println("Please enter your name:");
        String name = inputScanner.nextLine();
        System.out.println("Please enter your password:");
        String password = inputScanner.nextLine();

        GameMethods.authenticate(name, password);
        Player player = new Player(name);

        System.out.println("You will be given the details of a song. You must guess the title.");

        while (!failFlag) {
            playRound(player);
        }

        System.out.println("That's the end of the game!");
        System.out.println("You got " + player.getScore() + " points!");
        System.out.println();

        GameMethods.writeScore("music_quiz_scores.csv", player);

        GameMethods.displayHighScores("music_quiz_scores.csv");

        System.out.println();
        System.out.println("Press enter to finish.");
        inputScanner.nextLine();
        System.out.println("Thank you, " + player.getName() + ", for playing The Dice Game!");
        inputScanner.nextLine();
    }

}
