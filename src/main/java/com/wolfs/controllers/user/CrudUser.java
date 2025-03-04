package com.wolfs.controllers.user;

import com.google.api.client.util.DateTime;
import com.google.auth.oauth2.AccessToken;
import com.wolfs.models.*;
import com.wolfs.services.*;


import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.LocalDate;

import com.wolfs.services.ActiviteService2;
import com.wolfs.models.GoogleCalendar;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.wolfs.models.WeatherParser;
import com.wolfs.models.WeatherService;
import com.wolfs.models.WeatherDisplay;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import javafx.scene.image.Image;

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


//new

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
    private Button hotel_bt_ajouter;

    @FXML
    private Button hotel_bt_modifier;

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
    private TableView<Hotel> TableView_Hotel;
    @FXML
    private TableColumn<Hotel, Integer> hotel_col_id;
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


    private boolean isPasswordVisible = false;

    //fxml activite


    @FXML
    private Button activite_bt_ajouter;
    @FXML
    private Button activite_bt_modifier;
    @FXML
    private TextField nom_activite;
    @FXML
    private  TextField description_activite;
    @FXML
    private TextField localisation_activite;
    @FXML
    private TextField type_activite;
    @FXML
    private TextField prix_activite;
    @FXML
    private TextField fixed_nom;
    //tabelview activite
    @FXML
    private TableView<Activite> TableView_Activite;
    @FXML
    private TableColumn<Activite, Integer> act_col_nom;
    @FXML
    private TableColumn<Activite, String> act_col_desc;
    @FXML
    private TableColumn<Activite, String> act_col_loc;
    @FXML
    private TableColumn<Activite, String> act_col_type;
    @FXML
    private TableColumn<Activite, String> act_col_prix;
    @FXML
    private TableColumn<Activite, Void> act_col_mod;

    @FXML
    private TableColumn<Activite, Void> act_col_supp    ;
    private static final String APP_ID = "654796463732563";
@FXML
private Button sharefb;


    private ObservableList<Activite> ActiviteData = FXCollections.observableArrayList();




    //fxml session

    @FXML
    private Button session_bt_ajouter;
    @FXML
    private Button sessio_bt_modifier;
    @FXML
    private DatePicker date_sess;
    @FXML
    private TextField time_sess;
    @FXML
    private TextField sess_cap;
    @FXML
    private TextField sess_nbr_places;
    @FXML
    private DatePicker fixed_nom_sess;


    @FXML
    private ComboBox<Activite> combo_activite;


    //tabelview session
    @FXML
    private TableView<Session> TableView_Session;
    @FXML
    private TableColumn<Session, String> sess_col_nomact;
    @FXML
    private TableColumn<Session, String> sess_col_date;
    @FXML
    private TableColumn<Session, String> sess_col_time;
    @FXML
    private TableColumn<Session, String> sess_col_capacite;
    @FXML
    private TableColumn<Session, String> sess_col_nbrplace;
    @FXML
    private TableColumn<Session, Void> sess_col_mod;

    @FXML
    private TableColumn<Session, Void> sess_col_supp    ;







    private ObservableList<Session> SessionData = FXCollections.observableArrayList();



// calendar button
    @FXML
    private Button calendar_sess;
    @FXML
    private VBox weather_view;  // The VBox where weather icons and data will be displayed

    @FXML
    private ImageView weatherIcon;  // The ImageView for displaying the weather icon

    @FXML
    private Label weatherDescription;  // Label to display the weather description

    @FXML
    private Label weatherTemperature;


