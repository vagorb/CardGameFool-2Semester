import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.google.gson.JsonObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server received " + msg);
       // System.out.println(getMessage());
        ctx.write(getMessage() + "\r\n");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
            //.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    public Deck getDeck() {
        return deck;
    }

    public Card getTrumpCard() {
        return trumpCard;
    }

    public JsonObject getMessage() {
        ServerGameStartMessage();
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


    Deck deck;
    Card trumpCard;
    public void ServerGameStartMessage() {
        this.deck = new Deck();
        deck.shuffleDeck();
        this.trumpCard = deck.getDeck().get(12);
        deck.removeCard(trumpCard);
        trumpCard.setTrump(true);
        deck.makeCardsTrump(trumpCard.getSuit());

    }
}