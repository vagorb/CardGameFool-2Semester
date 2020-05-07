package Server;

import com.card.game.fool.cards.Card;
import com.card.game.fool.players.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerGameStartMessage extends ChannelInboundHandlerAdapter {

    //    public Deck getDeck() {
//        return deck;
//    }
//
//    public Card getTrumpCard() {
//        return trumpCard;
//    }
    public void moveMessage(JsonObject json, GameInfo gameInfo) {
        String suit = json.get("Suit").toString();
        suit = suit.replace("\"", "");
        String value = json.get("Value").toString();
        value = value.replace("\"", "");
        int val = Integer.parseInt(value);
        String trump = json.get("Trump").toString();
        trump = trump.replace("\"", "");
        Boolean tru = Boolean.parseBoolean(trump);
        Card card = new Card(suit, val, tru);
        gameInfo.addMoveCard(card);
    }

    public synchronized JsonObject getMessage(GameInfo gameinfo) {
        JsonObject obj = new JsonObject();
        if (gameinfo.getDeck().getDeck().size() > 0) {
            Card card = gameinfo.getCard();
            //System.out.println(card + "first card");
            obj.addProperty("Suit", card.getSuit());
            obj.addProperty("Value", card.getValue());
            obj.addProperty("Trump", card.getTrump());
            obj.addProperty("Id", card.getId());
            return obj;
        } else {
            obj.addProperty("Card", "NoCards");
            return obj;
        }
    }



    public synchronized JsonObject getTrumpMessage(GameInfo gameinfo) {
        JsonObject obj = new JsonObject();
        Card card = gameinfo.getTrump();
        //System.out.println(card + "first card");
        obj.addProperty("MessageType", "getTrump");
        obj.addProperty("Suit", card.getSuit());
        obj.addProperty("Value", card.getValue());
        obj.addProperty("Trump", card.getTrump());
        obj.addProperty("Id", card.getId());
        return obj;
    }



    public synchronized JsonObject canAttackGameStart(GameInfo gameInfo) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("GameInfo", "gameIsReady");
        jsonObject.addProperty("State", "ATTACK");
        return jsonObject;
    }

    public synchronized JsonObject canDefGameStart(GameInfo gameInfo) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("GameInfo", "gameIsReady");
        jsonObject.addProperty("State", "DEFENCE");
        return jsonObject;
    }


    public JsonObject skipMessage(GameInfo gameInfo) {
        JsonObject obj = new JsonObject();
        obj.addProperty("MessageType", "SKIP");
        return obj;
    }

    public JsonObject takeMessage(GameInfo gameInfo) {
        JsonObject obj = new JsonObject();
        obj.addProperty("MessageType", "TAKE");
        return obj;
    }

    public JsonObject updateTable(GameInfo gameInfo, Integer size) {
        JsonObject obj = new JsonObject();
        obj.addProperty("MessageType", "getOpponentCard");
        if (gameInfo.getCards().size() > size) {
            Card card = gameInfo.getMoveCard();
            obj.addProperty("MessageType", "Send");
            obj.addProperty("Suit", card.getSuit());
            obj.addProperty("Value", card.getValue());
            obj.addProperty("Trump", card.getTrump());
            obj.addProperty("Id", card.getId());
            return obj;
        } else {
            obj.addProperty("MessageType", "WAIT");
            return obj;
        }

    }


//    Deck deck;
//    Card trumpCard;
//    Server.ServerGameStartMessage() {
//        this.deck = new Deck();
//        deck.shuffleDeck();
//        this.trumpCard = deck.getDeck().get(12);
//        deck.removeCard(trumpCard);
//        trumpCard.setTrump(true);
//        deck.makeCardsTrump(trumpCard.getSuit());
//    }


    //static Deck deck = new Deck();

}
