package com.wolfs.controllers.user;

import com.wolfs.models.Client;
import com.wolfs.models.Hotel;
import com.wolfs.services.HotelService;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.wolfs.services.ClientServices;
import javafx.event.ActionEvent;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
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
    private AnchorPane background_back;
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
    private TextField fixed_email;
    @FXML
    private PasswordField password_field_signup;
    @FXML
    private PasswordField password_field_signup_match;

    @FXML
    private Label labal_prenom;

    @FXML
    private Label labal_tel;

    @FXML
    private Label label_mail;

    @FXML
    private Label label_nom;
    @FXML
    private AnchorPane page_acceuil_gestion_circuit;

    @FXML
    private AnchorPane page_acceuil_gestion_forum;

    @FXML
    private AnchorPane page_acceuil_gestion_hotel;

    @FXML
    private AnchorPane page_acceuil_gestion_location;

    @FXML
    private AnchorPane page_acceuil_gestion_vol;

    @FXML
    private AnchorPane page_activite;

    @FXML
    private AnchorPane page_chambre;

    @FXML
    private AnchorPane page_checkout;

    @FXML
    private AnchorPane page_contrat;

    @FXML
    private AnchorPane page_dashboard;

    @FXML
    private AnchorPane page_forum;

    @FXML
    private AnchorPane page_hotel;

    @FXML
    private AnchorPane page_post;

    @FXML
    private AnchorPane page_session;

    @FXML
    private AnchorPane page_utilisateur;

    @FXML
    private AnchorPane page_vehicule;

    @FXML
    private AnchorPane page_vol;

    @FXML
    private  Button bt_menu_dashboard;
    @FXML
    private  Button bt_menu_user;
    @FXML
    private  Button bt_menu_hotel;
    @FXML
    private  Button bt_menu_Circuit;
    @FXML
    private  Button bt_menu_vol;
    @FXML
    private  Button bt_menu_location;
    @FXML
    private  Button bt_menu_forum;
    @FXML
    private  Button bt_menu_offre;
    @FXML
    private  ImageView  photo_profile_signin;
    @FXML
    private  ImageView  photo_profile_front;
    @FXML
    private  AnchorPane page_ajouter_chambre;
    @FXML
    private  AnchorPane page_ajouter_hotel;
    @FXML
    private  AnchorPane page_ajouter_session;
    @FXML
    private  AnchorPane page_ajouter_activite;
    @FXML
    private  AnchorPane page_ajouter_vol;
    @FXML
    private  AnchorPane page_ajouter_checkout;
    @FXML
    private  AnchorPane page_ajouter_forum;
    @FXML
    private  AnchorPane page_ajouter_post;

    @FXML
    private  AnchorPane page_ajouter_vehicule;
    @FXML
    private  AnchorPane page_ajouter_contrat;

    @FXML
    private Button page_hotel_bt_go_to_hotel;
    @FXML
    private Button     page_hotel_bt_retour;
    @FXML
    private Button     page_ajouter_hotel_return;
    @FXML
    private Button     page_hotel_bt_ajouter;
    @FXML
    private Button     page_acceuil_hotel_go_to_chambre;
    @FXML
    private Button     page_chambre_retour;
    @FXML
    private Button     page_chambre_bt_ajouter;
    @FXML
    private Button     page_ajouter_chambre_retour;
    @FXML
    private Button     page_acceuil_gestion_circuit_go_to_activite;
    @FXML
    private Button     page_acceuil_gestion_circuit_go_to_session;
    @FXML
    private Button     page_activite_retour;
    @FXML
    private Button     page_activite_bt_ajouter;
    @FXML
    private Button     page_ajouter_activiter_retour;
    @FXML
    private Button     page_session_retour;
    @FXML
    private Button     page_session_bt_ajouter;
    @FXML
    private Button     page_ajouter_session_retour;
    @FXML
    private Button     page_acceuil_location_go_to_vehicule;

    @FXML
    private Button     page_acceuil_location_go_to_contrat;
    @FXML
    private Button     page_vehicule_retour;
    @FXML
    private Button     page_vehicule_bt_ajouter;
    @FXML
    private Button     page_ajouter_vehicule_retour;
    @FXML
    private Button     page_contrat_retour;
    @FXML
    private Button     page_contrat_bt_ajouter;
    @FXML
    private Button     page_ajouter_contrat_retour;
    @FXML
    private Button     page_vol_retour;
    @FXML
    private Button     page_vol_bt_ajouter;
    @FXML
    private Button     page_ajouter_vol_retour;
    @FXML
    private Button     page_checkout_retour;
    @FXML
    private Button     page_checkout_bt_ajouter;
    @FXML
    private Button     page_ajouter_checkout_retour;
    @FXML
    private Button     page_acceuil_forum_bt_forum;
    @FXML
    private Button     page_ajouter_post_retour;
    @FXML
    private Button     page_acceuil_forum_go_to_post;
    @FXML
    private Button     page_forum_retour;
    @FXML
    private Button     page_forum_bt_ajouter;
    @FXML
    private Button     page_ajouter_forum_retour;
    @FXML
    private Button     page_post_retour;
    @FXML
    private Button     page_post_bt_ajouter;
    @FXML
    private Button     gestion_acceuil_bt_go_to_vol;
    @FXML
    private Button     gestion_acceuil_bt_go_to_checkout;
    @FXML
    private  Button page_utilisateur_retour;
    @FXML
    private TextField hotel_description;

    @FXML
    private TextField hotel_email;

    @FXML
    private TextField hotel_image;

    @FXML
    private TextField hotel_localisation;

    @FXML
    private TextField hotel_nom;

    @FXML
    private TextField hotel_num_tel;
    @FXML
    private  Button page_hotel_refresh;
    @FXML
    private  Label count_client;
    @FXML
    private  Label count_bloque;
    @FXML
    private TableView<Hotel> TableView_Hotel;
    @FXML
    private TableColumn<Hotel, String> hotel_col_name;
    @FXML
    private TableColumn<Hotel, String> hotel_col_location;
    @FXML
    private TableColumn<Hotel, String> hotel_col_phone;
    @FXML
    private TableColumn<Hotel, String> hotel_col_email;
    @FXML
    private TableColumn<Hotel, String> hotel_col_image;
    @FXML
    private TableColumn<Hotel, String> hotel_col_description;

    private ObservableList<Hotel> HotelData = FXCollections.observableArrayList();

    private ObservableList<Client> userData = FXCollections.observableArrayList();


    private boolean isPasswordVisible = false;

    public void initialize() {
        startAnimation();

        //tableView Hotel
        hotel_col_name.setCellValueFactory(new PropertyValueFactory<>("nom_hotel"));
        hotel_col_location.setCellValueFactory(new PropertyValueFactory<>("localisation_hotel"));
        hotel_col_phone.setCellValueFactory(new PropertyValueFactory<>("num_telephone_hotel"));
        hotel_col_email.setCellValueFactory(new PropertyValueFactory<>("email_hotel"));
        hotel_col_image.setCellValueFactory(new PropertyValueFactory<>("image_hotel"));
        hotel_col_description.setCellValueFactory(new PropertyValueFactory<>("description_hotel"));

        HotelService hotelService = new HotelService();
        List<Hotel> hotelList = hotelService.rechercher(); // Fetch hotel list from DB
        ObservableList<Hotel> observableHotelList = FXCollections.observableArrayList(hotelList);

        TableView_Hotel.setItems(observableHotelList);
        setCircularImage("images//user_icon_001.jpg");
         photo_profile_signin.setOnMouseClicked(this::handleImageClick);


         photo_profile_front.setFitWidth(500);
         photo_profile_front.setFitHeight(500);
         photo_profile_front.setPreserveRatio(false);

        // Create a circular clip
        double radius = Math.min(photo_profile_front.getFitWidth(), photo_profile_front.getFitHeight()) / 2;
        Circle clip = new Circle(radius);
        clip.setCenterX(500/ 2);
        clip.setCenterY(500 / 2);

         photo_profile_front.setClip(clip);
    }
    private void setCircularImage(String imagePath) {
        Image image = new Image(imagePath);
         photo_profile_signin.setImage(image);

        double size = 100; // Set the fixed size

        // Ensure the ImageView matches the circle size
         photo_profile_signin.setFitWidth(size);
         photo_profile_signin.setFitHeight(size);
         photo_profile_signin.setPreserveRatio(false);

        // Create a circular clip
        Circle clip = new Circle(size / 2);
        clip.setCenterX(size / 2);
        clip.setCenterY(size / 2);

        // Apply the clip to the ImageView
         photo_profile_signin.setClip(clip);
    }

    private void handleImageClick(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
             photo_profile_signin.setImage(image);
        }
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
    private void handleUser(ActionEvent event, boolean isUpdate) throws IOException {
        if(!isUpdate){resetFieldStyles();};
        boolean hasError = false;

        TextField nomField = isUpdate ? lastName_field_update : lastName_field;
        TextField prenomField = isUpdate ? firstName_field_update : firstName_field;
        TextField emailField = isUpdate ? email_field_update : email_field_signup;
        TextField numField = isUpdate ? number_field_update : number_field;
        PasswordField passwordField = isUpdate ? password_field_update : password_field_signup;
        PasswordField confirmPasswordField = isUpdate ? password_field_match_update : password_field_signup_match;


        if (nomField.getText().isEmpty()) { setFieldError(nomField, true); hasError = true; }
        if (prenomField.getText().isEmpty()) { setFieldError(prenomField, true); hasError = true; }
        if (emailField.getText().isEmpty()) { setFieldError(emailField, true); hasError = true; }
        if (numField.getText().isEmpty()) { setFieldError(numField, true); hasError = true; }
        if (passwordField.getText().isEmpty()) { setFieldError(passwordField, true); hasError = true; }

        if (hasError) {
            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        String email = emailField.getText();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            setFieldError(emailField, true);
            showAlert("Erreur", "Veuillez entrer un email valide", Alert.AlertType.ERROR);
            return;
        }

        String number = numField.getText();
        try {
            Integer.parseInt(number);
            if (number.length() != 8) {
                setFieldError(numField, true);
                showAlert("Erreur", "Le numéro de téléphone doit être composé de 8 chiffres", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            setFieldError(numField, true);
            showAlert("Erreur", "Le numéro de téléphone doit être un nombre valide", Alert.AlertType.ERROR);
            return;
        }

        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            setFieldError(passwordField, true);
            setFieldError(confirmPasswordField, true);
            showAlert("Erreur", "Les mots de passe ne correspondent pas", Alert.AlertType.ERROR);
            return;
        }
        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*")) {
            setFieldError(passwordField, true);
            showAlert("Erreur", "Le mot de passe doit contenir au moins 8 caractères, une majuscule et un chiffre", Alert.AlertType.ERROR);
            return;
        }

        String hashedPassword =BCrypt.hashpw(password, BCrypt.gensalt());

        ClientServices C1 = new ClientServices();
        if (isUpdate) {

            Client client = new Client(C1.getUserIdByEmail(fixed_email.getText()),nomField.getText(), prenomField.getText(), email, hashedPassword, Integer.parseInt(number), 0, 0, "");
            C1.modifierUser(client);
            labal_prenom.setText(client.getPrenom().toUpperCase());
            labal_tel.setText(String.valueOf(client.getNum_tel()).toUpperCase());
            label_mail.setText(client.getEmail());
            label_nom.setText(client.getName().toUpperCase());
            showAlert("Confirmation", "Votre compte a été mis à jour", Alert.AlertType.INFORMATION);
            animateTransition(Modifier_page, background_front, 0);

        } else {
            Client newClient = new Client(nomField.getText(), prenomField.getText(), email, hashedPassword, Integer.parseInt(number), 2, 0,  photo_profile_signin.getImage().getUrl());
            C1.ajouterUser(newClient);
            showAlert("Confirmation", "Votre compte a été créé", Alert.AlertType.INFORMATION);
            switchToSignIn();
        }

        clearFields();
    }

    private void setFieldError(TextField field, boolean hasError) {
        if (hasError) {
            field.getStyleClass().add("error-field");
        } else {
            field.getStyleClass().remove("error-field");
        }
    }

    // Réinitialisation des styles
    private void resetFieldStyles() {
        lastName_field.getStyleClass().remove("error-field");
        firstName_field.getStyleClass().remove("error-field");
        email_field_signup.getStyleClass().remove("error-field");
        number_field.getStyleClass().remove("error-field");
        password_field_signup.getStyleClass().remove("error-field");
        password_field_signup_match.getStyleClass().remove("error-field");

        lastName_field_update.getStyleClass().remove("error-field");
        firstName_field_update.getStyleClass().remove("error-field");
        email_field_update.getStyleClass().remove("error-field");
        number_field_update.getStyleClass().remove("error-field");
        password_field_update.getStyleClass().remove("error-field");
        password_field_match_update.getStyleClass().remove("error-field");
    }
    @FXML
    private void AddUser(ActionEvent event) throws IOException {
        handleUser(event, false);
    }

    @FXML
    private void UpdateUser(ActionEvent event) throws IOException {
        handleUser(event, true);
    }



    private void clearFields() {
        TextField[] fields = {
                lastName_field, firstName_field, email_field_signup, number_field,
                password_field_signup, password_field_signup_match, lastName_field_update,
                firstName_field_update, email_field_update, number_field_update,
                password_field_update, password_field_match_update
        };

        for (TextField field : fields) {
            field.setText("");
        }

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
                case 1:
                    showAlert("Bienvenue", "Bonjour" + " " + client.getPrenom().toUpperCase() + " " + client.getName().toUpperCase(), Alert.AlertType.INFORMATION);
                    animateTransition(Se_connecter_page, background_back, 0);
                    break;
                case 2:
                    showAlert("Bienvenue", "Bonjour" + " " + client.getPrenom().toUpperCase() + " " + client.getName().toUpperCase(), Alert.AlertType.INFORMATION);
                    UserAccount();
                    animateTransition(Se_connecter_page, background_front, 0);
                    ClientServices c=new ClientServices();
                    photo_profile_front.setImage(c.loadImageFromDatabase(email_field_signin.getText()));
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

            labal_prenom.setText(client.getPrenom().toUpperCase());
            labal_tel.setText(String.valueOf(client.getNum_tel()).toUpperCase());
            label_mail.setText(client.getEmail());
            label_nom.setText(client.getName().toUpperCase());
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
    @FXML
    private void GoBackFront(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Annulation de la mise à jour du compte");
        alert.setContentText("Êtes-vous sûr de vouloir annuler la mise à jour de votre compte ? Cliquez sur OK pour confirmer.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
        animateTransition(Modifier_page, background_front, 0);}


    }
    @FXML
    private void SwitchToUpdateUser(ActionEvent event){

        animateTransition(background_front, Modifier_page, 0);
        lastName_field_update.setText(label_nom.getText().toLowerCase());
        firstName_field_update.setText(labal_prenom.getText().toLowerCase());
        email_field_update.setText(label_mail.getText());
        number_field_update.setText(String.valueOf(labal_tel.getText()));
        fixed_email.setText(label_mail.getText());
    }
    @FXML
    private void changeForum(javafx.event.ActionEvent actionEvent) {
        if (actionEvent.getSource() == bt_menu_dashboard) {
            page_chambre.setVisible(false);
            page_hotel.setVisible(false);
            page_forum.setVisible(false);
            page_activite.setVisible(false);
            page_contrat.setVisible(false);
            page_checkout.setVisible(false);
            page_session.setVisible(false);
            page_post.setVisible(false);
            page_utilisateur.setVisible(false);
            page_vehicule.setVisible(false);
            page_vol.setVisible(false);
            page_acceuil_gestion_location.setVisible(false);
            page_acceuil_gestion_hotel.setVisible(false);
            page_acceuil_gestion_circuit.setVisible(false);
            page_acceuil_gestion_forum.setVisible(false);
            page_acceuil_gestion_vol.setVisible(false);


            page_dashboard.setVisible(true);

        } else if (actionEvent.getSource() == bt_menu_user) {
            page_chambre.setVisible(false);
            page_hotel.setVisible(false);
            page_forum.setVisible(false);
            page_activite.setVisible(false);
            page_contrat.setVisible(false);
            page_checkout.setVisible(false);
            page_session.setVisible(false);
            page_post.setVisible(false);
            page_vehicule.setVisible(false);
            page_vol.setVisible(false);

            page_acceuil_gestion_location.setVisible(false);
            page_acceuil_gestion_hotel.setVisible(false);
            page_acceuil_gestion_circuit.setVisible(false);
            page_acceuil_gestion_forum.setVisible(false);
            page_acceuil_gestion_vol.setVisible(false);

            page_dashboard.setVisible(false);
            page_utilisateur.setVisible(true);

        } else if (actionEvent.getSource() == bt_menu_hotel) {
            page_chambre.setVisible(false);
            page_forum.setVisible(false);
            page_activite.setVisible(false);
            page_contrat.setVisible(false);
            page_checkout.setVisible(false);
            page_session.setVisible(false);
            page_post.setVisible(false);
            page_vehicule.setVisible(false);
            page_vol.setVisible(false);

            page_acceuil_gestion_location.setVisible(false);
            page_acceuil_gestion_circuit.setVisible(false);
            page_acceuil_gestion_forum.setVisible(false);
            page_acceuil_gestion_vol.setVisible(false);

            page_dashboard.setVisible(false);
            page_utilisateur.setVisible(false);
            page_hotel.setVisible(false);
            page_acceuil_gestion_hotel.setVisible(true);

        } else if (actionEvent.getSource() == bt_menu_Circuit) {
            page_chambre.setVisible(false);
            page_forum.setVisible(false);
            page_activite.setVisible(false);
            page_contrat.setVisible(false);
            page_checkout.setVisible(false);
            page_session.setVisible(false);
            page_post.setVisible(false);
            page_vehicule.setVisible(false);
            page_vol.setVisible(false);

            page_acceuil_gestion_location.setVisible(false);
            page_acceuil_gestion_hotel.setVisible(false);
            page_acceuil_gestion_forum.setVisible(false);
            page_acceuil_gestion_vol.setVisible(false);

            page_dashboard.setVisible(false);
            page_utilisateur.setVisible(false);
            page_hotel.setVisible(false);
            page_acceuil_gestion_circuit.setVisible(true);

        }
        else if (actionEvent.getSource() == bt_menu_vol) {
            page_chambre.setVisible(false);
            page_forum.setVisible(false);
            page_activite.setVisible(false);
            page_contrat.setVisible(false);
            page_checkout.setVisible(false);
            page_session.setVisible(false);
            page_post.setVisible(false);
            page_vehicule.setVisible(false);
            page_vol.setVisible(false);

            page_acceuil_gestion_location.setVisible(false);
            page_acceuil_gestion_hotel.setVisible(false);
            page_acceuil_gestion_forum.setVisible(false);

            page_dashboard.setVisible(false);
            page_utilisateur.setVisible(false);
            page_hotel.setVisible(false);
            page_acceuil_gestion_circuit.setVisible(false);
            page_acceuil_gestion_vol.setVisible(true);

        }
        else if (actionEvent.getSource() == bt_menu_location) {
            page_chambre.setVisible(false);
            page_forum.setVisible(false);
            page_activite.setVisible(false);
            page_contrat.setVisible(false);
            page_checkout.setVisible(false);
            page_session.setVisible(false);
            page_post.setVisible(false);
            page_vehicule.setVisible(false);
            page_vol.setVisible(false);

            page_acceuil_gestion_hotel.setVisible(false);
            page_acceuil_gestion_forum.setVisible(false);
            page_acceuil_gestion_vol.setVisible(false);

            page_dashboard.setVisible(false);
            page_utilisateur.setVisible(false);
            page_hotel.setVisible(false);
            page_acceuil_gestion_circuit.setVisible(false);
            page_acceuil_gestion_location.setVisible(true);

        }
        else if (actionEvent.getSource() == bt_menu_forum) {
            page_chambre.setVisible(false);
            page_forum.setVisible(false);
            page_activite.setVisible(false);
            page_contrat.setVisible(false);
            page_checkout.setVisible(false);
            page_session.setVisible(false);
            page_post.setVisible(false);
            page_vehicule.setVisible(false);
            page_vol.setVisible(false);

            page_acceuil_gestion_hotel.setVisible(false);
            page_acceuil_gestion_vol.setVisible(false);

            page_dashboard.setVisible(false);
            page_utilisateur.setVisible(false);
            page_hotel.setVisible(false);
            page_acceuil_gestion_circuit.setVisible(false);
            page_acceuil_gestion_location.setVisible(false);
            page_acceuil_gestion_forum.setVisible(true);

        }
        else if (actionEvent.getSource() == page_hotel_bt_go_to_hotel) {


            page_acceuil_gestion_hotel.setVisible(false);
            page_hotel.setVisible(true);

        }
        else if (actionEvent.getSource() == page_hotel_bt_retour) {


            page_hotel.setVisible(false);
            page_acceuil_gestion_hotel.setVisible(true);
        }
        else if (actionEvent.getSource() == page_hotel_bt_ajouter) {


            page_hotel.setVisible(false);
            page_ajouter_hotel.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_hotel_return) {


            page_ajouter_hotel.setVisible(false);
            page_hotel.setVisible(true);
        }else if (actionEvent.getSource() == page_acceuil_hotel_go_to_chambre) {


            page_acceuil_gestion_hotel.setVisible(false);
            page_chambre.setVisible(true);
        }else if (actionEvent.getSource() == page_chambre_retour) {


            page_chambre.setVisible(false);
            page_acceuil_gestion_hotel.setVisible(true);
        }else if (actionEvent.getSource() == page_chambre_bt_ajouter) {


            page_chambre.setVisible(false);
            page_ajouter_chambre.setVisible(true);
        }else if (actionEvent.getSource() == page_ajouter_chambre_retour) {


            page_ajouter_chambre .setVisible(false);
            page_chambre.setVisible(true);
        }else if (actionEvent.getSource() == page_acceuil_gestion_circuit_go_to_activite) {


            page_acceuil_gestion_circuit .setVisible(false);
            page_activite.setVisible(true);
        }else if (actionEvent.getSource() == page_acceuil_gestion_circuit_go_to_session) {


            page_acceuil_gestion_circuit .setVisible(false);
            page_session.setVisible(true);
        }
        else if (actionEvent.getSource() == page_activite_retour) {


            page_activite .setVisible(false);
            page_acceuil_gestion_circuit.setVisible(true);
        }  else if (actionEvent.getSource() == page_activite_bt_ajouter) {


            page_activite .setVisible(false);
            page_ajouter_activite.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_activiter_retour) {


            page_ajouter_activite .setVisible(false);
            page_activite.setVisible(true);
        }    else if (actionEvent.getSource() == page_session_retour) {


            page_session .setVisible(false);
            page_acceuil_gestion_circuit.setVisible(true);
        }
        else if (actionEvent.getSource() == page_session_bt_ajouter) {


            page_session .setVisible(false);
            page_ajouter_session.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_session_retour) {


            page_ajouter_session.setVisible(false);
            page_session.setVisible(true);
        }
        else if (actionEvent.getSource() == page_acceuil_location_go_to_vehicule) {


            page_acceuil_gestion_location.setVisible(false);
            page_vehicule.setVisible(true);
        }
        else if (actionEvent.getSource() == page_acceuil_location_go_to_contrat) {


            page_acceuil_gestion_location.setVisible(false);
            page_contrat.setVisible(true);
        }
        else if (actionEvent.getSource() == page_vehicule_retour) {


            page_vehicule.setVisible(false);
            page_acceuil_gestion_location.setVisible(true);
        }
        else if (actionEvent.getSource() == page_vehicule_bt_ajouter) {


            page_vehicule.setVisible(false);
            page_ajouter_vehicule.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_vehicule_retour) {


            page_ajouter_vehicule.setVisible(false);
            page_vehicule.setVisible(true);
        }
        else if (actionEvent.getSource() == page_contrat_retour) {


            page_contrat.setVisible(false);
            page_acceuil_gestion_location.setVisible(true);
        }
        else if (actionEvent.getSource() == page_contrat_bt_ajouter) {


            page_contrat.setVisible(false);
            page_ajouter_contrat.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_contrat_retour) {


            page_ajouter_contrat.setVisible(false);
            page_contrat.setVisible(true);
        }
        else if (actionEvent.getSource() == gestion_acceuil_bt_go_to_vol) {


            page_acceuil_gestion_vol.setVisible(false);
            page_vol.setVisible(true);
        }
        else if (actionEvent.getSource() == gestion_acceuil_bt_go_to_checkout) {


            page_acceuil_gestion_vol.setVisible(false);
            page_checkout.setVisible(true);
        }
        else if (actionEvent.getSource() == page_vol_retour) {


            page_vol.setVisible(false);
            page_acceuil_gestion_vol.setVisible(true);
        } else if (actionEvent.getSource() == page_vol_bt_ajouter) {


            page_vol.setVisible(false);
            page_ajouter_vol.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_vol_retour) {


            page_ajouter_vol.setVisible(false);
            page_vol.setVisible(true);
        }
        else if (actionEvent.getSource() == page_checkout_retour) {


            page_checkout.setVisible(false);
            page_acceuil_gestion_vol.setVisible(true);
        }
        else if (actionEvent.getSource() == page_checkout_bt_ajouter) {


            page_checkout.setVisible(false);
            page_ajouter_checkout.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_checkout_retour) {


            page_ajouter_checkout.setVisible(false);
            page_checkout.setVisible(true);
        }
        else if (actionEvent.getSource() == page_acceuil_forum_bt_forum) {


            page_acceuil_gestion_forum.setVisible(false);
            page_forum.setVisible(true);
        }
        else if (actionEvent.getSource() == page_acceuil_forum_go_to_post) {


            page_acceuil_gestion_forum.setVisible(false);
            page_post.setVisible(true);
        }
        else if (actionEvent.getSource() == page_forum_retour) {


            page_forum.setVisible(false);
            page_acceuil_gestion_forum.setVisible(true);
        }
        else if (actionEvent.getSource() == page_forum_bt_ajouter) {


            page_forum.setVisible(false);
            page_ajouter_forum.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_forum_retour) {


            page_ajouter_forum.setVisible(false);
            page_forum.setVisible(true);
        }
        else if (actionEvent.getSource() == page_post_retour) {


            page_post.setVisible(false);
            page_acceuil_gestion_forum.setVisible(true);
        }
        else if (actionEvent.getSource() == page_post_bt_ajouter) {


            page_post.setVisible(false);
            page_ajouter_post.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_post_retour) {


            page_ajouter_post.setVisible(false);
            page_post.setVisible(true);
        }
        else if (actionEvent.getSource() == page_utilisateur_retour) {


            page_utilisateur.setVisible(false);
            page_dashboard.setVisible(true);
        }
    }
    @FXML
    private void AddHotel(ActionEvent event) throws IOException {
        if (hotel_nom.getText().isEmpty() || hotel_num_tel.getText().isEmpty() ||
                hotel_email.getText().isEmpty() || hotel_description.getText().isEmpty() ) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        // Vérification du format de l'email
        if (!hotel_email.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert("Erreur", "Veuillez entrer un email valide", Alert.AlertType.ERROR);
            return;
        }

        // Vérification du numéro de téléphone
        /*try {
            Integer.parseInt(number_field.getText());  // Essaye de convertir en entier
            if (number_field.getText().length() != 8) {
                showAlert("Erreur", "Le numéro de téléphone doit être composé de 8 chiffres", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le numéro de téléphone doit être un nombre valide", Alert.AlertType.ERROR);
            return;
        }*/


        // Si toutes les vérifications passent
        HotelService H = new HotelService();
        H.ajouter(new Hotel(hotel_nom.getText(), hotel_localisation.getText(),hotel_num_tel.getText(), hotel_email.getText(),hotel_image.getText(),hotel_description.getText()));

        // Affichage de l'alerte de confirmation
        showAlert("Confirmation", "L'Hotel a été ajouté", Alert.AlertType.INFORMATION);
        hotel_nom.setText("");
        hotel_localisation.setText("");
        hotel_num_tel.setText("");
        hotel_email.setText("");
        hotel_image.setText("");
        hotel_description.setText("");
        RefreshTableView_Hotel();;
        page_ajouter_hotel.setVisible(false);
        page_hotel.setVisible(true);
    }
    @FXML
    private void UPDATEHotel(ActionEvent event) throws IOException {
        if (hotel_nom.getText().isEmpty() || hotel_num_tel.getText().isEmpty() ||
                hotel_email.getText().isEmpty() || hotel_description.getText().isEmpty() ) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        // Vérification du format de l'email
        if (!hotel_email.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert("Erreur", "Veuillez entrer un email valide", Alert.AlertType.ERROR);
            return;
        }

        // Vérification du numéro de téléphone
        /*try {
            Integer.parseInt(number_field.getText());  // Essaye de convertir en entier
            if (number_field.getText().length() != 8) {
                showAlert("Erreur", "Le numéro de téléphone doit être composé de 8 chiffres", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le numéro de téléphone doit être un nombre valide", Alert.AlertType.ERROR);
            return;
        }*/


        // Si toutes les vérifications passent
        HotelService H = new HotelService();
        H.modifier(new Hotel(5,hotel_nom.getText(), hotel_localisation.getText(),hotel_num_tel.getText(), hotel_email.getText(),hotel_image.getText(),hotel_description.getText()));

        // Affichage de l'alerte de confirmation
        showAlert("Confirmation", "L'Hotel a été modifié", Alert.AlertType.INFORMATION);
        hotel_nom.setText("");
        hotel_localisation.setText("");
        hotel_num_tel.setText("");
        hotel_email.setText("");
        hotel_image.setText("");
        hotel_description.setText("");
        page_ajouter_hotel.setVisible(false);
        page_hotel.setVisible(true);
        RefreshTableView_Hotel();
    }
    @FXML
    private void RefreshTableView_Hotel() {
        try {
            HotelService hotelService = new HotelService();
            List<Hotel> hotels = hotelService.rechercher(); // Fetch hotels from DB

            if (hotels != null) {
                // Clear and update ObservableList
                HotelData.clear();
                HotelData.addAll(hotels);

                // ✅ Manually adding a hotel with dummy data
                //Hotel manualHotel = new Hotel(999, "Test Hotel", "Test Location", "123456789", "test@email.com", "test.jpg", "This is a test hotel");
                // HotelData.add(manualHotel);

                // Debugging
                System.out.println("Number of hotels fetched: " + hotels.size());
                System.out.println("HotelData size after update: " + HotelData.size());

                TableView_Hotel.setItems(HotelData);
                TableView_Hotel.refresh(); // Force update

            } else {
                showAlert("Information", "Aucun hôtel trouvé.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour de la TableView.", Alert.AlertType.ERROR);
            e.printStackTrace();

        }

    }
}