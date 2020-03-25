package appearance;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BackgroundSetter {
    public Background setColor(String color) {
        return new Background(new BackgroundFill(Paint.valueOf(color), CornerRadii.EMPTY, Insets.EMPTY));
    }

    public Background setColor(Color color) {
        return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public Background setImage(String img, Button element) {
        return new Background(new BackgroundImage(
                new Image(img, element.getMaxHeight(), element.getMaxHeight(), true, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
    }
}
