package main.java;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientProgram extends Listener {

    static Client client;
    static String ip = "localhost";

    static int updPort = 27960, tcpPort = 27960;

    static boolean messageReceived = false;

    public static void main (String[] args) throws Exception {
        System.out.println("Connecting to client");
        client = new Client();

        client.getKryo().register(PacketMessage.class);

        client.start();

        client.connect(5000, ip, tcpPort, updPort);

        client.addListener(new ClientProgram());

        System.out.println("Waiting for packet...\n");

        while(!messageReceived){
            Thread.sleep(10);
        }
        System.out.println("Client exit!");
        System.exit(0);

    }

    public void receiveved(Connection c, Object p) {
        if (p instanceof PacketMessage) {
            PacketMessage packet = (PacketMessage) p;
            System.out.println("received a message" + packet.message);

            messageReceived = true;
        }

    }

}
