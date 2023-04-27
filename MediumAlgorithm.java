
import java.util.ArrayList;

public class MediumAlgorithm  {
    public Card selectCard(ArrayList<Card> cards) {
        int maxIndex=0;
        for (int i=0; i<cards.size();i++){
            if (cards.get(i).getPowerNumber() > cards.get(maxIndex).getPowerNumber()){
                maxIndex = i;
            }
        }
        return cards.get(maxIndex);
    }

}
