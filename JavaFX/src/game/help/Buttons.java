package game.help;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class Buttons {
    private final int maxWidth = 160;
    private final int maxHeight = 90;
    private final int minWidth = 80;
    private final int minHeight = 45;

    public static void oneSizeOnly(Button button, double width, double height) {
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
    }

    public Button throwCards() {
        Button pickUpButton = new Button();
        pickUpButton.setAlignment(Pos.CENTER);
        pickUpButton.setId("throwCards");
        pickUpButton.getStylesheets().add(getClass().getResource("/css/buttons.css").toExternalForm());
        return pickUpButton;
    }

    public Button pickCardsUp() {
        Button pickUpButton = new Button();
        pickUpButton.setAlignment(Pos.CENTER);
        pickUpButton.setId("pickUpCards");
        pickUpButton.getStylesheets().add(getClass().getResource("/css/buttons.css").toExternalForm());
        return pickUpButton;
    }

    public Button exit() {
        Button exitButton = new Button();
        exitButton.setMinSize(minWidth, minHeight);
        exitButton.setMaxSize(maxWidth, maxHeight);
        exitButton.setAlignment(Pos.CENTER);
        exitButton.setId("exit");
        exitButton.getStylesheets().add(getClass().getResource("/css/buttons.css").toExternalForm());
        return exitButton;
    }

    public Button fullscreen() {
        Button fullscreenButton = new Button("Fullscreen");
        fullscreenButton.setMinSize(minWidth, minHeight);
        fullscreenButton.setMaxSize(maxWidth, maxHeight);
        fullscreenButton.setAlignment(Pos.CENTER_RIGHT);
        fullscreenButton.setId("fullscreenEnter");
        fullscreenButton.getStylesheets().add(getClass().getResource("/css/buttons.css").toExternalForm());
        return fullscreenButton;
    }

    public Button settings() {
        Button settingsButton = new Button();
        settingsButton.setMinSize(minWidth, minHeight);
        settingsButton.setMaxSize(maxWidth, maxHeight);
        settingsButton.setAlignment(Pos.CENTER);
        settingsButton.setId("settings");
        settingsButton.getStylesheets().add(getClass().getResource("/css/buttons.css").toExternalForm());
        return settingsButton;
    }

    public Button back() {
        Button backButton = new Button();
        backButton.setMinSize(minWidth, minHeight);
        backButton.setMaxSize(maxWidth, maxHeight);
        backButton.setAlignment(Pos.CENTER);
        backButton.setId("back");
        backButton.getStylesheets().add(getClass().getResource("/css/buttons.css").toExternalForm());
        return backButton;
    }

    public Button play() {
        Button playButton = new Button("PLAY");
        playButton.setMinSize(minWidth, minHeight);
        playButton.setMaxSize(maxWidth, maxHeight);
        playButton.setAlignment(Pos.CENTER_RIGHT);
        playButton.setId("play");
        playButton.getStylesheets().add(getClass().getResource("/css/buttons.css").toExternalForm());
        return playButton;
    }


}
