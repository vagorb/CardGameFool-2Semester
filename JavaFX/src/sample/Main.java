package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application {
    static boolean fullscreenBoolean = false;


    @Override
    public void start(Stage primaryStage) {
        double windowHeight = primaryStage.getHeight();
        double windowWidth = primaryStage.getWidth();
        StackPane pane = new StackPane();
        primaryStage.setTitle("This is testing window...");
        primaryStage.setScene(new Scene(pane, 800, 600));

        ///// "exit" nupp
        Button exitButton = new Button("Exit");
        exitButton.setPrefSize(50, 25);

        exitButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });

        /// "fullscreen" nupp
        Button fullscreenButton = new Button("Fullscreen");

        // tekstipaigutus nuppudel
        fullscreenButton.setAlignment(Pos.BOTTOM_CENTER);

        VBox content = new VBox();
//        content.getChildren().addAll();
//        content.setMinSize(150, 75);
//        content.setMaxSize(200, windowWidth);
//        content.setAlignment(Pos.CENTER_LEFT);
//        content.relocate(200, 0);

        /// ülemine riba = menüü
        HBox menu = new HBox(25);
        menu.getChildren().addAll(fullscreenButton, exitButton);
        menu.setPrefWidth(windowWidth);
        menu.setMaxHeight(100);
        menu.setMaxHeight(150);
//        menu.setSpacing(25);  // nuppude vahekaugus (sama nagu HBox(25))
        menu.setPadding(new Insets(0, 20, 25, 60));
        //Insets(top=y, right=width-?, bottom=height-?, left=x)

        pane.setAlignment(Pos.TOP_CENTER);
        pane.setMinSize(240, 240);
        pane.getChildren().addAll(menu);

        /// setting backgrounds
        menu.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightgreen"), CornerRadii.EMPTY, Insets.EMPTY)));
        content.setBackground(new Background(new BackgroundFill(Paint.valueOf("green"), CornerRadii.EMPTY, Insets.EMPTY)));
        exitButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("Yellow"), CornerRadii.EMPTY, Insets.EMPTY)));
        fullscreenButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightblue"), CornerRadii.EMPTY, Insets.EMPTY)));

        /// tegevused "fullscreen" nupu vajutamisel
        fullscreenButton.setOnAction(actionEvent -> {
            fullscreenBoolean = !fullscreenBoolean;
            primaryStage.setFullScreen(fullscreenBoolean);
            if (fullscreenBoolean) {
                fullscreenButton.setPrefHeight(menu.getHeight());
                fullscreenButton.setPrefWidth(menu.getWidth() / 10);
                System.out.println(menu.getWidth());
                System.out.println(menu.getHeight());
            } else {
                fullscreenButton.setPrefHeight(menu.getHeight() / 5);
            }
//            System.out.println();
//            if (buttonBar.getAlignment() == Pos.TOP_CENTER) {
//                buttonBar.setAlignment(Pos.CENTER);
//            } else if (buttonBar.getAlignment() == Pos.CENTER) {
//                buttonBar.setAlignment(Pos.TOP_CENTER);
//            }
        });


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
