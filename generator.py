# Create new card types for algorithm-jitsu.
# Prompts user to enter a number, and the program will populate cards.txt with the appropriate number of cards.
# Card format: ice/snow/fire/water, number between 1 and 10, and colors red blue green and yellow
import random
import os

def generator ():
    num_cards = int(input("How many cards do you want to create? "))
    cards = []
    card_types = ["ice", "snow", "fire", "water"]
    card_numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    card_colors = ["red", "blue", "green", "yellow"]
    cards = []

    for i in range(num_cards):
        cards.append(random.choice(card_types) + "," + str(random.choice(card_numbers)) + "," + random.choice(card_colors))

    with open("cards.txt", "w") as f:
        for card in cards:
            f.write(card + "\n")
    print("Cards created!")

generator()