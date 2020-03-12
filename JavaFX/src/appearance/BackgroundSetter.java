package appearance;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public class BackgroundSetter {
    public Background setColor(String color) {
        return new Background(new BackgroundFill(Paint.valueOf(color), CornerRadii.EMPTY, Insets.EMPTY));
    }

}
