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
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

public class CrudUser {
    @FXML
    private ImageView eye_icon;
    @FXML
    private TextField email_field_signin;
    @FXML
    private TextField password_field_signin;
    @FXML
    private Text arrow_drop;
    @FXML
    private TextField text_field_mdp;
    @FXML
    private Text arrow_drop_2;
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
    private void AddUser(ActionEvent event) throws IOException {
        if (lastName_field.getText().isEmpty() || firstName_field.getText().isEmpty() ||
                email_field_signup.getText().isEmpty() || password_field_signup.getText().isEmpty() ||
                number_field.getText().isEmpty()) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        // Vérification du format de l'email
        String email = email_field_signup.getText();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert("Erreur", "Veuillez entrer un email valide", Alert.AlertType.ERROR);
            return;
        }

        // Vérification du numéro de téléphone
        String number = number_field.getText();
        try {
            Integer.parseInt(number);  // Essaye de convertir en entier
            if (number.length() != 8) {
                showAlert("Erreur", "Le numéro de téléphone doit être composé de 8 chiffres", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le numéro de téléphone doit être un nombre valide", Alert.AlertType.ERROR);
            return;
        }
        String password = password_field_signup.getText();
        String confirmPassword = password_field_signup_match.getText();

        if (!password.equals(confirmPassword)) {
            showAlert("Erreur", "Les mots de passe ne correspondent pas", Alert.AlertType.ERROR);
            return;
        }
        if (password.length() < 8) {
            showAlert("Erreur", "Le mot de passe doit contenir au moins 8 caractères", Alert.AlertType.ERROR);
            return;
        }

        // Vérification de la présence d'une lettre majuscule, d'un chiffre et d'un caractère spécial
        if (!password.matches(".*[A-Z].*")) {
            showAlert("Erreur", "Le mot de passe doit contenir au moins une lettre majuscule", Alert.AlertType.ERROR);
            return;
        }
        if (!password.matches(".*\\d.*")) {
            showAlert("Erreur", "Le mot de passe doit contenir au moins un chiffre", Alert.AlertType.ERROR);
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Si toutes les vérifications passent
        ClientServices C1 = new ClientServices();
        C1.ajouterUser(new Client(lastName_field.getText(), firstName_field.getText(), email,
                hashedPassword, Integer.parseInt(number), 0, 0, ""));

        // Affichage de l'alerte de confirmation
        showAlert("Confirmation", "Votre compte a été créé", Alert.AlertType.INFORMATION);
        lastName_field.setText("");
        firstName_field.setText("");
        email_field_signup.setText("");
        number_field.setText("");
        password_field_signup.setText("");
        switchToSignIn();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    private void UserRole(ActionEvent event) {
        ClientServices C1 = new ClientServices();
        Client client = C1.verifierUser(email_field_signin.getText(), password_field_signin.getText());

        if (client == null) {
            showAlert("Erreur", "Email ou mot de passe incorrect.", Alert.AlertType.ERROR);
        } else if (client.getStatus() != 0) {
            showAlert("Compte bloqué", "Votre compte est banni.", Alert.AlertType.ERROR);
        } else {
            // Vérifier le rôle
            switch (client.getRole()) {
                case 0:
                    showAlert("Bienvenue",  "Bonjour" + " " + client.getPrenom().toUpperCase() + " " + client.getName().toUpperCase(), Alert.AlertType.INFORMATION);
                    break;
                case 1:
                    showAlert("Bienvenue",  "Bonjour" + " " + client.getPrenom().toUpperCase() + " " + client.getName().toUpperCase(), Alert.AlertType.INFORMATION);
                    break;
                case 2:
                    showAlert("Bienvenue",  "Bonjour" + " " + client.getPrenom().toUpperCase() + " " + client.getName().toUpperCase(), Alert.AlertType.INFORMATION);
                    break;
                default:
                    showAlert("Erreur", "Rôle inconnu.", Alert.AlertType.ERROR);
            }
        }
    }


    @FXML
    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;

        if (isPasswordVisible) {
            text_field_mdp.setText(password_field_signin.getText());
            text_field_mdp.setVisible(true);
            password_field_signin.setVisible(false);
        } else {
            password_field_signin.setText(text_field_mdp.getText());
            password_field_signin.setVisible(true);
            text_field_mdp.setVisible(false);
        }
    }


}

