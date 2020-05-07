import com.card.game.fool.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    public void TestPlayer() {
        Player player = new Player("SW", 0);
        assertEquals(0, player.getScore());
        assertEquals("SW", player.getName());
    }

    @Test
    public void TestPlayerAddScore() {
        Player player = new Player("SW", 0);
        player.addScore(497675);
        assertEquals(497675, player.getScore());

        Player player1 = new Player("SW", 0);
        player1.addScore(-46476);
        assertEquals(0, player1.getScore());
    }

    @Test
    public void TestGetSetPlayerState() {
        Player player = new Player("SW", 0);
        Player player2 = new Player("SWS", 0);
        player.setPlayerState(Player.PlayerState.ATTACK);
        player2.setPlayerState(Player.PlayerState.DEFENSE);
        assertEquals(Player.PlayerState.ATTACK, player.getPlayerState());
        assertEquals(Player.PlayerState.DEFENSE, player2.getPlayerState());
    }


}
