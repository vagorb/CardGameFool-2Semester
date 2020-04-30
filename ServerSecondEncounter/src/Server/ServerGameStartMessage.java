package Server;

import com.card.game.fool.cards.Card;
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


    public JsonObject getMessage() {
        JsonObject obj = new JsonObject();
        Card card = Server.getDeck().getDeck().get(0);
        Server.getDeck().removeCard(card);
        //System.out.println(card + "first card");
        obj.addProperty("Suit", card.getSuit());
        obj.addProperty("Value", card.getValue());
        obj.addProperty("Trump", card.getTrump());
        obj.addProperty("Id", card.getId());
        return obj;
    }

    public JsonObject getTrumpMessage() {
        JsonObject obj = new JsonObject();
        Card card = Server.getTrump();
        //System.out.println(card + "first card");
        obj.addProperty("Suit", card.getSuit());
        obj.addProperty("Value", card.getValue());
        obj.addProperty("Trump", card.getTrump());
        obj.addProperty("Id", card.getId());
        return obj;
    }

    public JsonArray refreshCardsMessage(Integer cardsNeeded) {
        JsonArray array = new JsonArray();
        for (int i = 0; i < cardsNeeded; i++) {
            JsonObject obj = new JsonObject();
            Card card = Server.getDeck().getDeck().get(0);
            Server.getDeck().removeCard(card);
            obj.addProperty("Suit", card.getSuit());
            obj.addProperty("Value", card.getValue());
            obj.addProperty("Trump", card.getTrump());
            obj.addProperty("Id", card.getId());
            array.add(obj);
        }
        return array;
    }


    public JsonArray getGameStartMessage() {
        JsonArray array = new JsonArray();
        for (int i = 0; i < 6; i++) {
            JsonObject obj = new JsonObject();
            Card card = Server.getDeck().getDeck().get(0);
            Server.getDeck().removeCard(card);
            obj.addProperty("Suit", card.getSuit());
            obj.addProperty("Value", card.getValue());
            obj.addProperty("Trump", card.getTrump());
            obj.addProperty("Id", card.getId());
            array.add(obj);
        }
        return array;
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
