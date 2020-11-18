from CSNEA_Shared import *
import random

failFlag = False


class Song:
    def __init__(self, song_name: str, artist_name: str, album_name: str):
        self.song_name = song_name
        self.artist_name = artist_name
        self.album_name = album_name

    def __first_letters(self):
        """Get first letters of every word in song_name and capitalise them."""
        # Split by " " to get list of words
        # Then take the 0th index of each word string and capitalise it
        return "".join(word[0].upper() for word in self.song_name.split(" "))

    def display_first_letters(self):
        """Return a string to allow the Song to be guessed."""
        return self.__first_letters() + " by " + self.artist_name + " from " + self.album_name


# Create list of all songs as Song objects
with open("songs.csv") as f:
    songsList = [Song(line.split(",")[0], line.split(",")[1], line.split(",")[2]) for line in f.read().splitlines()]


def guess_song(song: Song):
    """Let user guess Song and return their guess."""
    print(song.display_first_letters())
    print()
    return input()


def play_round(player: Player):
    """Play a round of The Music Game."""
    global failFlag

    random_song = random.choice(songsList)

    guessed_song = guess_song(random_song)
    print()

    if guessed_song == random_song.song_name:
        print("Correct! That's 3 points!")
        player.increment_score(3)
        return

    # Only if failed first guess
    print("Incorrect. Try again.")

    guessed_song_2 = guess_song(random_song)
    print()

    if guessed_song_2 == random_song.song_name:
        print("Correct! That's 1 point!")
        player.increment_score()
        return

    # Only if failed both
    print(f"Sorry, {player.name}, but you failed to guess the song.")
    print("It was " + random_song.song_name)
    print()
    failFlag = True


print("Welcome to The Music Quiz!")
print("You must guess a song from a given hint.")
print("The hint is the first letter of every word in the name of the song, the artist, and the album.")
print()
addPlayerFlag = input("Press 1 to add a player. Press enter to log in.")
if addPlayerFlag == 1:
    add_player()
print()

# Authenticate players
name = input("Please enter your name: ")
password = input("Please enter your password: ")
authenticate(name, password)

user = Player(name)

print("You will be given a song. You must guess it.")
print("Just give the title of the song.")
print()

while not failFlag:
    play_round(user)

print("That's the end of the game!")
print(f"You got {user.score} points!")
print()

write_score("music_quiz_scores.csv", user.name + "," + str(user.score))

display_high_scores("music_quiz_scores.csv")

print()
input("Press enter to finish")
print()
print(f"Thank you, {user.name}, for playing The Music Quiz!")
input("Goodbye!")
