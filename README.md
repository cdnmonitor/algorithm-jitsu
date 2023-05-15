# Algorithm Jitsu
This goal of this project is to create a command line implementation of the [Club Pengin Card Jitsu](https://clubpenguin.fandom.com/wiki/Card-Jitsu) card game written in Java. As children, we grew up playing ClubPenguin which heavily inspired us to recreate the game for the purpose of testing algorithms in a player vs player setting. The game is played between two clients (players) that communicate with a server using network sockets. Logs of every battle will be kept so that we can train an AI to play against the human player. The game will implement AI algorithms with varying difficulty. This project is being developed by [Michael Bishai](https://github.com/cdnmonitor),[Jack Donnely](https://github.com/LeonTheMouse), and [Max Pugh](https://github.com/mpughcs)

# Documentation
Detailed documentation for the project can be found [here](https://loud-battery-5c3.notion.site/Card-Jitsu-Automata-394c48ba19ce45ea9993e6cc75747b9c)
![Class Diagram](https://github.com/cdnmonitor/algorithm-jitsu/blob/main/ClassDiagram.png)

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







# Feature Progress

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



