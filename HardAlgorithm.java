public class HardAlgorithm implements DifficultyAlgorithm {
    private boolean useAlternateStrategy = false;
    private int lossCount = 0;

    @Override
    public String selectCard(String[] cards) {
        int highestScore = Integer.MIN_VALUE;
        String selectedCard = null;

        for (String card : cards) {
            String[] cardData = card.split(",");
            String element = cardData[0];
            int powerNumber = Integer.parseInt(cardData[1]);
            String color = cardData[2];

            int score = powerNumber;

            if (!useAlternateStrategy) {
                // Code for unique color and element
                score += (element.hashCode() * color.hashCode());
            } else {
                // Code for different element and color
                score += (element.hashCode() - color.hashCode());
            }

            if (score > highestScore) {
                highestScore = score;
                selectedCard = card;
            }
        }

        return selectedCard;
    }

    public void incrementLossCount() {
        this.lossCount++;
        if (lossCount >= 2) {
            this.useAlternateStrategy = !useAlternateStrategy;
            this.lossCount = 0;
        }
    }

    public void resetLossCount() {
        this.lossCount = 0;
    }
}