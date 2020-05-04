package Server;

import Client.Client;
import com.card.game.fool.cards.Deck;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameInfo {

    private List<String> players = new ArrayList<>();
    private Deck deck;
    private String id;


    public GameInfo(Deck deck, List<String> players) {
        this.deck = deck;
        this.players = players;
        this.id = UUID.randomUUID().toString();
    }

    public void addPlayers(String player) {
        players.add(player);
    }

    public void removePlayers(String player) {
        players.remove(player);
    }

    public List<String> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public String getId() {
        return id;
    }

    public static void main(String[] args) {
        List<String> players = new ArrayList<>();
        players.add("player1");
        players.add("player2");
        GameInfo info = new GameInfo(new Deck(), players);
        Gson gson = new Gson();
        String json = gson.toJson(info);
        System.out.println(json);

        // client receives
        GameInfo receivedInfo = gson.fromJson(json, GameInfo.class);
        System.out.println(receivedInfo);

    }
}
