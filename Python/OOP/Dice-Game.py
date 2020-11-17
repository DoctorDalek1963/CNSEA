from CSNEA_Shared import *
from random import random


def dice_roll(name: str) -> int:
    """Roll 2 dice and work out the resultant total score and return it to overwrite the old score."""
    # Generate 2 random numbers in the interval (1,6)
    die1 = int(random() * 6 + 1)
    die2 = int(random() * 6 + 1)
    both = die1 + die2
    dice_score = 0

    # Determine player dice_score based on dice rolls
    if die1 == die2:
        print(f"{name} rolled a double! ({die1})")
        print("That means they get to roll another die!")
        double_die = int(random() * 6 + 1)
        print(f"Their extra die rolled a {double_die}!")
        dice_score = both + double_die

    elif both % 2 == 0:
        print(f"{name} rolled an even number! ({both})")
        print("That means they gain 10 points!")
        dice_score = both + 10

    elif both % 2 != 0:
        print(f"{name} rolled an odd number! ({both})")
        print("That means they lose 5 points!")
        dice_score = both - 5

    if dice_score < 0:
        dice_score = 0
        print(f"{name}'s dice_score went below 0, so it's been reset to 0.")

    print()

    return dice_score


print("Welcome to The Dice Game!")
print("Each player rolls a dice and the winner is decided by a set of rules.")
print()
addPlayerFlag = input("Press 1 to add a player. Press enter to log in.")
if addPlayerFlag == 1:
    add_player()
print()

# Authenticate players
player1, player2 = authenticate_two_players()

# ===== The Game

# Roll 5 dice and calculate all scores
for _ in range(5):
    player1.increment(dice_roll(player1.name))
    player2.increment(dice_roll(player2.name))
    print(f"{player1.name}'s score is {player1.score} and {player2.name}'s score is {player2.score}")
    input("Press enter to continue")
    print()

# If tie, roll an extra die until the tie is broken
while player1.score == player2.score:
    print("It's a tie! Let's roll another die to determine the winner!")
    p1Die = int(random() * 6 + 1)
    p2Die = int(random() * 6 + 1)
    print(f"{player1.name} rolled a {p1Die} and {player2.name} rolled a {p2Die}")
    print("That means...")

# Decide winner
if player1.score > player2.score:
    winner = player1.name
    winNum = player1.score
else:
    winner = player2.name
    winNum = player2.score

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
print(f"Thank you, {player1.name} and {player2.name}, for playing The Dice Game!")
input("Goodbye!")
