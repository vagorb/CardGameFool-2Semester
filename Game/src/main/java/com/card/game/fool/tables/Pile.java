package com.card.game.fool.tables;

import com.card.game.fool.cards.Card;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pile {
    // com.card.game.fool.tables.Pile class, should have all cards that were discarded after successful defense of a player
    //
    private List<Card> listOfDiscardedCards;
    // Currently it seems like this class is very useless to me. All it does is existing, and it servers a purpose of an ArrayList<com.card.game.fool.cards.Card>
    // which we can implement with 1 private List<com.card.game.fool.cards.Card> pile = new ArrayList<>() variable.


    public Pile(List<Card> pile){
        this.listOfDiscardedCards = pile;
    }



    public void addDiscardedCards(Card cardToAddToDiscard) {
        listOfDiscardedCards.add(cardToAddToDiscard);

    }
    public List<Card> getPile() {
        return listOfDiscardedCards;
    }

    public Map<Integer, List<Card>> mapOfCardsAndValues() {
        List<Card> pile = getPile();
        return pile.stream().collect(Collectors.groupingBy(Card::getValue));
    }


}
