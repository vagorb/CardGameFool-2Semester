package com.card.game.fool.AI;

import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.Player;
import com.card.game.fool.players.PlayerState;
import com.card.game.fool.players.gamerInterface;
import com.card.game.fool.tables.Pile;
import com.card.game.fool.tables.Table;

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

public class Ai implements gamerInterface {
    private final List<Card> cardsInHand = new LinkedList<>();

    public String getName() {
        return "CPU";
    }

    public List<Card> getHand() {
        return cardsInHand;
    }

    public Map<Integer, List<Card>> mapOfCardsInHand() {
        return cardsInHand.stream().collect(Collectors.groupingBy(Card::getValue));
    }

    public Map<Integer, List<Card>> mapOfCardsInTable(List<Card> table) {
        return table.stream().collect(Collectors.groupingBy(Card::getValue));
    }

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

    public Optional<Card> firstCardAttackMove() {
        for (Card card : cardsInHand) {
            if (mapOfCardsInHand().get(card.getValue()).size() >= 2 && !card.getTrump()) {
                return Optional.of(card);
            }
        }
        return cardsInHand.stream().min(Comparator.comparingInt(Card::getValue));
    }

    public List<Card> suitableCardsForAttackMoves(List<Card> table) {
        return cardsInHand.stream().filter(card -> mapOfCardsInTable(table).containsKey(card.getValue()))
                .collect(Collectors.toList());
    }

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

    public Map<String, List<Card>> playerCardsInTheEndBySuit(List<Card> table, Card trump, Pile pile) {
        List<Card> playerCards = playerCardsInTheEnd(table, trump, pile);
        return playerCards.stream().collect(Collectors.groupingBy(Card::getSuit));
    }

    public Map<Integer, List<Card>> playerCardsInTheEndByValue(List<Card> table, Card trump, Pile pile) {
        List<Card> playerCards = playerCardsInTheEnd(table, trump, pile);
        return playerCards.stream().collect(Collectors.groupingBy(Card::getValue));
    }


    public Optional<Card> suitableAttackMoveWhenDeckEnds(List<Card> table, Card trump, Pile pile) {
        Map<String, List<Card>> playerCards = playerCardsInTheEndBySuit(table, trump, pile);
        System.out.println(playerCards);
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

    public Optional<Card> maxValueBySuit(List<Card> table, String suit, Card trump, Pile pile) {
        return playerCardsInTheEnd(table, trump, pile).stream().filter(card -> card.getSuit().equals(suit)).max(Comparator.comparing(Card::getSuit).thenComparing(Card::getValue));
    }

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
