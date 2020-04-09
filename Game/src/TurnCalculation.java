import java.util.List;

public class TurnCalculation {

    private Table playingTable;
    private boolean currentDefenseState;
    private Card attackCard;
    private Card defenseCard;

    // Here we put attack and defense cards on the table by players
    // Also here happens most of the math for what cards can come and what type of cards can be put on table.

    //Should probably rename this to GameTurn class which implies that here happent attack/defense turns
    // we could possible work on skip in here as well

    public TurnCalculation(Table table) {
        this.playingTable = table;
    }


    public void putAttackCard(Player player , Card card) {
        if (playingTable.getTable().size() == 0) {
            playingTable.getTable().add(player.getHand().putCardOnTable(card));
            player.getHand().removeCard(card);
            attackCard = card;
        }else if (playingTable.getTable().size() > 0) {
            for (Card value : playingTable.getTable()) {
                if (value.getValue().equals(card.getValue())) {
                    playingTable.getTable().add(player.getHand().putCardOnTable(card));
                    player.getHand().removeCard(card);
                    attackCard = card;
                }
            }
        }
    }

    public void putDefenseCard(Player player, Card card) {
        if (card.getSuit().equals(attackCard.getSuit()) || card.getSuit().equals(playingTable.getTrumpSuit())) {
            if (card.getValue() > attackCard.getValue()) {
                playingTable.getTable().add(player.getHand().putCardOnTable(card));
                player.getHand().removeCard(card);
                defenseCard = card;
            }
        }
    }


//    public void addAttackAndDefenseCardsToPileOrPlayer(Player player) {
//        if (currentDefenseState) {
//            playingTable.getPile().addAll(playingTable.getTable());
//        } else {
//            for (Card card : playingTable.getTable()) {
//                player.getHand().addCard(card);
//            }
//        }
//        playingTable.getTable().removeAll(playingTable.getTable());
////        table.removeAll(table);
//    }
//
    public void compareCards() {
//        if (attackCard.getValue() > defenseCard.getValue() && attackCard.getSuit().equals(defenseCard.getSuit())) {
//            currentDefenseState = false;
        if (attackCard != null && defenseCard == null) {
            currentDefenseState = false;
        } else if (attackCard.getValue() < defenseCard.getValue() && attackCard.getSuit().equals(defenseCard.suit)) {
            currentDefenseState = true;
        }
    }


    public Card getAttackCard() {
        return attackCard;
    }

    public Card getDefenseCard() {
        return defenseCard;
    }
    public boolean getCurrentDefenseState() {
        return currentDefenseState;
    }
}
