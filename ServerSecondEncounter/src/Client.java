import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Client {

    private static String response;

    public static void sendMessage() throws IOException {


        String host = "localhost";
        int port = 5200;
        Message message = new Message();

        for (int i = 0; i < 35; i++) {
            try (Socket socket = new Socket(host, port)) {
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println(message.getMessage(Message.MessageType.playerMove));
                writer.flush();

                log("send > " + message);

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                response = reader.readLine();
                log("received < " + response);
            }
        }
    }

    public String getResponse() {
        return response;
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);

    }


    public static void main(String[] args) throws IOException {
        Client.sendMessage();
    }


}