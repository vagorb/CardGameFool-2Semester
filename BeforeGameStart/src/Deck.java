import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {
    ArrayList<String> suits = new ArrayList<>(Arrays.asList("SPADES", "DIAMONDS", "HEARTS", "CLUBS"));
    ArrayList<String> values = new ArrayList<>(Arrays.asList("6", "7", "8", "9", "10", "JACK", "'QUEEN", "KING", "ACE"));
    ArrayList<Card> deck;
    public Deck(){
        this.deck = createDeck();
    }

    public ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        for (String value: values) {
            for (String suit : suits) {
                if (value.equals("10")){
                    Card card = new Card(suit, value, null, Card.Visibility.NOONE, "0" + suit.substring(0,1));
                    deck.add(card);
                } else {
                    Card card = new Card(suit, value, null, Card.Visibility.NOONE, value.substring(0, 1)
                            + suit.substring(0, 1));
                    deck.add(card);
                }
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

}
