package Client;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {

    private static String response;

    private JsonObject message;

    public static void sendMessage(JsonObject object) throws IOException {

        try {
            TimeUnit.SECONDS.sleep((long) 0.5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String host = "localhost";
        int port = 5200;
        JsonObject message = object;
//        for (int i = 0; i < 35; i++) {
        try (Socket socket = new Socket(host, port)) {

            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(message);
            writer.flush();

            log("send > " + message);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = reader.readLine();
            log("received < " + response);
        }
//        }
    }

    public static String getResponse() {
        return response;
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);

    }

    public void setMessage(JsonObject jsonObject) {
        this.message = jsonObject;
    }


    public static void main(String[] args) throws IOException {

    }


}