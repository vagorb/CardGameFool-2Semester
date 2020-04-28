import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.Hand;
import com.card.game.fool.players.Player;
import com.google.gson.JsonObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


import java.util.ArrayList;
import java.util.stream.Collectors;

public class ServerGameStartMessage extends ChannelInboundHandlerAdapter {

//    public Deck getDeck() {
//        return deck;
//    }
//
//    public Card getTrumpCard() {
//        return trumpCard;
//    }
    private boolean hui = true;

    public JsonObject getMessage() {
        JsonObject obj = new JsonObject();
        Card card = getDick().getDeck().get(0);
        dick.removeCard(card);
        //System.out.println(card + "first card");
        obj.addProperty("Suit", card.getSuit());
        obj.addProperty("Value", card.getValue());
        obj.addProperty("Trump", card.getTrump());
        obj.addProperty("Id", card.getId());
        return obj;
    }

//    Deck deck;
//    Card trumpCard;
//    ServerGameStartMessage() {
//        this.deck = new Deck();
//        deck.shuffleDeck();
//        this.trumpCard = deck.getDeck().get(12);
//        deck.removeCard(trumpCard);
//        trumpCard.setTrump(true);
//        deck.makeCardsTrump(trumpCard.getSuit());
//    }


    static Deck dick = new Deck();

    static Deck getDick() {
        return dick;
    }



    static Card decideTrump() {
        Card card = dick.getDeck().get(12);
        dick.getDeck().remove(card);
        return card;
    }

    static void shuffle() {
        dick.shuffleDeck();
    }

    static void makeTrump() {
        dick.makeCardsTrump(decideTrump().getSuit());
    }




}
