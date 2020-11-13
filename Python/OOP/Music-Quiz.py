from CSNEA_Shared import *
import random


class Song:
    def __init__(self, song_name: str, artist_name: str, album_name: str):
        self.song_name = song_name
        self.artist_name = artist_name
        self.album_name = album_name

    def first_letters(self):
        """Get first letters of every word in song_name and capitalise them."""
        # Split by " " to get list of words
        # Then take the 0th index of each word string and capitalise it
        return "".join(word[0].upper() for word in self.song_name.split())


# Create list of all songs as Song objects
with open("songs.csv") as f:
    songsList = [Song(line.split(",")[0], line.split(",")[1],
                      line.split(",")[2]) for line in f.read().splitlines()]


# def get_random_song():
#     """Get a random song and return a formatted string to be guessed."""
#     chosen_song = choice(songsList)
#     return f"{chosen_song.first_letters()} by {chosen_song.artist_name} from {chosen_song.album_name}"


def play_round(player: str, player_score: int) -> int:
    """Play a round of The Music Game."""
    print(f"{player}, you will be given a song. You must guess it.")
    random_song = random.choice(songsList)
    print(f"{random_song.first_letters()} by {random_song.artist_name} from {random_song.album_name}")
    print()
    guess = input()

    return player_score


print("Welcome to The Music Quiz!")
print("There are 2 players. They take it in to turns to guess a song from a given hint.")
print("The hint is the first letter of every word in the name of the song, the artist, and the album.")
print()
addPlayerFlag = input("Press 1 to add a player. Press enter to log in.")
if addPlayerFlag == 1:
    add_player()
print()

# Authenticate players
p1Name = input("Player 1 please enter your name: ")
p1Pass = input("And your password: ")

authenticate(p1Name, p1Pass)

p2Name = input("Player 2 please enter your name: ")
p2Pass = input("And your password: ")

# Check that player 2 is a different person
if p2Name == p1Name:
    print(f"Sorry, {p2Name}, but that's the same account as player 1.")
    input("Press enter to exit")
    quit()

authenticate(p2Name, p2Pass)

endFlag = False
activePlayer = 1
p1Score = p2Score = score = 0
name = ""

while not endFlag:

    if activePlayer == 1:
        p1Score = play_round(p1Name, p1Score)
        activePlayer = 2

    elif activePlayer == 2:
        p2Score = play_round(p2Name, p2Score)
        activePlayer = 1


#
