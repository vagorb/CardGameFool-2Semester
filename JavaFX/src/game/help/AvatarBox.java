package game.help;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


public class AvatarBox {
    private final int playerCount;
    private final double elementSize;
    private final double gapSize;
    private HBox horizontalPlace = new HBox();
    private VBox leftVerticalPlace = new VBox();
    private VBox rightVerticalPlace = new VBox();
    private List<VBox> avatarList = new ArrayList<>();

    public AvatarBox(int playerCount, double screenWidth) {
        this.playerCount = playerCount;
        elementSize = screenWidth / 8;
        gapSize = elementSize * 0.7;
        horizontalPlace.setSpacing(gapSize);
//        leftVerticalPlace.setSpacing(elementSize);
//        rightVerticalPlace.setSpacing(elementSize);
        horizontalPlace.setPadding(new Insets(50));
        horizontalPlace.setAlignment(Pos.TOP_CENTER);
//        leftVerticalPlace.setStyle("-fx-background-color: rgb(150,255,0)");
//        rightVerticalPlace.setStyle("-fx-background-color: rgb(255,150,0)");
//        horizontalPlace.setStyle("-fx-background-color: rgb(255,0,0)");
    }

    private HBox makeEmptyElement() {
        HBox element = new HBox();
        oneSizeForElement(element, elementSize, elementSize);
        return element;
    }

    private void oneSizeForElement(Pane object, double width, double height) {
        object.setMinSize(width, height);
        object.setMaxSize(width, height);
    }

    //    public void makeAvatar(Image image, String playerName) {
    public void makeAvatar(String playerName) {
        if (avatarList.size() < playerCount) {
            Label name = new Label(playerName);
            HBox nameBox = new HBox(name);
            Label cardsRemaining = new Label("6");
            HBox cardBack = new HBox(cardsRemaining);
            ImageView avatarImage = new ImageView(String.valueOf(getClass().getResource("/images/avatar.png")));
            HBox avatarSubBox = new HBox(avatarImage, cardBack);
            VBox avatarBox = new VBox(avatarSubBox, nameBox);

            oneSizeForElement(cardBack, elementSize * 0.4, elementSize * 0.6);
            oneSizeForElement(avatarBox, elementSize, elementSize * 0.9);

//            ImageView avatarImage = new ImageView(image);
            double cardFontSize = cardBack.maxWidthProperty().get();
            cardBack.setStyle("-fx-background-image: url('/images/back.png');-fx-background-size: cover; -fx-opacity: 0.5");
            cardsRemaining.setStyle("-fx-font:" + cardFontSize + "px Arial ; -fx-text-fill: rgb(255,0,0)");
            avatarImage.setFitWidth(elementSize * 0.6);
            avatarImage.setFitHeight(elementSize * 0.6);
            name.setWrapText(true);
            name.setStyle("-fx-font: 15px Arial;");
            nameBox.setStyle("-fx-border-color: rgb(0,0,0)");

            cardBack.setAlignment(Pos.CENTER);
            avatarBox.setAlignment(Pos.TOP_CENTER);
            avatarList.add(avatarBox);
        }
    }

    public HBox showAvatars() {
        horizontalPlace.getChildren().add(leftVerticalPlace);
        if (playerCount == 1 || playerCount == 3) {
            avatarList.forEach(avatar -> horizontalPlace.getChildren().add(avatar));
        } else if (playerCount == 2) {
            horizontalPlace.getChildren().addAll(avatarList.get(0), makeEmptyElement(), avatarList.get(1));
        } else if (playerCount == 4) {
            leftVerticalPlace.getChildren().add(avatarList.get(0));
            horizontalPlace.getChildren().addAll(avatarList.get(1), makeEmptyElement(), avatarList.get(2));
            rightVerticalPlace.getChildren().add(avatarList.get(3));
        } else if (playerCount == 5) {
            leftVerticalPlace.getChildren().add(avatarList.get(0));
            horizontalPlace.getChildren().addAll(avatarList.get(1), avatarList.get(2), avatarList.get(3));
            rightVerticalPlace.getChildren().add(avatarList.get(4));
        }
        horizontalPlace.getChildren().add(rightVerticalPlace);
        leftVerticalPlace.setAlignment(Pos.CENTER);
        rightVerticalPlace.setAlignment(Pos.CENTER);
        return horizontalPlace;
    }
}
