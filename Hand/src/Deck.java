import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {
    ArrayList<Card> deck;
    public Deck(){
        this.deck = createDeck();
    }

    public static ArrayList<Card> createDeck() {
        ArrayList<Card.Suite> suits = new ArrayList<>(Arrays.asList(Card.Suite.CLUBS, Card.Suite.HEARTS, Card.Suite.DIAMONDS, Card.Suite.SPADES));
        ArrayList<Integer> values = new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10, 11, 12, 13, 14));
        ArrayList<Card> deck = new ArrayList<>();
        for (Integer value: values) {
            for (Card.Suite suit : suits) {
                Card card = new Card(value, suit);
                deck.add(card);
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

}