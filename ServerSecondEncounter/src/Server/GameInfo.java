package Server;

import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {


    private List<String> players;
    private Deck deck;
    private Card trump;
    private boolean gameStarted;
    private List<Card> cards = new ArrayList<>();


    public GameInfo(Deck deck, List<String> players) {
        this.deck = deck;
        this.players = players;

        this.trump = getDeck().getDeck().get(players.size() * 6);
        trump.setTrump(true);
        deck.removeCard(trump);
        deck.getDeck().add(trump);
        deck.makeCardsTrump(trump.getSuit());
    }

    public List<Card> getCards() {
        return cards;
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

    public Card replenishCard() {
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

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
}
