import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

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
                } else if (line.startsWith("CARDS")){
                    readCards(in);
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
            System.out.println("AI " + i + " :" + cardString);
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
        DifficultyAlgorithm difficultyAlgorithm;
        Scanner in = new Scanner(System.in);

        boolean goodInput = false;
        int choiceNum = 5;

        System.out.println("Please Select a Difficulty:");

        while (!goodInput) {
            System.out.println("0: Easy \n1: Medium \n2: Hard");
            String choice = in.nextLine();
            try {
                choiceNum = Integer.parseInt(choice);
                if (choiceNum == 0 || choiceNum == 1 || choiceNum == 2) {
                    goodInput = true;
                } else {
                    System.out.println("Please Enter a Valid Choice From 0 to 2");
                }
            } catch (Exception e) {
                System.out.println("Please Enter a Number from 0 to 2");
            }
        }

        //Set Difficulty
        switch (choiceNum) {
            case 0:
                difficultyAlgorithm = new EasyAlgorithm();
                System.out.println("Easy Algorithm Selected");
                break;
            case 1:
                difficultyAlgorithm = new MediumAlgorithm();
                System.out.println("Medium Algorithm Selected");
                break;
            case 2:
                difficultyAlgorithm = new HardAlgorithm();
                System.out.println("Hard Algorithm Selected");
                break;
            // Will never happen
            default:
                difficultyAlgorithm = new EasyAlgorithm();
        }

        SimpleAI simpleAI = new SimpleAI("localhost", difficultyAlgorithm);
        simpleAI.playAI();
        in.close();
    }
}