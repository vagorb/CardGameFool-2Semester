package game.help;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CardPackField {
    double cardUnit;
    double maxWidth;
    double maxHeight;

    public CardPackField(double cardUnit, double windowWidth) {
        this.cardUnit = cardUnit;
        maxWidth = windowWidth / 8;
        maxHeight = 8.5 * cardUnit;
    }

    public HBox createField() {
        HBox hbox = new HBox();
//        hbox.getChildren().add(vbox);
//        vbox.setTranslateY(-1.75 * cardUnit);
        hbox.setMinSize(maxWidth, maxHeight);
        hbox.setMaxSize(maxWidth, maxHeight);
//        hbox.setStyle("-fx-background-color: lightcoral");
//        vbox.setStyle("-fx-background-color: #00ffff");
        return hbox;
    }
}
