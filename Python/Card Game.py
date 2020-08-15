import random

# --- Welcome and rules
print()
print("Welcome to The Card Game!")
print("In this game, each player draws a card, the cards are compared and the winner takes both cards.")
print()
input("Press enter to continue.")
print()

# --- Authentication system

# Gets txt file of player details as a list as ["name, password", "name, password"...]
with open("player_list.csv") as f:
    player_list = f.read().splitlines()


# - Define an authenticator


def auth(name, password):
    """Authorise player names and passwords."""
    # Formats player details correctly
    details = name + ", " + password
    match = 0

    # Searches player_list for details. If found, make match = 1
    for x in player_list:
        if x == details:
            match = 1

    # If no match was found
    if match != 1:
        print("Sorry, " + name + ", your username or password was incorrect.")
        input("Press enter to exit.")
        quit()

    print()
    print("Welcome " + name + "!")
    print()


# - Authenticate players
p1Name = input("Player 1 please enter your name: ")
p1Pass = input("And your password: ")

auth(p1Name, p1Pass)

p2Name = input("Player 2 please enter your name: ")
p2Pass = input("And your password: ")

# Check that player 2 is a different person
if p2Name == p1Name:
    print("Sorry, " + p2Name + ", but that's the same account as player 1.")
    input("Press enter to exit.")
    quit()

auth(p2Name, p2Pass)


# --- The game

# Get deck as a list as ["colour number", ...]
with open("deck.txt") as f:
    deck = f.read().splitlines()

random.shuffle(deck)

colourDict = {
    'Red Black': 'Red',
    'Black Red': 'Red',
    'Yellow Red': 'Yellow',
    'Red Yellow': 'Yellow',
    'Black Yellow': 'Black',
    'Yellow Black': 'Black'
}


def win(card_list, player):
    """Append cards to winner's stack and report win."""
    # Appends both cards to the winner's card stack
    card_list.append(p1ActiveCard)
    card_list.append(p2ActiveCard)
    print(player + " won that hand!")
    print()
    input("Press enter to continue.")
    print()


def colour_compare(colour1, colour2):
    """Compare colours and declare winner."""
    colour = colour1 + " " + colour2
    # Compares concatenated colour string with dictionary to get winning colour
    colour_win = colourDict.get(colour)
    if colour_win == colour1:
        win(p1_cards, p1Name)
    else:
        win(p2_cards, p2Name)


# Initialises player's card stacks as empty
p1_cards = []
p2_cards = []

input("Let's begin the game!")
print()

# Loop until deck is empty
while len(deck) > 0:
    # Both players take the top card
    p1ActiveCard = deck[0]
    del deck[0]
    p2ActiveCard = deck[0]
    del deck[0]

    print(p1Name + " drew a " + p1ActiveCard)
    print(p2Name + " drew a " + p2ActiveCard)
    input("Press enter to continue.")
    print()

    p1Colour = p1ActiveCard.split(" ")[0]
    p2Colour = p2ActiveCard.split(" ")[0]

    # If colours are the same, largest number wins
    if p1Colour == p2Colour:
        p1Number = int(p1ActiveCard.split(" ")[1])
        p2Number = int(p2ActiveCard.split(" ")[1])

        if p1Number > p2Number:
            win(p1_cards, p1Name)
        else:
            win(p2_cards, p2Name)

    # If colours are different, call function
    else:
        colour_compare(p1Colour, p2Colour)

print("All cards have been drawn!")
input("The winner is...")
print()

if len(p1_cards) > len(p2_cards):
    winner = p1Name
    winNum = len(p1_cards)
    win_cards = p1_cards
else:
    winner = p2Name
    winNum = len(p2_cards)
    win_cards = p2_cards

print(winner + "! With " + str(winNum) + " cards!")
print()

input("They had these cards:")
print()

for i in win_cards:
    print(i)

print()

# Append score & player to scores.txt
with open("scores.txt", "a") as f:
    f.write(str(winNum) + " " + winner + "\n")

# Get scores.txt as list called scores_all[] in the form ["score name", ...]
with open("scores.txt") as f:
    scores_all = f.read().splitlines()

# Python will just sort the scores for me. Thanks guys!
scores_high = sorted(scores_all, reverse=True)

input("These were the high scores:")
print()
for i in range(5):
    print(scores_high[i])

print()
print("Thank you, " + p1Name + " and " + p2Name + ", for playing The Card Game!")
input("Goodbye!")
