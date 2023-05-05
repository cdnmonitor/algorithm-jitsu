import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MediumAlgorithm implements DifficultyAlgorithm {

    @Override
    public String selectCard(String[] cards) {
        // Analyze the cards and count the occurrence of each element
        HashMap<String, Integer> elementCount = new HashMap<>();
        for (String card : cards) {
            String[] cardData = card.split(",");
            String element = cardData[0];
            elementCount.put(element, elementCount.getOrDefault(element, 0) + 1);
        }

        // Find the most common element
        Map.Entry<String, Integer> mostCommonElement = null;
        for (Map.Entry<String, Integer> entry : elementCount.entrySet()) {
            if (mostCommonElement == null || entry.getValue() > mostCommonElement.getValue()) {
                mostCommonElement = entry;
            }
        }

        // Select the card with the highest power number of the most common element
        String selectedCard = null;
        int highestPower = Integer.MIN_VALUE;
        for (String card : cards) {
            String[] cardData = card.split(",");
            String element = cardData[0];
            int powerNumber = Integer.parseInt(cardData[1]);
            if (element.equals(mostCommonElement.getKey()) && powerNumber > highestPower) {
                selectedCard = card;
                highestPower = powerNumber;
            }
        }

        // If the most common element is not found in the hand, fall back to the MediumAlgorithm logic
        if (selectedCard == null) {
            int maxIndex = 0;
            for (int i = 0; i < cards.length; i++) {
                String[] card = cards[i].split(",");
                if (Integer.parseInt(card[1]) > Integer.parseInt(cards[maxIndex].split(",")[1])) {
                    maxIndex = i;
                }
            }
            selectedCard = cards[maxIndex];
        }

        return selectedCard;
    }
}
