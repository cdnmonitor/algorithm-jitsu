# Algorithm Jitsu
This goal of this project is to create a command line implementation of the [Club Pengin Card Jitsu](https://clubpenguin.fandom.com/wiki/Card-Jitsu) card game written in Java. As children, we grew up playing ClubPenguin which heavily inspired us to recreate the game for the purpose of testing algorithms in a player vs player setting. The game is played between two clients (players) that communicate with a server using network sockets. Logs of every battle will be kept so that we can train an AI to play against the human player. The game will implement AI algorithms with varying difficulty. This project is being developed by [Michael Bishai](https://github.com/cdnmonitor),[Jack Donnely](https://github.com/LeonTheMouse), and [Max Pugh](https://github.com/mpughcs)

# Table of Contents
- [Class Structure:](#class-structure)
- [To Run](#to-run)
  - [To play against another human player](#to-play-against-another-human-player)
  - [To play against AI](#to-play-against-ai)
    - [Experimental feature for macOS users.](#experimental-feature-for-macos-users)
- [Work Division](#work-division)
- [Feature Progress](#feature-progress)
  - [Server Features](#server-features)
  - [Client Features](#client-features)
  - [Algorithm Features](#algorithm-features)
# Class Structure:
![Class Diagram](https://github.com/cdnmonitor/algorithm-jitsu/blob/main/ClassDiagram.png)  

The important classes are Server.java, Client.java, and SimpleAI.java. Server.java connects to two Clients, either of which can be a human (Client.java) or an AI(SimpleAI.java). The Server class handles all the game logic, dealing cards, getting cards from players, calculating who wins the round etc. Client.java communicates with the server to display a human player with relevent information to pick cards. SimpleAI.java allows a user to choose between 3 levels of AI difficulty, and adding a new difficulty is as easy as creating a class that extends the interface exposed by DifficultyAlgorithm.java. For more information about the architecture, implementation, and developement of the project see [Presentation](https://github.com/cdnmonitor/algorithm-jitsu/blob/main/406%20Final%20Presentation.pdf).

# To Run
1. Clone the repository
2. Open a terminal and navigate to the repository
3. Run the following commands
```bash
    javac *.java
 ```
## To play against another human player
 Run the following commands in separate terminals
 ```bash
    java Server
    java Client
    java Client
```
Follow the prompts in the terminal to play the game
## To play against AI
1. Play against the computer by running the following
```bash
    java Server
    java Client
    java SimpleAI
```
2. Select AI difficulty in the corresponding terminal window
### Experimental feature for macOS users. 
If you are running macOS, you can try using the zsh shell script to run the game against AI rather than opening 3 separate terminal windows. Make sure you are using zsh, and that you are using the default terminal app. Run the following command in the root directory of the project
```bash
    ./runAIGameMAC.sh
```
(this may cause errors if there are multiple terminal windows open)



# Work Division
Michael: Server, Client, Player classes, DifficultyAlgorithm.java Interface, HardAlgorithm.java and MediumAlgorithm.java, Debugging

Jack: AI interface implementation, SimpleAI.java, EasyAlgorithm.java,Index based card selection,
Difficulty selection,
Game logic bug fixes,
debugging

Max:Log System implementation, polishing Mechanics (Cards pulled from deck after playing. 
Update and store deck state),
MediumAlgorithm.java, HardAlgorithm.java,Shell Scripts,
debugging







# Feature Progress 

## Adding some of the unmarked features would be a great way to expand this project!
## Server Features
- [x] Server can start and end game
- [x] Server can handle 2 clients
- [x] Server Instantiates 1 player object for each client
- [x] Server can deal cards to clients
- [x] Server implements game logic
- [x] Server logs game beginning, end, and round outcomes. 
### Server ToDos 
- [x] Server implements AI algorithm for 2nd player
- [x] Server prompts player 1 to choose between AI and human player
- [ ] Easy menu/matchmaking, p2p, AI, tutorial.

## Client Features
- [x] Client can connect to server
- [x] Client can display cards to player
- [x] Client can only play cards in their hand
- [ ] Client can end the game at any time
- [x] Client can pick cards with [1-5] instead of typing whole card.
- [ ] Client can opt in to tutorial.
- [ ] Frontend / GUI development

## Algorithm Features
### Algorithm ToDos
- [x] "Easy Mode" AI implemented, plays random cards
- [x] "Medium Mode" AI implemented
- [x] "Hard Mode" AI implemented
- [ ] Algorithm can be trained using logs



