package com.card.game.fool.logic;

public class GameStart {
//    private com.card.game.fool.tables.Table playingTable;
//    private List<com.card.game.fool.cards.Card> trumpCard = new ArrayList<>();


    /**
     * Our main method that runs all the required classes and methods to play a game of player vs player
     * @param args main method arguments
     */
    public static void main(String[] args) {

        boolean gameIsBeingPlayed = true;
        GameController codeIsHere = new GameController();
        codeIsHere.createBasic();
        codeIsHere.assignTrumpAndFillHands();
        while (gameIsBeingPlayed) {
            if (codeIsHere.getTable().getGameDeck().size() >= 0 && codeIsHere.getCardThatDecidesTrump() != null) {
                codeIsHere.setPlayerStates();
                codeIsHere.attackDefenseTurn();
                codeIsHere.refillCards();
            } else {
                codeIsHere.setPlayerStates();
                codeIsHere.attackDefenseTurn();
                gameIsBeingPlayed = codeIsHere.endGame();
            }
        }
        codeIsHere.ending();
    }

//    while (someStatement == true) {
    // Decide a trump card, give every player in the game (in this exact table) 6 cards from com.card.game.fool.cards.Deck to they're com.card.game.fool.players.Hand
    // Decide by some logic who starts first ( makes the first attack )
    // Put attack card / defense card. Repeat if possible
    // Ask other players ( if table size > 2 ) if they want to add extra attack cards to defend against
    // Decide if cards go to defending player com.card.game.fool.players.Hand(Lost defense) or to pile ( Successful defense)
    // Replenish player com.card.game.fool.players.Hand ( up to 6 cards) from deck
    // If defense is successful next player (Defending player becomes the attacker) and the next player clock wise becomes the defender
    // If defense is unsuccessful skip the player that was defending and make another player Attacker ( and the next one a defender )
    //
    //
    // All this happens continuously until deck size becomes 0
    // When it becomes zero any player who has 0 cards in com.card.game.fool.players.Hand finishes the game as a (partial) winner
    // Last player in the game with cards is declared a loser ( FOOL )
    // When loser is declared someStatement == false
    // Program ( game ) ends
//    }

}
