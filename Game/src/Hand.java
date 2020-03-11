import java.util.ArrayList;
import java.util.List;

public class Hand {
    //contain cards what are in player hands(they must be deleted from deck)
    //can get cards from table if unsuccesful defence and can get cards from deck if amount of cards in hand < 6.
    // choose card which will be putted on table
    private List<Card> cardsInHand = new ArrayList<>();


    //Owner of the hand
//    Player player;z
    public void addCard(Card card) {
        cardsInHand.add(card);
    }

    //public void addCardsInHandFromTable(Table, Player player) {
    //    for (Card card : Table) {
    //        player.hand.addCard(card);
    //    }
   // }
    

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

}
