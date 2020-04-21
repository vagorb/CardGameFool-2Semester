package game.help;

import javafx.stage.Stage;

public class Resolution {
    private double windowWidth;
    private double windowHeight;

    public double width() {
        return windowWidth;
    }

    public double height() {
        return windowHeight;
    }

    public void change(Stage window, double width, double height) {
        this.windowWidth = width;
        this.windowHeight = height;
        window.setWidth(windowWidth);
        window.setHeight(windowHeight);
    }
}
