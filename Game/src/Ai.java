import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// What could be done:
// AI can watch how many cards of every suit are already in pile and start attack with card with most popular suit in pile
// Moves after 0 cards in deck

public class Ai {
    private Hand hand;
    private Table table;
    private Pile pile;
    private Deck deck;

    public Ai(Hand hand) {
        this.hand = hand;
    }

    public Hand getAiHand() {
        return this.hand;
    }

    public Map<Integer, List<Card>> mapOfCardsInHand() {
        return hand.getCardsInHand().stream().collect(Collectors.groupingBy(Card::getValue));
    }

    public List<Card> suitableForDefCards() {
        Card lastCard = table.getLastCardOnTable();
        if (lastCard.getTrump()) {
            return hand.getCardsInHand().stream().filter(card -> card.getValue() > lastCard.getValue()
                    && card.getTrump()).collect(Collectors.toList());
        } else {
            return hand.getCardsInHand().stream().filter(card -> card.getTrump()
                    || (card.getSuit().equals(lastCard.getSuit())
                    && card.getValue() > lastCard.getValue())).collect(Collectors.toList());
        }
    }

    public Optional<Card> mostSuitableCardForDef() {
        List<Card> cards = suitableForDefCards();
        if (cards.size() < 1) {
            return Optional.empty();
        }
        if (cards.size() == 1) {
            return Optional.of(cards.get(0));
        }
        for (Card card : cards) {
            if (table.mapOfCardsInTable().containsKey(card.getValue())) {
                return Optional.of(card);
            }
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
                if (mapOfCardsInHand().get(card.value).size() >= 2 && !card.getTrump()) {
                    return Optional.of(card);
                }
            }
            return hand.getCardsInHand().stream().min(Comparator.comparingInt(Card::getValue));
        }
        return Optional.empty();
    }

    public List<Card> suitableCardsForAttackMoves() {
        return hand.getCardsInHand().stream().filter(card -> table.mapOfCardsInTable().containsKey(card.getValue()))
                .collect(Collectors.toList());
    }

    public Optional<Card> suitableAttackMoveBeforeEndOfDeck() {
        List<Card> cards = suitableCardsForAttackMoves();
        if (cards.size() == 0) {
            return Optional.empty();
        }
        if (cards.size() == 1) {
            if (!cards.get(0).getTrump()) {
                return Optional.of(cards.get(0));
            }
        }
        return hand.getCardsInHand().stream().min(Comparator.comparingInt(Card::getValue));
    }

    public Optional<Card> suitableAttackMoveWhenDeckEnds() {
        return Optional.empty();
    }
}
