package com.card.game.fool.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> deck;


    /**
     * com.card.game.fool.cards.Deck class constructor
     * Created object automatically has 36 cards in it ( Full deck )
     */
    public Deck() {
        this.deck = new ArrayList<>();
        ArrayList<String> suits = new ArrayList<>(Arrays.asList("Clubs", "Hearts", "Diamonds", "Spades"));
        ArrayList<Integer> values = new ArrayList<>(Arrays.asList(6, 7, 8));
        for (Integer value : values) {
            for (String suit : suits) {
                Card card = new Card(suit, value, false);
                this.deck.add(card);
            }
        }
    }

    /**
     * This method shuffles cards in our deck
     */
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    /**
     * Method that removes the specified card from the deck
     * @param card to remove from this deck
     */
    public void removeCard(Card card) {
        deck.remove(card);
    }


    public void makeCardsTrump(String suit) {
        for (Card card : deck) {
            if (card.getSuit().equals(suit)) {
                card.setTrump(true);
            }
        }
    }

    /**
     * Method returns list of cards
     * @return return List of cards in our deck
     */
    public ArrayList<Card> getDeck(){
        return this.deck;
    }

}
