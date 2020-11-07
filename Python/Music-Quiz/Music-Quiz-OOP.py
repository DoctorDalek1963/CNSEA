# from random import choice
import random


class Score:
    def __init__(self, player_name: str, number: int):
        self.name = player_name
        self.number = number


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

# Gets file of player details as a list
with open("player_list.csv") as f:
    player_list = f.read().splitlines()


def authenticate(param_name: str, password: str):
    """Authenticate players."""
    details = param_name + "," + password
    match = False

    for x in player_list:
        if x == details:
            match = True

    if not match:
        print()
        print(f"Sorry, {param_name}, your username or password was incorrect.")
        input("Press enter to exit")
        quit()

    print()
    print(f"Welcome, {param_name}!")
    print()


def add_player():
    """Add new name and password to player_list."""
    print()
    param_name = input("Please enter the name of the new player: ")
    password = input("Please enter the password: ")

    new_data = f"{param_name},{password}\n"
    with open("player_list.csv", "a") as file:
        file.write(new_data)

    print()
    print(f"{param_name} added!")
    print()


# def get_random_song():
#     """Get a random song and return a formatted string to be guessed."""
#     chosen_song = choice(songsList)
#     return f"{chosen_song.first_letters()} by {chosen_song.artist_name} from {chosen_song.album_name}"


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
        name = p1Name
        score = p1Score
        activePlayer = 2

    elif activePlayer == 2:
        name = p2Name
        score = p2Score
        activePlayer = 1

    print(f"{name}, you will be given a song. You must guess it.")
    randomSong = random.choice(songsList)
    print(f"{randomSong.first_letters()} by {randomSong.artist_name} from {randomSong.album_name}")
    print()
    guess = input()

#
