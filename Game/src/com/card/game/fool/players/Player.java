package com.card.game.fool.players;

import com.card.game.fool.cards.Card;

public class Player implements gamerInterface {
    private Hand hand;
    private String name;
    private Integer score;
    private PlayerState playerState;

    /**
     * com.card.game.fool.players.Player class constructor
     *
     * @param name  of the player ( String )
     * @param score of the player ( Integer )
     * @param hand  of the player ( com.card.game.fool.players.Hand object )
     */
    public Player(String name, Integer score, Hand hand) { //PlayerState playerState) {
        //this.playerState = playerState;
        this.name = name;
        this.score = score;
        this.hand = hand;
    }

    /**
     * PlayerState
     * Attack state - player is attacking
     * Defense state - player is defending
     */
    public enum PlayerState {
        ATTACK, DEFENSE, SKIP
    }

    /**
     * Getter
     *
     * @return com.card.game.fool.players.Hand of this player
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Getter
     *
     * @return name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     *
     * @return score of this player
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Method that adds score to the player
     *
     * @param score to add
     */
    public void addScore(int score) {
        if (this.score + score > 0) {
            this.score += score;
        } else {
            this.score = 0;
        }
    }

    /**
     * Setter
     *
     * @param playerState that we set
     */
    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    /**
     * Getter
     *
     * @return player state
     */
    public PlayerState getPlayerState() {
        return this.playerState;
    }

    /**
     * Method that decides what card we will take from our list of cards
     *
     * @param cardNumber value we give to our list of cards , which we use to take the card with
     * @return card that we plan on taking
     */
    public Card chooseCard(Integer cardNumber) {
        Integer handSize = getHand().getCardsInHand().size();
        return getHand().getCardsInHand().get(cardNumber);

    }


}
