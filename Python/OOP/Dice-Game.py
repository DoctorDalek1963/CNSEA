from CSNEA_Shared import *
from random import random


def dice_roll(name: str, score: int) -> int:
    """Roll 2 dice and work out the resultant total score and return it to overwrite the old score."""
    # Generate 2 random numbers in the interval (1,6)
    die1 = int(random() * 6 + 1)
    die2 = int(random() * 6 + 1)
    both = die1 + die2

    # Determine player score based on dice rolls
    if die1 == die2:
        print(f"{name} rolled a double! ({die1})")
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

    print()

    return score


print("Welcome to The Dice Game!")
print("Each player rolls a dice and the winner is decided by a set of rules.")
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

# ===== The Game

p1Score = p2Score = 0

# Roll 5 dice and calculate all scores
for _ in range(5):
    p1Score = dice_roll(p1Name, p1Score)
    p2Score = dice_roll(p2Name, p2Score)
    print(f"{p1Name}'s score is {p1Score} and {p2Name}'s score is {p2Score}")
    input("Press enter to continue")
    print()

# If tie, roll an extra die until the tie is broken
while p1Score == p2Score:
    print("It's a tie! Let's roll another die to determine the winner!")
    p1Die = int(random() * 6 + 1)
    p2Die = int(random() * 6 + 1)
    print(f"{p1Name} rolled a {p1Die} and {p2Name} rolled a {p2Die}")
    print("That means...")

# Decide winner
if p1Score > p2Score:
    winner = p1Name
    winNum = p1Score
else:
    winner = p2Name
    winNum = p2Score

print(f"The winner is {winner}!")

with open("dice_game_scores.csv", "a") as f:
    f.write(f"{winner},{winNum}\n")

# Create list of all scores as Score objects
with open("dice_game_scores.csv") as f:
    scoresList = [Score(line.split(",")[0], int(line.split(",")[1])) for line in f.read().splitlines()]

scoresList.sort(key=lambda x: x.number, reverse=True)

input("These are the high scores:")
print()

for i in range(5):
    try:
        print(scoresList[i])
    except IndexError:
        break  # Stops loop if IndexError occurs

print()
input("Press enter to finish")
print()
print(f"Thank you, {p1Name} and {p2Name}, for playing The Dice Game!")
input("Goodbye!")
