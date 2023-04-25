import java.util.Random;

public class EasyAlgorithm implements DifficultyAlgorithm {
    @Override
    public String selectCard(String[] cards) {
        Random generator = new Random();
        int cardChoice = generator.nextInt(cards.length);
        return cards[cardChoice];
    }
}
