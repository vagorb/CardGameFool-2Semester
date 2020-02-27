import java.util.HashMap;
import java.util.Map;

public class Card {

    private Integer value;
    //     Card value 6, 7, ... 12 etc.
    private Suite suite;
    //     Card suite Diamonds, Hearts etc.
    private boolean cardFaceDown;
    // cardFaceDown true = means you see the back. Will be true for opponents and false for THE player.
    private Map<Integer, String> valueMap = new HashMap<>(Map.of(6, "6", 7, "7", 8, "8",
            9, "9", 10, "10", 11, "JACK", 12, "QUEEN", 13, "KING",
            14, "ACE"));

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

    public Card(Integer value, Suite suite) {
        this.value = value;
        this.suite = suite;
        this.cardFaceDown = true;
    }

// CARD GENERATOR
//    public Card() {
//        for (int i = 6; i <= 14; i++) {
//            Card card = new Card(i, Suite.Diamonds);
//        }
//        for (int i = 6; i <= 14; i++) {
//            Card card = new Card(i, Suite.Hearts);
//        }
//        for (int i = 6; i <= 14; i++) {
//            Card card = new Card(i, Suite.Spades);
//        }
//        for (int i = 6; i <= 14; i++) {
//            Card card = new Card(i, Suite.Clubs);
//        }
//
//    }

    public Integer getValue() {
        return value;
    }

    public String getValueName() {
        return valueMap.get(value);
    }

    public Suite getSuite() {
        return suite;
    }

    public boolean isFaceDown() {
        return cardFaceDown;
    }

    public void setFaceDown(boolean faceDown) {
        this.cardFaceDown = faceDown;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", getValueName(), suite);
    }

    public static void main(String[] args) {
        System.out.println(new Card(14, Suite.SPADES));
        System.out.println(new Card(6, Suite.DIAMONDS));
        System.out.println(new Card(10, Suite.DIAMONDS));
        System.out.println(new Card(15, Suite.HEARTS));
    }
}
