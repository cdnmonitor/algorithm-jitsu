import java.util.ArrayList;
public class MediumAlgorithm implements DifficultyAlgorithm {
    @Override
    public String selectCard(String[] cards) {
        int maxIndex=0;
        // split each string into an array of strings on the comma
        // print out each card
        
    
        for (int i=0; i<cards.length;i++){
            String[] card = cards[i].split(",");
            if (Integer.parseInt(card[1]) > Integer.parseInt(cards[maxIndex].split(",")[1])){
                maxIndex = i;
            }
        }
        return cards[maxIndex];
    

        
    }

    // public Card selectCard(ArrayList<Card> cards) {
    //     int maxIndex=0;
    //     for (int i=0; i<cards.size();i++){
    //         if (cards.get(i).getPowerNumber() > cards.get(maxIndex).getPowerNumber()){
    //             maxIndex = i;
    //         }
    //     }
    //     return cards.get(maxIndex);
    // }

}
