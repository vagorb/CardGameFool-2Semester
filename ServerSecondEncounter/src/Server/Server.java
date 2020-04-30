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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Server {
    private static Deck deck = new Deck();
    private static Card card = Server.decideTrump();

    public static Deck getDeck() {
        return deck;
    }

    public static Card getTrump() {
        return card;
    }

    static Card decideTrump() {
        //Server.Server.shuffle();
        Card card = deck.getDeck().get(12);
        deck.getDeck().remove(card);
        return card;
    }

    static void shuffle() {
        deck.shuffleDeck();
    }

    static void makeTrump() {
        deck.makeCardsTrump(card.getSuit());
    }


    public static void runServer() throws InterruptedException {
        Server.makeTrump();

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
