package com.card.game.fool.players;

import com.card.game.fool.cards.Card;

import java.util.LinkedList;
import java.util.List;

public class Player implements gamerInterface{
    private List<Card> cardsInHand = new LinkedList<>();
    private final String name;
    private Integer score;
    private PlayerState playerState;

    /**
     * com.card.game.fool.players.Player class constructor
     * @param name of the player ( String )
     * @param score of the player ( Integer )
     */
    public Player(String name, Integer score) { //PlayerState playerState) {
        //this.playerState = playerState;
        this.name = name;
        this.score = score;
    }

    /**
     * Getter
     * @return List of Cards of this player
     */
    public List<Card> getHand() {
        if (cardsInHand == null) {
            cardsInHand = new LinkedList<>();
            return cardsInHand;
        } else {
            return cardsInHand;
        }
    }

    /**
     * Getter
     * @return name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return score of this player
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Method that adds score to the player
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
     * @param playerState that we set
     */
    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    /**
     * Getter
     * @return player state
     */
    public PlayerState getPlayerState() {
        return this.playerState;
    }
}

