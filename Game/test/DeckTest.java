import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {


    @Test
    public void TestGetDeck() {
        Deck deck = new Deck();
        assertEquals(36, deck.getDeck().size());
    }

    @Test
    public void TestRemoveCard() {
        Deck deck = new Deck();
        Card card = new Card("Hearts", 9, false);
        Card card2 = new Card("Hearts", 12, false);
        deck.removeCard(card);
        assertEquals(35, deck.getDeck().size());
        deck.removeCard(card2);
        assertEquals(34, deck.getDeck().size());
    }

    
    @Test
    public void TestShuffle() {
        // Tested with debugger
        Deck deck = new Deck();
        System.out.println(deck);
        deck.shuffleDeck();
        System.out.println(deck);
    }
}
