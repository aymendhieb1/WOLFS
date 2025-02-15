package com.wolfs.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Projet extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file from the resources folder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();

        // Create a scene with fixed width and height (800x600)
        Scene scene = new Scene(root, 1900, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Projet");

        // Disable window resizing
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }
}
