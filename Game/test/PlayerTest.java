import com.card.game.fool.players.Player;
import com.card.game.fool.players.PlayerState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    public void TestPlayer() {
        Player player = new Player();
        player.setName("SW");
        assertEquals("SW", player.getName());
    }

    @Test
    public void TestGetSetPlayerState() {
        Player player = new Player();
        Player player2 = new Player();
        player.setPlayerState(PlayerState.ATTACK);
        player2.setPlayerState(PlayerState.DEFENSE);
        assertEquals(PlayerState.ATTACK, player.getPlayerState());
        assertEquals(PlayerState.DEFENSE, player2.getPlayerState());
    }


}
