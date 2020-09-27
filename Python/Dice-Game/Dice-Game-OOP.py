from random import random


class Score:
    def __init__(self, name: str, number: int):
        self.name = name
        self.number = number


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


def dice_roll(name: str, score: int) -> int:
    """Roll 2 dice and work out the resultant total score and return it to overwrite old score."""
    die1 = int(random() * 6 + 1)
    die2 = int(random() * 6 + 1)
    both = die1 + die2

    if die1 == die2:
        print(f"{name} rolled a double {die1}!")
        print("That means they get to roll another die!")
        double_die = int(random() * 6 + 1)
        print(f"Their extra die rolled a {double_die}!")
        score += both + double_die

    elif both % 2 == 0:
        print(f"{name} rolled an even number! ({both})")
        print("That means they gain 10 points!")
        score += both + 10

    elif both % 2 != 0:
        print(f"{name} rolled an odd number! ({both})")
        print("That means they lose 5 points!")
        score += both - 5

    if score < 0:
        score = 0
        print(f"{name}'s score went below 0, so it's been reset to 0.")

    return score


print("Welcome to The Dice Game!")
print("Each player rolls a dice and the winner is decided by a set of rules.")

#
