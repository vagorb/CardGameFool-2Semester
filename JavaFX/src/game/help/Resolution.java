package game.help;

import javafx.stage.Screen;
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

    public Double[] max(double width, double height) {
        if (width > Screen.getPrimary().getBounds().getWidth()) {
            width = Screen.getPrimary().getBounds().getWidth();
        }
        if (height > Screen.getPrimary().getBounds().getHeight()) {
            height = Screen.getPrimary().getBounds().getHeight();
        }
        return new Double[]{width, height};
    }
    public Double[] max(String width, String height) {
        double widthDouble = Double.parseDouble(width);
        double heightDouble = Double.parseDouble(height);
        return max(widthDouble, heightDouble);
    }

    public void change(Stage window, double width, double height) {
        Double[] max = max(width, height);
        this.windowWidth = max[0];
        this.windowHeight = max[1];
        window.setWidth(windowWidth);
        window.setHeight(windowHeight);
    }
}
