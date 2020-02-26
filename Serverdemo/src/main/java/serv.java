import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.Date;

public class serv extends Listener {
    static Server server;

    static int updPort = 27960, tcpPort = 27960;

    public static void main (String[] args) throws Exception {
        System.out.println(("Creating the server..."));
        server = new Server();
        server.getKryo().register(PacketMessage.class);
        server.bind(tcpPort, updPort);
        server.start();

        server.addListener(new serv());

        System.out.println("Server is working!");
    }

    public void connected (Connection c) {
        System.out.printf("Connection from%s%n", c.getRemoteAddressTCP().getHostString());

        PacketMessage packetMessage = new PacketMessage();

        packetMessage.message = "Hi!" + new Date().toString();

        c.sendTCP(packetMessage);
    }

    public void received(Connection c, Object p) {
    }

    public void disconnected(Connection c) {
        System.out.println("Disconnected");
    }
}
