# Risk_main

Java implementation of a basic GUI for the risk game 

version 2  of the Game 

Note to User:
•	The GUI was tested in an environment where the size of the monitor was atleast 24”. On a smaller screen, such as a laptop, the user won’t be able to see the whole board and play the game. Thus, the testing should be done on a larger monitor if you have access to one. We are currently working on allowing our game to be adjustable for all devices.

Troops.java 
is a super class for different types of armies and for the wildCard 
so we can assign different armies or wildCard to cards clasa

artillery.java 
is a type of troop thats worth 10 infantry 

Cavalry.java
is a type of troop thats worth 5 infantry

infantry.java 
is the basic unit type of troop worth 1 

wildCard.java 
is a class extending Troop.java to be able to create 
Cards of troop type wildCard 

Card.java
is a class used to represent the cards to be availabe in the deck 
that will be used when creating the board there are 42 normal cards and 2 wildCards

Deck.java
is a class used to represent the cards in the deck and the
cards out of the deck used (in players hand) while the game is running 

Die.java
is to represent one - six sided die 

twoDice.java 
is to represent 2 dice when attacker player decides to attack only using 2 or 
when defender has 2 troops to defend 

threeDice.java
is to represent 3 dice whenw attacker player decides to attack using 3 dice 

Continent.java
represent each continent to be available on the map there is a list to hold
the territories available in the continent

Territory.java
represents each territorty and the amount of troops on it and who is 
the owner player along with a list of all adjacent territiories 

Hand.java
hand class represent each player's hand and a list of cards available in tthe
player's hand in addition to a boolean which is set true if player can tradeinmatching cards

Player.java
represents each player playing the game and their Hand of cars and a list of the territories owned and 
continents a player is controlling to manage the amount of bonus troops a player is to get on a new turn 

Board.java
is the class that initializes the board structure and assigns territories to continents along with initializing territories
and initializing the continents. Moreover it also initializes the deck of cards to be used in the 
game.

gamePlayModel.java
A class that has to operate the game on the board initialize players amount and allow players to take turn runs the game shows the winner, used by JFrame 
following MVC pattern for implementing 

GameplayModelTest.java
GamePlayModelTest class has the unit tests for the gamePlayModel.java class and they all run wuth no failures. Additional testing could be added but time
is limited 

GamePlayEvent.java
handlese the attack outcome movements of troops, deployment of troops and helps to change player turns. 

GamePlayController.java
usede to control phases of the game whenever a button is press it figures whether its a territory or the next button. GamePlayController use the methods in gamePlayModel to run the game 
properly

GamePlayView.java

GamePlayFrame.java
creaetes the visual aspects of the GUI creates all the territories and has a display field to tell users what interactions they should do and updates them on the game status each 
territory is created of a button 




Improvements
- We need to show the players which Territory they own on the map
- We need to change the colour of the buttons
- We need to make adjustments to the size of the window for the game to be more dynamic for all devices


