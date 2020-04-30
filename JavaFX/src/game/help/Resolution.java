package game.help;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;

public class Resolution {
    private final Stage window;
    private double windowWidth;
    private double windowHeight;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean fixedResolution = true;
    private CheckBox customResolution;
    private ComboBox<String> resolutionChoices;

    public Resolution(Stage window) {
        this.window = window;
        makeCustomResoCheckbox();
        makeResolutionChoices();
        actions();
    }

    private void actions() {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            if (!customResolution.isSelected()) {
                window.setX((Screen.getPrimary().getBounds().getWidth() - windowWidth) / 2);
                window.setY((Screen.getPrimary().getBounds().getHeight() - windowHeight) / 2);
            }
        };

        window.getScene().setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        window.getScene().setOnMouseDragged(event -> {
            window.setX(event.getScreenX() - xOffset);
            window.setY(event.getScreenY() - yOffset);
        });

        window.heightProperty().addListener(stageSizeListener);
        window.minWidthProperty().bind(window.heightProperty().multiply(16d / 9));
        window.maxWidthProperty().bind(window.heightProperty().multiply(16d / 9));

        window.getScene().setOnScroll(scrollEvent -> {
            if (!fixedResolution) {
                double newHeight = window.getHeight() + scrollEvent.getDeltaY();
                if (newHeight >= 720 && newHeight <= Screen.getPrimary().getBounds().getHeight()) {
                    window.setHeight(newHeight);
                }
            }
        });
    }

    private void makeCustomResoCheckbox() {
        CheckBox customResolution = new CheckBox();
        customResolution.setOnAction(actionEvent -> {
            fixedResolution = !customResolution.isSelected();
            window.setResizable(customResolution.isSelected());
            resolutionChoices.setDisable(customResolution.isSelected());
            resolutionChoices.setValue("");
        });
        this.customResolution = customResolution;
    }

    private void makeResolutionChoices() {
        ComboBox<String> choices = new ComboBox<>();
        for (String reso : List.of("1280x720", "1366x768", "1600x900", "1920x1080", "2560x1440", "3840x2160")) {
            if (Integer.parseInt(reso.split("x")[1]) <= Screen.getPrimary().getBounds().getHeight()) {
                choices.getItems().add(reso);
            }
        }
        choices.valueProperty().addListener((observableValue, s, t1) -> {
            if (!t1.equals("")) {
                String[] values = t1.split("x");
                change(values[0], values[1]);
                window.setY((Screen.getPrimary().getBounds().getHeight() - windowHeight) / 2);
            }
        });
        this.resolutionChoices = choices;
    }

    public boolean isFixedResolution() {
        return fixedResolution;
    }

    public CheckBox getCustomResolution() {
        return customResolution;
    }

    public ComboBox<String> getResolutionChoices() {
        return resolutionChoices;
    }

    public double width() {
        return windowWidth;
    }

    public double height() {
        return windowHeight;
    }

    private Double[] max(double width, double height) {
        if (width > Screen.getPrimary().getBounds().getWidth()) {
            width = Screen.getPrimary().getBounds().getWidth();
        }
        if (height > Screen.getPrimary().getBounds().getHeight()) {
            height = Screen.getPrimary().getBounds().getHeight();
        }
        return new Double[]{width, height};
    }

    public void change(double width, double height) {
        Double[] max = max(width, height);
        windowWidth = max[0];
        windowHeight = max[1];
        window.setWidth(windowWidth);
        window.setHeight(windowHeight);
    }

    public void change(String width, String height) {
        double widthDouble = Double.parseDouble(width);
        double heightDouble = Double.parseDouble(height);
        change(widthDouble, heightDouble);
    }
}
