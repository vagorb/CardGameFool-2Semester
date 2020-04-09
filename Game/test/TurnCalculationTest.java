import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TurnCalculationTest {


//    @Test
//    public void TestAddToPile() {
//        Hand hand1 = new Hand();
//        Player player1 = new Player("Ja", 0, hand1);
//        Hand hand2 = new Hand();
//        Player player2 = new Player("Ochen", 0, hand2);
//        Table tableForTwo = new Table(player1, player2);
//        Card card = new Card("Hearts", 9, false);
//        Card card2 = new Card("Hearts", 12, false);
//        TurnCalculation turnCalculation = new TurnCalculation(tableForTwo);
//        turnCalculation.putAttackCard(player1, card);
//        turnCalculation.putDefenseCard(player2, card2);
//        turnCalculation.compareCards();
//        assertTrue(turnCalculation.getCurrentDefenseState());
//        turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(player2);
//        System.out.println(tableForTwo.getPile().size());
//    }

//    @Test
//    public void TestAddToPlayerHand() {
//        Hand hand1 = new Hand();
//        Player player1 = new Player("Ja", 0, hand1);
//        Hand hand2 = new Hand();
//        Player player2 = new Player("Ochen", 0, hand2);
//        Table tableForTwo = new Table(player1, player2);
//        Card card = new Card("Hearts", 9, false);
//        Card card2 = new Card("Hearts", 6, false);
//        TurnCalculation turnCalculation = new TurnCalculation(tableForTwo);
//        turnCalculation.putAttackCard(player1, card);
//        turnCalculation.putDefenseCard(player2, card2);
//        turnCalculation.compareCards();
//        // There are some issues with this one, i need to rethink how to do some methods, but if they are changed then the intended output is correct. || fixed
//        assertFalse(turnCalculation.getCurrentDefenseState());
//        turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(player2);
//        System.out.println(tableForTwo.getPile().size());
//        System.out.println(tableForTwo.getPlayers().get(1).getHand().getCardsInHand().size());
//    }

    @Test
    public void TestPutAttackCard() {
        Hand hand1 = new Hand();
        Player player1 = new Player("Ja", 0, hand1);
        Hand hand2 = new Hand();
        Player player2 = new Player("Ochen", 0, hand2);
        Table tableForTwo = new Table(player1, player2);
        Card card = new Card("Hearts", 9, false);
        Card card2 = new Card("Hearts", 12, false);
        TurnCalculation turnCalculation = new TurnCalculation(tableForTwo);
        turnCalculation.putAttackCard(player1, card);
        assertEquals(card, turnCalculation.getAttackCard());
    }

    @Test
    public void TestPutDefenseCard() {
        Hand hand1 = new Hand();
        Player player1 = new Player("Ja", 0, hand1);
        Hand hand2 = new Hand();
        Player player2 = new Player("Ochen", 0, hand2);
        Table tableForTwo = new Table(player1, player2);
        Card card = new Card("Hearts", 9, false);
        Card card2 = new Card("Hearts", 12, false);
        TurnCalculation turnCalculation = new TurnCalculation(tableForTwo);
        turnCalculation.putAttackCard(player1, card);
        turnCalculation.putDefenseCard(player2, card2);
        assertEquals(card2, turnCalculation.getDefenseCard());
    }
}
