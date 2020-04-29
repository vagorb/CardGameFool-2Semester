package Server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.Arrays;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private ServerGameStartMessage message = new ServerGameStartMessage();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server.Server received " + msg);
        String str = msg.toString();
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        System.out.println(jsonObject);
        String string = jsonObject.get("MessageType").toString();
        System.out.println(string);
        string = string.replace("\"", "");

        if (string.equalsIgnoreCase("gameStart")) {
            ctx.write(message.getGameStartMessage() + "\r\n");
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        } else if (string.equalsIgnoreCase("gameMove")) {
            // change this later
            ctx.write(message.getMessage() + "\r\n");
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        } else if (string.equalsIgnoreCase("Skip")) {
            //
        }
//        ctx.write(message.getMessage() + "\r\n");
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        //JsonObject msgg = message.getMessage();
        //System.out.println(msgg);
//        ctx.write(message.getGameStartMessage() + "\r\n");
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);

            //.addListener(ChannelFutureListener.CLOSE);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}