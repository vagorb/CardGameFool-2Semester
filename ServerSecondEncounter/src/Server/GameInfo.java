package Server;

import Client.Client;
import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameInfo {

    private List<String> players = new ArrayList<>();
    private Deck deck;

    private Card trump;

    private Card card = new Card("Test", 100, false);


    public GameInfo(Deck deck, List<String> players) {
        this.deck = deck;
        this.players = players;

        this.trump = getDeck().getDeck().get(players.size() * 6);
        trump.setTrump(true);
        deck.removeCard(trump);
        deck.getDeck().add(trump);
        deck.makeCardsTrump(trump.getSuit());
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

    public synchronized Card getCard() {
        Card card = deck.getDeck().get(0);
        deck.getDeck().remove(card);
        return card;
    }

    public synchronized Card getTrump() {
        return trump;
    }

    public synchronized Card getMoveCard() {
        return this.card;
    }

    public synchronized void setMoveCard(Card card) {
        this.card = card;
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
