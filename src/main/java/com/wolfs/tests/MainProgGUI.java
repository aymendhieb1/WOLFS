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
            // Load the first FXML (Vol.fxml)
            Parent root1 = FXMLLoader.load(getClass().getResource("/Vol.fxml"));
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root1));
            stage1.setTitle("Gestion des Vols");
            stage1.show();

            // Load the second FXML (CheckoutVol.fxml)
            Parent root2 = FXMLLoader.load(getClass().getResource("/CheckoutVol.fxml"));
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root2));
            stage2.setTitle("Checkout Vol");
            stage2.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
