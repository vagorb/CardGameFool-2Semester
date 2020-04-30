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
    private double cardUnitSize;
    private Map<Integer, Pane> playFieldButtons;

    public PlayField(double cardUnitSize) {
        this.cardUnitSize = cardUnitSize;
    }

    public Map<Integer, Pane> createButtons() {
        Pane first = new Pane();
        first.setId("firstPair");
        Pane second = new Pane();
        second.setId("secondPair");
        Pane third = new Pane();
        third.setId("thirdPair");
        Pane fourth = new Pane();
        fourth.setId("fourthPair");
        Pane fifth = new Pane();
        fifth.setId("fifthPair");
        Pane sixth = new Pane();
        sixth.setId("sixthPair");

        for (Pane pane : List.of(first, second, third, fourth, fifth, sixth)) {
            pane.getChildren().addAll(new Button(), new Button());
            pane.setMinSize(cardUnitSize * 3.5, cardUnitSize * 4);
            if (pane != first) {
                pane.setVisible(false);
            }
            for (Node child : pane.getChildren())
                if (child.getClass() == Button.class) {
                    ((Button) child).setMinSize(cardUnitSize * 2, cardUnitSize * 3);
                    child.setStyle("-fx-background-image: null");
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

    public void setDefault(Map<Integer, Pane> playFieldButtons) {
        playFieldButtons.forEach((integer, pane) -> {
            if (!pane.getId().equals("firstPair")) {
                pane.setVisible(false);
            }
            for (int i = 0; i < 2; i++) {
                Button button = (Button) pane.getChildrenUnmodifiable().get(i);
                button.setDisable(false);
                button.setStyle("-fx-background-image: null");
                button.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
                button.setVisible(!button.getId().equals("Defence"));
            }
        });
    }

    public void nextAttackVisible(Button defence) {
        for (int nr = 1; nr <= 6; nr++) {
            if (defence == playFieldButtons.get(nr).getChildrenUnmodifiable().get(1)) {
                playFieldButtons.get(nr + 1).setVisible(true);
                break;
            }
        }
    }
}
