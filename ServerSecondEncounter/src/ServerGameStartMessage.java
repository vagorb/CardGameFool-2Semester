import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
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
    private boolean hui = true;

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


    //static Deck deck = new Deck();

}
