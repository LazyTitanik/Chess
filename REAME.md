# Semestral work "Chess"
The goal of the work is learning how to implement applications in Java programming language. It was decided to choose, because there is nothing to make up, so I could concentrate on the structure of the application, its ability to be read easily and observance of declared rules of implementing in this language. 

1. [[#Manual for programmers]]
	1. [[#Used technologies]]
	2. [[#Describing classes]]
	3. [[#Implemented functionality]]
3. [[#Manual for users]]
	1. [[#Starting a game]]
	2. [[#Playing]]
		1. [[#Playing against a robot on one computer]]
		2. [[#Playing via network connection]]
	3. [[#Ending]]
	4. [[#Viewing completed game]]
## Manual for programmers
### Used technologies
- The whole project is going to be implemented in Maven build tool. 
- Javafx will be used for grafic user unterface.

### Implemented functionality
- Game between two players (person vs. person on same device and via network, person vs random robot player, )
- Game rules controll
- A possibility to save and load games in standart PGN format
- A possibility to put pieces manually before a game

### Describing classes
All classes and their hierarchy are presented in the picture below. 

![[Pasted image 20220401214926.png]]

The program starts at the main method of class "Start", where it offers to choose a mode (in final version there will be a network game, one against a bot and on one computer) and either set it up or begin with default settings. 

Class Game is responsible for running the game (requesting both players for moves, communicating between them in case of draw). 
Game instance must be initialized by one of setDefault(), setYourGame() or loadGame() before running. Also it provides methods for setting a game up, loading and saving it. 

Abstract class Player is mainly used to process actual moves of a player. The way it is procesed depends on type of Player. NetworkOpponent sends data to opponent's computer and recieves them from it. Bot chooses moves randomly. ThisPlayer gets data from user's input (pressing buttons on a board).

Abstract class Piece (and its descendants) provides methods to get possible moves and stores its position and colour. Also it stores number of Pieces as static variable in order to controll manual set up, so user would not able to put 20 queens.

Classes Position ans Move are required to store data in an appropriate way, so classes could communicate without strange sequences of String, that have to be decoded. Position stores char and int. Setting them up is possible with setPosition() and setMove() methods. setPosition() controlls, whether the values make sense and returns false in case of wrong values ( 'i' and 9 are reserved for requesting draw and giving up, because method getMove() of Player returns Move).

Enum type Colour stores colours BLACK and WHITE. (No racism, it is just a rule of the game).

## Manual for users
Welcome to the Chess game! We are glad to know that You have chosen our application and promise You will only have the best experience.

Here is tutorial how to launch your game, set it up and have a good time.

### Starting a game 
After You launch the application there are several modes available: playing against a real human via the Internet connection, against a robot, playing on one computer and [[#Viewing completed game]]. 

#### Playing against a robot/ on one computer.
After You choose one of these options, the app will offer You to load an existing game, setup your own or load new game with default settings. 
To load an existing one You have to choose a file with a game in pgn format. In "Set up" option you can put pieces as you want, but number of them is limited according to the rules (8 pawns, 2 rooks, 2 knights, 2 bishops, 1 queen, 1 king is mandatory, you can choose one more piece for the price of a pawn for every piece, except king). After that you have to set time up (Default value is 30 minutes for each player).

### Playing 
Congratulations! You have set the game up and now you can have fun. To take a move, press the piece you would like to move, so you can see every every possible move by the piece. After that choose a field, where you want to move the piece. As soon as it is done, your move is over and your timer is stopped, while the timer of your opponent is continued. The game continues untill one of the players has chechmate, gives up, has no more time for move or draw is claimed. 

At the right side You can see three buttons with names "Pause", "Draw" and "Give up". 
- After "Draw" is pressed, the opponent recieves a suggestion of a draw. In case they accespt it, the draw is claimed. (In case of a game against a robot, it will be always accepted).
- After "Give up" is pressed, you will recieve a confirmation of your action (It is made in order to prevent a missclicking) and opponent wins. 

In both situations the game ends.

- After "Pause" is pressed, your opponent recieves a suggestion of a pause. In case they accept it, current game ends and You have to save it in order to load it next time. (In case of a game against a robot, it will be always accepted).

### Ending
After a game is ended, you can choose to save it on your computer or not. 

### Viewing completed game
In this mode you can load a game that was finished and watch it from very begining. At the right side are two buttons available: "Next move" and "Previous move", which shows relevant information.