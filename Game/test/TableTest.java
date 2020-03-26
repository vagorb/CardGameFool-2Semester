import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableTest {


//    @Test
//    public void tableCreatedWithDifferentConstructors() {
//        Hand hand1 = new Hand();
//        Player player1 = new Player("Ja", 0, hand1);
//        Hand hand2 = new Hand();
//        Player player2 = new Player("Ochen", 0, hand2);
//        Hand hand3 = new Hand();
//        Player player3 = new Player("Hochy", 0, hand3);
//        Hand hand4 = new Hand();
//        Player player4 = new Player("Kushat", 0, hand4);
//        Table tableForTwo = new Table(player1, player2);
//        assertEquals(2, tableForTwo.getPlayers().size());
//        System.out.println(tableForTwo.getGameDeck());
//        Table tableForThree = new Table(player1, player2, player3);
//        assertEquals(3, tableForThree.getPlayers().size());
//        System.out.println(tableForThree.getGameDeck());
//        Table tableForFour = new Table(player1, player2, player3, player4);
//        assertEquals(4, tableForFour.getPlayers().size());
//        System.out.println(tableForFour.getGameDeck());
//    }
//
//    @Test
//    public void tableGetters() {
//        Hand hand1 = new Hand();
//        Player player1 = new Player("Ja", 0, hand1);
//        Hand hand2 = new Hand();
//        Player player2 = new Player("Ochen", 0, hand2);
//        Table tableForTwo = new Table(player1, player2);
//        System.out.println(tableForTwo.getPlayers());
//        assertEquals(2, tableForTwo.getPlayers().size());
//        // Works
//        System.out.println(tableForTwo.getGameDeck());
//        assertEquals(36, tableForTwo.getGameDeck().size());
//        // Works
//        System.out.println(tableForTwo.getPile());
//        assertEquals(0, tableForTwo.getPile().size());
//        // Works
//    }
//
//    @Test
//    public void testAddToPile() {
//        Hand hand1 = new Hand();
//        Player player1 = new Player("Ja", 0, hand1);
//        Hand hand2 = new Hand();
//        Player player2 = new Player("Ochen", 0, hand2);
//        Table tableForTwo = new Table(player1, player2);
//        Card card = new Card("Hearts", 9, false);
//        tableForTwo.addToPile(card);
//        assertEquals(1, tableForTwo.getPile().size());
//        List<Card> cards = new ArrayList<>();
//        cards.add(card);
//        assertEquals(cards, tableForTwo.getPile());
//    }

//    @Test
//    public void putAttackOrDefenseCards() {
//
//    }

//    @Test
//    public void compareCards() {
//        Hand hand1 = new Hand();
//        Player player1 = new Player("Ja", 0, hand1);
//        Hand hand2 = new Hand();
//        Player player2 = new Player("Ochen", 0, hand2);
//        Table tableForTwo = new Table(player1, player2);
//    }
}
