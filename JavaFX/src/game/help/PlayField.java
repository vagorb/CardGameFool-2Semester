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
    double playfieldCardUnit;

    public PlayField(double playfieldCardUnit) {
        this.playfieldCardUnit = playfieldCardUnit;
    }

    public Map<Integer, Pane> createButtons() {
        Button attack1 = new Button("attack1");
        Button attack2 = new Button("attack2");
        Button attack3 = new Button("attack3");
        Button attack4 = new Button("attack4");
        Button attack5 = new Button("attack5");
        Button attack6 = new Button("attack6");
        Button defence1 = new Button("defence1");
        Button defence2 = new Button("defence2");
        Button defence3 = new Button("defence3");
        Button defence4 = new Button("defence4");
        Button defence5 = new Button("defence5");
        Button defence6 = new Button("defence6");

        Pane first = new Pane();
        first.getChildren().addAll(attack1, defence1);
        Pane second = new Pane();
        second.getChildren().addAll(attack2, defence2);
        Pane third = new Pane();
        third.getChildren().addAll(attack3, defence3);
        Pane fourth = new Pane();
        fourth.getChildren().addAll(attack4, defence4);
        Pane fifth = new Pane();
        fifth.getChildren().addAll(attack5, defence5);
        Pane sixth = new Pane();
        sixth.getChildren().addAll(attack6, defence6);
        for (Pane pane : Arrays.asList(first, second, third, fourth, fifth, sixth)) {
            pane.setMinSize(playfieldCardUnit * 3.5, playfieldCardUnit * 4);
            for (Node child : pane.getChildren())
                if (child.getClass() == Button.class) {
                    ((Button) child).setMinSize(playfieldCardUnit * 2, playfieldCardUnit * 3);
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

        playZone.setStyle("-fx-background-color: rgb(200, 200, 200, 0.5)");
        upperLayer.setStyle("-fx-background-color: rgb(200, 0, 100,0.8)");
        lowerLayer.setStyle("-fx-background-color: rgb(0, 100, 200, 0.8)");
        return Arrays.asList(playZone, upperLayer, lowerLayer);
    }
}
