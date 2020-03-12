import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardTest {



    @Test
    public void CardClassConstructorValues() {
        Card card = new Card("Hearts", 9);
        assertEquals("Hearts", card.getSuit());
        assertEquals(9, card.getValue());
        assertEquals(Card.Visibility.NOONE, card.getVisibility());
        assertEquals("9 of Hearts", card.toString());
    }

}