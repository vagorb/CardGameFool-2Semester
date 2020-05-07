package Server;

import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {

    public List<Card> getCards() {
        return cards;
    }

    private List<String> players = new ArrayList<>();
    private Deck deck;
    private Card trump;

    private static List<Card> cards = new ArrayList<>();


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

    public Card getMoveCard() {
        return cards.get(cards.size() - 1);
    }

    public synchronized void addMoveCard(Card card) {
        cards.add(card);
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
