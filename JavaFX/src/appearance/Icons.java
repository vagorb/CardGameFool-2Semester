package appearance;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icons {
    private String color;
    private int width = 50;
    private int height = 50;

    enum Color {
        WHITE("white"), BLACK("black");

        private final String colour;

        Color(String color) {
            this.colour = color;
        }

        @Override
        public String toString() {
            return colour;
        }
    }

    public Icons(String color, int width, int height) {
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public Icons(String color) {
        this.color = color;
    }

    private ImageView makeImage(String imageName) {
        String iconColor = "black";
        try {
            iconColor = Color.valueOf(color.toUpperCase()).toString();
        } catch (IllegalArgumentException ignore) {
        }
        return new ImageView(new Image(getClass().getResource("/icons/" + iconColor + "/" + imageName).toString(),
                width, height, false, false));
    }

    public ImageView fullscreenExit() {
        String img = "fullscreen_exit.png";
        return makeImage(img);
    }

    public ImageView fullscreenEnter() {
        String img = "fullscreen.png";
        return makeImage(img);
    }

    public ImageView play() {
        String img = "play_circle.png";
        return makeImage(img);
    }

    public ImageView exit() {
        String img = "close.png";
        return makeImage(img);
    }

    public ImageView back() {
        String img = "back.png";
        return makeImage(img);
    }

    public ImageView settings() {
        String img = "settings.png";
        return makeImage(img);
    }

    public ImageView pickCardsUp() {
        String img = "pickUp.png";
        return makeImage(img);
    }

}
