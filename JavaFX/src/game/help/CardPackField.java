package game.help;

import com.card.game.fool.cards.Card;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CardPackField {
    private final double cardUnit;
    private final double maxWidth;
    private final double maxHeight;
    private final Button button;
    private final HBox pileCards = new HBox();
    private final HBox deckCards = new HBox();
    private final HBox pileField = new HBox(pileCards);
    private final HBox deckField = new HBox(deckCards);

    public CardPackField(double cardUnitSize, double windowWidth, Button throwCards) {
        this.cardUnit = cardUnitSize;
        maxWidth = windowWidth / 8;
        maxHeight = 8.5 * cardUnit;
        button = throwCards;
    }

    private void oneCard(HBox field) {
        Label newB = new Label();
        newB.setMinSize(cardUnit * 2, cardUnit * 3);
        newB.setMaxSize(cardUnit * 2, cardUnit * 3);
        newB.setStyle("-fx-background-image: url('/images/cards/back_of_card.png'); -fx-background-size: cover");
        newB.setTranslateX(-(2 * field.getChildren().size() * cardUnit - 2) * 0.98);
        newB.setTranslateY(field.getChildren().size() * cardUnit / 6);
        field.getChildren().add(newB);
    }

    private void trumpCard(Card trump) {
        Label newB = new Label();
        newB.setMinSize(cardUnit * 2, cardUnit * 3);
        newB.setMaxSize(cardUnit * 2, cardUnit * 3);
        newB.setStyle(String.format("-fx-background-image: url('/images/cards/%s/%s.png'); -fx-background-size: cover",
                trump.getSuit(), trump.getId()));
        newB.setTranslateX(-2 * deckCards.getChildren().size() * cardUnit - 30);
        newB.setTranslateY(deckCards.getChildren().size() * cardUnit / 6);
        newB.setRotate(90);
        deckCards.getChildren().add(newB);
    }

    public HBox addFields(VBox playField) {
        for (HBox field : List.of(deckField, pileField)) {
            field.setMinSize(maxWidth, maxHeight);
            field.setMaxSize(maxWidth, maxHeight);
        }

        trumpCard(new Card("Hearts", 14, false));
        for (int i = 0; i < 35; i++) {
            oneCard(deckCards);
        }

        deckCards.getChildren().addListener((ListChangeListener<Node>) change -> {
            change.next();
            if (!change.getRemoved().isEmpty()) {
                for (int i = 0; i < change.getRemovedSize(); i++) {
                    oneCard(pileCards);
                }
            }
        });
        return new HBox(deckField, playField, pileField);
    }
}
