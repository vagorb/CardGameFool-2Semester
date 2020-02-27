import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {
    ArrayList<Card> deck;
    public Deck(){
        this.deck = createDeck();
    }

    public static ArrayList<Card> createDeck() {
        ArrayList<String> suits = new ArrayList<>(Arrays.asList("Clubs", "Hearts", "Diamonds", "Spades"));
        ArrayList<Integer> values = new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10, 11, 12, 13, 14));
        ArrayList<Card> deck = new ArrayList<>();
        for (Integer value: values) {
            for (String suit : suits) {
                Card card = new Card(suit, value);
                deck.add(card);
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    public static void main(String[] args) {
        Deck deckTest = new Deck();
        createDeck();
        System.out.println(deckTest.getDeck());

    }


    public ArrayList<Card> getDeck(){
        return deck;
    }

}
