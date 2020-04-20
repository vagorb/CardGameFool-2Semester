import com.card.game.fool.cards.Card;
import com.card.game.fool.players.Hand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HandTest {

    @Test
    public void TestHandInitialSize() {
        Hand hand = new Hand();
        assertEquals(0, hand.getCardsInHand().size());
    }

    @Test
    public void TestAddCard() {
        Hand hand = new Hand();
        Card card1 = new Card("Hearts", 6, false);
        Card card2 = new Card("Hearts", 7, false);
        Card card3 = new Card("Hearts", 8, false);
        Card card4 = new Card("Hearts", 9, false);
        Card card5 = new Card("Hearts", 10, false);
        Card card6 = new Card("Hearts", 11, false);
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);
        assertEquals(6, hand.getCardsInHand().size());
        List<Card> testList = new ArrayList<>();
        testList.add(card1);
        testList.add(card2);
        testList.add(card3);
        testList.add(card4);
        testList.add(card5);
        testList.add(card6);
        assertEquals(testList, hand.getCardsInHand());
    }

    @Test
    public void TestRemoveCard() {
        Hand hand = new Hand();
        Card card1 = new Card("Hearts", 6, false);
        Card card2 = new Card("Hearts", 7, false);
        Card card3 = new Card("Hearts", 8, false);
        Card card4 = new Card("Hearts", 9, false);
        Card card5 = new Card("Hearts", 10, false);
        Card card6 = new Card("Hearts", 11, false);
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);
        hand.removeCard(card1);
        hand.removeCard(card2);
        hand.removeCard(card3);
        hand.removeCard(card4);
        assertEquals(2, hand.getCardsInHand().size());
        List<Card> testList = new ArrayList<>();
        testList.add(card5);
        testList.add(card6);
        assertEquals(testList, hand.getCardsInHand());
    }

    @Test
    public void TestPutCardOnTable() {
        Hand hand = new Hand();
        Card card1 = new Card("Hearts", 6, false);
        hand.addCard(card1);
        assertEquals(card1, hand.putCardOnTable(card1));
        // Point is it should return a proper card . Because we are supposingly are going to use this return in future
    }

}
