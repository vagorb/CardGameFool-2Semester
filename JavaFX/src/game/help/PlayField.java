package game.help;

import com.card.game.fool.players.Player;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PlayField {
    double playfieldCardUnit;

    public void setDefault(Map<Integer, Pane> playFieldButtons) {
        playFieldButtons.forEach((integer, pane) -> {
            for (int i = 0; i < 2; i++) {
                Button button = (Button) pane.getChildrenUnmodifiable().get(i);
                button.setDisable(false);
                button.setStyle("-fx-background-image: null");
                button.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
                button.setVisible(!button.getId().equals("Defence"));
            }
        });
    }

    public PlayField(double playfieldCardUnit) {
        this.playfieldCardUnit = playfieldCardUnit;
    }

    public Map<Integer, Pane> createButtons() {
        Button attack1 = new Button();
        Button attack2 = new Button();
        Button attack3 = new Button();
        Button attack4 = new Button();
        Button attack5 = new Button();
        Button attack6 = new Button();
        Button defence1 = new Button();
        Button defence2 = new Button();
        Button defence3 = new Button();
        Button defence4 = new Button();
        Button defence5 = new Button();
        Button defence6 = new Button();

        Pane first = new Pane();
        first.getChildren().addAll(attack1, defence1);
        Pane second = new Pane();
        second.getChildren().addAll(attack2, defence2);
        second.setVisible(false);
        Pane third = new Pane();
        third.getChildren().addAll(attack3, defence3);
        third.setVisible(false);
        Pane fourth = new Pane();
        fourth.getChildren().addAll(attack4, defence4);
        fourth.setVisible(false);
        Pane fifth = new Pane();
        fifth.getChildren().addAll(attack5, defence5);
        fifth.setVisible(false);
        Pane sixth = new Pane();
        sixth.getChildren().addAll(attack6, defence6);
        sixth.setVisible(false);
        for (Pane pane : Arrays.asList(first, second, third, fourth, fifth, sixth)) {
            pane.setMinSize(playfieldCardUnit * 3.5, playfieldCardUnit * 4);
            for (Node child : pane.getChildren())
                if (child.getClass() == Button.class) {
                    ((Button) child).setMinSize(playfieldCardUnit * 2, playfieldCardUnit * 3);
                    child.setStyle("-fx-background-image: null");
                }
        }
        return Map.of(1, first, 2, second, 3, third, 4, fourth, 5, fifth, 6, sixth);
    }

    public List<Pane> createPlayfield() {
        HBox upperLayer = new HBox();
        HBox lowerLayer = new HBox();
        VBox playZone = new VBox(playfieldCardUnit / 2);

        playZone.setTranslateY(-playfieldCardUnit);

        playZone.setMaxSize(playfieldCardUnit * 10.5, playfieldCardUnit * 8.5);
        upperLayer.setMinSize(playZone.getMaxWidth(), playfieldCardUnit * 4);
        lowerLayer.setMinSize(playZone.getMaxWidth(), playfieldCardUnit * 4);

        return Arrays.asList(playZone, upperLayer, lowerLayer);
    }
}
