from random import choice


class Score:
    def __init__(self, name: str, number: int):
        self.name = name
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
    songsList = [Song(line.split(",")[0], line.split(",")[1], line.split(",")[2]) for line in f.read().splitlines()]


def authenticate(name: str, password: str):
    """Authenticate players."""
    details = name + "," + password
    match = False

    for x in player_list:
        if x == details:
            match = True

    if not match:
        print()
        print(f"Sorry, {name}, your username or password was incorrect.")
        input("Press enter to exit")
        quit()

    print()
    print(f"Welcome, {name}!")
    print()


def add_player():
    """Add new name and password to player_list."""
    print()
    name = input("Please enter the name of the new player: ")
    password = input("Please enter the password: ")

    new_data = f"{name},{password}\n"
    with open("player_list.csv", "a") as file:
        file.write(new_data)

    print()
    print(f"{name} added!")
    print()


def get_random_song():
    """Get a random song and return a formatted string to be guessed."""
    chosen_song = choice(songsList)
    return f"{chosen_song.first_letters()} by {chosen_song.artist_name} from {chosen_song.album_name}"


#
