import com.card.game.fool.cards.Card;
import com.card.game.fool.logic.TurnCalculation;
import com.card.game.fool.players.Player;
import com.card.game.fool.tables.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TurnCalculationTest {


    @Test
    public void TestAddToPile() {
        Player player1 = new Player();
        Player player2 = new Player();
        Table tableForTwo = new Table(player1, player2);
        Card card = new Card("Hearts", 9, false);
        Card card2 = new Card("Hearts", 12, false);
        TurnCalculation turnCalculation = new TurnCalculation(tableForTwo);
        turnCalculation.putAttackCard(player1, card);
        turnCalculation.putDefenseCard(player2, card2);
        turnCalculation.compareCards();
        assertTrue(turnCalculation.getDefenseSuccess());
        turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(player2);
        System.out.println(tableForTwo.getPile().getPile().size());
    }

    @Test
    public void TestAddToPlayerHand() {
        Player player1 = new Player();
        Player player2 = new Player();
        Table tableForTwo = new Table(player1, player2);
        Card card = new Card("Hearts", 9, false);
        Card card2 = new Card("Hearts", 6, false);
        TurnCalculation turnCalculation = new TurnCalculation(tableForTwo);
        turnCalculation.putAttackCard(player1, card);
        turnCalculation.putDefenseCard(player2, card2);
        turnCalculation.compareCards();
        // There are some issues with this one, i need to rethink how to do some methods, but if they are changed then the intended output is correct. || fixed
        assertFalse(turnCalculation.getDefenseSuccess());
        turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(player2);
        System.out.println(tableForTwo.getPile().getPile().size());
        System.out.println(tableForTwo.getPlayers().get(1).getHand().size());
    }

    @Test
    public void TestPutAttackCard() {
        Player player1 = new Player();
        Player player2 = new Player();
        Table tableForTwo = new Table(player1, player2);
        Card card = new Card("Hearts", 9, false);
        TurnCalculation turnCalculation = new TurnCalculation(tableForTwo);
        turnCalculation.putAttackCard(player1, card);
        assertEquals(card, turnCalculation.getAttackCard());
    }

    @Test
    public void TestPutDefenseCard() {
        Player player1 = new Player();
        Player player2 = new Player();
        Table tableForTwo = new Table(player1, player2);
        Card card = new Card("Hearts", 9, false);
        Card card2 = new Card("Hearts", 12, false);
        TurnCalculation turnCalculation = new TurnCalculation(tableForTwo);
        turnCalculation.putAttackCard(player1, card);
        turnCalculation.putDefenseCard(player2, card2);
        assertEquals(card2, turnCalculation.getDefenseCard());
    }


    @Test
    public void TestAttackAndDefenseTurns() {
        Player player1 = new Player();
        Player player2 = new Player();
        Table tableForTwo = new Table(player1, player2);
        TurnCalculation turnCalculation = new TurnCalculation(tableForTwo);
        Card card1 = new Card("Hearts", 6, false);
        Card card2 = new Card("Hearts", 7, false);
        Card card3 = new Card("Hearts", 8, false);
        Card card4 = new Card("Diamonds", 9, false);
        Card card5 = new Card("Diamonds", 10, false);
        Card card6 = new Card("Diamonds", 11, false);

        Card card7 = new Card("Diamonds", 6, false);
        Card card8 = new Card("Diamonds", 7, false);
        Card card9 = new Card("Diamonds", 8, false);
        Card card10 = new Card("Hearts", 9, false);
        Card card11 = new Card("Hearts", 10, false);
        Card card12 = new Card("Hearts", 11, false);

        player1.getHand().add(card1);
        player1.getHand().add(card2);
        player1.getHand().add(card3);
        player1.getHand().add(card4);
        player1.getHand().add(card5);
        player1.getHand().add(card6);

        player2.getHand().add(card7);
        player2.getHand().add(card8);
        player2.getHand().add(card9);
        player2.getHand().add(card10);
        player2.getHand().add(card11);
        player2.getHand().add(card12);

        turnCalculation.putAttackCard(player1, player1.getHand().get(0));
        turnCalculation.putDefenseCard(player2, player2.getHand().get(3));

        turnCalculation.putAttackCard(player1, player1.getHand().get(0));
        turnCalculation.putDefenseCard(player2, player2.getHand().get(3));

        System.out.println(player1.getHand());
        System.out.println(player2.getHand());





    }
}
