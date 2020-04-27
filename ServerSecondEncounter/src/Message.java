import com.card.game.fool.cards.Card;
import com.card.game.fool.players.Player;
import com.card.game.fool.tables.Table;
import com.google.gson.JsonObject;


public class Message {
    public enum MessageType {
        gameStart, playerMove, gameStateUpdate
    }
    private Table table;
    private Player player;

    public JsonObject getMessage(MessageType type) {
        if (type.equals(MessageType.gameStateUpdate)) {
            JsonObject obj = new JsonObject();
            obj.addProperty("MessageType", "gameStateUpdate");
            //obj.get("MessageType").getAsJsonObject().addProperty("DeckSize", 30); // table.getGameDeck().size()
            return obj;
        } else if (type.equals(MessageType.playerMove)) {
            Card card = new Card("Hearts", 9, false);
            JsonObject obj = new JsonObject();
            obj.addProperty("MessageType", "gameMove");
            obj.addProperty("PlayerName", "Sanja"); //player.getName()
            obj.addProperty("MoveType", card.toString()); //player.getPlayerState().toString()
            //if (!player.getPlayerState().equals(Player.PlayerState.SKIP)) {
                obj.addProperty("Card","Seven_of_Hearts"); //table.getLastCardOnTable().getId());
            //}
            return obj;
        }

        return null;
    }
}