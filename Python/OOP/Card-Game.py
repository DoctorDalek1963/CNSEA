from CSNEA_Shared import *
import random


class Card:
    def __init__(self, colour: str, number: int):
        self.colour = colour
        self.number = number

    def __repr__(self):
        return self.colour + " " + str(self.number)


def win_hand(player_name: str, card_list: list):
    """Append cards to winner's stack and report win."""
    # Appends both cards to the winner's card stack
    card_list.append(player1.active_card)
    card_list.append(p2ActiveCard)
    print(f"{player_name} won that hand!")
    print()
    input("Press enter to continue")
    print()


colourDict = {
    'Red,Black' or 'Black,Red': 'Red',
    'Yellow,Red' or 'Red,Yellow': 'Yellow',
    'Black,Yellow' or 'Yellow,Black': 'Black'
}


def compare(card1: Card, card2: Card):
    """Compare cards to find winner of hand."""
    if card1.colour == card2.colour:
        if card1.number > card2.number:
            win_hand(player1.name, p1Cards)
        else:
            win_hand(player2.name, p2Cards)

        return

    colour = card1.colour + "," + card2.colour
    win_colour = colourDict.get(colour)
    if win_colour == card1.colour:
        win_hand(player1.name, p1Cards)
    else:
        win_hand(player2.name, p2Cards)


# ===== Welcome & Rules

print("Welcome to The Card Game!")
print("In this game, each player draws a card, the cards are compared and the winner takes both cards.")
print("In total, 15 hands are drawn.")
print("The player with the most cards at the end wins.")
print()
addPlayerFlag = input("Press 1 to add a new player. Press enter to log in. ")
if addPlayerFlag == 1:
    add_player()
print()

# Authenticate players
player1, player2 = authenticate_two_players()

# ===== The Game

# Initialises player's card stacks as empty
p1Cards = []
p2Cards = []

input("Press enter to start")
print("Let's begin the game!")
print()

# Create list of all cards as Card objects
with open("deck.csv") as f:
    deckList = [Card(line.split(",")[0], int(line.split(",")[1])) for line in f.read().splitlines()]

random.shuffle(deckList)

handNum = 1

while len(deckList) > 0:
    print(f"Hand: {handNum}")
    player1.active_card = deckList[0]
    del deckList[0]
    p2ActiveCard = deckList[0]
    del deckList[0]

    print(f"{player1.name} drew a {player1.active_card}")
    print(f"{player2.name} drew a {p2ActiveCard}")
    print()
    input("Press enter to continue")
    print()

    compare(player1.active_card, p2ActiveCard)

    handNum = handNum + 1

print("All cards have been drawn!")
input("The winner is...")
print()

if len(p1Cards) > len(p2Cards):
    winner = player1.name
    winNum = len(p1Cards)
    win_cards = p1Cards
else:
    winner = player2.name
    winNum = len(p2Cards)
    win_cards = p2Cards

print(f"{winner}! With {winNum} cards!")
print()
input("They had these cards:")
print()

for card in win_cards:
    print(card)

print()

write_score("card_game_scores.csv", winner + "," + str(winNum))

display_high_scores("card_game_scores.csv")

print()
input("Press enter to finish")
print()
print(f"Thank you, {player1.name} and {player2.name}, for playing The Card Game!")
input("Goodbye!")
