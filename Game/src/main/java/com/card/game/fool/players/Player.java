package com.card.game.fool.players;

import com.card.game.fool.cards.Card;

import java.util.LinkedList;
import java.util.List;

public class Player implements gamerInterface{
    private final List<Card> cardsInHand = new LinkedList<>();
    private String name = "player";
    private PlayerState playerState;

    /**
     * Getter
     * @return List of Cards of this player
     */
    public List<Card> getHand() {
        return cardsInHand;
    }

    /**
     * Getter
     * @return name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * Setter
     * @param name real name of player
     */
    public void setName(String name) {
        this.name = name;
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
