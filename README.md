# Algorithm Jitsu
This goal of this project is to create a command line implementation of the [Club Pengin Card Jitsu](https://clubpenguin.fandom.com/wiki/Card-Jitsu) card game written in Java. The game is played between two clients (players) that communicate with a server using network sockets. Logs of every battle will be kept so that we can train an AI to play against the human player. The game will implement AI algorithms with varying difficulty. This project is being developed by [Michael Bishai](https://github.com/cdnmonitor),[Jack Donnely](https://github.com/LeonTheMouse), and [Max Pugh](https://github.com/mpughcs)

# Documentation
Detailed documentation for the project can be found [here](https://loud-battery-5c3.notion.site/Card-Jitsu-Automata-394c48ba19ce45ea9993e6cc75747b9c)

# To Run
1. Clone the repository
2. Open a terminal and navigate to the repository
3. Run the following commands
```bash
    javac *.java
 ```
 4. Run the following commands in separate terminals
 ```bash
    java Server
    java Client
    java Client
```
5. Play against the computer by running the following
```bash
    java Server
    java Client
    java SimpleAI
```
6. Follow the prompts in the client terminals to play the game
<!-- to do list -->




# Feature Progress

## Server Features
- [x] Server can start and end game
- [x] Server can handle 2 clients
- [x] Server Instantiates 1 player object for each client
- [x] Server can deal cards to clients
- [x] Server implements game logic
- [x] Server logs game beginning, end, and round outcomes. 
### Server ToDos 
- [ ] Server implements AI algorithm for 2nd player
- [ ] Server prompts player 1 to choose between AI and human player

## Client Features
- [x] Client can connect to server
- [x] Client can display cards to player
- [x] Client can only play cards in their hand
- [ ] Client can end the game at any time
- [ ] Client can pick cards with [1-5] instead of typing whole card.
- [ ] Client can opt in to tutorial.

## Algorithm Features
### Algorithm ToDos
- [x] "Easy Mode" AI implemented, plays random cards
- [ ] Algorithm can be trained using logs



