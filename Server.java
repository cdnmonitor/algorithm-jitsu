import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Server {
  private static final int PORT = 58901;
  private ArrayList<Player> players = new ArrayList<>();

  public void start() throws Exception {
    ServerSocket serverSocket = new ServerSocket(PORT);
    System.out.println("Waiting for players...");

    while (players.size() < 2) {
      Socket socket = serverSocket.accept();
      Player player = new Player("Player " + (players.size() + 1));
      player.setSocket(socket);
      players.add(player);
      System.out.println("Player connected: " + player.getName());
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.println("WAIT Waiting for another player to connect...");
    }

    // Start of game
    System.out.println("Starting game...");
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
        player.addToHand(card);
      }
      PrintWriter out = new PrintWriter(player.getSocket().getOutputStream(), true);
      out.println("START Game starting...");
      out.println(player.getHand().size());
      for (Card card : player.getHand()) {
        out.println(card.getElement() + "," + card.getPowerNumber() + "," + card.getColor());
      }
    }
  }

  private ArrayList<Card> readDeckFromFile() throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
    ArrayList<Card> deck = new ArrayList<>();
    String line;
    while ((line = reader.readLine()) != null) {
      String[] cardInfo = line.split(",");
      String element = cardInfo[0];
      int powerNumber = Integer.parseInt(cardInfo[1]);
      String color = cardInfo[2];
      Card card = new Card(element, powerNumber, color);
      deck.add(card);
    }
    reader.close();
    return deck;
  }
  private void playRound() throws Exception {
    Player currentPlayer = players.get(0);
    Player otherPlayer = players.get(1);
    PrintWriter out = new PrintWriter(currentPlayer.getSocket().getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(currentPlayer.getSocket().getInputStream()));

    sendAndWait(out, "SELECT Select a card to play...");
    String cardStr = in.readLine();
    Card currentCard = parseCardString(cardStr);
    currentPlayer.getHand().remove(currentCard);

    out = new PrintWriter(otherPlayer.getSocket().getOutputStream(), true);
    sendAndWait(out, "OTHER " + currentPlayer.getName() + " played a card.");

    in = new BufferedReader(new InputStreamReader(otherPlayer.getSocket().getInputStream()));
    cardStr = in.readLine();
    Card otherCard = parseCardString(cardStr);
    otherPlayer.getHand().remove(otherCard);

    // Combat phase
    String result = determineRoundResult(currentCard, otherCard);
    out = new PrintWriter(currentPlayer.getSocket().getOutputStream(), true);
    out.println("COMBAT " + currentCard.getElement() + "," + currentCard.getPowerNumber() + "," + currentCard.getColor());
    out.println(otherCard.getElement() + "," + otherCard.getPowerNumber() + "," + otherCard.getColor());
    out.println(result);

    // Scoring phase
    if (gameOver()) {
      displayScore();
    }
  }
  // Combat phase
  private String determineRoundResult(Card currentCard, Card otherCard) {
    String result;
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
    } else if ((currentCard.getElement().equals("Water") && otherCard.getElement().equals("Fire")) ||
               (currentCard.getElement().equals("Fire") && otherCard.getElement().equals("Snow")) ||
               (currentCard.getElement().equals("Snow") && otherCard.getElement().equals("Water"))) {
      result = "WIN";
      players.get(0).addToWonCards(currentCard);
    } else {
      result = "LOSE";
      players.get(1).addToWonCards(otherCard);
    }
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
    if (elements.size() >= 3) {
      return true;
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
    String element = cardInfo[0];
    int powerNumber = Integer.parseInt(cardInfo[1]);
    String color = cardInfo[2];
    return new Card(element, powerNumber, color);
  }

  private void sendAndWait(PrintWriter out, String message) {
    out.println(message);
    out.println("WAIT Waiting for other player...");
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