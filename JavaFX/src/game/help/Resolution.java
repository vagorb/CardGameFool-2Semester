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
    private CheckBox customResolution;
    private ComboBox<String> resolutionChoices;

    public Resolution(Stage window) {
        this.window = window;
        resizeWindow();
        dragWindow();
        makeCustomResoCheckbox();
        makeResolutionChoices();
    }

    private void resizeWindow() {
        ChangeListener<Number> windowSizeListener = (observable, oldValue, newValue) -> {
            if (!customResolution.isSelected() && !window.isFullScreen()) {
                window.setX((Screen.getPrimary().getBounds().getWidth() - windowWidth) / 2);
                window.setY((Screen.getPrimary().getBounds().getHeight() - windowHeight) / 2);
            }
        };

        window.heightProperty().addListener(windowSizeListener);
        window.minWidthProperty().bind(window.heightProperty().multiply(16d / 9));
        window.maxWidthProperty().bind(window.heightProperty().multiply(16d / 9));

        window.getScene().setOnScroll(scrollEvent -> {
            if (window.isResizable()) {
                double newHeight = window.getHeight() + scrollEvent.getDeltaY();
                if (newHeight >= 720 && newHeight <= Screen.getPrimary().getBounds().getHeight()) {
                    window.setHeight(newHeight);
                }
            }
        });
    }

    public boolean isCustomResolution() {
        return customResolution.isSelected();
    }
    public void dragWindow() {
        window.getScene().setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        window.getScene().setOnMouseDragged(event -> {
            if (!window.isFullScreen()) {
                window.setX(event.getScreenX() - xOffset);
                window.setY(event.getScreenY() - yOffset);
            }
        });
    }

    private void makeCustomResoCheckbox() {
        CheckBox customResolution = new CheckBox();
        customResolution.setOnAction(actionEvent -> {
            window.setResizable(customResolution.isSelected());
            resolutionChoices.setDisable(customResolution.isSelected());
            resolutionChoices.setValue("");
        });
        this.customResolution = customResolution;
    }

    private void makeResolutionChoices() {
        ComboBox<String> choices = new ComboBox<>();
        for (String reso : List.of("1280x720", "1366x768", "1600x900", "1920x1080", "2560x1440", "3200x1800",
                "3840x2160", "5120x2880", "7680x4320")) {
            if (Integer.parseInt(reso.split("x")[1]) <= Screen.getPrimary().getBounds().getHeight()) {
                choices.getItems().add(reso);
            } else {
                break;
            }
        }
        choices.valueProperty().addListener((observableValue, s, t1) -> {
            if (!t1.equals("") && !window.isFullScreen()) {
                String[] values = t1.split("x");
                change(values[0], values[1]);
                window.setX((Screen.getPrimary().getBounds().getWidth() - windowWidth) / 2);
                window.setY((Screen.getPrimary().getBounds().getHeight() - windowHeight) / 2);
            }
        });
        this.resolutionChoices = choices;
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
        return new Double[]{width, height};
    }

    public void change(double width, double height) {
        Double[] max = max(width, height);
        windowWidth = max[0];
        windowHeight = max[1];
        window.setWidth(windowWidth);
        window.setHeight(windowHeight);
        if (!window.isFullScreen()) {
            window.setX((Screen.getPrimary().getBounds().getWidth() - windowWidth) / 2);
            window.setY((Screen.getPrimary().getBounds().getHeight() - windowHeight) / 2);
        }
    }

    public void change(String width, String height) {
        double widthDouble = Double.parseDouble(width);
        double heightDouble = Double.parseDouble(height);
        change(widthDouble, heightDouble);
    }
}
