package appearance;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;

public class BackgroundSetter {
    public Background setByColor(String color) {
        return new Background(new BackgroundFill(Paint.valueOf(color), CornerRadii.EMPTY, Insets.EMPTY));
    }

    public Background setByColor(Color color) {
        return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public Background setImage(String imagePath, Button element) {
        double width = element.getWidth();
        double height = element.getHeight();
        if (element.widthProperty().get() < 1) {
            width = element.getMaxWidth();
        }
        if (element.heightProperty().get() < 1) {
            height = element.getMaxHeight();
        }
        return new Background(new BackgroundImage(
                new Image(String.valueOf(imagePath), width, height, true, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
    }

    public Background setImage(URL imagePath, Pane element) {
        double width = element.getWidth();
        double height = element.getHeight();
        if (element.widthProperty().get() < 1) {
            width = element.getMaxWidth();
        }
        if (element.heightProperty().get() < 1) {
            height = element.getMaxHeight();
        }
        return new Background(new BackgroundImage(
                new Image(String.valueOf(imagePath), width, height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        ));
    }

}
