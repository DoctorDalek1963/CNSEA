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
        String[] letterList = songName.split(",");
        return "AAA"; // TODO: FIX THIS
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
