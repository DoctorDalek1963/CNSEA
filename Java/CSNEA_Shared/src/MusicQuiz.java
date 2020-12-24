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

    // TODO: Make Array of Song objects and draw random indices

    public static void main(String[] args) {
        // GAME HERE
    }

}
