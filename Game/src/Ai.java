import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Map<Integer, List<Card>> mapOfCardsInHand() {
        return hand.getCardsInHand().stream().collect(Collectors.groupingBy(Card::getValue));
    }

    public ArrayList<Card> suitableForDefCards() {
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

    public Optional<Card> mostSuitableCardForDef() {
        ArrayList<Card> cards = suitableForDefCards();
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

    public Optional<Card> firstCardAttackMove() {
        if (table.getTable().size() == 0) {
            for (Card card : getAiHand().getCardsInHand()) {
                if (mapOfCardsInHand().get(card.value).size() >= 2 && card.getTrump().equals(false)) {
                    return Optional.of(card);
                }
            }
        }
        return hand.getCardsInHand().stream().min(Comparator.comparingInt(Card::getValue));
    }

    public Optional<Card> nextMoves() {
        for (Card card : hand.getCardsInHand()) {
            if (table.mapOfCardsInTable().containsKey(card.value)) {
                if (card.getTrump().equals(false)) {
                    return Optional.of(card);
                }
            }
        }
        return Optional.empty();
    }
}
