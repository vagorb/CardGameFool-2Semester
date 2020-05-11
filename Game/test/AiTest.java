//import com.card.game.fool.cards.Card;
//import com.card.game.fool.cards.Deck;
//import com.card.game.fool.players.Player;
//import com.card.game.fool.tables.Table;
//import com.card.game.fool.AI.Ai;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class AiTest {
//
//
//    Card card1;
//    Card card2;
//    Card card3;
//    Card card4;
//    Card card5;
//    Card card6;
//    Card card7;
//    Card card8;
//    Card card9;
//    Card card10;
//    Card card11;
//    Card card12;
//    Card cardPile1;
//    Card cardPile2;
//    Card cardPile3;
//    Card cardPile4;
//    Card cardPile5;
//    Card cardPile6;
//    Player player1;
//    Ai ai;
//    Table tableFor;
//
//    //    private Map<Integer, String> valueMap = new HashMap<>(Map.of(6, "6", 7, "7", 8, "8",
////            9, "9", 10, "10", 11, "Jack", 12, "Queen", 13, "King",
////            14, "Ace"));
//    @BeforeEach
//    void setUp() {
//        card3 = new Card("Spades", 6, true);
//        card5 = new Card("Spades", 14, true);
//        card1 = new Card("Hearts", 6, false);
//        card2 = new Card("Hearts", 9, false);
//        card4 = new Card("Hearts", 13, false);
//        card6 = new Card("Hearts", 11, false);
//        card7 = new Card("Hearts", 7, false);
//        card8 = new Card("Hearts", 8, false);
//        card9 = new Card("Hearts", 12, false);
//        card10 = new Card("Hearts", 10, false);
//        card11 = new Card("Spades", 8, true);
//        card12 = new Card("Spades", 13, true);
//        cardPile1 = new Card("Diamonds", 12, false);
//        cardPile2 = new Card("Diamonds", 6, false);
//        cardPile3 = new Card("Diamonds", 9, false);
//        cardPile4 = new Card("Clubs", 6, false);
//        cardPile5 = new Card("Clubs", 9, false);
//        cardPile6 = new Card("Clubs", 10, false);
//        player1.getHand().add(card3);
//        player1.getHand().add(card2);
//        player1.getHand().add(card1);
//        player1.getHand().add(card4);
//        player1.getHand().add(card5);
//        player1.getHand().add(card6);
//        ai = new Ai();
//        ai.getHand().add(card7);
//        ai.getHand().add(card8);
//        ai.getHand().add(card9);
//        ai.getHand().add(card10);
//        ai.getHand().add(card11);
//        ai.getHand().add(card12);
//        player1 = new Player("Ja", 0);
//        tableFor = new Table(player1, ai);
//        tableFor.addToPile(cardPile1);
//        tableFor.addToPile(cardPile2);
//        tableFor.addToPile(cardPile3);
//        tableFor.addToPile(cardPile4);
//        tableFor.addToPile(cardPile5);
//        tableFor.addToPile(cardPile6);
//
//    }
//
//    @Test
//    void getAiHand() {
//        ai.getHand().clear();
//        ai.getHand().add(card3);
//        ai.getHand().add(card2);
//        ai.getHand().add(card1);
//        ai.getHand().add(card4);
//        ai.getHand().add(card5);
//        ai.getHand().add(card6);
//        System.out.println(tableFor.getPile().getPile());
//        assertEquals(List.of(card1, card2, card3, card4, card5, card6), ai.getHand());
//    }
//
//    @Test
//    void mapOfCardsInHand() {
//        ai.getHand().clear();
//        ai.getHand().add(card1);
//        ai.getHand().add(card2);
//        ai.getHand().add(card3);
//        ai.getHand().add(card4);
//        ai.getHand().add(card5);
//        ai.getHand().add(card6);
//        System.out.println(ai.getHand().stream().collect(Collectors.groupingBy(Card::getValue)));
//    }
//
//    @Test
//    void suitableForDefCards() {
//        tableFor.addCardOnTable(card9);
//        System.out.println(tableFor.getTable());
//        System.out.println(tableFor.getLastCardOnTable().getTrump());
//        assertEquals(List.of(card3, card4, card5), ai.suitableForDefCards(tableFor));
//    }
//
//    @Test
//    void mostSuitableCardForDef() {
//        tableFor.addCardOnTable(card9);
//        System.out.println(tableFor.getPile().getPile());
//        assertEquals(Optional.of(card3), ai.mostSuitableCardForDef(tableFor));
//    }
//
//    @Test
//    void firstCardAttackMove() {
//        assertEquals(Optional.of(card1), ai.firstCardAttackMove());
//    }
//
//    @Test
//    void suitableCardsForAttackMoves() {
//        tableFor.addCardOnTable(card7);
//        tableFor.addCardOnTable(card8);
//        tableFor.addCardOnTable(card9);
//        tableFor.addCardOnTable(card10);
//        System.out.println(ai.suitableCardsForAttackMoves(tableFor));
//        assertEquals(List.of(), ai.suitableCardsForAttackMoves(tableFor));
//    }
//
//    @Test
//    void suitableAttackMoveBeforeEndOfDeck() {
//        tableFor.addCardOnTable(cardPile4);
//        assertEquals(Optional.of(card1), ai.suitableAttackMoveBeforeEndOfDeck(tableFor));
//    }
//
//
//    @Test
//    void playerCardsInTheEnd() {
//        Deck deck = new Deck();
//        deck.makeCardsTrump(tableFor.getTrumpSuit());
//        for (Card card : deck.getDeck()) {
//            if (!ai.getHand().contains(card) && !tableFor.getPile().getPile().contains(card) && !player1.getHand().contains(card)) {
//                tableFor.addToPile(card);
//            }
//        }
//        System.out.println(ai.playerCardsInTheEnd(tableFor));
//        assertEquals(List.of(card7, card8, card11, card10, card9, card12), ai.playerCardsInTheEnd(tableFor));
//    }
//
//    @Test
//    void playerCardsInTheEndBySuit() {
//    }
//
//    @Test
//    void playerCardsInTheEndByValue() {
//    }
//
//    @Test
//    void testSuitableAttackMoveWhenDeckEnds() {
//        //tableFor.setTrumpSuit("Spades");
//        System.out.println(tableFor.getTrumpSuit());
//        Deck deck = new Deck();
//        System.out.println(card3.getTrump());
//        //tableFor.setTrumpSuit((tableFor.getTrumpSuit());
//        for (Card card : deck.getDeck()) {
//            if (!ai.getHand().contains(card) && !tableFor.getPile().getPile().contains(card) && !player1.getHand().contains(card)) {
//                tableFor.addToPile(card);
//            }
//        }
//        System.out.println(tableFor.getPile().getPile());
//        assertEquals(Optional.of(card4), ai.suitableAttackMoveWhenDeckEnds(tableFor));
//    }
//
//    @Test
//    void maxValueBySuit() {
//    }
//
//    @Test
//    void suitableDefMoveWhenDeckEnds() {
//        Deck deck = new Deck();
//        Card cardForDef = new Card("Diamonds", 14, false);
//        player1.getHand().add(cardForDef);
//        for (Card card : deck.getDeck()) {
//            if (!ai.getHand().contains(card) && !tableFor.getPile().getPile().contains(card) && !player1.getHand().contains(card)) {
//                tableFor.addToPile(card);
//            }
//        }
//        tableFor.addCardOnTable(cardForDef);
//        assertEquals(Optional.of(card3), ai.suitableDefMoveWhenDeckEnds(tableFor));
//    }
//
//    @Test
//    void getAiMove() {
//    }
//}