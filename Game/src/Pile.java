import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

public class Pile {
    // Pile class, should have all cards that were discarded after successful defense of a player
    //
    private List<Card> listOfDiscardedCards;
    // Currently it seems like this class is very useless to me. All it does is existing, and it servers a purpose of an ArrayList<Card>
    // which we can implement with 1 private List<Card> pile = new ArrayList<>() variable.
//
//
//    public void addCardsToPile(Table table.getPileOfCards){
//        if (playerTookCards == true) {
//            listOfDiscardedCards.add(table)
//        }
//    }

    public Pile(){

    }

    public List<Card> createPile(){
        return listOfDiscardedCards = new ArrayList<>();
    }


    public void addDiscardedCards(Card cardToAddToDiscard) {
        if (isDiscardable() == true) {
            listOfDiscardedCards.add(cardToAddToDiscard);
        }
    }


    public boolean isDiscardable() {
        return true;
    }

    public List<Card> getPile() {
        return listOfDiscardedCards;
    }

    public static void main(String[] args) {
//        Deck deckTest = new Deck();
//        createDeck();
//        System.out.println(deckTest.getDeck());
    }

}
