import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  private String serverAddress;
  private Scanner scanner = new Scanner(System.in);
  private String[] cardStrings = new String[5];
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_YELLOW = "\u001B[33m";

  public Client(String serverAddress) {
    this.serverAddress = serverAddress;
  }

  public void play() throws Exception {
    Socket socket = new Socket(serverAddress, 58901);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

    while (true) {
      try {
        String line = in.readLine();
        if (line.startsWith("WAIT")) {
          System.out.println(line);
          continue;
        } else if (line.startsWith("START")) {
          line = in.readLine();
          System.out.println(line);
          displayCards(in);
          out.println("READY");
        } else if (line.startsWith("SELECT")) {
          System.out.println(line);
          String cardString = selectCard();
          while (cardString.equals("")) {
            cardString = selectCard();
          }
          out.println(cardString);
        } else if (line.startsWith("COMBAT")) {
          System.out.println(line);
          displayCombat(in);
        } else if (line.startsWith("RESULT")) {
          printColor(line);
        } else if (line.startsWith("CARDS")){
          displayCards(in);
        } else if (line.startsWith("SCORE")) {
          System.out.println(line);
          break;
        }
      } catch (Exception e) {
      System.out.println(e.getMessage());
      break;
      }
    }
    socket.close();
  }

  private void displayCards(BufferedReader in) throws Exception {
    int numCards = Integer.parseInt(in.readLine());
    System.out.println("Your cards are:");
    for (int i = 0; i < numCards; i++) {
      String line = in.readLine();
      cardStrings[i] = line;
      System.out.println(i + ": " + line);
    }
  }

  private String selectCard() {
    System.out.print("Select a card (Number Only): ");
    String cardNumberString = scanner.nextLine();
    try {
      int cardNumber = Integer.parseInt(cardNumberString);
      for (int i = 0; i < 5; i++) {
        if (i == cardNumber) {
          return cardStrings[i];
        }
      }
    } catch (Exception e) {
      System.out.println("Please Enter a Number From 0 to 4");
    }
    System.out.println("Please play a card that is in your deck!");
    return "";
  }

  private void printColor(String message) {
        if (message.contains("WIN")) {
            System.out.println(ANSI_GREEN + message + ANSI_RESET);
        } else if (message.contains("LOSE")) {
            System.out.println(ANSI_RED + message + ANSI_RESET);
        } else if (message.contains("TIE")) {
            System.out.println(ANSI_YELLOW + message + ANSI_RESET);
        } else {
            System.out.println(message);
        }
    }

  private void displayCombat(BufferedReader in) throws Exception {
    String yourCard = in.readLine();
    String theirCard = in.readLine();
    System.out.println("Your card: " + yourCard);
    System.out.println("Their card: " + theirCard);
  }

  public static void main(String[] args) throws Exception {
    Client client = new Client("localhost");
    client.play();
  }
}