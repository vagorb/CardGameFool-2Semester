import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HandTest {

    @Test
    public void HandInitialSize() {
        Hand hand = new Hand();
        assertEquals(0, hand.getCardsInHand().size());
    }

    @Test
    public void addCard() {
        Hand hand = new Hand();
        Card card1 = new Card("Hearts", 6);
        Card card2 = new Card("Hearts", 7);
        Card card3 = new Card("Hearts", 8);
        Card card4 = new Card("Hearts", 9);
        Card card5 = new Card("Hearts", 10);
        Card card6 = new Card("Hearts", 11);
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
    public void removeCard() {
        Hand hand = new Hand();
        Card card1 = new Card("Hearts", 6);
        Card card2 = new Card("Hearts", 7);
        Card card3 = new Card("Hearts", 8);
        Card card4 = new Card("Hearts", 9);
        Card card5 = new Card("Hearts", 10);
        Card card6 = new Card("Hearts", 11);
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
    public void putCardOnTable() {
        Hand hand = new Hand();
        Card card1 = new Card("Hearts", 6);
        hand.addCard(card1);
        assertEquals(card1, hand.putCardOnTable(card1));
        // Point is it should return a proper card . Because we are supposingly are going to use this return in future
    }

}
