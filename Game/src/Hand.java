import java.util.ArrayList;
import java.util.List;

public class Hand {
    //contain cards that are in player hands(they must be deleted from deck). - Implemented
    //can get cards from table if unsuccessful defence and can get cards from deck if amount of cards in hand < 6. - Partially(nothing for <6)
    // choose card which will be putted on table.
    private List<Card> cardsInHand = new ArrayList<>();
//    private String handName;


    /**
     * Hand class constructor
     */
    public Hand() {
        // We will name player's Hand after his player name ( I GUESS )
//        this.handName = playerName;

    }

    //Owner of the hand
//    Player player;z

    /**
     * Add card to hand
     * @param card to add to the players hand
     */
    public void addCard(Card card) {
        cardsInHand.add(card);
    }

    /**
     * Method that puts the card on the table and removes it from our hand
     * @param card to put on the table
     * @return the card we put on the table
     */
    public Card putCardOnTable(Card card) {
        removeCard(card);
        return card;
    }

    /**
     * Method that removes the card from our hand
     * @param card to remove from our hand
     */
    public void removeCard(Card card) {
        cardsInHand.remove(card);
    }

    //public void addCardsInHandFromTable(Table, Player player) {
    //    for (Card card : Table) {
    //        player.hand.addCard(card);
    //    }
   // }


    /**
     * Getter
     * @return list of cards in our hand
     */
    public List<Card> getCardsInHand() {
        return cardsInHand;
    }
}
