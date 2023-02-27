import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
  private String serverAddress;
  private Scanner scanner = new Scanner(System.in);

  public Client(String serverAddress) {
    this.serverAddress = serverAddress;
  }

  public void play() throws Exception {
    Socket socket = new Socket(serverAddress, 58901);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

    while (true) {
      String line = in.readLine();
      if (line.startsWith("WAIT")) {
        System.out.println(line);
        continue;
      } else if (line.startsWith("START")) {
        System.out.println(line);
        displayCards(in);
        out.println("READY");
      } else if (line.startsWith("SELECT")) {
        System.out.println(line);
        selectCard(out);
      } else if (line.startsWith("COMBAT")) {
        System.out.println(line);
        displayCombat(in);
      } else if (line.startsWith("SCORE")) {
        System.out.println(line);
        break;
      }
    }
    socket.close();
  }

  private void displayCards(BufferedReader in) throws Exception {
    int numCards = Integer.parseInt(in.readLine());
    System.out.println("Your cards are:");
    for (int i = 0; i < numCards; i++) {
      System.out.println(in.readLine());
    }
  }

  private void selectCard(PrintWriter out) {
    System.out.print("Select a card: ");
    String card = scanner.nextLine();
    out.println(card);
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
