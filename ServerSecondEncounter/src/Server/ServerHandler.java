package Server;

import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private ServerGameStartMessage message = new ServerGameStartMessage();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server.Server received " + msg);
        String str = msg.toString();
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
//        JsonObject jsonObject = JsonParser.parseString()
        System.out.println(jsonObject);
        String messageType = jsonObject.get("MessageType").toString();
        System.out.println(messageType);
        messageType = messageType.replace("\"", "");
        if(messageType.equalsIgnoreCase("GameStart")) {
            String messageGameSize = jsonObject.get("GameSize").toString();
            messageGameSize = messageGameSize.replace("\"", "");
            int gameSize = Integer.parseInt(messageGameSize);
            if (gameSize == 2) {
                String uuid = jsonObject.get("UUID").toString();
                uuid = uuid.replace("\"", "");
                Server.gameForTwo.add(uuid);
                if (Server.gameForTwo.size() == 2) {
                    GameInfo gameInfo = new GameInfo(new Deck(), Server.gameForTwo);
                    Server.players.addAll(Server.gameForTwo);
                    for (String player : Server.gameForTwo){
                        Server.playersToGames.put(player, gameInfo);
                    }
                    for (String player : Server.gameForTwo) {
                        ctx.write(message.canGameStart(gameInfo) + "\r\n");
                        ctx.writeAndFlush(UUID.fromString(player));
                    }
                    Server.gameForTwo.clear();

                }
           }
        } else if (messageType.equalsIgnoreCase("replenish")) {
            String uuid = jsonObject.get("UUID").toString();
            uuid = uuid.replace("\"", "");
            GameInfo game = Server.playersToGames.get(uuid);
            ctx.write(message.getMessage(game) + "\r\n");
            ctx.writeAndFlush(UUID.fromString(uuid));
        } else if (messageType.equalsIgnoreCase("getTrump")) {
            String uuid = jsonObject.get("UUID").toString();
            uuid = uuid.replace("\"", "");
            GameInfo game = Server.playersToGames.get(uuid);
            ctx.write(message.getTrumpMessage(game) + "\r\n");
            ctx.writeAndFlush(UUID.fromString(uuid));
        } else if (messageType.equalsIgnoreCase("SKIP")) {
            String uuid = jsonObject.get("UUID").toString();
            uuid = uuid.replace("\"", "");
            GameInfo game = Server.playersToGames.get(uuid);
            for (String player : game.getPlayers()) {
                if (!player.equalsIgnoreCase(uuid)) {
                    ctx.write(message.skipMessage(game) + "\r\n");
                    ctx.writeAndFlush(UUID.fromString(uuid));
                }
            }
        } else if (messageType.equalsIgnoreCase("TAKE")) {
            String uuid = jsonObject.get("UUID").toString();
            uuid = uuid.replace("\"", "");
            GameInfo game = Server.playersToGames.get(uuid);
            for (String player : game.getPlayers()) {
                if (!player.equalsIgnoreCase(uuid)) {
                    ctx.write(message.takeMessage(game) + "\r\n");
                    ctx.writeAndFlush(UUID.fromString(uuid));
                }
            }
        } else if (messageType.equalsIgnoreCase("GameMove")) {
            String uuid = jsonObject.get("UUID").toString();
            uuid = uuid.replace("\"", "");
            GameInfo game = Server.playersToGames.get(uuid);
            for (String player : game.getPlayers()) {
                if (!player.equalsIgnoreCase(uuid)) {
                    ctx.write(message.moveMessage(jsonObject) + "\r\n");
                    ctx.writeAndFlush(UUID.fromString(uuid));
                }
            }
        }


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

//        if (messageType.equalsIgnoreCase("gameStart")) {
//            //
//            Server.players.add("Player1");
//
//            //todo wait for other players
//            // creating new game
//            List<String> gamePLayers = new ArrayList<>();
//            gamePLayers.add(Server.players.get(0));
//            GameInfo gameInfo = new GameInfo(new Deck(), gamePLayers);
//            Server.games.add(gameInfo);
//            Server.playersToGames.put("Player1", gameInfo);
//            // send gameinfo id to clients

//            ctx.write(message.getTrumpMessage() + "\r\n");
//            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);

//        } else if (messageType.equalsIgnoreCase("Replenish")) {
//            // change this later
//            ctx.write(message.getMessage() + "\r\n");
//            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER); //UUID sjuda ebat
//        } else if (messageType.equalsIgnoreCase("Skip")) {
//            String hand = jsonObject.get("HandSize").toString();
//            hand = hand.replace("\"", "");
//            ctx.write(message.refreshCardsMessage(6 - Integer.parseInt(hand)) + "\r\n");
//            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
//        } else if (messageType.equalsIgnoreCase("GameMove")) {
//
//        //playerID = message.getPlayerId()
////            Server.playersToGames.get(playerId);
//        GameInfo game = Server.playersToGames.get("Player1");
//        //todo apply move to game
////            game.getDeck();
////            game.ge
//
//        ctx.write(message.getMessage() + "\r\n");
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
//        }
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