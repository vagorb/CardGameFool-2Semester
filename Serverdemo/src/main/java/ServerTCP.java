package main.java;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.Date;

public class ServerTCP extends Listener {
    static Server server;
    static String serverIP = "193.40.255.14";


    static int tcpPort = 5201;

    public static void main (String[] args) throws Exception {
        server = new Server();
        server.start();
        server.bind(tcpPort);
        System.out.println(("Creating the server..."));

        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest)object;
                    //System.out.println(request.text);

                    SomeResponse response = new SomeResponse();
                    //response.text = "Thanks";
                    connection.sendTCP(response);
                }
            }
        });
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
