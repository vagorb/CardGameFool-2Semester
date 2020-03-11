import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {

    @Test
    public void player() {
        Hand hand = new Hand();
        Player player = new Player("SW", 0, hand);
        assertEquals(hand, player.getHand());
        assertEquals(0, player.getScore());
        assertEquals("SW", player.getName());
    }

    @Test
    public void playerAddScore() {
        Hand hand = new Hand();
        Player player = new Player("SW", 0, hand);
        player.addScore(497675);
        assertEquals(497675, player.getScore());

        Hand hand1 = new Hand();
        Player player1 = new Player("SW", 0, hand1);
        player1.addScore(-46476);
        assertEquals(0, player1.getScore());


    }


}
