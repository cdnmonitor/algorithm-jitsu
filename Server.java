import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Server {
  private static final int PORT = 58901;
  private ArrayList<Player> players = new ArrayList<>();

  //stores the deck of cards the players pull from
  private ArrayList<Card> remainingDeck = new ArrayList<>();

  public void start() throws Exception {
    ServerSocket serverSocket = new ServerSocket(PORT);
    System.out.println("Waiting for players...");

    while (players.size() < 2) {
      Socket socket = serverSocket.accept();
      Player player = new Player("Player " + (players.size() + 1));
      player.setSocket(socket);
      players.add(player);
      System.out.println("Player connected: " + player.getName());
      //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.println("WAIT Waiting for another player to connect...");
    }

    // Start of game
    System.out.println("Starting game...");
    logStart();
    dealCards();
    while (!gameOver()) {
      playRound();
    }
    displayScore();

    serverSocket.close();
  }
  private void dealCards() throws Exception {
    
    ArrayList<Card> deck = readDeckFromFile();
    Collections.shuffle(deck);
    for (Player player : players) {
      for (int i = 0; i < 5; i++) {
        Card card = deck.remove(0);
        remainingDeck=deck;
        player.addToHand(card);
      }
      PrintWriter out = new PrintWriter(player.getSocket().getOutputStream(), true);
      out.println("START Game starting...\n");
      out.println(player.getHand().size());
      for (Card card : player.getHand()) {
        out.println(card.getElement() + "," + card.getPowerNumber() + "," + card.getColor());
      }
    }
  }
  private void pullFromDeck(Player p){
    if(remainingDeck.size()==0){
      return;
    }
    Card toAdd=remainingDeck.remove(0);
    p.addToHand(toAdd);

  }

  private ArrayList<Card> readDeckFromFile() throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
    ArrayList<Card> deck = new ArrayList<>();
    String line;
    while ((line = reader.readLine()) != null) {
      String[] cardInfo = line.split(",");
      String element = cardInfo[0].strip();
      int powerNumber = Integer.parseInt(cardInfo[1].strip());
      String color = cardInfo[2].strip();
      Card card = new Card(element, powerNumber, color);
      deck.add(card);
    }
    reader.close();
    return deck;
  }

  private Boolean removeCard(Player player, Card card) {
    for (Card c : player.getHand()) {
      if(card.equals(c)){
        player.getHand().remove(c);
        return true;
      }
    }
    return false;
  }

  private void playRound() throws Exception {
    Player currentPlayer = players.get(0);
    Player otherPlayer = players.get(1);

    //Establish connections to clients input/outputs
    PrintWriter outCurrent = new PrintWriter(currentPlayer.getSocket().getOutputStream(), true);
    BufferedReader inCurrent = new BufferedReader(new InputStreamReader(currentPlayer.getSocket().getInputStream()));
    
    PrintWriter outOther = new PrintWriter(otherPlayer.getSocket().getOutputStream(), true);
    BufferedReader inOther = new BufferedReader(new InputStreamReader(otherPlayer.getSocket().getInputStream()));

    //Tell player 2 to wait and ask for player 1's card
    sendAndWait(outOther, "READY Waiting for other player...");
    sendAndWait(outCurrent, "SELECT Select a card to play...");
    
    //Wait till player 1 goes from ready to providing their card
    String cardStr = inCurrent.readLine();
    while (cardStr.equals("READY")) {
      cardStr = inCurrent.readLine();
    }
    
    sendAndWait(outOther, "SELECT Select a card to play...");

    //Parse player 1's card and remove it
    Card currentCard = parseCardString(cardStr);
    
    // Remove Played Card From player1 deck
    removeCard(currentPlayer, currentCard);
    pullFromDeck(currentPlayer);

    cardStr = inOther.readLine();
    while (cardStr.equals("READY")) {
      cardStr = inOther.readLine();
    }

    //Parse player2's card and remove it
    Card otherCard = parseCardString(cardStr);

    //Remove Played Card From player2 deck
    removeCard(otherPlayer, otherCard);
    pullFromDeck(otherPlayer);

    // Combat phase
    String result = determineRoundResult(currentCard, otherCard);

    //Print output to Player1
    outCurrent.println("COMBAT " + currentCard.getElement() + "," + currentCard.getPowerNumber() + "," + currentCard.getColor());
    outCurrent.println(currentCard.getElement() + "," + currentCard.getPowerNumber() + "," + currentCard.getColor());
    outCurrent.println(otherCard.getElement() + "," + otherCard.getPowerNumber() + "," + otherCard.getColor());
    outCurrent.println("RESULT " + result);

    //Change Result so that it refelcts other players point of view.
    if (result.equals("WIN")) {
      result = "LOSE";
    } else if (result.equals("LOSE")) {
      result = "WIN";
    }
    //Print output to Player2
    outOther.println("COMBAT " + otherCard.getElement() + "," + otherCard.getPowerNumber() + "," + otherCard.getColor());
    outOther.println(otherCard.getElement() + "," + otherCard.getPowerNumber() + "," + otherCard.getColor());
    outOther.println(currentCard.getElement() + "," + currentCard.getPowerNumber() + "," + currentCard.getColor());
    outOther.println("RESULT " + result);

    // Scoring phase
    if (gameOver()) {
      displayScore();
      logEnd();
    }
    for (Player player : players) {
      PrintWriter out = new PrintWriter(player.getSocket().getOutputStream(), true);
      out.println("CARDS Displaying Hand...");
      out.println(player.getHand().size());
      for (Card card : player.getHand()) {
        out.println(card.getElement() + "," + card.getPowerNumber() + "," + card.getColor());
      }
    }
  }

  // Combat phase
  private String determineRoundResult(Card currentCard, Card otherCard) {
    String result;
    String winner=""; 
    if (currentCard.getElement().equals(otherCard.getElement())) {
      if (currentCard.getPowerNumber() > otherCard.getPowerNumber()) {
        result = "WIN";  
        players.get(0).addToWonCards(currentCard);
      } else if (currentCard.getPowerNumber() < otherCard.getPowerNumber()) {
        result = "LOSE"; 
        players.get(1).addToWonCards(otherCard);
      } else {
        result = "TIE";
      }
    } else if ((currentCard.getElement().equals("water") && otherCard.getElement().equals("fire")) ||
               (currentCard.getElement().equals("fire") && otherCard.getElement().equals("snow")) ||
               (currentCard.getElement().equals("snow") && otherCard.getElement().equals("water"))) {
      result = "WIN";
      players.get(0).addToWonCards(currentCard);
    } else {
      result = "LOSE";
      players.get(1).addToWonCards(otherCard);
    }
    if(result=="WIN"){
      winner="Player 1";
    } else if(result=="LOSE"){
      winner="Player 2";
    } else{
      winner="neither player";
    }
    log(winner,result,currentCard,otherCard);
    return result;
  }
  // Scoring phase
  private boolean gameOver() {
    HashSet<String> elements = new HashSet<>();
    for (Player player : players) {
      for (Card card : player.getWonCards()) {
        elements.add(card.getElement() + "," + card.getColor());
      }
    }
    for (String element : elements) {
      int count = 0;
      for (Player player : players) {
        for (Card card : player.getWonCards()) {
          if ((card.getElement() + "," + card.getColor()).equals(element)) {
            count++;
          }
        }
      }
      if (count >= 3) {
        return true;
      }
    }
    for (Player player : players) {
      if (player.getHand().size() <= 0) {
        return true;
      }
    }
    return false;
  }
  
  private void displayScore() throws Exception {
    int player1Score = players.get(0).getWonCards().size();
    int player2Score = players.get(1).getWonCards().size();
    System.out.println("Player 1: " + player1Score + " points");
    System.out.println("Player 2: " + player2Score + " points");
    String player1Cards = "";
    String player2Cards = "";
    for (Card card : players.get(0).getWonCards()) {
      player1Cards += card.getElement() + "," + card.getPowerNumber() + "," + card.getColor() + " ";
    }
    for (Card card : players.get(1).getWonCards()) {
      player2Cards += card.getElement() + "," + card.getPowerNumber() + "," + card.getColor() + " ";
    }
    PrintWriter out = new PrintWriter(players.get(0).getSocket().getOutputStream(), true);
    out.println("SCORE " + player1Score + " " + player2Score + " " + player2Cards);
    out = new PrintWriter(players.get(1).getSocket().getOutputStream(), true);
    out.println("SCORE " + player2Score + " " + player1Score + " " + player1Cards);
  }
  
  private Card parseCardString(String cardStr) {
    String[] cardInfo = cardStr.split(",");
    String element = cardInfo[0].strip();
    int powerNumber = Integer.parseInt(cardInfo[1].strip());
    String color = cardInfo[2].strip();
    return new Card(element, powerNumber, color);
  }

  private void sendAndWait(PrintWriter out, String message) {
    out.println(message);
    out.println("WAIT Waiting for other player...");
  }
  // There is an output log that is saved as a text file that reads something to the tune of 'Player 1 beat Player 2 with CARDID. 
  // Player 2 played CARDID. The score is SCORE'. This log also appears in the console so we can troubleshoot better.

  private void log(String winner, String result, Card p1, Card p2) {
    String message="";
    if(result!="TIE"){
      message = winner + " beat " + (winner.equals("Player 1") ? "Player 2" : "Player 1");
      if (winner.equals("Player 1")) {
        message += " with '" + p1.getElement() + "," + p1.getPowerNumber() + "," + p1.getColor() + "', Player 2 played '" + p2.getElement() + "," + p2.getPowerNumber() + "," + p2.getColor() + "', ";
      } else {
        message += " with '" + p2.getElement() + "," + p2.getPowerNumber() + "," + p2.getColor() + "', Player 1 played '" + p1.getElement() + "," + p1.getPowerNumber() + "," + p1.getColor() + "',";
      }
    } else {
      message = "TIE. Player 1 played '" + p1.getElement() + "," + p1.getPowerNumber() + "," + p1.getColor() + "', Player 2 played '"+ p2.getElement() + "," + p2.getPowerNumber() + "," + p2.getColor()+"'";
    }
    message+= "The score is " + players.get(0).getWonCards().size() + " to " + players.get(1).getWonCards().size() + ".";

    try {
      // get current date and time
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
      LocalDateTime now = LocalDateTime.now();  
      message = "[" + dtf.format(now) + "] " + message;
      System.out.println(message);
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
      out.println(message);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
      
  }
  private void logStart(){
    try {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
      LocalDateTime now = LocalDateTime.now();  
      String message = "[" + dtf.format(now) + "] " + "GAME STARTED";
      System.out.println(message);
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
      out.println(message);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private void logEnd(){
    try {
      int p1 = players.get(0).getWonCards().size();
      int p2 = players.get(1).getWonCards().size();
      String winner="";
      if(p1>p2){
        winner="Player 1";
      } else if(p2>p1){
        winner="Player 2";
      }
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
      LocalDateTime now = LocalDateTime.now();  
      String message = "[" + dtf.format(now) + "] " + "GAME END, "+ "WINNER: " + winner ;
      System.out.println(message);
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
      out.println(message);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
   


  public static void main(String[] args) {
    Server server = new Server();
    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}