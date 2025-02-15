package com.wolfs.tests;

import com.wolfs.controllers.user.CrudUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Projet extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file from the resources folder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();

        //CrudUser controller = loader.getController();
      //controller.RefreshTableView();
        // Create a scene with fixed width and height (1900x1000)
        Scene scene = new Scene(root, 1900, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TripToGo");
        primaryStage.show();
    }
}
