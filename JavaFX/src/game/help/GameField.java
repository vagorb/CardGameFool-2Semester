package game.help;

import com.card.game.fool.cards.Card;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class GameField {
    private final double cardUnit;
    private final double maxWidth;
    private final double maxHeight;
    private final HBox pileCards = new HBox();
    private final HBox deckCards = new HBox();
    private final HBox pileField = new HBox(pileCards);
    private final HBox deckField = new HBox(deckCards);

    public GameField(double cardUnitSize, double windowWidth) {
        this.cardUnit = cardUnitSize;
        maxWidth = windowWidth / 8;
        maxHeight = 8.5 * cardUnit;

        oneSizeOnly(deckCards);
        oneSizeOnly(pileCards);
        deckField.setAlignment(Pos.CENTER);
        pileField.setAlignment(Pos.CENTER);
    }

    private void oneSizeOnly(HBox hBox) {
        hBox.setMinSize(cardUnit * 2, cardUnit * 3);
        hBox.setMaxSize(cardUnit * 2, cardUnit * 3);
    }

    private void oneSizeOnly(Label label) {
        label.setMinSize(cardUnit * 2, cardUnit * 3);
        label.setMaxSize(cardUnit * 2, cardUnit * 3);
    }

    private Label oneCard() {
        Label cardLabel = new Label();
        oneSizeOnly(cardLabel);
        cardLabel.setStyle("-fx-background-image: url('/images/cards/back_of_card.png'); -fx-background-size: cover;" +
                "-fx-text-fill: red; -fx-alignment: center; -fx-font: bold " + 1.2 * cardUnit + "pt Arial;");
        return cardLabel;
    }

    private Label trumpCard(Card trumpCard) {
        Label trumpLabel = new Label();
        oneSizeOnly(trumpLabel);
        trumpLabel.setStyle(String.format("-fx-background-image: url('/images/cards/%s/%s.png');" +
                "-fx-background-size: cover", trumpCard.getSuit(), trumpCard.getId()) + ';');
        trumpLabel.setTranslateX(-0.6 * cardUnit);
        trumpLabel.setRotate(95);
        return trumpLabel;
    }

    public HBox addFields(VBox playField, Card trump) {
        for (HBox field : List.of(deckField, pileField)) {
            field.setMinSize(maxWidth, maxHeight);
            field.setMaxSize(maxWidth, maxHeight);
        }
        deckCards.getChildren().addAll(trumpCard(trump), oneCard());
        deckCards.getChildren().get(1).setTranslateX(-2 * cardUnit);
        pileCards.getChildren().add(oneCard());
        pileCards.setVisible(false);
        for (Node label : List.of(deckCards.getChildren().get(1), pileCards.getChildren().get(0))) {
            System.out.println(label);
        }

        return new HBox(deckField, playField, pileField);
    }

    public void updateVisualPile(int size) {
        ((Label) pileCards.getChildren().get(0)).setText(String.valueOf(size));
        pileCards.setVisible(!((Label) pileCards.getChildren().get(0)).getText().equals("0"));
    }

    public void updateVisualDeck(int size) {
        ((Label) deckCards.getChildren().get(1)).setText(String.valueOf(size));
        deckCards.getChildren().get(1).setVisible(size > 1);
        deckCards.getChildren().get(0).setVisible(size >= 1);
    }
}
