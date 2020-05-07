package Server;

import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;
import java.util.UUID;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private ServerGameStartMessage message = new ServerGameStartMessage();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server.Server received " + msg);
        System.out.println(Server.playersToGames);
        System.out.println(Server.games);
        String str = msg.toString();
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
//        JsonObject jsonObject = JsonParser.parseString()
//        System.out.println(jsonObject);
        String messageType = jsonObject.get("MessageType").toString();
//        System.out.println(messageType);
        messageType = messageType.replace("\"", "");
        if(messageType.equalsIgnoreCase("GameStart")) {
            String messageGameSize = jsonObject.get("GameSize").toString();
            messageGameSize = messageGameSize.replace("\"", "");
            int gameSize = Integer.parseInt(messageGameSize);
            if (gameSize == 2) {
                String uuid = jsonObject.get("UUID").toString();
                uuid = uuid.replace("\"", "");
//                if (Server.gameForTwo.size() == 1 && Server.games.size() < 1) {
//                    GameInfo gameInfo = new GameInfo(new Deck(), Server.gameForTwo);
//                    Server.games.add(gameInfo);
//                    ctx.write("WAIT" + "\r\n");
//                    ctx.writeAndFlush(UUID.fromString(uuid));
                 if (Server.gameForTwo.size() == 2) {
                     if (uuid.equalsIgnoreCase(Server.gameForTwo.get(0))) {
                         GameInfo gameInfo = new GameInfo(new Deck(), List.of(Server.gameForTwo.get(0), Server.gameForTwo.get(1)));
                         Server.games.add(gameInfo);
                         for (String player : Server.gameForTwo) {
                             Server.playersToGames.put(player, gameInfo);
                         }
                         ctx.write(message.canAttackGameStart(gameInfo) + "\r\n");
                         ctx.writeAndFlush(UUID.fromString(uuid));
                     } else {
                         if (Server.playersToGames.containsKey(uuid)) {
                             GameInfo gameInfo = Server.games.get(0);
                             ctx.write(message.canDefGameStart(gameInfo) + "\r\n");
                             ctx.writeAndFlush(UUID.fromString(uuid));
                             Server.games.clear();
                             Server.gameForTwo.clear();
                         } else {
                             ctx.write("WAIT" + "\r\n");
                             ctx.writeAndFlush(UUID.fromString(uuid));
                         }

                     }
                         //                           System.out.println(Server.playersToGames);
                         //                           System.out.println(Server.gameForTwo);
//                        Server.gameForTwo.clear(); TODO This
                 } else {
                     if (!Server.gameForTwo.contains(uuid)) {
                         Server.gameForTwo.add(uuid);
                         System.out.println(Server.playersToGames);
                     }
//                        System.out.println(Server.playersToGames);
//                        System.out.println(Server.gameForTwo);
                         ctx.write("WAIT" + "\r\n");
                         ctx.writeAndFlush(UUID.fromString(uuid));
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
                } else {
                    ctx.write("RECEIVED" + "\r\n");
                }
                ctx.writeAndFlush(UUID.fromString(uuid));
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
//            for (String player : game.getPlayers()) {
//                if (!player.equalsIgnoreCase(uuid)) {
//                    System.out.println("LUL KEK");
//                    ctx.write(message.updateTable(game) + "\r\n");
//                    ctx.writeAndFlush(UUID.fromString(uuid));
//                } else {
//                    System.out.println("RECEIVED");
//                    //ctx.write("RECEIVED" + "\r\n");
//                }
//            }
            message.moveMessage(jsonObject, game);
            if (game.getMoveCard().getValue() != 100) {
                ctx.write("RECEIVED" + "\r\n");
                ctx.writeAndFlush(UUID.fromString(uuid));
            }
        } else if (messageType.equalsIgnoreCase("getOpponentCard")) {
            String uuid = jsonObject.get("UUID").toString();
            uuid = uuid.replace("\"", "");
            String size = jsonObject.get("SIZE").toString();
            size = size.replace("\"", "");
            Integer intSize = Integer.parseInt(size);
            GameInfo game = Server.playersToGames.get(uuid);
            ctx.write(message.updateTable(game, intSize) + "\r\n");
            ctx.writeAndFlush(UUID.fromString(uuid));
//                Card card = new Card("Test", 100, false);
//                game.setMoveCard(card);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}