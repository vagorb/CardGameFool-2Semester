package com.card.game.fool.tables;

import com.card.game.fool.AI.Ai;
import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Table {
//com.card.game.fool.tables.Table(cards on the table) defence and attack . - Implemented
//when card get on table, they removed from hand of player. - Implemented
//comparing cards( can kill or not attack card) - Nope
//put card on table from hand for attacking or defence. the card must be removed from hand. - Implemented


    public Deck deckForThisGame;
    private List<Player> listOfPlayers = new ArrayList<>();
    private List<Card> pileOfCardsForThisGame = new ArrayList<>();
    Pile pile;
    Ai ai;
    List<Card> table = new ArrayList<>();
    private boolean currentDefenseState;
    private String trumpSuit;

    /**
     * Constructor
     * @param player1 that will play in this table
     * @param player2 that will play in this table
     */
    public Table(Player player1, Player player2) {
        this.table = new ArrayList<>();
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();
        this.pile = new Pile(pileOfCardsForThisGame);
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
//        System.out.println(deckForThisGame);
    }

    /**
     * Constructor
     * @param player1 that will play in this table
     * @param player2 that will play in this table
     * @param player3 that will play in this table
     */
    public Table(Player player1, Player player2, Player player3) {
        this.table = new ArrayList<>();
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();
        this.pile = new Pile(pileOfCardsForThisGame);
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
//        System.out.println(deckForThisGame);
    }

    /**
     * Constructor
     * @param player1 that will play in this table
     * @param player2 that will play in this table
     * @param player3 that will play in this table
     * @param player4 that will play in this table
     */
    public Table(Player player1, Player player2, Player player3, Player player4) {
        this.table = new ArrayList<>();
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();
        this.pile = new Pile(pileOfCardsForThisGame);
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
        listOfPlayers.add(player4);
//        System.out.println(deckForThisGame);
    }

    public Table(Player player1, Ai ai) {
        this.ai = ai;
        this.pile = new Pile(pileOfCardsForThisGame);
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();

        listOfPlayers.add(player1);
    }

    /**
     * Getter
     * @return list of players that are playing at this table
     */
    public List<Player> getPlayers() {
        return listOfPlayers;
    }

    /**
     * Getter
     * @return the deck of cards that we are playing with at this table
     */
    public ArrayList<Card> getGameDeck() {
        return deckForThisGame.getDeck();
    }

    public Pile getPile() {
        return pile;
    }

    public void addToPile(Card card) {
        deckForThisGame.removeCard(card);
        pileOfCardsForThisGame.add(card);
    }

    /**
     * Method that add a specified card on the playing table
     * @param card to add on the table
     */
    public void addCardOnTable(Card card) {
        table.add(card);
    }

//    /**
//     * Getter
//     * @return the last card put on table
//     */
//    public com.card.game.fool.cards.Card getLastCardPutOnTable() {
//        int lastCardOnTable = table.size() - 1;
//        return table.get(lastCardOnTable);
//    }

    /**
     * Getter
     * @return list of cards currently being in-play on this table
     */
    public List<Card> getTable() {
        return table;
    }


//    public void assignTrumpCard() {
//        // This method should be changed, currently serves a purpose of assigning a trump card ( not the way it should be in final game)
//        trumpSuit = deckForThisGame.getDeck().get(0).getSuit();
//    }

    /**
     * Getter
     * @return trump suit of the table we are playing on
     */
    public String getTrumpSuit() {
        return trumpSuit;
    }

    /**
     * Setter
     * @param card that we use to set trump suit for the game
     */
    public void setTrumpSuit(Card card) {
        this.trumpSuit = card.getSuit();
    }


    /**
     * Getter
     * @return last card that was put on table
     */
    public Card getLastCardOnTable() {
        return table.get(table.size() - 1);
    }

    /**
     *
     * @return map of cards in our table
     */
    public Map<Integer, List<Card>> mapOfCardsInTable() {
        return getTable().stream().collect(Collectors.groupingBy(Card::getValue));
    }
}
