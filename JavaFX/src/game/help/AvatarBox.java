package game.help;

import com.card.game.fool.players.gamerInterface;
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
    private final HBox horizontalPlace = new HBox();
    private final VBox leftVerticalPlace = new VBox();
    private final VBox rightVerticalPlace = new VBox();
    private final List<VBox> avatarList = new ArrayList<>();
    public Label cardsRemaining;


    public AvatarBox(int playerCount, double screenWidth) {
        this.playerCount = playerCount;
        elementSize = screenWidth / 8;
        horizontalPlace.setSpacing(elementSize * 0.7);
        horizontalPlace.setPadding(new Insets(50));
        horizontalPlace.setAlignment(Pos.TOP_CENTER);
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

    public void makeAvatar(gamerInterface gamer) {
        if (avatarList.size() < playerCount) {
            Label name = new Label(gamer.getName());
            HBox nameBox = new HBox(name);
            nameBox.setAlignment(Pos.CENTER);
            // TODO This line of code below gives us the amount of cards the opponent has ( Should have )
//            Label cardsRemaining = new Label(String.valueOf(gamer.getHand().size()));
            cardsRemaining = new Label(String.valueOf(6));
            HBox cardBack = new HBox(cardsRemaining);
//            ImageView avatarImage = new ImageView(image);
            ImageView avatarImage = new ImageView(String.valueOf(getClass().getResource("/images/avatar.png")));
            HBox avatarSubBox = new HBox(avatarImage, cardBack);
            VBox avatarBox = new VBox(avatarSubBox, nameBox);

            oneSizeForElement(cardBack, elementSize * 0.4, elementSize * 0.6);
            oneSizeForElement(avatarBox, elementSize, elementSize * 0.9);

            double cardFontSize = cardBack.maxWidthProperty().get() * 0.9;
            cardBack.setStyle("-fx-background-image: url('/images/cards/back_of_card.png');-fx-background-size: cover;");
            cardsRemaining.setStyle("-fx-font:" + cardFontSize + "px Arial ; -fx-text-fill: red;");
            avatarImage.setFitWidth(elementSize * 0.6);
            avatarImage.setFitHeight(elementSize * 0.6);
            name.setWrapText(true);
            name.setStyle("-fx-font: 15px Arial; -fx-text-fill: lawngreen;");
            nameBox.setStyle("-fx-border-color: black;");

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
