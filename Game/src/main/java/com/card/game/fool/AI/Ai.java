package com.card.game.fool.AI;

import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.PlayerState;
import com.card.game.fool.players.gamerInterface;
import com.card.game.fool.tables.Pile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ai implements gamerInterface {
    /**
     * Cards in AI hand
     */
    private final List<Card> cardsInHand = new LinkedList<>();

    public String getName() {
        return "CPU";
    }

    /**
     *
     * @return Cards in AI hand
     */
    public List<Card> getHand() {
        return cardsInHand;
    }

    /**
     *
     * @return Map of cards in ai hand where key is cards value and value is list of cards with this value
     */
    public Map<Integer, List<Card>> mapOfCardsInHand() {
        return cardsInHand.stream().collect(Collectors.groupingBy(Card::getValue));
    }

    /**
     *
     * @param table - cards on the table
     * @return Map of cards on the table where key is cards value and value is list of cards with this value
     */

    public Map<Integer, List<Card>> mapOfCardsInTable(List<Card> table) {
        return table.stream().collect(Collectors.groupingBy(Card::getValue));
    }

    /**
     *
     * @param table - cards on the table
     * @return List of cards what are suitable for defensive move
     */
    public List<Card> suitableForDefCards(List<Card> table) {
        Card lastCard = table.get(table.size() - 1);
        if (lastCard.getTrump()) {
            return cardsInHand.stream().filter(card -> card.getValue() > lastCard.getValue()
                    && card.getTrump()).collect(Collectors.toList());
        } else {
            return cardsInHand.stream().filter(card -> card.getTrump()
                    || (card.getSuit().equals(lastCard.getSuit())
                    && card.getValue() > lastCard.getValue())).collect(Collectors.toList());
        }
    }

    /**
     *
     * @param table - cards on the table
     * @param pile - cards what are already out from the game
     * @return card what are most suitable for def at the moment
     */
    public Optional<Card> mostSuitableCardForDef(List<Card> table, Pile pile) {
        List<Card> cards = suitableForDefCards(table);
        if (cards.size() < 1) {
            return Optional.empty();
        }
        if (cards.size() == 1) {
            return Optional.of(cards.get(0));
        }
        for (Card card : cards) {
            if (mapOfCardsInTable(table).containsKey(card.getValue())) {
                return Optional.of(card);
            }
        }
        if (pile.getPile().size() > 1) {
            for (Card card : cards) {
                if (pile.mapOfCardsAndValues().containsKey(card.getValue()) && pile.mapOfCardsAndValues().get(card.getValue())
                        .size() >= 2) {
                    return Optional.of(card);
                }
            }
        }
        return cards.stream().min(Comparator.comparingInt(Card::getValue));
    }

    /**
     *
     * @return perfect attack card for empty table
     */

    public Optional<Card> firstCardAttackMove() {
        for (Card card : cardsInHand) {
            if (mapOfCardsInHand().get(card.getValue()).size() >= 2 && !card.getTrump()) {
                return Optional.of(card);
            }
        }
        return cardsInHand.stream().min(Comparator.comparingInt(Card::getValue));
    }

    /**
     *
     * @param table - cards on the table
     * @return all cards what are suitable for attack
     */
    public List<Card> suitableCardsForAttackMoves(List<Card> table) {
        return cardsInHand.stream().filter(card -> mapOfCardsInTable(table).containsKey(card.getValue()))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param table - cards on the table
     * @return perfect attack card for not empty table and not empty deck
     */
    public Optional<Card> suitableAttackMoveBeforeEndOfDeck(List<Card> table) {
        List<Card> cards = suitableCardsForAttackMoves(table);
        if (cards.size() == 0) {
            return Optional.empty();
        }
        if (cards.size() == 1) {
            if (!cards.get(0).getTrump()) {
                return Optional.of(cards.get(0));
            }
        }
        return cards.stream().min(Comparator.comparingInt(Card::getValue).thenComparing(Card::getTrump));
    }

    /**
     *
     * @param table - cards on the table
     * @param trump - trump card
     * @param pile - cards what are already out from the game
     * @return List of cards what have player when deck size is 0
     */
    public List<Card> playerCardsInTheEnd(List<Card> table, Card trump, Pile pile) {
        List<Card> playerCards = new ArrayList<>();
        Deck deck = new Deck();
        deck.makeCardsTrump(trump.getSuit());
        for (Card card : deck.getDeck()) {
            if (!cardsInHand.contains(card) && !pile.getPile().contains(card)) {
                playerCards.add(card);
            }
        }
        return playerCards;
    }

    /**
     *
     * @param table - cards on the table
     * @param trump - trump card
     * @param pile - cards what are already out from the game
     * @return Map of cards what have player when deck size is 0 where key is suit
     */
    public Map<String, List<Card>> playerCardsInTheEndBySuit(List<Card> table, Card trump, Pile pile) {
        List<Card> playerCards = playerCardsInTheEnd(table, trump, pile);
        return playerCards.stream().collect(Collectors.groupingBy(Card::getSuit));
    }
    /**
     *
     * @param table - cards on the table
     * @param trump - trump card
     * @param pile - cards what are already out from the game
     * @return Map of cards what have player when deck size is 0 where key is value
     */
    public Map<Integer, List<Card>> playerCardsInTheEndByValue(List<Card> table, Card trump, Pile pile) {
        List<Card> playerCards = playerCardsInTheEnd(table, trump, pile);
        return playerCards.stream().collect(Collectors.groupingBy(Card::getValue));
    }

    /**
     *
     * @param table - cards on the table
     * @param trump - trump card
     * @param pile - cards what are already out from the game
     * @return best card for attack move when deck size is 0
     */
    public Optional<Card> suitableAttackMoveWhenDeckEnds(List<Card> table, Card trump, Pile pile) {
        Map<String, List<Card>> playerCards = playerCardsInTheEndBySuit(table, trump, pile);
        for (Card card : cardsInHand) {
            if (!playerCards.containsKey(card.getSuit()) && !card.getTrump()) {
                return Optional.of(card);
            }
        }
        for (Card card : cardsInHand) {
            if (!card.getTrump()) {
                if (card.getValue() > maxValueBySuit(table, card.getSuit(), trump, pile).get().getValue())
                    return Optional.of(card);
            }
        }
        for (Card card : cardsInHand) {
            if (mapOfCardsInHand().get(card.getValue()).size() >= 2 && !card.getTrump()) {
                return Optional.of(card);
            }
        }
        for (Card card : cardsInHand) {
            if (!playerCards.containsKey(card.getSuit())) {
                return Optional.of(card);
            }
        }
        return cardsInHand.stream().min(Comparator.comparingInt(Card::getValue).thenComparing(Card::getTrump));
    }

    /**
     *
     * @param suit - suit of the card for what you search max value
     * @param table - cards on the table
     * @param trump - trump card
     * @param pile - cards what are already out from the game
     * @return card with maximum value of given suit in player hand
     */
    public Optional<Card> maxValueBySuit(List<Card> table, String suit, Card trump, Pile pile) {
        return playerCardsInTheEnd(table, trump, pile).stream().filter(card -> card.getSuit().equals(suit)).max(Comparator.comparing(Card::getSuit).thenComparing(Card::getValue));
    }

    /**
     *
     * @param table - cards on the table
     * @param trump - trump card
     * @param pile - cards what are already out from the game
     * @return card which is most suitable for def when deck size is 0
     */
    public Optional<Card> suitableDefMoveWhenDeckEnds(List<Card> table, Card trump, Pile pile) {
        Map<Integer, List<Card>> playerCards = playerCardsInTheEndByValue(table, trump, pile);
        List<Card> cards = suitableForDefCards(table);
        if (cards.size() < 1) {
            return Optional.empty();
        }
        if (cards.size() == 1) {
            return Optional.of(cards.get(0));
        }
        for (Card card : cards) {
            if (!playerCards.containsKey(card.getValue()) && !card.getTrump()) {
                return Optional.of(card);
            }
        }
        for (Card card : cards) {
            if (!playerCards.containsKey(card.getValue())) {
                return Optional.of(card);
            }
        }
        return cards.stream().min(Comparator.comparingInt(Card::getValue));
    }

    /**
     *
     * @param state - state of the player
     * @param deck - deck of the game
     * @param table - cards on the table
     * @param trump - trump card
     * @param pile - cards what are already out from the game
     * @return best suitable card for this situation
     */
    public Optional<Card> getAiMove(PlayerState state, Deck deck, List<Card> table, Card trump, Pile pile) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (state.equals(PlayerState.ATTACK)) {
            if (deck.getDeck().size() < 1) {
                return suitableDefMoveWhenDeckEnds(table, trump, pile);
            } else {
                return mostSuitableCardForDef(table, pile);
            }
        } else {
            if (deck.getDeck().size() < 1) {
                if (table.size() == 0) {
                    return suitableAttackMoveWhenDeckEnds(table, trump, pile);
                } else {
                    return suitableAttackMoveBeforeEndOfDeck(table);
                }
            } else {
                if (table.size() == 0) {
                    return firstCardAttackMove();
                } else {
                    return suitableAttackMoveBeforeEndOfDeck(table);
                }
            }
        }
    }
}
