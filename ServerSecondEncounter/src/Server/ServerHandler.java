package Server;

import Server.model.Message;
import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;
import java.util.UUID;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private Gson gson = new Gson();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Server received: " + msg);
        String str = msg.toString();
        JsonObject message = JsonParser.parseString(str).getAsJsonObject();
        String messageType = message.get("type").getAsString();
        String player = message.get("playerId").getAsString();
        if (messageType.equalsIgnoreCase("newPlayer")) {
            GameInfo game = Server.addPlayerToGame(player);
            ctx.writeAndFlush(gson.toJson(game) + "\r\n");
        } else if (messageType.equalsIgnoreCase("replenish")) {
            List<Card> cards = Server.replenishPlayerCards(player, message.get("cardsInHand").getAsInt());
            ctx.writeAndFlush(gson.toJson(cards) + "\r\n");
        }



    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }

}
