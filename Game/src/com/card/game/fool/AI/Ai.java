package com.card.game.fool.AI;

import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.Player;
import com.card.game.fool.players.gamerInterface;
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

    public List<Card> suitableForDefCards(Table table) {
        Card lastCard = table.getLastCardOnTable();
        if (lastCard.getTrump()) {
            return cardsInHand.stream().filter(card -> card.getValue() > lastCard.getValue()
                    && card.getTrump()).collect(Collectors.toList());
        } else {
            return cardsInHand.stream().filter(card -> card.getTrump()
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
                if (table.getPile().mapOfCardsAndValues().containsKey(card.getValue()) && table.getPile().mapOfCardsAndValues().get(card.getValue())
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

    public List<Card> suitableCardsForAttackMoves(Table table) {
        return cardsInHand.stream().filter(card -> table.mapOfCardsInTable().containsKey(card.getValue()))
                .collect(Collectors.toList());
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
        return cardsInHand.stream().min(Comparator.comparingInt(Card::getValue).thenComparing(Card::getTrump));
    }

    public List<Card> playerCardsInTheEnd(Table table) {
        List<Card> playerCards = new ArrayList<>();
        Deck deck = new Deck();
        deck.makeCardsTrump(table.getTrumpSuit());
        for (Card card : deck.getDeck()) {
            if (!cardsInHand.contains(card) && !table.getPile().getPile().contains(card)) {
                playerCards.add(card);
            }
        }
        return playerCards;
    }

    public Map<String, List<Card>> playerCardsInTheEndBySuit(Table table) {
        List<Card> playerCards = playerCardsInTheEnd(table);
        return playerCards.stream().collect(Collectors.groupingBy(Card::getSuit));
    }

    public Map<Integer, List<Card>> playerCardsInTheEndByValue(Table table) {
        List<Card> playerCards = playerCardsInTheEnd(table);
        return playerCards.stream().collect(Collectors.groupingBy(Card::getValue));
    }


    public Optional<Card> suitableAttackMoveWhenDeckEnds(Table table) {
        Map<String, List<Card>> playerCards = playerCardsInTheEndBySuit(table);
        System.out.println(playerCards);
        for (Card card : cardsInHand) {
            if (!playerCards.containsKey(card.getSuit()) && !card.getTrump()) {
                return Optional.of(card);
            }
        }
        for (Card card : cardsInHand) {
            if (!card.getTrump()) {
                if (card.getValue() > maxValueBySuit(table, card.getSuit()).get().getValue())
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

    public Optional<Card> maxValueBySuit(Table table, String suit) {
        return playerCardsInTheEnd(table).stream().filter(card -> card.getSuit().equals(suit)).max(Comparator.comparing(Card::getSuit).thenComparing(Card::getValue));
    }

    public Optional<Card> suitableDefMoveWhenDeckEnds(Table table) {
        Map<Integer, List<Card>> playerCards = playerCardsInTheEndByValue(table);
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


    public Optional<Card> getAiMove(Table table) {
        if (table.getPlayers().get(0).getPlayerState().equals(Player.PlayerState.ATTACK)) {
            if (table.getGameDeck().size() < 1) {
                return suitableDefMoveWhenDeckEnds(table);
            } else {
                return mostSuitableCardForDef(table);
            }
        } else {
            if (table.getGameDeck().size() < 1) {
                if (table.getTable().size() == 0) {
                    return suitableAttackMoveWhenDeckEnds(table);
                } else {
                    return suitableAttackMoveBeforeEndOfDeck(table);
                }
            } else {
                if (table.getTable().size() == 0) {
                    return firstCardAttackMove();
                } else {
                    return suitableAttackMoveBeforeEndOfDeck(table);
                }
            }
        }

    }
}
