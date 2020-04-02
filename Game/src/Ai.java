import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// What could be done:
// AI can watch how many cards of every suit are already in pile and start attack with card with most popular suit in pile
// Moves after 0 cards in deck
// method in table getAi move

public class Ai {
    private Hand hand;
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

    public List<Card> suitableForDefCards(Table table) {
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

    public Optional<Card> mostSuitableCardForDef(Table table) {
        List<Card> cards = suitableForDefCards(table);
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
        if (table.getPile().getPile().size() > 1) {
            for (Card card : cards) {
                if (table.getPile().mapOfCardsAndValues().containsKey(card.value) && table.getPile().mapOfCardsAndValues().get(card.value)
                            .size() >= 2) {
                        return Optional.of(card);
                }
            }
        }
        return cards.stream().min(Comparator.comparingInt(Card::getValue));
    }

    public Optional<Card> firstCardAttackMove(Table table) {
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

    public List<Card> suitableCardsForAttackMoves(Table table) {
        return hand.getCardsInHand().stream().filter(card -> table.mapOfCardsInTable().containsKey(card.getValue()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Optional<Card> suitableAttackMoveBeforeEndOfDeck(Table table) {
        List<Card> cards = suitableCardsForAttackMoves(table);
        if (cards.size() == 0) {
            return Optional.empty();
        }
        if (cards.size() == 1) {
            if (!cards.get(0).getTrump()) {
                return Optional.of(cards.get(0));
            }
        }
        return hand.getCardsInHand().stream().min(Comparator.comparingInt(Card::getValue).thenComparing(Card::getTrump));
    }

    public Optional<Card> suitableAttackMoveWhenDeckEnds() {
        return Optional.empty();
    }
}
