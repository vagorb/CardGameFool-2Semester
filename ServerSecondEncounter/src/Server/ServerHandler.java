package Server;

import com.card.game.fool.cards.Deck;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private ServerGameStartMessage message = new ServerGameStartMessage();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server.Server received " + msg);
        String str = msg.toString();
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
//        JsonObject jsonObject = JsonParser.parseString()
        System.out.println(jsonObject);
        String string = jsonObject.get("MessageType").toString();
        System.out.println(string);
        string = string.replace("\"", "");

//        if(mesage.type == "newPLayer"){
//            // starting new game
//            Game game = new Game()
//                    //add player 1
//            // add player 2
////            game.addPlayer()
////            game.addPlayer()
////            Deck deck = new Deck();
////            game.add(deck)
//            Server.games.add(game);
//        }
//
//        if(message.type == "PlayerMOve") {
//            game = Server.games.getGameByPlayer(playerId);
//            game.move(mesage)
//        }

        if (string.equalsIgnoreCase("gameStart")) {
            //
            Server.players.add("Player1");

            //todo wait for other players
            // creating new game
            List<String> gamePLayers = new ArrayList<>();
            gamePLayers.add(Server.players.get(0));
            GameInfo gameInfo = new GameInfo(new Deck(), gamePLayers);
            Server.games.add(gameInfo);
            Server.playersToGames.put("Player1", gameInfo);
            // send gameinfo id to clients

            ctx.write(message.getTrumpMessage() + "\r\n");
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);

        } else if (string.equalsIgnoreCase("Replenish")) {
            // change this later
            ctx.write(message.getMessage() + "\r\n");
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        } else if (string.equalsIgnoreCase("Skip")) {
            String hand = jsonObject.get("HandSize").toString();
            hand = hand.replace("\"", "");
            ctx.write(message.refreshCardsMessage(6 - Integer.parseInt(hand)) + "\r\n");
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        } else if (string.equalsIgnoreCase("GameMove")) {

            //playerID = message.getPlayerId()
//            Server.playersToGames.get(playerId);
            GameInfo game = Server.playersToGames.get("Player1");
            //todo apply move to game
//            game.getDeck();
//            game.ge

            ctx.write(message.getMessage() + "\r\n");
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
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