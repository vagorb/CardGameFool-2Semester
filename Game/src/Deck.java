
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck;


    public Deck() {
        this.deck = new ArrayList<>();
        ArrayList<String> suits = new ArrayList<>(Arrays.asList("Clubs", "Hearts", "Diamonds", "Spades"));
        ArrayList<Integer> values = new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10, 11, 12, 13, 14));
        for (Integer value : values) {
            for (String suit : suits) {
                Card card = new Card(suit, value, false);
                this.deck.add(card);
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    public void removeCard(Card card) {
        deck.remove(card);
    }

    public ArrayList<Card> getDeck(){
        return this.deck;
    }

}
