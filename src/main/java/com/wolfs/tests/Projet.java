package com.wolfs.tests;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;

public class Projet extends Application{


    public static void main(String[] args) {launch(args);}
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setHeight(1000);
        primaryStage.setWidth(1900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Projet");
        primaryStage.show();
    }
}
