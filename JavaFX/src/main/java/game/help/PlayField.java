package game.help;


import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PlayField {
    private final double cardUnitSize;
    private Map<Integer, HBox> playFieldButtons;

    public PlayField(double cardUnitSize) {
        this.cardUnitSize = cardUnitSize;
    }

    public Map<Integer, HBox> createButtons() {
        HBox first = new HBox();
        first.setId("firstPair");
        HBox second = new HBox();
        second.setId("secondPair");
        HBox third = new HBox();
        third.setId("thirdPair");
        HBox fourth = new HBox();
        fourth.setId("fourthPair");
        HBox fifth = new HBox();
        fifth.setId("fifthPair");
        HBox sixth = new HBox();
        sixth.setId("sixthPair");

        for (HBox hbox : List.of(first, second, third, fourth, fifth, sixth)) {
            Button attack = new Button();
            Button defense = new Button();
            attack.setId("Attack");
            defense.setId("Defence");

            attack.setTranslateX(cardUnitSize);
            attack.setTranslateY(cardUnitSize);
            defense.setVisible(false);

            hbox.getChildren().addAll(attack, defense);
            hbox.setMinSize(cardUnitSize * 3.5, cardUnitSize * 4);
            if (hbox != first) {
                hbox.setVisible(false);
            }
            for (Node child : hbox.getChildren())
                if (child.getClass() == Button.class) {
                    ((Button) child).setMinSize(cardUnitSize * 2, cardUnitSize * 3);
                    child.setStyle("-fx-background-image: null;");
                }
        }
        playFieldButtons = Map.of(1, first, 2, second, 3, third, 4, fourth, 5, fifth, 6, sixth);
        return playFieldButtons;
    }

    public List<Pane> createPlayfield() {
        HBox upperLayer = new HBox();
        HBox lowerLayer = new HBox();
        VBox playZone = new VBox(cardUnitSize / 2);

        playZone.setMaxSize(cardUnitSize * 10.5, cardUnitSize * 8.5);
        upperLayer.setMinSize(playZone.getMaxWidth(), cardUnitSize * 4);
        lowerLayer.setMinSize(playZone.getMaxWidth(), cardUnitSize * 4);

        return Arrays.asList(playZone, upperLayer, lowerLayer);
    }
}
