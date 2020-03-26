import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Card {

    String suit;
    Integer value;
    Visibility visibility;
    Boolean trump;
    private Map<Integer, String> valueMap = new HashMap<>(Map.of(6, "6", 7, "7", 8, "8",
            9, "9", 10, "10", 11, "JACK", 12, "QUEEN", 13, "KING",
            14, "ACE"));




    public enum Visibility {
        EVERYONE, PLAYER, NOONE
    }

    // We can use trump as a server variable/ information holder to do calculations.
    /* So for us not to rewrite what the card is we just add a server trump variable that just gives extra value points to the card
    before it's calculation process.
    */


    public enum Suite {
        DIAMONDS("Diamonds"), HEARTS("Hearts"), SPADES("Spades"), CLUBS("Clubs");
        private final String suiteString;

        Suite(String suite) {
            suiteString = suite;
        }

        @Override
        public String toString() {
            return suiteString;
        }
    }

    public Card(String suit, Integer value, Boolean trump) {
        this.suit = suit;
        this.value = value;
        this.visibility = Visibility.NOONE;
        this.trump = false;
    }


    public String getSuit() {
        return suit;
    }

    public Integer getValue() {
        return value;
    }


    public Visibility getVisibility() {
        return visibility;
    }

    public void setTrump(Boolean trump) {
        this.trump = trump;
    }

    public Boolean getTrump() {
        return this.trump;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", getValueName(), suit);
    }

    public String getValueName() {
        return valueMap.get(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit.equals(card.suit) &&
                value.equals(card.value);
    }
}

