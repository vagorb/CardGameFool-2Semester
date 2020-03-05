package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {
    Button button = new Button();
    boolean bool = false;

    @Override
    public void start(Stage primaryStage) {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        Scene scene = new Scene(root, 640, 400);

        primaryStage.setTitle("This is testing window...");
        primaryStage.setScene(new Scene(button, 640, 400));

        button.setMinSize(60, 40);
        button.setMaxSize(120, 80);
        button.setPrefSize(90, 60);
        button.setText("Fullscreen");
        button.setOnAction(actionEvent -> {
            bool = !bool;
            primaryStage.setFullScreen(bool);
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
