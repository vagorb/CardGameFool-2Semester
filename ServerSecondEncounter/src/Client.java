import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Client {

    public static void main(String[] args) throws IOException {


        String host = "localhost";
        int port = 5200;
        Message message = new Message();

        try (Socket socket = new Socket(host, port)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(message.getMessage(Message.MessageType.playerMove));
            writer.flush();
            log("send > " + message);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            log("received < " + response);
        }
    }


    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }

    private static void delay(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException ignored) {}
    }
}

