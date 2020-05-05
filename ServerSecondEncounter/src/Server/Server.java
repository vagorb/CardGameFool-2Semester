package Server;

import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
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
import java.util.List;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Server {
    public static List<String> gameForTwo = new ArrayList<>();
    public static List<String> gameForThree = new ArrayList<>();
    public static List<String> gameForFour = new ArrayList<>();
    public static List<GameInfo> games = new ArrayList<GameInfo>();
    public static List<String> players = new ArrayList<>();
    public static Map<String, GameInfo> playersToGames = new HashMap<>();

//    private static Deck deck = new Deck();
//    private static Card card = Server.decideTrump();
//
//    public static Deck getDeck() {
//        return deck;
//    }
//
//    public static Card getTrump() {
//        return card;
//    }
//
//    public static Card decideTrump() {
//        //Server.Server.shuffle();
//        Card card = deck.getDeck().get(12);
//        card.setTrump(true);
//        deck.getDeck().remove(card);
//        deck.getDeck().add(card);
//        return card;
//    }
//
//    public static void shuffle() {
//        deck.shuffleDeck();
//    }
//
//    public static void makeTrump() {
//        deck.makeCardsTrump(card.getSuit());
//    }


    public static void runServer() throws InterruptedException {
        System.out.println("LONG LINE OF TEXT THAT WE CAN SEE ??????????");
//        Server.makeTrump();
//
//        GameHlder = nbew GameHolder;
//        gameholder.addPlayer(1)
//        gameholder.addPlayer(2)
//                Deck deck11 = new Deck()
//        gameholder.addDeck(deck11)
//         gameHolder.startGame();
//        games.add(game1);
//
//        GameHlder game2


        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
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
