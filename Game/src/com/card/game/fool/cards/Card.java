package com.card.game.fool.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Card {

    String suit;
    String id;
    Integer value;
    Visibility visibility;
    Boolean trump;
    private Map<Integer, String> valueMap = new HashMap<>(Map.of(6, "6", 7, "7", 8, "8",
            9, "9", 10, "10", 11, "JACK", 12, "QUEEN", 13, "KING",
            14, "ACE"));


    /**
     * com.card.game.fool.cards.Card visibility state for players
     * Everyone - everyone can see the cards trump and value
     * com.card.game.fool.players.Player - only player can see the cards trump and value
     * Noone - noone can see the cards trump and value
     */
    public enum Visibility {
        EVERYONE, PLAYER, NOONE
    }

    // We can use trump as a server variable/ information holder to do calculations.
    /* So for us not to rewrite what the card is we just add a server trump variable that just gives extra value points to the card
    before it's calculation process.
    */


//    public enum Suite {
//        DIAMONDS("Diamonds"), HEARTS("Hearts"), SPADES("Spades"), CLUBS("Clubs");
//        private final String suiteString;
//
//        Suite(String suite) {
//            suiteString = suite;
//        }
//
//        @Override
//        public String toString() {
//            return suiteString;
//        }
//    }

    /**
     * Class constructor
     * @param suit Cards suit ( String )
     * @param value Cards value ( Integer )
     * @param trump Cards trump state ( Boolean )
     */
    public Card(String suit, Integer value, Boolean trump) {
        this.suit = suit;
        this.value = value;
        this.visibility = Visibility.NOONE;
        this.trump = trump;
        id = getValueName() + "_of_" + getSuit();
    }

    /**
     * Getter
     * @return id of this card (purely to connect JavaFX style with this card)
     */
    public String getId() {
        return id;
    }

    /**
     * Getter
     * @return suit of this card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Getter
     * @return value of the card
     */
    public Integer getValue() {
        return value;
    }


    /**
     * Getter
     * @return get card visibility state
     */
    public Visibility getVisibility() {
        return visibility;
    }

    /**
     * Setter
     * @param trump set's the trumps boolean state
     */
    public void setTrump(Boolean trump) {
        this.trump = trump;
    }

    /**
     * Getter
     * @return get boolean state of this card being trump or not
     */
    public Boolean getTrump() {
        return this.trump;
    }

    /**
     * Overrided method that prints out all relevant information about the card
     * @return returns this information in readable text format
     */
    @Override
    public String toString() {
        return String.format("%s of %s", getValueName(), suit);
    }

    /**
     *
     * @return returns name of the card for this value ( Example card with value of 11 is JACK )
     */
    public String getValueName() {
        return valueMap.get(value);
    }


    /**
     * Overrided equals method, that counts objects as equal if they have the same parameters as another card
     * @param o our card object
     * @return proper result for our equals method.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit.equals(card.suit) &&
                value.equals(card.value);
    }

    public static Card javaFXCardToCard(String javafxId) {
        List<String> splitted = new ArrayList<>(Arrays.asList(javafxId.split("_")));
        int value = 0;
        if (splitted.get(0).equalsIgnoreCase("Jack")) {
            value = 11;
        } else if (splitted.get(0).equalsIgnoreCase("Queen")) {
            value = 12;
        } else if (splitted.get(0).equalsIgnoreCase("King")) {
            value = 13;
        } else if (splitted.get(0).equalsIgnoreCase("Ace")) {
            value = 14;
        } else
            value = Integer.parseInt(splitted.get(0));
        return new Card(splitted.get(2), value, false );
    }
}