@FXML
private Button weather_sess;



    //Animation part
    public void initialize() {
        startAnimation();
        loadComboBoxActivites(); // Load the activities into the ComboBox
        addSessionsToCalendar();
        // Assuming ActiviteService2 is defined elsewhere
        ActiviteService2 activiteService = new ActiviteService2();

        List<Activite> activitesList = activiteService.rechercher();  // Fetch the List<Activite> from the rechercher method
        ObservableList<Activite> activites = FXCollections.observableArrayList(activitesList);

        // Set the items in the ComboBox
        combo_activite.setItems(activites);

        // Set the ComboBox to display just the names (or any other field) for user selection
        combo_activite.setCellFactory(param -> new ListCell<Activite>() {
            @Override
            protected void updateItem(Activite item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom_act());  // Display only the name of the activity
                }
            }
        });

        // Set the converter to handle the selected Activite
        combo_activite.setConverter(new StringConverter<Activite>() {
            @Override
            public String toString(Activite activite) {
                return activite == null ? null : activite.getNom_act();  // Display name when selected
            }

            @Override
            public Activite fromString(String string) {
                return null;  // Not necessary in this case, since we work with Activite objects directly
            }
        });

        // Initialize table columns act
        act_col_nom.setCellValueFactory(new PropertyValueFactory<>("nom_act"));
        act_col_desc.setCellValueFactory(new PropertyValueFactory<>("descript"));
        act_col_loc.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        act_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        act_col_prix.setCellValueFactory(new PropertyValueFactory<>("prix_act"));

        // Set cell factories for action buttons
        act_col_mod.setCellFactory(column -> {
            return new TableCell<Activite, Void>() {
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
                        Activite activite = getTableView().getItems().get(getIndex());
                        activite_bt_modifier.setVisible(true);
                        activite_bt_ajouter.setVisible(false);

                        nom_activite.setText(activite.getNom_act());
                        description_activite.setText(activite.getDescript());
                        localisation_activite.setText(activite.getLocalisation());
                        type_activite.setText(activite.getType());
                        prix_activite.setText(String.valueOf(activite.getPrix_act()));
                        fixed_nom.setText(activite.getNom_act());
                        page_ajouter_activite.setVisible(true);
                        page_activite.setVisible(false);
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
                act_col_supp.setCellFactory(column -> {
                    return new TableCell<Activite, Void>() {
                        private final Button btn = new Button("Supprimer");

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
                                RefreshTableView_Session();
                                // Récupérer l'activité associée à la ligne actuelle
                                Activite activiteSelectionnee = getTableView().getItems().get(getIndex());
                                System.out.println("Activité sélectionnée : " + activiteSelectionnee.getNom_act());

                                if (activiteSelectionnee == null) {
                                    showAlert("Erreur", "Impossible de récupérer l'activité sélectionnée.", Alert.AlertType.ERROR);
                                    return;
                                }

                                // Demander confirmation avant suppression
                                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                confirmationAlert.setTitle("Confirmation de suppression");
                                confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette activité ?");
                                confirmationAlert.setContentText("Cette action est irréversible.");

                                Optional<ButtonType> result = confirmationAlert.showAndWait();

                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    // Supprimer l'activité via le nom_act
                                    ActiviteService2 activiteService = new ActiviteService2();
                                    activiteService.supprimer(activiteSelectionnee);

                                    showAlert("Confirmation", "L'Activité a été supprimée avec succès", Alert.AlertType.INFORMATION);

                                    // Mettre à jour la TableView
                                    getTableView().getItems().remove(activiteSelectionnee);
                                    RefreshTableView_Session();
                                    RefreshTableView_Activite();
                                }
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
                })

        ;
        ActiviteService2 ActiviteService2= new ActiviteService2();
        List<Activite> acitviteliste = ActiviteService2.rechercher();
        ObservableList<Activite> observableAcitviteListt = FXCollections.observableArrayList(acitviteliste);

        TableView_Activite.setItems(observableAcitviteListt);
        // Initialize session table columns
        sess_col_nomact.setCellValueFactory(cellData -> {
            // Ensure cellData is working with a Session object
            Session session = cellData.getValue();  // This should be of type Session

            // Fetch the Activite using the session's idAct field
            Activite activite = activiteService.getActiviteById(session.getIdAct());  // Using idAct from the Session

            // Return the name of the activity (nom_act) or "Unknown" if not found
            return new SimpleStringProperty(activite != null ? activite.getNom_act() : "Unknown");
        });

        // Initialize other columns for session data
        sess_col_date.setCellValueFactory(new PropertyValueFactory<>("date_sess"));
        sess_col_time.setCellValueFactory(new PropertyValueFactory<>("time_sess"));
        sess_col_capacite.setCellValueFactory(new PropertyValueFactory<>("cap_sess"));
        sess_col_nbrplace.setCellValueFactory(new PropertyValueFactory<>("nbr_places_sess"));
        sess_col_mod.setCellFactory(column -> {
            return new TableCell<Session, Void>() {
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
                        Session session = getTableView().getItems().get(getIndex());




                            // Show/hide the buttons
                            sessio_bt_modifier.setVisible(true);
                            session_bt_ajouter.setVisible(false);

                            // Set the values from the Activite object
                        LocalDate sessionDate = session.getDate_sess();
                        LocalTime sessiotime = session.getTime_sess();
                            date_sess.setValue(sessionDate);
                        String formattedTime = sessiotime.format(DateTimeFormatter.ofPattern("HH:mm"));
                        time_sess.setText(formattedTime); // Set the location
                        int capSess = session.getCap_sess();
                            sess_cap.setText(String.valueOf(capSess));
                        int nbr_place_sess = session.getNbr_places_sess();
                        sess_nbr_places.setText(String.valueOf(nbr_place_sess));
                            fixed_nom_sess.setValue(sessionDate); // Set the fixed name (could be used elsewhere in the UI)
                        sessio_bt_modifier.setVisible(true);
                        session_bt_ajouter.setVisible(false);
                        page_ajouter_session.setVisible(true);
                        page_session.setVisible(false);
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
        sess_col_supp.setCellFactory(column -> {
            return new TableCell<Session, Void>() {
                private final Button btn = new Button("Supprimer");

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

                        // Récupérer la session associée à la ligne actuelle
                        Session SessionSelectionnee = getTableView().getItems().get(getIndex());


                        if (SessionSelectionnee == null) {
                            showAlert("Erreur", "Impossible de récupérer l'activité sélectionnée.", Alert.AlertType.ERROR);
                            return;
                        }

                        // Demander confirmation avant suppression
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("Confirmation de suppression");
                        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette session ?");
                        confirmationAlert.setContentText("Cette action est irréversible.");



                        Optional<ButtonType> result = confirmationAlert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            // Supprimer la session via id_sess
                            SessionService sessionService = new SessionService();
                            sessionService.supprimer(SessionSelectionnee);

                            showAlert("Confirmation", "La session a été supprimée avec succès", Alert.AlertType.INFORMATION);

                            // Mettre à jour la TableView
                            getTableView().getItems().remove(SessionSelectionnee);
                            RefreshTableView_Session();
                        }
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
        })


        ;
        // Fetch session list from DB
        SessionService sessionService = new SessionService();
        List<Session> sessionList = sessionService.rechercher();  // Fetch session list from DB

        ObservableList<Session> observableSessionList = FXCollections.observableArrayList(sessionList);
        TableView_Session.setItems(observableSessionList);
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

            RefreshTableView();;
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

            activite_bt_modifier.setVisible(false);
            activite_bt_ajouter.setVisible(true);
            page_activite .setVisible(false);
            page_ajouter_activite.setVisible(true);
        }
                 else if (actionEvent.getSource() == page_ajouter_activiter_retour) {
            nom_activite.setText("");
            description_activite.setText("");
            localisation_activite.setText("");
            type_activite.setText("");
            prix_activite.setText("");

            page_ajouter_activite .setVisible(false);
            page_activite.setVisible(true);
        }    else if (actionEvent.getSource() == page_session_retour) {


            page_session .setVisible(false);
            page_acceuil_gestion_circuit.setVisible(true);
        }
        else if (actionEvent.getSource() == page_session_bt_ajouter) {

            sessio_bt_modifier.setVisible(false);
            session_bt_ajouter.setVisible(true);
            page_session .setVisible(false);
            page_ajouter_session.setVisible(true);
        }
        else if (actionEvent.getSource() == page_ajouter_session_retour) {
            RefreshTableView_Session();
            RefreshTableView_Activite();
            time_sess.setText("");
            sess_cap.setText("");
            sess_nbr_places.setText("");
            date_sess.setValue(null);
            combo_activite.getSelectionModel().clearSelection();
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
        RefreshTableView();;
    }


   @FXML
    private void RefreshTableView() {
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



  /*  public void initialize_tableview_hotel() {
        // Initialize table columns
       hotel_col_id.setCellValueFactory(new PropertyValueFactory<>("id_hotel"));
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
    }*/
  @FXML
  private void AddActivte(ActionEvent event) throws IOException {


      if (nom_activite.getText().isEmpty() ||
              description_activite.getText().isEmpty() ||
              localisation_activite.getText().isEmpty() ||
              type_activite.getText().isEmpty() ||
              prix_activite.getText().isEmpty()) {

          showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
          return;
      }

        float prix;
        try {


            prix = Float.parseFloat(prix_activite.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le prix doit être un nombre valide", Alert.AlertType.ERROR);
            return;
        }

      // Si toutes les vérifications passentA
        ActiviteService2 A= new ActiviteService2();
      A.ajouter(new Activite(nom_activite.getText(),
              description_activite.getText(),
              localisation_activite.getText(),
              type_activite.getText(),
              prix));


      showAlert("Confirmation", "L'Activité a été ajoutée avec succès", Alert.AlertType.INFORMATION);

      // Réinitialisation des champs
      nom_activite.setText("");
      description_activite.setText("");
      localisation_activite.setText("");
      type_activite.setText("");
      prix_activite.setText("");

      RefreshTableView_Activite();
      page_ajouter_activite.setVisible(false);
      page_activite.setVisible(true);
  }


    @FXML
    private void RefreshTableView_Activite() {
        try {
            ActiviteService2 ACTAFF = new ActiviteService2();
            List<Activite> activites = ACTAFF.rechercher(); // Fetch hotels from DB

            if (activites != null) {
                // Clear and update ObservableList
                ActiviteData.clear();
                ActiviteData.addAll(activites);


                System.out.println("Number of activites " + activites.size());
                System.out.println("ActiviteData: " +ActiviteData.size());
                TableView_Activite.setItems(ActiviteData);
                TableView_Activite.refresh(); // Force update

            } else {
                showAlert("Information", "Aucun activite", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour de la TableView.", Alert.AlertType.ERROR);
            e.printStackTrace();

        }

    }
@FXML
    private void Mod_act(ActionEvent event) throws IOException{

        if (nom_activite.getText().isEmpty() ||
                description_activite.getText().isEmpty() ||
                localisation_activite.getText().isEmpty() ||
                type_activite.getText().isEmpty() ||
                prix_activite.getText().isEmpty()) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }
        float prix;
        try {


            prix = Float.parseFloat(prix_activite.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le prix doit être un nombre valide", Alert.AlertType.ERROR);
            return;
        }
    ActiviteService2 activiteService = new ActiviteService2();
    int idAct = activiteService.getIdByNom(fixed_nom.getText());
    if (idAct == -1) {
        showAlert("Erreur", "Activité non trouvée dans la base de données", Alert.AlertType.ERROR);
        return;
    }

    Activite activite = new Activite(
            idAct,
            nom_activite.getText(),
            description_activite.getText(),
            localisation_activite.getText(),
            type_activite.getText(),
            prix
    );

    // Modification de l'activité
    activiteService.modifier(activite);

    showAlert("Confirmation", "L'Activité a été modifiée avec succès", Alert.AlertType.INFORMATION);



        // Réinitialisation des champs
        nom_activite.setText("");
        description_activite.setText("");
        localisation_activite.setText("");
        type_activite.setText("");
        prix_activite.setText("");

        RefreshTableView_Activite();
        page_ajouter_activite.setVisible(false);
        page_activite.setVisible(true);

    }




    @FXML
    private void AddSession(ActionEvent event) throws IOException {
        // Check if all fields are filled
        if (time_sess.getText().isEmpty() ||
                sess_cap.getText().isEmpty() ||
                sess_nbr_places.getText().isEmpty() ||
                date_sess.getValue() == null ||
                combo_activite.getSelectionModel().getSelectedItem() == null) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        // Convert the time from TextField to LocalTime
        LocalTime time;
        try {
            time = LocalTime.parse(time_sess.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            showAlert("Erreur", "Le format de l'heure est invalide. Utilisez HH:mm", Alert.AlertType.ERROR);
            return;
        }

        // Convert date_sess (DatePicker) to LocalDate
        LocalDate date = date_sess.getValue();

        // Convert cap & nbrPlaces
        int cap, nbrPlaces;
        try {
            cap = Integer.parseInt(sess_cap.getText());
            nbrPlaces = Integer.parseInt(sess_nbr_places.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le nombre de places et la capacité doivent être des entiers valides", Alert.AlertType.ERROR);
            return;
        }

        // Get selected activity name from the ComboBox
        String nomAct = combo_activite.getSelectionModel().getSelectedItem().getNom_act();

        // Fetch the id_act using the name (nom_act) selected
        ActiviteService2 activiteService = new ActiviteService2();
        int idAct = activiteService.getIdByName(nomAct);

        // If idAct is -1, that means the activity doesn't exist in the database
        if (idAct == -1) {
            showAlert("Erreur", "L'ID de l'activité sélectionnée n'existe pas dans la base de données.", Alert.AlertType.ERROR);
            return;
        }

        // Create the session object
        Session newSession = new Session(date, time, cap, nbrPlaces, idAct);

        // Save to database
        SessionService sessionService = new SessionService();
        sessionService.ajouter(newSession);

        // Show success message
        showAlert("Confirmation", "La session a été ajoutée avec succès", Alert.AlertType.INFORMATION);

        // Reset fields
        time_sess.setText("");
        sess_cap.setText("");
        sess_nbr_places.setText("");
        date_sess.setValue(null);
        combo_activite.getSelectionModel().clearSelection(); // Clear selection
        page_ajouter_session.setVisible(false);
        page_session.setVisible(true);
        RefreshTableView_Session();
    }





    private void loadComboBoxActivites() {
        ActiviteService2 activiteService = new ActiviteService2();
        List<Activite> activites = activiteService.rechercher(); // Fetch activities from DB

        if (activites == null || activites.isEmpty()) {
            System.out.println("⚠ No activities found in the database!");
            return;
        }

        // Populate the ComboBox with the full Activite objects
        combo_activite.setItems(FXCollections.observableArrayList(activites));

        // Set the ComboBox to display just the names (or any other field) for user selection
        combo_activite.setCellFactory(param -> new ListCell<Activite>() {
            @Override
            protected void updateItem(Activite item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom_act());  // Display only the name of the activity
                }
            }
        });

        // Set the converter to handle the selected Activite
        combo_activite.setConverter(new StringConverter<Activite>() {
            @Override
            public String toString(Activite activite) {
                return activite == null ? null : activite.getNom_act();  // Display name when selected
            }

            @Override
            public Activite fromString(String string) {
                return null;  // Not necessary in this case, since we work with Activite objects directly
            }
        });

    }


  @FXML
    private void RefreshTableView_Session() {
        try {
            // Assuming SessionService handles database interactions for sessions
            SessionService sessionService = new SessionService();
            List<Session> sessions = sessionService.rechercher(); // Fetch sessions from DB

            if (sessions != null) {
                // Clear and update ObservableList
                SessionData.clear();

                // Add each session to the ObservableList
                SessionData.addAll(sessions);

                // After loading sessions, now update the TableView
                TableView_Session.setItems(SessionData);
                TableView_Session.refresh(); // Force update
            } else {
                showAlert("Information", "Aucune session", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour de la TableView.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

     @FXML
    private void Mod_sess(ActionEvent event) throws IOException {
        // Check if all fields are filled for the session
        if (time_sess.getText().isEmpty() ||
                sess_cap.getText().isEmpty() ||
                sess_nbr_places.getText().isEmpty() ||
                date_sess.getValue() == null ||
                combo_activite.getSelectionModel().getSelectedItem() == null) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        // Convert the session time from string to LocalTime
        LocalTime time;
        try {
            time = LocalTime.parse(time_sess.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            showAlert("Erreur", "Le format de l'heure est invalide. Utilisez HH:mm", Alert.AlertType.ERROR);
            return;
        }

        // Convert date_sess to LocalDate
        LocalDate date = date_sess.getValue();

        // Convert cap & nbrPlaces to integers
        int cap, nbrPlaces;
        try {
            cap = Integer.parseInt(sess_cap.getText());
            nbrPlaces = Integer.parseInt(sess_nbr_places.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le nombre de places et la capacité doivent être des entiers valides", Alert.AlertType.ERROR);
            return;
        }
         String nomAct = combo_activite.getSelectionModel().getSelectedItem().getNom_act();
        // Fetch the ID of the activity using the name (fixed_nom is the name field)
        ActiviteService2 activiteService = new ActiviteService2();

        int idAct = activiteService.getIdByNom(nomAct);  // Fetch the ID based on the activity name

        if (idAct == -1) {
            showAlert("Erreur", "Activité non trouvée dans la base de données", Alert.AlertType.ERROR);
            return;
        }
         SessionService sessserv = new SessionService();

         LocalDate dateSess = fixed_nom_sess.getValue();

         int id_sess = sessserv.getIdByDate(dateSess);


        if (id_sess == -1) {
            showAlert("Erreur", "Session non trouvée pour cette heure", Alert.AlertType.ERROR);
            return;
        }

        // Initialize the updated Session object
         Session updatedSession = new Session(
                 id_sess,    // ID of the session (make sure this is passed properly)
                 date,       // Date of session
                 time,       // Time of session
                 cap,        // Capacity
                 nbrPlaces,  // Number of places
                 idAct       // ID of the activity linked to this session
         );

// Update the session
         sessserv.modifier(updatedSession);
         // Update the session using the session service

        // Show confirmation message
        showAlert("Confirmation", "La session a été modifiée avec succès", Alert.AlertType.INFORMATION);

        // Reset fields
        time_sess.setText("");
        sess_cap.setText("");
        sess_nbr_places.setText("");
        date_sess.setValue(null);

        // Refresh TableView and navigate between pages
        RefreshTableView_Session();
        page_ajouter_session.setVisible(false);
        page_session.setVisible(true);
    }

    // Method to add all sessions to Google Calendar
    private void addSessionsToCalendar() {
        try {
            // Fetch all sessions from the database
            List<Session> sessions = getSessionsFromDatabase();

            // Check if all sessions have valid id_act (not 0)
            for (Session session : sessions) {
                if (session.getIdAct() == 0) {
                    showError("Invalid Session", "Session ID " + session.getId_sess() + " has an invalid activity ID (id_act = 0).");
                    return;
                }
            }

            if (sessions.isEmpty()) {
                showError("No Sessions Found", "There are no sessions available to add to Google Calendar.");
                return;
            }

            GoogleCalendar.AjouterSessionsDansCalendrier(sessions);
            showInfo("Success", "All sessions were successfully added to Google Calendar.");

        } catch (Exception ex) {
            showError("Error adding sessions", "An error occurred: " + ex.getMessage());
            ex.printStackTrace();  // Debugging purposes
        }
    }


    // Fetch all sessions from the database
    private List<Session> getSessionsFromDatabase() {
        SessionService sessionService = new SessionService();
        List<Session> sessions = sessionService.getAllSessions();

        if (sessions == null || sessions.isEmpty()) {
            System.out.println("⚠ No sessions found in the database.");
        }

        return sessions;
    }

    // This method will be called when the "Show Calendar" button is clicked
    @FXML
    private void showCalendar(ActionEvent event) {
        addSessionsAndDisplayCalendar();
    }

    // Method to add all sessions to Google Calendar and display the full calendar
    private void addSessionsAndDisplayCalendar() {
        try {
            // Fetch all sessions
            List<Session> sessions = getSessionsFromDatabase();

            if (sessions.isEmpty()) {
                showError("No Sessions Found", "There are no sessions available to add.");
                return;
            }

            // Add sessions to Google Calendar
            GoogleCalendar.AjouterSessionsDansCalendrier(sessions);
            showInfo("Success", "All sessions were successfully added to Google Calendar.");

            // Open Google Calendar in a browser
            openGoogleCalendar();

        } catch (Exception ex) {
            showError("Error", "An error occurred while adding sessions: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Method to open Google Calendar in the browser
    private void openGoogleCalendar() {
        try {
            Desktop.getDesktop().browse(new URI("https://calendar.google.com"));
        } catch (Exception e) {
            showError("Error Opening Calendar", "Could not open Google Calendar.");
            e.printStackTrace();
        }
    }

    // Helper method to show info alerts
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show error alerts
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }






    // Method to update the weather information for a session based on the fetched weather data
    public void updateWeatherForSession(String sessionDate, String weatherData) {
        if (weatherData != null) {
            // Parse the weatherData string
            String weatherDescriptionText = weatherData.split(",")[0].replace("Weather: ", "").trim();
            double temperature = Double.parseDouble(weatherData.split(",")[1].replace("Temp: ", "").replace("°C", "").trim());

            // Set the weather icon based on the description
            Image icon = getWeatherIcon(weatherDescriptionText);
            weatherIcon.setImage(icon);  // Update the ImageView with the appropriate icon

            // Update weather description and temperature labels
            weatherDescription.setText("Weather: " + weatherDescriptionText);
            weatherTemperature.setText("Temp: " + temperature + "°C");
        } else {
            // Handle the case where no data is available
            weatherDescription.setText("Weather: Data not available");
            weatherTemperature.setText("Temp: N/A");
        }
    }

    // This method returns the appropriate icon based on weather description
    private Image getWeatherIcon(String description) {
        // Return appropriate icons based on the description
        if (description.contains("clear")) {
            return new Image(getClass().getResource("/images/sun_icon.png").toExternalForm());  // Path to sun icon
        } else if (description.contains("cloud")) {
            return new Image(getClass().getResource("/images/cloud_icon.png").toExternalForm());  // Path to cloud icon
        } else if (description.contains("rain")) {
            return new Image(getClass().getResource("/images/rain_icon.png").toExternalForm());  // Path to rain icon
        } else if (description.contains("overcast clouds")) {
            return new Image(getClass().getResource("/images/cloud_icon.png").toExternalForm());  // Path to overcast cloud icon
        } else {
            return new Image(getClass().getResource("/images/default_icon.png").toExternalForm());  // Default icon (in case description is unknown)
        }
    }


    // Show weather method when a session is selected from TableView
    @FXML
    private void showWeather(ActionEvent event) {
        // Get the selected session from the TableView
        Session selectedSession = TableView_Session.getSelectionModel().getSelectedItem();  // Adjust TableView_Session with your actual TableView ID

        if (selectedSession != null) {
            // Extract the session date (LocalDate)
            LocalDate sessionDate = selectedSession.getDate_sess();  // Assuming `getDate_sess()` returns a LocalDate

            // Debugging step: Check if the session date changes
            System.out.println("Selected session date: " + sessionDate);

            // Fetch the weather for the selected session date using the WeatherService
            String weatherData = WeatherService.getWeather(sessionDate.toString());  // Pass session date as String (YYYY-MM-DD)

            // Now update the weather based on the fetched data
            updateWeatherForSession(sessionDate.toString(), weatherData);
        } else {
            // If no session is selected, display a message
            weatherDescription.setText("Please select a session first.");
            weatherTemperature.setText("Temp: N/A");
        }
    }

    // Example method to initialize the weather for a specific session
    public void initializeWeatherForSession(String sessionDate) {
        // Fetch weather data for the session and pass it to update
        String weatherData = WeatherService.getWeather(sessionDate);
        updateWeatherForSession(sessionDate, weatherData);
    }

    @FXML
    private void onShareButtonClick(ActionEvent event) {
        // Get the selected activity from the TableView
        Activite selectedActivity = TableView_Activite.getSelectionModel().getSelectedItem();

        if (selectedActivity != null) {
            // Construct a link for Facebook to preview (can be any valid URL)
            String activityLink = "https://yourapp.com/activity/" + selectedActivity.getNom_act(); // Optional, replace if needed

            // Use a hashtag to categorize the post
            String hashtag = "#AmazingActivity";

            try {
                // URL-encode the link and hashtag
                String encodedLink = URLEncoder.encode(activityLink, StandardCharsets.UTF_8.toString());
                String encodedHashtag = URLEncoder.encode(hashtag, StandardCharsets.UTF_8.toString());

                // Construct the Facebook Feed Dialog URL
                String shareUrl = "https://www.facebook.com/dialog/feed?" +
                        "app_id=" + APP_ID + // Your Facebook App ID
                        "&display=popup" +
                        "&link=" + encodedLink + // Facebook requires a valid link for previews
                        "&hashtag=" + encodedHashtag + // Adds a hashtag to the post
                        "&redirect_uri=https://www.facebook.com/connect/login_success.html";

                // Open Facebook Share Dialog in the browser
                Desktop.getDesktop().browse(new URI(shareUrl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No activity selected!");
        }
    }


}


















