import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cardsInHand = new ArrayList<>();

    //Owner of the hand
//    Player player;
    public void addCard(Card card) {
        cardsInHand.add(card);
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

}
