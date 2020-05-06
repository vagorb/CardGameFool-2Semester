import com.card.game.fool.cards.Card;
import com.card.game.fool.players.Hand;
import com.card.game.fool.players.Player;
import com.card.game.fool.tables.Table;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableTest {


    @Test
    public void TestTableCreatedWithDifferentConstructors() {
        Player player1 = new Player("Ja", 0);
        Player player2 = new Player("Ochen", 0);
        Player player3 = new Player("Hochy", 0);
        Player player4 = new Player("Kushat", 0);
        Table tableForTwo = new Table(player1, player2);
        assertEquals(2, tableForTwo.getPlayers().size());
        System.out.println(tableForTwo.getGameDeck());
        Table tableForThree = new Table(player1, player2, player3);
        assertEquals(3, tableForThree.getPlayers().size());
        System.out.println(tableForThree.getGameDeck());
        Table tableForFour = new Table(player1, player2, player3, player4);
        assertEquals(4, tableForFour.getPlayers().size());
        System.out.println(tableForFour.getGameDeck());

    }

    @Test
    public void TestTableGetters() {
        Player player1 = new Player("Ja", 0);
        Player player2 = new Player("Ochen", 0);
        Table tableForTwo = new Table(player1, player2);
        System.out.println(tableForTwo.getPlayers());
        assertEquals(2, tableForTwo.getPlayers().size());
        // Works
        System.out.println(tableForTwo.getGameDeck());
        assertEquals(36, tableForTwo.getGameDeck().size());
        // Works
        System.out.println(tableForTwo.getPile());
        assertEquals(0, tableForTwo.getPile().getPile().size());
        // Works
    }

    @Test
    public void TestAddToPile() {
        Player player1 = new Player("Ja", 0);
        Player player2 = new Player("Ochen", 0);
        Table tableForTwo = new Table(player1, player2);
        Card card = new Card("Hearts", 9, false);
        tableForTwo.getPile().addDiscardedCards(card);
        assertEquals(1, tableForTwo.getPile().getPile().size());
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        assertEquals(cards, tableForTwo.getPile().getPile());
    }
    
    // Need to make it work
    @Test
    public void getTable() {
        Player player1 = new Player("Ja", 0);
        Player player2 = new Player("Ochen", 0);
        Table tableForTwo = new Table(player1, player2);
        Card card = new Card("Hearts", 9, false);
        tableForTwo.addToPile(card);
        System.out.println(tableForTwo.getTable());
    }
    @Test
    public void putAttackOrDefenseCards() {

    }

    @Test
    public void compareCards() {
        Player player1 = new Player("Ja", 0);
        Player player2 = new Player("Ochen", 0);
        Table tableForTwo = new Table(player1, player2);
    }
}
