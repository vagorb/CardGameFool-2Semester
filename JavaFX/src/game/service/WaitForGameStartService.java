package game.service;

import Client.Client;
import Server.GameInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

public class WaitForGameStartService extends Service<GameInfo> {
    private String playerId;
    private Gson gson = new Gson();

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    protected Task<GameInfo> createTask() {
        return new Task<>() {
            @Override
            protected GameInfo call() {
                while (true) {
                    try {
                        GameInfo updatedInfo = getGameInfo();
                        if (updatedInfo.isGameStarted()) {
                            return updatedInfo;
                        } else {
                            Thread.sleep(500);
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private GameInfo getGameInfo() throws IOException {
        JsonObject getGameInfo = new JsonObject();
        getGameInfo.addProperty("playerId", playerId);
        getGameInfo.addProperty("type", "getGameInfo");
        String response = Client.sendMessage(getGameInfo);
        return gson.fromJson(response, GameInfo.class);
    }
}
