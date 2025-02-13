package com.wolfs.controllers.user;

import com.wolfs.models.Client;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import com.wolfs.models.User;
import com.wolfs.services.ClientServices;

import javafx.event.ActionEvent;
import java.io.IOException;

public class CrudUser {
    @FXML
    private ImageView eye_icon;
    @FXML
    private PasswordField password_field;
    @FXML
    private TextField email_field;
    @FXML
    private TextField text_field_mdp;
    @FXML
    private Text arrow_drop;
    @FXML
    private Text arrow_drop_2;
    @FXML
    private Button submit_button;
    @FXML
    private AnchorPane Se_connecter_page;
    @FXML
    private AnchorPane Sinscrire_page;
    @FXML
    private TextField lastName_field;
    @FXML
    private TextField firstName_field;
    @FXML
    private TextField email_field_signup;
    @FXML
    private TextField number_field;
    @FXML
    private PasswordField password_field_signup;
    @FXML
    private PasswordField password_field_signup_match;


    //Class Variables
    private boolean isPasswordVisible = false;


    //Animation part
    public void initialize() {
        startAnimation();
    }

    private void startAnimation() {

        TranslateTransition transition_2 = new TranslateTransition(Duration.millis(600), arrow_drop_2);
        transition_2.setByY(10);
        transition_2.setAutoReverse(true);
        transition_2.setCycleCount(TranslateTransition.INDEFINITE);
        transition_2.play();

        TranslateTransition transition = new TranslateTransition(Duration.millis(600), arrow_drop);
        transition.setByY(10);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();

    }

    @FXML
    private void switchToSignup() {
        animateTransition(Se_connecter_page, Sinscrire_page, 0);
    }

    @FXML
    private void switchToSignIn() {
        animateTransition(Sinscrire_page, Se_connecter_page, 0);
    }

    private void animateTransition(AnchorPane current, AnchorPane next, double distance) {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), current);
        slideOut.setByX(distance);
        slideOut.setOnFinished(e -> current.setVisible(false));

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), next);
        next.setVisible(true);
        slideIn.setByX(-distance);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), current);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), next);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        slideOut.play();
        fadeOut.play();
        slideIn.play();
        fadeIn.play();
    }

    @FXML
    void AddUser(ActionEvent event) throws IOException {
        ClientServices C1 = new ClientServices();
        C1.ajouterUser(new Client(lastName_field.getText(), firstName_field.getText(), email_field_signup.getText(), password_field_signup.getText(),Integer.parseInt(number_field.getText()),0,0, ""));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Votre compte a été créer");
        alert.show();
    }

}

