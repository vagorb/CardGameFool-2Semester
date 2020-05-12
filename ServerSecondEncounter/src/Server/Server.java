package Server;

import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.Player;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    public static final List<GameInfo> games = Collections.synchronizedList(new ArrayList<GameInfo>());
    public static final Map<String, GameInfo> playersToGames = Collections.synchronizedMap(new HashMap<String, GameInfo>());


    public static GameInfo addPlayerToGame(String player) {
        synchronized (games) {
            GameInfo game;
            if (games.isEmpty()) {
                List<String> playersList = new ArrayList<>();
                playersList.add(player);
                game = new GameInfo(new Deck(), playersList);
                games.add(game);
            } else {
                game = games.get(0);
                game.addPlayers(player);
                games.remove(game);
            }
            playersToGames.put(player, game);
            return game;
        }
    }

    public static GameInfo newWithAIGame(String player) {
        GameInfo game;
        List<String> playersList = new ArrayList<>();
        playersList.add(player);
        game = new GameInfo(new Deck(), playersList);
        playersToGames.put(player, game);
        return game;
    }

    public static List<Card> replenishPlayerCards(String player, int cardsInHand) {
        synchronized (games) {
            GameInfo game = playersToGames.get(player);
            List<Card> replenishedCards = new ArrayList<>();
            for (int i = cardsInHand; i < 6; i++) {
//                replenishedCards.add(game.replenishCard());
                Card card = game.replenishCard();
                if (card == null) {
                    break;
                } else {
                    replenishedCards.add(card);
                }
            }
            return replenishedCards;
        }
    }

    public static void endGame(String player) {
        synchronized (games) {
            GameInfo game = playersToGames.get(player);
            // TODO finish this
            game.getCardsOnTable().clear();
            if (game.getPlayers().size() > 1) {
                game.getPlayers().remove(player);
            }
            if (game.getPlayers().size() == 1) {
                game.setEndTheGame();
                game.setFool(game.getPlayers().get(0));
            }
        }
    }



            public static void playerPicksUpCards(String player) {
        synchronized (games) {
            GameInfo game = playersToGames.get(player);
            game.getCardsOnTable().clear();
            game.increaseTurnCounter();
            game.setCurrentPlayerTurn(game.getAttackingPlayer());
        }
    }

    public static void AIPicksUpCards(String player) {
        synchronized (games) {
            GameInfo game = playersToGames.get(player);
            game.getAi().getHand().addAll(game.getCardsOnTable());
            game.getCardsOnTable().clear();
            game.increaseTurnCounter();
            game.setCurrentPlayerTurn(game.getAttackingPlayer());
        }
    }

    public static void cardsToPile(String player) {
        synchronized (games) {
            GameInfo game = playersToGames.get(player);
            for (Card card : game.getCardsOnTable()) {
                game.getPile().addDiscardedCards(card);
            }
            game.getCardsOnTable().clear();
            game.increaseTurnCounter();
            game.switchAttackerAndDefender();
        }
    }



    public static void runServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1000, Delimiters.lineDelimiter()));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(5200).sync();
            System.out.println("Starting nio server at " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Server.runServer();
    }
}

