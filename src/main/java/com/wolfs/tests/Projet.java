package com.wolfs.tests;

import javafx.application.HostServices;
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
        primaryStage.setHeight(1080);
        primaryStage.setWidth(1920);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Projet");
        primaryStage.show();
    }
}
