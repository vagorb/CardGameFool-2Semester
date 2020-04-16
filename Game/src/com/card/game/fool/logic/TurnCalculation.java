package com.card.game.fool.logic;

import com.card.game.fool.cards.Card;
import com.card.game.fool.players.Player;
import com.card.game.fool.tables.Table;

public class TurnCalculation {

    private Table playingTable;
    private boolean defenseSuccess = true;
    private Card attackCard;
    private Card defenseCard;

    // Here we put attack and defense cards on the table by players
    // Also here happens most of the math for what cards can come and what type of cards can be put on table.

    //Should probably rename this to GameTurn class which implies that here happent attack/defense turns
    // we could possible work on skip in here as well

    /**
     * Class constructor, requires the table class object on which it does it's calculations
     * @param table object from which the data is gathered to do the calculations
     */
    public TurnCalculation(Table table) {
        this.playingTable = table;
    }

    /**
     * Getter
     * @return table we are doing calculations for
     */
    public Table getPlayingTable() {
        return playingTable;
    }

    /**
     * Method that checks if we are allowed to put a specific card on the table by the attacker
     * @param player player that is attacking
     * @param card  that the player puts for the attack
     */
    public void putAttackCard(Player player , Card card) {
        if (playingTable.getTable().size() == 0) {
            playingTable.getTable().add(player.getHand().putCardOnTable(card));
            player.getHand().removeCard(card);
            attackCard = card;
        }else if (playingTable.getTable().size() > 0) {
            if (playingTable.mapOfCardsInTable().containsKey(card.getValue())) {
                playingTable.getTable().add(player.getHand().putCardOnTable(card));
                player.getHand().removeCard(card);
                attackCard = card;
            }
//            for (com.card.game.fool.cards.Card value : playingTable.getTable()) {

//                if (value.getValue().equals(card.getValue())) {
//                    playingTable.getTable().add(player.getHand().putCardOnTable(card));
//                    player.getHand().removeCard(card);
//                    attackCard = card;
//                }
//            }
        }
    }

    /**
     * Method that checks if we can defend with a specific card that the defender puts
     * @param player player that is defending
     * @param card that the player puts for the defense
     */
    public void putDefenseCard(Player player, Card card) {
        if (card.getSuit().equals(playingTable.getTrumpSuit())) {
            if (attackCard.getSuit().equals(playingTable.getTrumpSuit())) {
                if (card.getValue() > attackCard.getValue()) {
                    playingTable.getTable().add(player.getHand().putCardOnTable(card));
                    player.getHand().removeCard(card);
                    defenseCard = card;
                    defenseSuccess = true;
                } else {
                    defenseSuccess = false;
                }
            } else {
                playingTable.getTable().add(player.getHand().putCardOnTable(card));
                player.getHand().removeCard(card);
                defenseCard = card;
                defenseSuccess = true;
            }
        } else if (card.getSuit().equals(attackCard.getSuit())) {
            if (card.getValue() > attackCard.getValue()) {
                playingTable.getTable().add(player.getHand().putCardOnTable(card));
                player.getHand().removeCard(card);
                defenseCard = card;
                defenseSuccess = true;
            } else {
                defenseSuccess = false;
            }
        } else if (!card.getSuit().equals(attackCard.getSuit())) {
            defenseSuccess = false;
        }
//        if (card.getSuit().equals(attackCard.getSuit()) || card.getSuit().equals(playingTable.getTrumpSuit())) {
//            if (card.getValue() > attackCard.getValue()) {
//                playingTable.getTable().add(player.getHand().putCardOnTable(card));
//                player.getHand().removeCard(card);
//                defenseCard = card;
//                defenseSuccess = true;
//            } else {
//                defenseSuccess = false;
//            }
//        }
//        defenseSuccess = false;

    }


    /**
     * Method that adds cards to pile or to the hand of the defender who lost his defense.
     * @param player  to possibly add the cards to.
     */
    public void addAttackAndDefenseCardsToPileOrPlayer(Player player) {
        if (defenseSuccess) {
            playingTable.getPile().getPile().addAll(playingTable.getTable());
        } else {
            for (Card card : playingTable.getTable()) {
                player.getHand().addCard(card);
            }
        }
        playingTable.getTable().removeAll(playingTable.getTable());
//        table.removeAll(table);
    }

    /**
     * com.card.game.fool.cards.Card comparison class
     */
    public void compareCards() {
        if (attackCard != null && defenseCard == null) {
            defenseSuccess = false;
        } else if (attackCard.getValue() < defenseCard.getValue() && attackCard.getSuit().equals(defenseCard.getSuit())) {
            defenseSuccess = true;
        }
    }


    /**
     * Getter
     * @return attack card
     */
    public Card getAttackCard() {
        return attackCard;
    }


    /**
     * Getter
     * @return defense card
     */
    public Card getDefenseCard() {
        return defenseCard;
    }


    /**
     * Getter
     * @return boolean true if player successfully defender , or false if player failed to defend
     */
    public boolean getDefenseSuccess() {
        return defenseSuccess;
    }

    /**
     * Setter
     * @param bool set defense success to a specific boolean value ( happens after calculations for a lost defense ended )
     */
    public void setDefenseSuccess(boolean bool) {
        this.defenseSuccess = bool;
    }
}
