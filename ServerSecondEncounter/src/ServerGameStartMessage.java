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
    public Deck getDeck() {
        return deck;
    }

    public Card getTrumpCard() {
        return trumpCard;
    }

    public JsonObject getMessage() {
        JsonObject obj = new JsonObject();
        Card card = getDeck().getDeck().get(0);
        deck.removeCard(card);
        //System.out.println(card);
        obj.addProperty("Suit", card.getSuit());
        obj.addProperty("Value", card.getValue());
        obj.addProperty("Trump", card.getTrump());
        obj.addProperty("Id", card.getId());
        return obj;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server received " + msg);
        ctx.write(getMessage());
        ctx.writeAndFlush(getMessage());
                //.addListener(ChannelFutureListener.CLOSE);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    Deck deck;
    Card trumpCard;
    ServerGameStartMessage() {
        this.deck = new Deck();
        deck.shuffleDeck();
        this.trumpCard = deck.getDeck().get(12);
        deck.removeCard(trumpCard);
        trumpCard.setTrump(true);
        deck.makeCardsTrump(trumpCard.getSuit());
    }




}
