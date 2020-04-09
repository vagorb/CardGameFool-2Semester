package game.help;

import appearance.Icons;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;


public class Buttons {
    public Button exit() {
        Button exitButton = new Button("", new Icons("black").exit());
        exitButton.setAlignment(Pos.CENTER);
        exitButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("orange"),
                new CornerRadii(5d, 5d, 5d, 5d, false), Insets.EMPTY)));
        return exitButton;
    }

    public Button fullscreen() {
        Button fullscreenButton = new Button("Fullscreen", new Icons("black").fullscreenEnter());
        fullscreenButton.setAlignment(Pos.CENTER);
        fullscreenButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightblue"),
                CornerRadii.EMPTY, Insets.EMPTY)));
        return fullscreenButton;
    }

    public Button settings() {
        Button settingsButton = new Button("", new Icons("black").settings());
        settingsButton.setAlignment(Pos.CENTER);
        return settingsButton;
    }

    public Button back() {
        Button backButton = new Button("", new Icons("black").back());
        backButton.setAlignment(Pos.CENTER);
        return backButton;
    }

    public Button play() {
        Button playButton = new Button("PLAY", new Icons("black").play());
        playButton.setMinSize(150, 100);
        playButton.setAlignment(Pos.CENTER);
        playButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("green"),
                new CornerRadii(50, 0, 50, 0, false), Insets.EMPTY)));
        return playButton;
    }
}
