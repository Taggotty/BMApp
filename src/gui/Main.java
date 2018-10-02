package gui;

import core.SeriesContainer;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import util.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Util.loadAll();
        Parent root = FXMLLoader.load(getClass().getResource("BookController.fxml"));
        primaryStage.setTitle("Book Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() {
        Util.saveAll();
    }
}
