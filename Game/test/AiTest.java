import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AiTest {
    Card card1;
    Card card2;
    Card card3;
    Card card4;
    Card card5;
    Card card6;
    Card card7;
    Card card8;
    Card card9;
    Card card10;
    Card card11;
    Card card12;
    Card cardPile1;
    Card cardPile2;
    Card cardPile3;
    Card cardPile4;
    Card cardPile5;
    Card cardPile6;
    Hand hand;
    Pile pile;
    Deck deck;
    Hand hand1;
    Player player1;
    Ai ai;
    Table tableFor;

//    private Map<Integer, String> valueMap = new HashMap<>(Map.of(6, "6", 7, "7", 8, "8",
//            9, "9", 10, "10", 11, "JACK", 12, "QUEEN", 13, "KING",
//            14, "ACE"));
    @BeforeEach
    void setUp() {
        hand = new Hand();
        card3 = new Card("Spades", 6, true);
        card5 = new Card("Spades", 14, true);
        card1 = new Card("Hearts", 6, false);
        card2 = new Card("Hearts", 9, false);
        card4 = new Card("Hearts", 13, false);
        card6 = new Card("Hearts", 11, false);
        card7 = new Card("Hearts", 7, false);
        card8 = new Card("Hearts", 8, false);
        card9 = new Card("Hearts", 12, false);
        card10 = new Card("Hearts", 10, false);
        card11 = new Card("Spades", 8, true);
        card12 = new Card("Spades", 13, true);
        cardPile1 = new Card("Diamonds", 12, false);
        cardPile2 = new Card("Diamonds", 6, false);
        cardPile3 = new Card("Diamonds", 9, false);
        cardPile4 = new Card("Clubs", 6, false);
        cardPile5 = new Card("Clubs", 9, false);
        cardPile6 = new Card("Clubs", 10, false);
        hand1 = new Hand();
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);
        ai = new Ai(hand);
        hand1.addCard(card7);
        hand1.addCard(card8);
        hand1.addCard(card9);
        hand1.addCard(card10);
        hand1.addCard(card11);
        hand1.addCard(card12);
        player1 = new Player("Ja", 0, hand1);
        tableFor = new Table(player1, ai);
        tableFor.addToPile(cardPile1);
        tableFor.addToPile(cardPile2);
        tableFor.addToPile(cardPile3);
        tableFor.addToPile(cardPile4);
        tableFor.addToPile(cardPile5);
        tableFor.addToPile(cardPile6);

    }

    @Test
    void getAiHand() {
        Hand hand2 = new Hand();
        hand2.addCard(card1);
        hand2.addCard(card2);
        hand2.addCard(card3);
        hand2.addCard(card4);
        hand2.addCard(card5);
        hand2.addCard(card6);
        assertEquals(hand2.getCardsInHand(), ai.getAiHand().getCardsInHand());
    }

    @Test
    void mapOfCardsInHand() {
        Hand hand2 = new Hand();
        hand2.addCard(card1);
        hand2.addCard(card2);
        hand2.addCard(card3);
        hand2.addCard(card4);
        hand2.addCard(card5);
        hand2.addCard(card6);
        System.out.println(hand2.getCardsInHand().stream().collect(Collectors.groupingBy(Card::getValue)));
    }

    @Test
    void suitableForDefCards() {
        tableFor.addCardOnTable(card9);
        System.out.println(tableFor.getTable());
        System.out.println(tableFor.getLastCardOnTable().getTrump());
        assertEquals(List.of(card3, card4, card5) , ai.suitableForDefCards(tableFor.getLastCardOnTable()));
    }

    @Test
    void mostSuitableCardForDef() {
        tableFor.addCardOnTable(card9);
        System.out.println(tableFor);
        assertEquals(Optional.of(card3), ai.mostSuitableCardForDef());
    }

    @Test
    void firstCardAttackMove() {
    }

    @Test
    void suitableCardsForAttackMoves() {
    }

    @Test
    void suitableAttackMoveBeforeEndOfDeck() {
    }

    @Test
    void suitableAttackMoveWhenDeckEnds() {
    }
}