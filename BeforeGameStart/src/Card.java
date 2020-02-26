import java.util.ArrayList;
import java.util.Arrays;

public class Card {
    String suit;
    String value;
    Boolean trump;
    Visibility visibility;
    String code;

    public enum Visibility {
        EVERYONE, PLAYER, NOONE
    }

    public Card(String suit, String value, Boolean trump, Visibility visibility, String code) {
        this.suit = suit;
        this.value = value;
        this.trump = trump;
        this.visibility = visibility;
        this.code = code;
    }


    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public Boolean getTrump() {
        return trump;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public String getCode() {
        return code;
    }
}
