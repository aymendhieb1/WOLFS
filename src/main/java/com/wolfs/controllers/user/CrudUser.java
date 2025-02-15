package com.wolfs.controllers.user;

import com.wolfs.models.Client;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import com.wolfs.services.ClientServices;
import javafx.event.ActionEvent;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.util.Optional;


public class CrudUser {

    @FXML
    private AnchorPane Modifier_page;
    @FXML
    private TextField email_field_update;
    @FXML
    private TextField firstName_field_update;
    @FXML
    private TextField lastName_field_update;
    @FXML
    private TextField number_field_update;
    @FXML
    private PasswordField password_field_update;
    @FXML
    private PasswordField password_field_match_update;
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
    private AnchorPane background_front;
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
    @FXML
    private TableView<Client> userTableView;
    @FXML
    private TableColumn<Client, String> nomColumn;
    @FXML
    private TableColumn<Client, String> prenomColumn;
    @FXML
    private TableColumn<Client, String> emailColumn;
    @FXML
    private TableColumn<Client, String> telColumn;

    @FXML
    private TableColumn<Client, Void> actionColumn;

    private ObservableList<Client> userData = FXCollections.observableArrayList();


    private boolean isPasswordVisible = false;

    public void initialize() {
        nomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName().toUpperCase())
        );

        prenomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPrenom().toUpperCase())
        );

        emailColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail().toUpperCase())
        );

        telColumn.setCellValueFactory(new PropertyValueFactory<>("num_tel"));

        actionColumn.setCellFactory(column -> {
            return new TableCell<Client, Void>() {
                private final Button btn = new Button("Modifier");


                {
                    HBox hbox = new HBox(btn);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                    btn.setStyle(
                            "-fx-background-color: #E78D1E; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-border-radius: 5px; " +
                                    "-fx-cursor: hand;"
                    );


                    btn.setOnAction(event -> {
                        Client client = getTableView().getItems().get(getIndex());
                        animateTransition(background_front, Modifier_page, 0);
                        lastName_field_update.setText(lastName_field.getText());



                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                        HBox hbox = new HBox(btn);
                        hbox.setAlignment(Pos.CENTER);
                        setGraphic(hbox);
                    }
                }
            };
        });
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
        resetFieldStyles();

        boolean hasError = false;

        if (lastName_field.getText().isEmpty()) {
            lastName_field.getStyleClass().add("error-field");
            hasError = true;
        }

        if (firstName_field.getText().isEmpty()) {
            firstName_field.getStyleClass().add("error-field");
            hasError = true;
        }
        if (email_field_signup.getText().isEmpty()) {
            email_field_signup.getStyleClass().add("error-field");
            hasError = true;
        }
        if (password_field_signup.getText().isEmpty()) {
            password_field_signup.getStyleClass().add("error-field");
            hasError = true;
        }
        if (number_field.getText().isEmpty()) {
            number_field.getStyleClass().add("error-field");
            hasError = true;
        }

        if (hasError) {
            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        String email = email_field_signup.getText();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            email_field_signup.getStyleClass().add("error-field");
            showAlert("Erreur", "Veuillez entrer un email valide", Alert.AlertType.ERROR);
            return;
        }

        String number = number_field.getText();
        try {
            Integer.parseInt(number);
            if (number.length() != 8) {
                number_field.getStyleClass().add("error-field");
                showAlert("Erreur", "Le numéro de téléphone doit être composé de 8 chiffres", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            number_field.getStyleClass().add("error-field");
            showAlert("Erreur", "Le numéro de téléphone doit être un nombre valide", Alert.AlertType.ERROR);
            return;
        }

        String password = password_field_signup.getText();
        String confirmPassword = password_field_signup_match.getText();

        if (!password.equals(confirmPassword)) {
            password_field_signup.getStyleClass().add("error-field");
            password_field_signup_match.getStyleClass().add("error-field");
            showAlert("Erreur", "Les mots de passe ne correspondent pas", Alert.AlertType.ERROR);
            return;
        }
        if (password.length() < 8) {
            password_field_signup.getStyleClass().add("error-field");
            showAlert("Erreur", "Le mot de passe doit contenir au moins 8 caractères", Alert.AlertType.ERROR);
            return;
        }
        if (!password.matches(".*[A-Z].*")) {
            password_field_signup.getStyleClass().add("error-field");
            showAlert("Erreur", "Le mot de passe doit contenir au moins une lettre majuscule", Alert.AlertType.ERROR);
            return;
        }
        if (!password.matches(".*\\d.*")) {
            password_field_signup.getStyleClass().add("error-field");
            showAlert("Erreur", "Le mot de passe doit contenir au moins un chiffre", Alert.AlertType.ERROR);
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        ClientServices C1 = new ClientServices();
        C1.ajouterUser(new Client(lastName_field.getText(), firstName_field.getText(), email,
                hashedPassword, Integer.parseInt(number), 0, 0, ""));

        showAlert("Confirmation", "Votre compte a été créé", Alert.AlertType.INFORMATION);
        clearFields();
        switchToSignIn();
    }

    private void resetFieldStyles() {
        lastName_field.getStyleClass().remove("error-field");
        firstName_field.getStyleClass().remove("error-field");
        email_field_signup.getStyleClass().remove("error-field");
        password_field_signup.getStyleClass().remove("error-field");
        password_field_signup_match.getStyleClass().remove("error-field");
        number_field.getStyleClass().remove("error-field");
    }

    private void clearFields() {
        lastName_field.setText("");
        firstName_field.setText("");
        email_field_signup.setText("");
        number_field.setText("");
        password_field_signup.setText("");
        password_field_signup_match.setText("");
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
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
            switch (client.getRole()) {
                case 0:
                    showAlert("Bienvenue", "Bonjour" + " " + client.getPrenom().toUpperCase() + " " + client.getName().toUpperCase(), Alert.AlertType.INFORMATION);
                    break;
                case 1:
                    showAlert("Bienvenue", "Bonjour" + " " + client.getPrenom().toUpperCase() + " " + client.getName().toUpperCase(), Alert.AlertType.INFORMATION);
                    break;
                case 2:
                    showAlert("Bienvenue", "Bonjour" + " " + client.getPrenom().toUpperCase() + " " + client.getName().toUpperCase(), Alert.AlertType.INFORMATION);
                    UserAccount();
                    animateTransition(Se_connecter_page, background_front, 0);
                    break;
                default:
                    showAlert("Erreur", "Rôle inconnu.", Alert.AlertType.ERROR);
            }
        }
    }
    private void UserAccount() {
        try {
            ClientServices C1 = new ClientServices();
            System.out.println("Email entré : " + email_field_signin.getText());
            Client client = C1.verifierUser(email_field_signin.getText(), password_field_signin.getText());

            if (client != null) {
                ObservableList<Client> singleClient = FXCollections.observableArrayList(client);



                userData.clear();
                userData.addAll(singleClient);
                userTableView.setItems(userData);

            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la vérification de l'utilisateur.", Alert.AlertType.ERROR);
            e.printStackTrace();
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

    @FXML
    private void LogOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de déconnexion");
        alert.setHeaderText("Voulez-vous vraiment vous déconnecter ?");
        alert.setContentText("Cliquez sur OK pour confirmer.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            text_field_mdp.clear();
            password_field_signin.clear();
            email_field_signin.clear();
            animateTransition(background_front, Se_connecter_page, 0);
            background_front.setVisible(false);
            Se_connecter_page.setVisible(true);
        }

    }
}