package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    static boolean fullscreenBoolean = false;


    @Override
    public void start(Stage window) {
        StackPane pane = new StackPane();
        window.setTitle("This is testing window...");
        window.setScene(new Scene(pane, 800, 600));
        window.setResizable(false);
        double windowHeight = window.getHeight();
        double windowWidth = window.getWidth();


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
        content.getChildren().addAll();
        content.setMinSize(50, 70);
        content.setMaxSize(150, 210);
        content.setPrefSize(50, 70);

        /// ülemine riba = menüü
        HBox menu = new HBox(25);
        menu.getChildren().addAll(fullscreenButton, exitButton);
        menu.setPrefWidth(windowWidth);
        menu.setMaxHeight(100);
        menu.setMaxHeight(150);
//        menu.setSpacing(25);  // nuppude vahekaugus (sama nagu HBox(25))
        menu.setPadding(new Insets(20, 20, 20, 20));
        //Insets(top=y, right=width-?, bottom=height-?, left=x)

        /// tegevused "fullscreen" nupu vajutamisel
        fullscreenButton.setOnAction(actionEvent -> {
            fullscreenBoolean = !fullscreenBoolean;
            System.out.println(window.getWidth());
            System.out.println(window.getHeight());
            window.setFullScreen(fullscreenBoolean);
            if (fullscreenBoolean) {
                fullscreenButton.setPrefHeight(menu.getHeight());
                fullscreenButton.setPrefWidth(menu.getWidth() / 10);
//                System.out.println(menu.getWidth());
//                System.out.println(menu.getHeight());
            } else {
                fullscreenButton.setPrefHeight(menu.getHeight() / 5);
            }
        });

//        pane.setAlignment(Pos.TOP_CENTER);
        pane.setMinSize(240, 240);
        pane.getChildren().addAll(menu, content);

        /// setting backgrounds
        menu.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightgreen"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setBackground(new Background(new BackgroundFill(Paint.valueOf("red"), CornerRadii.EMPTY, new Insets(20, 20, 20, 20))));
        content.setBackground(new Background(new BackgroundFill(Paint.valueOf("black"), new CornerRadii(5d), Insets.EMPTY)));
        exitButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("Yellow"), new CornerRadii(10d, 10d, 25d, 25d, false), Insets.EMPTY)));
        fullscreenButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightblue"), CornerRadii.EMPTY, Insets.EMPTY)));


        window.show();
        System.out.println(window.getProperties());

    }


    public static void main(String[] args) {
        launch(args);
    }
}
