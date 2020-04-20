import com.card.game.fool.players.Hand;
import com.card.game.fool.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {

    @Test
    public void TestPlayer() {
        Hand hand = new Hand();
        Player player = new Player("SW", 0, hand);
        assertEquals(hand, player.getHand());
        assertEquals(0, player.getScore());
        assertEquals("SW", player.getName());
    }

    @Test
    public void TestPlayerAddScore() {
        Hand hand = new Hand();
        Player player = new Player("SW", 0, hand);
        player.addScore(497675);
        assertEquals(497675, player.getScore());

        Hand hand1 = new Hand();
        Player player1 = new Player("SW", 0, hand1);
        player1.addScore(-46476);
        assertEquals(0, player1.getScore());
    }

    @Test
    public void TestGetSetPlayerState() {
        Hand hand = new Hand();
        Hand hand2 = new Hand();
        Player player = new Player("SW", 0, hand);
        Player player2 = new Player("SWS", 0, hand2);
        player.setPlayerState(Player.PlayerState.ATTACK);
        player2.setPlayerState(Player.PlayerState.DEFENSE);
        assertEquals(Player.PlayerState.ATTACK, player.getPlayerState());
        assertEquals(Player.PlayerState.DEFENSE, player2.getPlayerState());
    }


}
