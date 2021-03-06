import com.card.game.fool.cards.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {



    @Test
    public void TestCardClassConstructorValues() {
        Card card = new Card("Hearts", 9, false);
        assertEquals("Hearts", card.getSuit());
        assertEquals(9, card.getValue());
        assertEquals(Card.Visibility.NOONE, card.getVisibility());
        assertEquals("9 of Hearts", card.toString());
    }

    @Test
    public void TestSetGetTrump() {
        Card card = new Card("Hearts", 9, false);
        assertEquals(false, card.getTrump());
        card.setTrump(true);
        assertEquals(true, card.getTrump());
    }

}
