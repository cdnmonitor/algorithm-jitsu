# Algorithm Jitsu
This project is a command line implementation of the [Club Pengin Card Jitsu](https://clubpenguin.fandom.com/wiki/Card-Jitsu) card game written in Java. The game is played between two clients (players) that communicate with server using network sockets. Logs of every battle will be kept so that we can train an AI to play against the human player. The game will implement AI algorithms with varying difficulty. 

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
5. Follow the prompts in the client terminals to play the game
<!-- to do list -->




# Feature Progress

## Server Features
- [x] Server can start and end game
- [x] Server can handle 2 clients
- [x] Server Instantiates 1 player object for each client
- [x] Server can deal cards to clients
- [x] Server implements game logic
- [x] Server logs game beginning, end, and round outcomes. 
### to do
- [ ] Server implements AI algorithm for 2nd player
- [ ] Server prompts player 1 to choose between AI and human player

## Client Features
- [x] Client can connect to server
- [x] Client can display cards to player
- [x] Client can play cards

## Algorithm Features
- [ ] "Easy Mode" AI implemented, plays random cards
- [ ] Algorithm can be trained using logs
### to do



