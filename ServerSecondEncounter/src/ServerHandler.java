import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.google.gson.JsonObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private ServerGameStartMessage message = new ServerGameStartMessage();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server received " + msg);
        //JsonObject msgg = message.getMessage();
        //System.out.println(msgg);
        ctx.write(message.getMessage() + "\r\n");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);

            //.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}