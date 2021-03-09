package com.card.game.fool.tables;

import com.card.game.fool.cards.Card;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pile {
    private List<Card> listOfDiscardedCards;



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
