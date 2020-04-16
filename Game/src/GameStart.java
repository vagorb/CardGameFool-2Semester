import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameStart {
//    private Table playingTable;
//    private List<Card> trumpCard = new ArrayList<>();


    /**
     * Our main method that runs all the required classes and methods to play a game of player vs player
     * @param args main method arguments
     */
    public static void main(String[] args) {

        boolean gameIsBeingPlayed = true;
        GameController codeIsHere = new GameController();
        codeIsHere.createBasic();
        codeIsHere.assignTrumpAndFillHands(codeIsHere.getTable());
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

}
