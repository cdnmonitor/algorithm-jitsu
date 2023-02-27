import java.net.Socket;
import java.util.ArrayList;

public class Player {
  private String name;
  private ArrayList<Card> hand;
  private ArrayList<Card> wonCards;
  private Socket socket;

  public Player(String name) {
    this.name = name;
    this.hand = new ArrayList<>();
    this.wonCards = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void addToHand(Card card) {
    hand.add(card);
  }

  public ArrayList<Card> getHand() {
    return hand;
  }

  public void addToWonCards(Card card) {
    wonCards.add(card);
  }

  public ArrayList<Card> getWonCards() {
    return wonCards;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public Socket getSocket() {
    return socket;
  }
}
