Java implementation of a basic GUI for the risk game 

version 3 of the Game 
In this version we added the fortify function
ATTENTION: Fortify is enforced on the user to use it.
Also we added AI players and allowed the user to choose which players to be the AI 
We need to show the players which Territory they own on the map
Instead We changed the colours of the buttons to match which players own which territories. 
THE FORTIFY IS NOT ENTIRELY FIGURED OUT YET SO ITS ONLY BETWEEN ADJACENT TERRITORIES 


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
Started implementing AI functionalities for this class. now AI works and attacks, defends and passes to next turn

Board.java
is the class that initializes the board structure and assigns territories to continents along with initializing territories
and initializing the continents. Moreover it also initializes the deck of cards to be used in the 
game.Added methods for shuffling/randomizing the territory ownerships at the start of game. instead of hardcoding assignment in the class now it is all random
depending on the number of players playing

gamePlayModel.java
A class that has to operate the game on the board initialize players amount and allow players to take turn runs the game shows the winner, used by JFrame 
following MVC pattern for implementing. implemented a version of Fortify with out an effective pathfinding algo-. but FORTIFY WORKS BETWEEN ADJACENT TERRITORIES ONLT FOR THE TERRITORIES
THAT THE PLAYER OWNS


GameplayModelTest.java
GamePlayModelTest class has the unit tests for the gamePlayModel.java class and they all run wuth no failures. Additional testing could be added but time
is limited Added additional tests for the AI players

GamePlayEvent.java
handlese the attack outcome movements of troops, deployment of troops and helps to change player turns. 

GamePlayController.java
usede to control phases of the game whenever a button is press it figures whether its a territory or the next button. GamePlayController use the methods in gamePlayModel to run the game 
properly implemented Fortify method and adjusted the gameplay loops.


GamePlayFrame.java
Added new buttons for player selection, replaced excessive input dialog pop-ups and added changing colours to indicate territory ownership.


