import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class Ai {
    private Hand hand;
    private Table table;
    private Pile pile;

    public Ai(Hand hand) {
        this.hand = hand;
    }

    public Hand getAiHand() {
        return this.hand;
    }

    public ArrayList<Card> suitableForDefCards(Table table) {
        ArrayList<Card> suitableCards = new ArrayList<>();
        Card lastCard = table.getLastCardOnTable();
        for (Card card : hand.getCardsInHand()) {
            if (lastCard.getTrump().equals(false)) {
                if (card.getSuit().equals(lastCard.getSuit()) && card.value > lastCard.getValue()
                        && card.getTrump().equals(false)) {
                    suitableCards.add(card);
                } else if (card.getTrump().equals(true)) {
                    suitableCards.add(card);
                }
            } else if (lastCard.getTrump().equals(true)) {
                if (card.getSuit().equals(lastCard.getSuit()) && card.value > lastCard.getValue()) {
                    suitableCards.add(card);
                }
            }
        }
        return suitableCards;
    }

    public Optional<Card> mostSuitableCardForDef(Pile pile) {
        ArrayList<Card> cards = suitableForDefCards(table);
        if (cards.size() < 1) {
            return Optional.empty();
        }
        if (cards.size() == 1) {
            return Optional.of(cards.get(0));
        }
        if (pile.getPile().size() > 1) {
            for (Card card : cards) {
                if (pile.mapOfCardsAndValues().containsKey(card.value) && pile.mapOfCardsAndValues().get(card.value)
                        .size() >= 2) {
                    return Optional.of(card);
                }
            }
        }
        return cards.stream().min(Comparator.comparingInt(Card::getValue));
    }

    
}
