package game.service;

import Server.GameInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import game.Game;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import Client.Client;

import java.io.IOException;

public class WaitForMyTurnService extends Service<GameInfo> {

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
                        if (updatedInfo.isPlayersTurn(playerId) || updatedInfo.getEndTheGame()) {
                            System.out.println(playerId + " Opponent made his move");
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
        getGameInfo.addProperty("State", "Update");
        String response = Client.sendMessage(getGameInfo);
        return gson.fromJson(response, GameInfo.class);
    }

}

