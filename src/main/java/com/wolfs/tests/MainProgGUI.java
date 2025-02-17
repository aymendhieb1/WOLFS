package com.wolfs.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainProgGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Vol.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Vol App");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
