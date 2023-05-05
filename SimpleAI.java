import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleAI {
    private String serverAddress;
    private String[] cards = new String[5];
    private DifficultyAlgorithm difficultyAlgorithm;

    public SimpleAI(String serverAddress, DifficultyAlgorithm difficultyAlgorithm) {
        this.serverAddress = serverAddress;
        this.difficultyAlgorithm = difficultyAlgorithm;
    }

    public void playAI() throws Exception {
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
                    readCards(in);
                    out.println("READY");
                } else if (line.startsWith("SELECT")) {
                    System.out.println(line);
                    out.println("READY");
                    selectCard(out);
                } else if (line.startsWith("COMBAT")) {
                    System.out.println(line);
                    displayCombat(in);
                } else if (line.startsWith("RESULT")) {
                    System.out.println(line);
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

    private void readCards(BufferedReader in) throws Exception {
        int numCards = Integer.parseInt(in.readLine());
        System.out.println("Your cards are:");
        for (int i = 0; i < numCards; i++) {
            String cardString = in.readLine();
            System.out.println("AI:" + cardString);
            cards[i] = cardString;
        }
    }

    private void selectCard(PrintWriter out) {
        String cardChoice = difficultyAlgorithm.selectCard(cards);
        System.out.println("The card is " + cardChoice);
        out.println(cardChoice);
    }

    private void displayCombat(BufferedReader in) throws Exception {
        String yourCard = in.readLine();
        String theirCard = in.readLine();
        System.out.println("Your card: " + yourCard);
        System.out.println("Their card: " + theirCard);
    }

    public static void main(String[] args) throws Exception {
        // Replace this part with user input to choose the difficulty level
        DifficultyAlgorithm difficultyAlgorithm = new MediumAlgorithm();

        SimpleAI simpleAI = new SimpleAI("localhost", difficultyAlgorithm);
        simpleAI.playAI();
    }
}