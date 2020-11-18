"""This is merely a collection of a class and two methods which are
used by all OOP CSNEA Projects. This organisation makes it easier
to make changes across all projects."""


class Score:
    def __init__(self, name: str, number: int):
        self.name = name
        self.number = number

    def __repr__(self):
        return self.name + " - " + str(self.number)


class Player:
    def __init__(self, name: str):
        self.name = name
        self.score = 0

    def increment_score(self, value=1):
        self.score += value


# Gets file of player details as a list for authenticate()
with open("player_list.csv") as f:
    player_list = f.read().splitlines()


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
    """Add new name and password to player_list.csv."""
    print()
    name = input("Please enter the name of the new player: ")
    password = input("Please enter the password: ")

    new_data = f"{name},{password}\n"
    with open("player_list.csv", "a") as file:
        file.write(new_data)

    print()
    print(f"{name} added!")
    print()


def authenticate_two_players() -> (Player, Player):
    p1_name = input("Player 1 please enter your name: ")
    p1_pass = input("Player 1 please enter your password: ")

    authenticate(p1_name, p1_pass)
    p1 = Player(p1_name)

    p2_name = input("Player 2 please enter your name: ")
    p2_pass = input("Player 2 please enter your password: ")

    # Check that player 2 is a different person
    if p2_name == p1.name:
        print(f"Sorry, {p2_name}, but that's the same account as player 1.")
        input("Press enter to exit")
        quit()

    authenticate(p2_name, p2_pass)
    p2 = Player(p2_name)

    return p1, p2
