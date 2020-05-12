package Server;

import com.card.game.fool.AI.Ai;
import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.PlayerState;
import com.card.game.fool.tables.Pile;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {

    private int firstMessage;
    public int getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(int firstMessage) {
        this.firstMessage = firstMessage;
    }

    public Ai getAi() {
        return ai;
    }

    public Pile getPile() {
        return pile;
    }
    private Ai ai = null;
    private List<String> players;
    private Deck deck;
    private Card trump;
    private boolean gameStarted;
    //    private List<Card> cards = new ArrayList<>();
    private String currentPlayerTurn;
    private String attackingPlayer;
    private String defendingPlayer;
    // Cards on table that attacked and defender are putting
    private List<Card> cardsOnTable = new ArrayList<>();
    private int turnCounter;
    private Pile pile;

    public GameInfo(Deck deck, List<String> players) {
        this.firstMessage = 0;
        this.deck = deck;
        this.players = players;

        this.trump = getDeck().getDeck().get(players.size() * 6);
        trump.setTrump(true);
        deck.removeCard(trump);
        deck.getDeck().add(trump);
        deck.makeCardsTrump(trump.getSuit());
        this.pile = new Pile(new ArrayList<>());
        deck.shuffleDeck();
    }

    public void switchAttackerAndDefender() {
        String prevAtt = this.attackingPlayer;
        String prevDef = this.defendingPlayer;
        this.attackingPlayer = prevDef;
        this.defendingPlayer = prevAtt;
        this.currentPlayerTurn = this.attackingPlayer;
    }

    public void addCardToTable(Card card) {
        cardsOnTable.add(card);
    }

    public void playerMadeMove(String player) {
        if (attackingPlayer.equals(player)) {
            currentPlayerTurn = defendingPlayer;
        } else {
            currentPlayerTurn = attackingPlayer;
        }
    }

    public void startGame() {
        attackingPlayer = players.get(0);
        currentPlayerTurn = players.get(0);
        defendingPlayer = players.get(1);
        gameStarted = true;
    }

    public void startGameWithAI() {
        this.ai = new Ai();
        for (int i = 0; i < 6; i++) {
            ai.getHand().add(replenishCard());
        }
        players.add(ai.getName());
        attackingPlayer = players.get(0);
        currentPlayerTurn = players.get(0);
        defendingPlayer = players.get(1);
        gameStarted = true;
    }

    public void replenishAIHand() {
        for (int i = ai.getHand().size(); i < 6; i++) {
            if (getDeck().getDeck().size() > 0) {
                ai.getHand().add(replenishCard());
            }
        }
    }

    public PlayerState getPlayerState(String player) {
        if (attackingPlayer.equals(player)) {
            return PlayerState.ATTACK;
        } else if (defendingPlayer.equals(player)) {
            return PlayerState.DEFENSE;
        } else {
            return PlayerState.WAITING;
        }
    }

    public boolean isPlayersTurn(String player) {
        return currentPlayerTurn.equals(player);
    }

//    public List<Card> getCards() {
//        return cards;
//    }

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
        if (deck.getDeck().size() > 0) {
            Card card = deck.getDeck().get(0);
            deck.getDeck().remove(card);
            return card;
        } else {
            return null;
        }
    }


    public synchronized Card getTrump() {
        return trump;
    }

//    public Card getMoveCard() {
//        return cards.get(cards.size() - 1);
//    }
//
//    public synchronized void addMoveCard(Card card) {
//        cards.add(card);
//    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public String getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public String getAttackingPlayer() {
        return attackingPlayer;
    }

    public String getDefendingPlayer() {
        return defendingPlayer;
    }

    public List<Card> getCardsOnTable() {
        return cardsOnTable;
    }

    public void setCurrentPlayerTurn(String currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void increaseTurnCounter() {
        this.turnCounter++;
    }
}
