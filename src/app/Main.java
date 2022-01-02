package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        primaryStage.setTitle("File Manager");
        primaryStage.setScene(new Scene(root, 800, 500));
        Image pcIcon = new Image(getClass().getResourceAsStream("/img/pcPng.png"));
        primaryStage.getIcons().add(pcIcon);
        primaryStage.show();
        primaryStage.setOnHidden(e-> Platform.exit());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
