import java.util.Random;


public class Card {



    private String name;
    // Card name "6 of Spades" etc.
    private Integer value;
    // Card value 6, 7, ... 12 etc.
    private Suite suite;
    // Card suite Diamonds, Hearts etc.
    private boolean cardBack;
    // cardBack true = means you see the back. Will be true for opponents and false for THE player.
    private boolean cardFront;
    // cardFront true = means you see the front. Will be false for opponents and true for THE player.

    enum Suite {
        Diamonds, Hearts, Spades, Clubs
    }

    public Card(Integer value, Suite suite) {
//        this.name = name;
        this.value = value;
        this.suite = suite;
        this.cardBack = false;
        this.cardFront = true;
    }


//    public Card() {
//        for (int i = 6; i < 14; i++) {
//            Card card = new Card(i, Suite.Diamonds);
//        }
//        for (int i = 6; i < 14; i++) {
//            Card card = new Card(i, Suite.Hearts);
//        }
//        for (int i = 6; i < 14; i++) {
//            Card card = new Card(i, Suite.Spades);
//        }
//        for (int i = 6; i < 14; i++) {
//            Card card = new Card(i, Suite.Clubs);
//        }
//
//    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Suite getSuite() {
        return suite;
    }

    public void setSuite(Suite suite) {
        this.suite = suite;
    }

    public boolean isCardBack() {
        return cardBack;
    }

    public void setCardBack(boolean cardBack) {
        this.cardBack = cardBack;
    }

    public boolean isCardFront() {
        return cardFront;
    }

    public void setCardFront(boolean cardFront) {
        this.cardFront = cardFront;
    }

    public String getOfficialCardName(){
        this.name = this.value + " of " + this.suite;
        return this.value + " of " + this.suite;
    }
}
