package com.wolfs.controllers.user;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.oauth2.model.Userinfo;
import com.wolfs.models.*;
import com.wolfs.services.*;
import com.wolfs.utils.GoogleAuthUtil;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;


public class CrudUser {

    private Timeline timeline;

@FXML
private Label countdownLabel;

    @FXML
    private AnchorPane forget_password;
    @FXML
    private TextField forget_password_mail;
    @FXML
    private AnchorPane forget_password_reset;
    @FXML
    private AnchorPane RederictToLogin;

    private String codeEnvoye;
    @FXML
    private TextField code_field;
    @FXML
    private PasswordField password_field_reset;
    @FXML
    private PasswordField confirmer_password_field_reset;

    private final ClientServices ClientService_user = new ClientServices();


    /*********************NEWWWWWWWWWWWWWWWWWWWWW********************************/

    @FXML
    private AnchorPane Modifier_page;
    @FXML
    private AnchorPane background_mon_profil;
    @FXML
    private AnchorPane background_front_forum;
    @FXML
    private AnchorPane background_front_offres;
    @FXML
    private AnchorPane background_front_acceuil;
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
    private TextField hotel_id;
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
    @FXML
    private TableColumn<Hotel, Void> hotel_col_supprimer;
    @FXML
    private TableColumn<Hotel, Void> hotel_col_modifier;
    private ObservableList<Hotel> HotelData = FXCollections.observableArrayList();



    //chambre

    @FXML
    private TextField chambre_description;

    @FXML
    private ComboBox<String> chambre_disponibilite;

    @FXML
    private ComboBox<Hotel> chambre_hotel_nom;

    @FXML
    private TextField chambre_prix;

    @FXML
    private ComboBox<String> chambre_type;

    @FXML
    private TableView<Chambre> TableView_chambre;

    @FXML
    private TableColumn<Chambre, String> TableView_chambre_description;

    @FXML
    private TableColumn<Chambre, Boolean> TableView_chambre_disponibilite;

    @FXML
    private TableColumn<Chambre, Void> TableView_chambre_modifier;

@FXML
    private Button button_logout;
    @FXML
    private TableColumn<Chambre, String > TableView_chambre_nom_hotel;

    @FXML
    private TableColumn<Chambre, Integer> TableView_chambre_prix;

    @FXML
    private TableColumn<Chambre, Void> TableView_chambre_supprimer;

    @FXML
    private TableColumn<Chambre, String> TableView_chambre_type;


    @FXML
    private TextField chambre_id;

    private ObservableList<Chambre> ChambreData = FXCollections.observableArrayList();




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






    @FXML
    private TableView<Client> TableView_Utilisateur;
    @FXML
    private TableColumn<Client, String> cl_user_mail;

    @FXML
    private TableColumn<Client, String> cl_user_nom;

    @FXML
    private TableColumn<Client, Integer> cl_user_num_tel;

    @FXML
    private TableColumn<Client, String> cl_user_photo;

    @FXML
    private TableColumn<Client, String> cl_user_prenom;

    @FXML
    private TableColumn<Client, Integer> cl_user_status;
    @FXML
    private TableColumn<Client, Void> cl_user_bloquer;
    @FXML
    private TableColumn<Client, Void> cl_user_debloquer;
    @FXML
    private TableColumn<Client, Void> cl_user_supprimer;

    private ObservableList<Client> userData_back = FXCollections.observableArrayList();









    //vehicule


    @FXML
    private TextField page_vehicule_cylindre;

    @FXML
    private TextField page_vehicule_image;

    @FXML
    private TextField page_vehicule_matricule;

    @FXML
    private TextField page_vehicule_nb_place;

    @FXML
    private TextField page_vehicule_prix;
    @FXML
    private TextField page_vehicule_status;

    @FXML
    private TableView<Vehicule> TableView_vehicule;

    @FXML
    private TableColumn<Voiture, Integer> TableView_vehicule_cylindre;

    @FXML
    private TableColumn<Vehicule, String> TableView_vehicule_matricule;

    @FXML
    private TableColumn<Vehicule, Void> TableView_vehicule_modifier;

    @FXML
    private TableColumn<Bus, Integer> TableView_vehicule_nb_place;

    @FXML
    private TableColumn<Vehicule, Integer> TableView_vehicule_prix;

    @FXML
    private TableColumn<Vehicule, String> TableView_vehicule_status;

    @FXML
    private TableColumn<Vehicule, Void> TableView_vehicule_supprimer;

    @FXML
    private TextField page_vehicule_id;

    @FXML
    private ComboBox<Vehicule> contrat_matricule;

    @FXML
    private TextField contrat_photo_permit;

    @FXML
    private TextField contrat_cin;
    @FXML
    private TextField contrat_date_debut;
    @FXML
    private TextField contrat_date_fin;

    private ObservableList<Vehicule> VehiculeData = FXCollections.observableArrayList();





    //tableView Contrat
    @FXML
    private TableView<Contrat> TableView_contrat;

    @FXML
    private TableColumn<Contrat, Integer> TableView_contrat_cin;

    @FXML
    private TableColumn<Contrat, String> TableView_contrat_debut;

    @FXML
    private TableColumn<Contrat,String> TableView_contrat_fin;

    @FXML
    private TableColumn<Contrat, String> TableView_contrat_matricule;

    @FXML
    private TableColumn<Contrat,Void> TableView_contrat_modifier;

    @FXML
    private TableColumn<Contrat,Void> TableView_contrat_supprimer;

    private ObservableList<Contrat> ContratData = FXCollections.observableArrayList();

    @FXML
    private TextField contrat_id2;

    @FXML
    private ImageView contrat_imageView;
    @FXML
    private ImageView hotel_imageView;

    @FXML
    private ImageView page_vehicule_image_View;

//FXML VOL
@FXML
private TableView<Vol> tableViewVols;

    @FXML
    private TableColumn<Vol, String> colDeparture, colDestination, colClasseChaise, colAirline, colDescription;

    @FXML
    private TableColumn<Vol, LocalDateTime> colDepartureTime, colArrivalTime;

    @FXML
    private TableColumn<Vol, Double> colFlightPrice;

    @FXML
    private TableColumn<Vol, Integer> colAvailableSeats;

    @FXML
    private TableColumn<Vol, Void> colModify, colDelete;

    @FXML
    private ComboBox<ClasseChaise> cbClasseChaise;

    @FXML
    private TextField tfDeparture, tfDestination, tfDepartureTime, tfArrivalTime, tfAirline, tfFlightPrice, tfAvailableSeats, tfDescription;

    @FXML
    private Button btnAddVol;




    private boolean isPasswordVisible = false;

    public void initialize() {
        startAnimation();
        chambre_type.setItems(FXCollections.observableArrayList(
                "Standard",
                "Deluxe",
                "Suite",
                "Executive Room"
        ));
        chambre_disponibilite.setItems(FXCollections.observableArrayList(
                "Disponible",
                "Non disponible"
        ));
        //tableView Hotel
        hotel_col_name.setCellValueFactory(new PropertyValueFactory<>("nom_hotel"));
        hotel_col_location.setCellValueFactory(new PropertyValueFactory<>("localisation_hotel"));
        hotel_col_phone.setCellValueFactory(new PropertyValueFactory<>("num_telephone_hotel"));
        hotel_col_email.setCellValueFactory(new PropertyValueFactory<>("email_hotel"));
        hotel_col_image.setCellValueFactory(new PropertyValueFactory<>("image_hotel"));
        hotel_col_description.setCellValueFactory(new PropertyValueFactory<>("description_hotel"));
        hotel_id.setVisible(false);
        hotel_col_modifier.setCellFactory(column -> {
            return new TableCell<Hotel, Void>() {
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
                        page_hotel.setVisible(false);
                        Hotel Hot = getTableView().getItems().get(getIndex());
                        animateTransition(background_front, page_ajouter_hotel, 0);
                        hotel_nom.setText(Hot.getNom_hotel());
                        hotel_localisation.setText(Hot.getLocalisation_hotel());
                        hotel_num_tel.setText(Hot.getNum_telephone_hotel());
                        hotel_email.setText(String.valueOf(Hot.getEmail_hotel()));
                        hotel_image.setText(Hot.getImage_hotel());
                        hotel_description.setText(Hot.getDescription_hotel());
                        HotelService HotelService = new HotelService();
                        hotel_id.setText(String.valueOf(HotelService.ChercherId(String.valueOf(Hot.getEmail_hotel()))));


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


        hotel_col_supprimer.setCellFactory(column -> {
            return new TableCell<Hotel, Void>() {
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
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de Suppression");
                        alert.setHeaderText("Voulez-vous vraiment vous supprimer ce hotel ?");
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            Hotel Hot = getTableView().getItems().get(getIndex());
                            HotelService HotelService = new HotelService();
                            hotel_id.setText(String.valueOf(HotelService.ChercherId(String.valueOf(Hot.getEmail_hotel()))));
                            Hotel H1 = new Hotel();
                            H1.setId_hotel(HotelService.ChercherId(String.valueOf(Hot.getEmail_hotel())));
                            HotelService.supprimer(H1);
                            RefreshTableView_Hotel();
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
        });


        HotelService hotelService = new HotelService();
        List<Hotel> hotelList = hotelService.rechercher(); // Fetch hotel list from DB
        ObservableList<Hotel> observableHotelList = FXCollections.observableArrayList(hotelList);

        TableView_Hotel.setItems(observableHotelList);






        setCircularImage("images/user_icon_001.jpg");

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















        loadComboBoxActivites(); // Load the activities into the ComboBox

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





        page_vehicule_id.setVisible(false);

        TableView_vehicule_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        TableView_vehicule_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableView_vehicule_nb_place.setCellValueFactory(new PropertyValueFactory<>("nbPlace"));
        TableView_vehicule_cylindre.setCellValueFactory(new PropertyValueFactory<>("cylinder"));
        TableView_vehicule_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        TableView_vehicule_modifier.setCellFactory(column -> {
            return new TableCell<Vehicule, Void>() {
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
                        Vehicule Vl = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de modifier");
                        alert.setHeaderText("Voulez-vous vraiment vous Modifer  ? " + Vl.getMatricule());
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            page_vehicule.setVisible(false);
                            page_ajouter_vehicule.setVisible(true);
                            page_vehicule_matricule.setText(Vl.getMatricule());
                            VehiculeService VLS = new VehiculeService();
                            Vehicule V = new Vehicule();
                            V = VLS.getVehiculeByMatricule(Vl.getMatricule());
                            // Bus B= new Bus();
                            // Voiture Ve= new Voiture();

                            page_vehicule_matricule.setText(V.getMatricule());
                            page_vehicule_status.setText(V.getStatus());
                            page_vehicule_prix.setText(String.valueOf(V.getPrix()));
                            page_vehicule_id.setText(String.valueOf(VLS.getIdVehiculeByMatricule(V.getMatricule())));
                            if (V instanceof Voiture) {
                                Voiture voiture = (Voiture) V; // Cast V to Voiture
                                page_vehicule_cylindre.setText(String.valueOf(voiture.getCylinder())); // Call getCylinder() method
                                page_vehicule_image.setText(String.valueOf(voiture.getImage_vehicule())); // Set image text to empty
                                page_vehicule_nb_place.setText("0"); // Call getCylinder() method

                            }
                            if (V instanceof Bus) {
                                Bus B = (Bus) V; // Cast V to Voiture
                                page_vehicule_nb_place.setText(String.valueOf(B.getNbPlace()));
                                page_vehicule_cylindre.setText(""); // Call getCylinder() method
                                page_vehicule_image.setText(""); // Set image text to empty
                            }
                            RefreshTableView_Vehicule();

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
        });
        page_vehicule_image.setVisible(false);

        TableView_vehicule_supprimer.setCellFactory(column -> {
            return new TableCell<Vehicule, Void>() {
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
                        Vehicule Vl = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de suppression");
                        alert.setHeaderText("Voulez-vous vraiment vous supprimer  ? " + Vl.getMatricule());
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            VehiculeService VL1 = new VehiculeService();
                            Vehicule V = new Vehicule();
                            V.setMatricule(Vl.getMatricule());
                            VL1.supprimer(V);
                            RefreshTableView_Vehicule();

                        }


                        RefreshTableView_Vehicule();

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
        VehiculeService VS = new VehiculeService();
        List<Vehicule> VehiculeList = VS.getAllVehicules(); // Fetch hotel list from DB
        ObservableList<Vehicule> observableVehiculeList = FXCollections.observableArrayList(VehiculeList);

        TableView_vehicule.setItems(observableVehiculeList);

        loadComboBoxVehicules();

        VehiculeService VehiculeService = new VehiculeService(); // Assuming service class

        List<Vehicule> vehiculeListe = VehiculeService.getAllVehicules();  // Fetch the List<Activite> from the rechercher method

        // Convert List<Activite> to ObservableList<Activite>
        ObservableList<Vehicule> vehicules = FXCollections.observableArrayList(vehiculeListe);

        // Set the items in the ComboBox
        contrat_matricule.setItems(vehicules);

        // Set the ComboBox to display just the names (or any other field) for user selection
        contrat_matricule.setCellFactory(param -> new ListCell<Vehicule>() {
            @Override
            protected void updateItem(Vehicule item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getMatricule());  // Display only the name of the activity
                }
            }
        });

        // Set the converter to handle the selected Activite
        contrat_matricule.setConverter(new StringConverter<Vehicule>() {
            @Override
            public String toString(Vehicule vehicule) {
                return vehicule == null ? null : vehicule.getMatricule();  // Display name when selected
            }

            @Override
            public Vehicule fromString(String string) {
                return null;  // Not necessary in this case, since we work with Activite objects directly
            }
        });







        ContratService ContratService = new ContratService();
        List<Contrat> ContratList = ContratService.getListContrats(); // Fetch hotel list from DB
        ObservableList<Contrat> observableContratList = FXCollections.observableArrayList(ContratList);

        TableView_contrat.setItems(observableContratList);


        //tableView

        contrat_id2.setVisible(false);


        TableView_contrat_debut.setCellValueFactory(new PropertyValueFactory<>("dateD"));
        TableView_contrat_fin.setCellValueFactory(new PropertyValueFactory<>("dateF"));
        TableView_contrat_cin.setCellValueFactory(new PropertyValueFactory<>("cinLocateur"));
        TableView_contrat_matricule.setCellValueFactory(new PropertyValueFactory<>("nomVehicule"));

        TableView_contrat_supprimer.setCellFactory(column -> {
            return new TableCell<Contrat, Void>() {
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
                        Contrat ContratSelectionnee = getTableView().getItems().get(getIndex());


                        if (ContratSelectionnee == null) {
                            showAlert("Erreur", "Impossible de récupérer l'contrat sélectionnée.", Alert.AlertType.ERROR);
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
                            ContratService  ContratService = new ContratService();
                            ContratService.supprimer(ContratSelectionnee);

                            showAlert("Confirmation", "La session a été supprimée avec succès", Alert.AlertType.INFORMATION);

                            // Mettre à jour la TableView
                            getTableView().getItems().remove(ContratSelectionnee);
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
        });
        TableView_contrat_modifier.setCellFactory(column -> {
            return new TableCell<Contrat, Void>() {
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
                        Contrat Contrat = getTableView().getItems().get(getIndex());






                        contrat_cin.setText(String.valueOf(Contrat.getCinLocateur()));
                        contrat_date_debut.setText(String.valueOf(Contrat.getDateD()));
                        contrat_date_fin.setText(String.valueOf(Contrat.getDateF()));
                        contrat_photo_permit.setText(String.valueOf(Contrat.getPhotoPermit()));

                        // contrat_matricule.setItems(Contrat.getNomVehicule());
                        Contrat C= new Contrat(String.valueOf(Contrat.getDateD()),String.valueOf(Contrat.getDateF()),Integer.valueOf(String.valueOf(Contrat.getCinLocateur())),String.valueOf(Contrat.getPhotoPermit()));
                        ContratService ContratService = new ContratService();
                        contrat_id2.setText(String.valueOf(ContratService.chercher_id(C)));
                        contrat_id2.setVisible(false);
                        page_contrat.setVisible(false);
                        page_ajouter_contrat.setVisible(true);
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

        RefreshTableView_Contrat();








        //tableView USER Back
        cl_user_nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        cl_user_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cl_user_mail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cl_user_num_tel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        cl_user_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        cl_user_bloquer.setCellFactory(column -> {
            return new TableCell<Client, Void>() {
                private final Button btn = new Button("Bloquer");


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
                        Client Cl = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de Bloquer");
                        alert.setHeaderText("Voulez-vous vraiment vous Bloquer  ? " + Cl.getName() + " " + Cl.getPrenom());
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            ClientServices CL1 = new ClientServices();
                            CL1.bloquer_client(CL1.getUserIdByEmail(Cl.getEmail()));
                            RefreshTableView_User();

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
        });


        cl_user_debloquer.setCellFactory(column -> {
            return new TableCell<Client, Void>() {
                private final Button btn = new Button("Debloquer");


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
                        Client Cl = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de debloquer");
                        alert.setHeaderText("Voulez-vous vraiment vous debloquer  ? " + Cl.getName() + " " + Cl.getPrenom());
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            ClientServices CL1 = new ClientServices();
                            CL1.debloquer_client(CL1.getUserIdByEmail(Cl.getEmail()));
                            RefreshTableView_User();

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
        });

        cl_user_supprimer.setCellFactory(column -> {
            return new TableCell<Client, Void>() {
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
                        Client Cl = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de Suppression");
                        alert.setHeaderText("Voulez-vous vraiment vous supprimer  ? " + Cl.getName() + " " + Cl.getPrenom());
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            ClientServices ClientService = new ClientServices();
                            Client C1 = new Client(ClientService.getUserIdByEmail(Cl.getEmail()), "", "", "", "", 0, 0, 0, "");
                            ClientService.supprimerUser(C1);
                            RefreshTableView_User();
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
        });


        ClientServices ClS = new ClientServices();
        List<Client> ClientList = ClS.rechercherUser();
        ObservableList<Client> observableClientList = FXCollections.observableArrayList(ClientList);

        TableView_Utilisateur.setItems(observableClientList);





        //TableView chambre
        chambre_id.setVisible(false);


        loadComboBox_hotel_nom() ;
        HotelService hotelService1 = new HotelService();
        List<Hotel> hotelList1 = hotelService1.rechercher();
        ChambreService  ChambreService= new ChambreService();
        List<Chambre> ChambreList = ChambreService.rechercher(); // Fetch hotel list from DB
        ObservableList<Chambre> observableChambreList = FXCollections.observableArrayList(ChambreList);
        TableView_chambre.setItems(observableChambreList);


        //tableView Chambre
        TableView_chambre_type.setCellValueFactory(new PropertyValueFactory<>("type_Chambre"));
        TableView_chambre_prix.setCellValueFactory(new PropertyValueFactory<>("prix_Chambre"));
        TableView_chambre_disponibilite.setCellValueFactory(new PropertyValueFactory<>("disponibilite_Chambre"));
        TableView_chambre_description.setCellValueFactory(new PropertyValueFactory<>("description_Chambre"));
        TableView_chambre_nom_hotel.setCellValueFactory(new PropertyValueFactory<>("nom_hotel_chambre"));
        TableView_chambre_supprimer.setCellFactory(column -> {
            return new TableCell<Chambre, Void>() {
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
                        Chambre Cl = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de suppression");
                        alert.setHeaderText("Voulez-vous vraiment vous supprimer  ? ");
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            ChambreService CL1 = new ChambreService();
                            HotelService hotelService1 = new HotelService();
                            Chambre C = new Chambre(
                                    String.valueOf(Cl.getType_Chambre()), // String
                                    Integer.parseInt(String.valueOf(Cl.getPrix_Chambre())), // Convert String to int
                                    Boolean.parseBoolean(String.valueOf(Cl.getDisponibilite_Chambre())), // Convert String to boolean
                                    String.valueOf(Cl.getDescription_Chambre()), // String
                                    hotelService1.getIdHotelBynom(String.valueOf(Cl.getNom_hotel_chambre())) // Get hotel ID from name
                            );
                            CL1.supprimer(C);
                            RefreshTableView_Chambre();

                        }


                        RefreshTableView_Chambre();

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
        TableView_chambre_modifier.setCellFactory(column -> {
            return new TableCell<Chambre, Void>() {
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
                        HotelService Hot=  new HotelService();
                        page_chambre.setVisible(false);
                        Chambre CH = getTableView().getItems().get(getIndex());
                        animateTransition(background_front, page_ajouter_chambre, 0);
                        chambre_type.setValue(CH.getType_Chambre());
                        chambre_prix.setText(String.valueOf(CH.getPrix_Chambre()));
                        chambre_disponibilite.setValue(String.valueOf(CH.getDisponibilite_Chambre()));
                        chambre_description.setText(CH.getDescription_Chambre());
                        // chambre_hotel_nom.setValue(String.valueOf(CH.getNom_hotel_chambre()));

                        ChambreService ChambreService = new ChambreService();


                        Chambre C = new Chambre(
                                String.valueOf(CH.getType_Chambre()), // String
                                Integer.parseInt(String.valueOf(CH.getPrix_Chambre())), // Convert String to int
                                Boolean.parseBoolean(String.valueOf(CH.getDisponibilite_Chambre())), // Convert String to boolean
                                String.valueOf(CH.getDescription_Chambre()), // String
                                hotelService1.getIdHotelBynom(String.valueOf(CH.getNom_hotel_chambre())) // Get hotel ID from name
                        );
                        chambre_id.setText(String.valueOf(ChambreService.chercher_id(CH)));;
                        page_ajouter_chambre.setVisible(true);
                        chambre_id.setVisible(false);
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
        RefreshTableView_Chambre();


        //image

        contrat_imageView.setOnMouseClicked(this::handleImageClick_contrat);

        contrat_photo_permit.setVisible(false);

        hotel_imageView.setOnMouseClicked(this::handleImageClick_hotel);
        hotel_image.setVisible(false);

//vehicule
        //image

        page_vehicule_image_View.setOnMouseClicked(this::handleImageClick_vehicule);



//Hotel
       /* cbClasseChaise.setItems(FXCollections.observableArrayList(
                "ECONOMY",
                "PREMIUM_ECONOMY",
                "BUSINESS",
                "FIRST_CLASS"
        ));*/



        cbClasseChaise.setItems(FXCollections.observableArrayList(ClasseChaise.values()));

        // Bind columns to properties
        colDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDepartureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        colArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        colClasseChaise.setCellValueFactory(new PropertyValueFactory<>("classeChaise"));
        colAirline.setCellValueFactory(new PropertyValueFactory<>("airline"));
        colFlightPrice.setCellValueFactory(new PropertyValueFactory<>("flightPrice"));
        colAvailableSeats.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Modify Button
        colModify.setCellFactory(param -> new TableCell<Vol, Void>() {
            private final Button modifyButton = new Button("Modifier");

            {
                modifyButton.setOnAction(event -> {
                    Vol vol = getTableView().getItems().get(getIndex());
                    editVol(vol);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : modifyButton);
            }
        });

        // Delete Button
        colDelete.setCellFactory(param -> new TableCell<Vol, Void>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Vol vol = getTableView().getItems().get(getIndex());
                    deleteVol(vol);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        refreshTable();

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
    private void handleImageClick_contrat(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            contrat_imageView.setImage(image);
            contrat_photo_permit.setText(image.getUrl());
        }
    }

    private void handleImageClick_hotel (MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            hotel_imageView.setImage(image);
            hotel_image.setText(image.getUrl());
        }
    }
    private void handleImageClick_vehicule (MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            page_vehicule_image_View.setImage(image);
            page_vehicule_image.setText(image.getUrl());
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
        ClientServices c=new ClientServices();
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
        if (c.userExists(email)) {
            setFieldError(emailField, true);
            showAlert("Erreur", "Cet email est déjà utilisé.", Alert.AlertType.ERROR);
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
            //NEW
            EmailService.sendWelcomeEmail(email);
            //
            showAlert("Confirmation", "Votre compte a été créé", Alert.AlertType.INFORMATION);
            switchToSignIn();
            RefreshTableView_User();
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
        photo_profile_signin.setImage(new Image("images/user_icon_001.jpg"));

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
        Client client = C1.verifierUser(email_field_signin.getText());

        if (client == null) {
            showAlert("Erreur", "Email ou mot de passe incorrect.", Alert.AlertType.ERROR);
        } else if (client.getStatus() != 0) {
            showAlert("Compte bloqué", "Votre compte est banni.", Alert.AlertType.ERROR);
        } else {
            switch (client.getRole()) {
                case 0:
                case 1:
                    showAlert("Connexion réussie", "Bienvenue" + " " + client.getPrenom() + " " + client.getName(), Alert.AlertType.INFORMATION);
                    animateTransition(Se_connecter_page, background_back, 0);
                    break;
                case 2:
                    showAlert("Bienvenue", "Bonjour" + " " + client.getPrenom() + " " + client.getName(), Alert.AlertType.INFORMATION);
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
            Client client = C1.verifierUser(email_field_signin.getText());
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
    private void LogOut_back(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de déconnexion");
        alert.setHeaderText("Voulez-vous vraiment vous déconnecter ?");
        alert.setContentText("Cliquez sur OK pour confirmer.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            text_field_mdp.clear();
            password_field_signin.clear();
            email_field_signin.clear();
            animateTransition(background_back, Se_connecter_page, 0);
            background_back.setVisible(false);
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
            //RefreshTableView_User();
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

        } else if (actionEvent.getSource() == bt_menu_vol) {
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

        } else if (actionEvent.getSource() == bt_menu_location) {
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

        } else if (actionEvent.getSource() == bt_menu_forum) {
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

        } else if (actionEvent.getSource() == page_hotel_bt_go_to_hotel) {


            page_acceuil_gestion_hotel.setVisible(false);
            page_hotel.setVisible(true);

        } else if (actionEvent.getSource() == page_hotel_bt_retour) {


            page_hotel.setVisible(false);
            page_acceuil_gestion_hotel.setVisible(true);
        } else if (actionEvent.getSource() == page_hotel_bt_ajouter) {


            page_hotel.setVisible(false);
            page_ajouter_hotel.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_hotel_return) {


            page_ajouter_hotel.setVisible(false);
            page_hotel.setVisible(true);
        } else if (actionEvent.getSource() == page_acceuil_hotel_go_to_chambre) {


            page_acceuil_gestion_hotel.setVisible(false);
            page_chambre.setVisible(true);
        } else if (actionEvent.getSource() == page_chambre_retour) {


            page_chambre.setVisible(false);
            page_acceuil_gestion_hotel.setVisible(true);
        } else if (actionEvent.getSource() == page_chambre_bt_ajouter) {


            page_chambre.setVisible(false);
            page_ajouter_chambre.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_chambre_retour) {


            page_ajouter_chambre.setVisible(false);
            page_chambre.setVisible(true);
        } else if (actionEvent.getSource() == page_acceuil_gestion_circuit_go_to_activite) {


            page_acceuil_gestion_circuit.setVisible(false);
            page_activite.setVisible(true);
        } else if (actionEvent.getSource() == page_acceuil_gestion_circuit_go_to_session) {


            page_acceuil_gestion_circuit.setVisible(false);
            page_session.setVisible(true);
        } else if (actionEvent.getSource() == page_activite_retour) {


            page_activite.setVisible(false);
            page_acceuil_gestion_circuit.setVisible(true);
        } else if (actionEvent.getSource() == page_activite_bt_ajouter) {
            RefreshTableView_Session();
            activite_bt_modifier.setVisible(false);
            activite_bt_ajouter.setVisible(true);
            page_activite.setVisible(false);
            page_ajouter_activite.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_activiter_retour) {
            RefreshTableView_Session();
            nom_activite.setText("");
            description_activite.setText("");
            localisation_activite.setText("");
            prix_activite.setText("");
            type_activite.setText("");
            page_ajouter_activite.setVisible(false);
            page_activite.setVisible(true);
        } else if (actionEvent.getSource() == page_session_retour) {


            page_session.setVisible(false);
            page_acceuil_gestion_circuit.setVisible(true);
        } else if (actionEvent.getSource() == page_session_bt_ajouter) {
            RefreshTableView_Session();
            loadComboBoxActivites();
            sessio_bt_modifier.setVisible(false);
            session_bt_ajouter.setVisible(true);

            page_session.setVisible(false);
            page_ajouter_session.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_session_retour) {

            RefreshTableView_Session();
            RefreshTableView_Activite();
            time_sess.setText("");
            sess_cap.setText("");
            sess_nbr_places.setText("");
            date_sess.setValue(null);
            combo_activite.getSelectionModel().clearSelection();
            page_ajouter_session.setVisible(false);
            page_session.setVisible(true);
        } else if (actionEvent.getSource() == page_acceuil_location_go_to_vehicule) {


            page_acceuil_gestion_location.setVisible(false);
            page_vehicule.setVisible(true);
        } else if (actionEvent.getSource() == page_acceuil_location_go_to_contrat) {


            page_acceuil_gestion_location.setVisible(false);
            page_contrat.setVisible(true);
        } else if (actionEvent.getSource() == page_vehicule_retour) {


            page_vehicule.setVisible(false);
            page_acceuil_gestion_location.setVisible(true);
        } else if (actionEvent.getSource() == page_vehicule_bt_ajouter) {


            page_vehicule.setVisible(false);
            page_ajouter_vehicule.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_vehicule_retour) {


            page_ajouter_vehicule.setVisible(false);
            page_vehicule.setVisible(true);
        } else if (actionEvent.getSource() == page_contrat_retour) {


            page_contrat.setVisible(false);
            page_acceuil_gestion_location.setVisible(true);
        } else if (actionEvent.getSource() == page_contrat_bt_ajouter) {


            page_contrat.setVisible(false);
            page_ajouter_contrat.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_contrat_retour) {


            page_ajouter_contrat.setVisible(false);
            page_contrat.setVisible(true);
        } else if (actionEvent.getSource() == gestion_acceuil_bt_go_to_vol) {


            page_acceuil_gestion_vol.setVisible(false);
            page_vol.setVisible(true);
        } else if (actionEvent.getSource() == gestion_acceuil_bt_go_to_checkout) {


            page_acceuil_gestion_vol.setVisible(false);
            page_checkout.setVisible(true);
        } else if (actionEvent.getSource() == page_vol_retour) {


            page_vol.setVisible(false);
            page_acceuil_gestion_vol.setVisible(true);
        } else if (actionEvent.getSource() == page_vol_bt_ajouter) {


            page_vol.setVisible(false);
            page_ajouter_vol.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_vol_retour) {


            page_ajouter_vol.setVisible(false);
            page_vol.setVisible(true);
        } else if (actionEvent.getSource() == page_checkout_retour) {


            page_checkout.setVisible(false);
            page_acceuil_gestion_vol.setVisible(true);
        } else if (actionEvent.getSource() == page_checkout_bt_ajouter) {


            page_checkout.setVisible(false);
            page_ajouter_checkout.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_checkout_retour) {


            page_ajouter_checkout.setVisible(false);
            page_checkout.setVisible(true);
        } else if (actionEvent.getSource() == page_acceuil_forum_bt_forum) {


            page_acceuil_gestion_forum.setVisible(false);
            page_forum.setVisible(true);
        } else if (actionEvent.getSource() == page_acceuil_forum_go_to_post) {


            page_acceuil_gestion_forum.setVisible(false);
            page_post.setVisible(true);
        } else if (actionEvent.getSource() == page_forum_retour) {


            page_forum.setVisible(false);
            page_acceuil_gestion_forum.setVisible(true);
        } else if (actionEvent.getSource() == page_forum_bt_ajouter) {


            page_forum.setVisible(false);
            page_ajouter_forum.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_forum_retour) {


            page_ajouter_forum.setVisible(false);
            page_forum.setVisible(true);
        } else if (actionEvent.getSource() == page_post_retour) {


            page_post.setVisible(false);
            page_acceuil_gestion_forum.setVisible(true);
        } else if (actionEvent.getSource() == page_post_bt_ajouter) {


            page_post.setVisible(false);
            page_ajouter_post.setVisible(true);
        } else if (actionEvent.getSource() == page_ajouter_post_retour) {


            page_ajouter_post.setVisible(false);
            page_post.setVisible(true);
        } else if (actionEvent.getSource() == page_utilisateur_retour) {


            page_utilisateur.setVisible(false);
            page_dashboard.setVisible(true);
        }
    }
    @FXML
    private void AddHotel(ActionEvent event) throws IOException {
        // Vérification des champs vides
        if (hotel_nom.getText().trim().isEmpty() ||
                hotel_localisation.getText().trim().isEmpty() ||
                hotel_num_tel.getText().trim().isEmpty() ||
                hotel_email.getText().trim().isEmpty() ||
                hotel_description.getText().trim().isEmpty() ||
                hotel_image.getText().trim().isEmpty()) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        // Vérification du format de l'email
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!hotel_email.getText().matches(emailRegex)) {
            showAlert("Erreur", "Veuillez entrer un email valide", Alert.AlertType.ERROR);
            return;
        }

        // Vérification du numéro de téléphone
        String phoneText = hotel_num_tel.getText().trim();
        if (!phoneText.matches("\\d{8}")) {  // Vérifie que le numéro contient exactement 8 chiffres
            showAlert("Erreur", "Le numéro de téléphone doit être composé de 8 chiffres", Alert.AlertType.ERROR);
            return;
        }


        // Si toutes les validations sont passées
        HotelService H = new HotelService();
        H.ajouter(new Hotel(
                hotel_nom.getText().trim(),
                hotel_localisation.getText().trim(),
                hotel_num_tel.getText().trim(),
                hotel_email.getText().trim(),
                hotel_image.getText().trim(),
                hotel_description.getText().trim()
        ));

        loadComboBox_hotel_nom();
        showAlert("Confirmation", "L'hôtel a été ajouté avec succès", Alert.AlertType.INFORMATION);

        hotel_nom.clear();
        hotel_localisation.clear();
        hotel_num_tel.clear();
        hotel_email.clear();
        hotel_image.clear();
        hotel_description.clear();
        hotel_imageView.setImage(null);

        RefreshTableView_Hotel();
        page_ajouter_hotel.setVisible(false);
        page_hotel.setVisible(true);
    }
    @FXML
    private void UPDATEHotel(ActionEvent event) throws IOException {
        if (hotel_nom.getText().isEmpty() || hotel_num_tel.getText().isEmpty() ||
                hotel_email.getText().isEmpty() || hotel_description.getText().isEmpty()) {

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
        H.modifier(new Hotel(Integer.parseInt(hotel_id.getText()), hotel_nom.getText(), hotel_localisation.getText(), hotel_num_tel.getText(), hotel_email.getText(), hotel_image.getText(), hotel_description.getText()));

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



        @FXML
        private void SwitchToMonProfile(ActionEvent event){
            background_mon_profil.setVisible(true);
            background_front_offres.setVisible(false);
            background_front_forum.setVisible(false);
            background_front_acceuil.setVisible(false);
        }
        @FXML
        private void SwitchToOffres(ActionEvent event){
        background_mon_profil.setVisible(false);
        background_front_offres.setVisible(true);
        background_front_forum.setVisible(false);
        background_front_acceuil.setVisible(false);
    }
        @FXML
        private void SwitchToForum(ActionEvent event) {
            background_mon_profil.setVisible(false);
            background_front_offres.setVisible(false);
            background_front_forum.setVisible(true);
            background_front_acceuil.setVisible(false);
        }
        @FXML
        private void SwitchToAcceuil(ActionEvent event){
        background_mon_profil.setVisible(false);
        background_front_offres.setVisible(false);
        background_front_forum.setVisible(false);
        background_front_acceuil.setVisible(true);}









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
        if (time_sess.getText().isEmpty() ||
                sess_cap.getText().isEmpty() ||
                sess_nbr_places.getText().isEmpty() ||
                date_sess.getValue() == null ||
                combo_activite.getSelectionModel().getSelectedItem() == null) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        LocalTime time;
        try {
            time = LocalTime.parse(time_sess.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            showAlert("Erreur", "Le format de l'heure est invalide. Utilisez HH:mm", Alert.AlertType.ERROR);
            return;
        }

        LocalDate date = date_sess.getValue();

        int cap, nbrPlaces;
        try {
            cap = Integer.parseInt(sess_cap.getText());
            nbrPlaces = Integer.parseInt(sess_nbr_places.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le nombre de places et la capacité doivent être des entiers valides", Alert.AlertType.ERROR);
            return;
        }

        String nomAct = combo_activite.getSelectionModel().getSelectedItem().getNom_act();

        ActiviteService2 activiteService = new ActiviteService2();
        int idAct = activiteService.getIdByName(nomAct);

        if (idAct == -1) {
            showAlert("Erreur", "L'ID de l'activité sélectionnée n'existe pas dans la base de données.", Alert.AlertType.ERROR);
            return;
        }

        Session newSession = new Session(date, time, cap, nbrPlaces, idAct);

        SessionService sessionService = new SessionService();
        sessionService.ajouter(newSession);

        showAlert("Confirmation", "La session a été ajoutée avec succès", Alert.AlertType.INFORMATION);

        time_sess.setText("");
        sess_cap.setText("");
        sess_nbr_places.setText("");
        date_sess.setValue(null);
        combo_activite.getSelectionModel().clearSelection();
        page_ajouter_session.setVisible(false);
        page_session.setVisible(true);
        RefreshTableView_Session();
    }





    private void loadComboBoxActivites() {
        ActiviteService2 activiteService = new ActiviteService2();
        List<Activite> activites = activiteService.rechercher(); // Fetch activities from DB
        RefreshTableView_Session();
        if (activites == null || activites.isEmpty()) {
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







    @FXML
    private void RefreshTableView_Vehicule() {
        try {
            VehiculeService VehiculeService = new VehiculeService();
            List<Vehicule> V = VehiculeService.getAllVehicules(); // Fetch hotels from DB

            if (V != null) {
                // Clear and update ObservableList
                VehiculeData.clear();
                VehiculeData.addAll(V);

                // ✅ Manually adding a hotel with dummy data
                //Hotel manualHotel = new Hotel(999, "Test Hotel", "Test Location", "123456789", "test@email.com", "test.jpg", "This is a test hotel");
                // HotelData.add(manualHotel);


                TableView_vehicule.setItems(VehiculeData);
                TableView_vehicule.refresh(); // Force update

            } else {
                showAlert("Information", "Aucun User trouvé.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour de la TableView.", Alert.AlertType.ERROR);
            e.printStackTrace();

        }
    }

    @FXML
    private void RefreshTableView_Contrat() {
        try {
            ContratService ContratService = new ContratService();
            List<Contrat> contrats = ContratService.getListContrats(); // Fetch hotels from DB

            if (contrats != null) {
                // Clear and update ObservableList
                ContratData.clear();
                ContratData.addAll(contrats);

                // ✅ Manually adding a hotel with dummy data
                //Hotel manualHotel = new Hotel(999, "Test Hotel", "Test Location", "123456789", "test@email.com", "test.jpg", "This is a test hotel");
                // HotelData.add(manualHotel);


                TableView_contrat.setItems(ContratData);
                TableView_contrat.refresh(); // Force update
            } else {
                showAlert("Information", "Aucun Contrat trouvé.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour de la TableView.", Alert.AlertType.ERROR);
            e.printStackTrace();

        }

    }


    @FXML
    private void AddContrat(ActionEvent event) {
        if (contrat_cin.getText().trim().isEmpty() ||
                contrat_photo_permit.getText().trim().isEmpty() ||
                contrat_date_debut.getText().trim().isEmpty() ||
                contrat_date_fin.getText().trim().isEmpty() ||
                contrat_matricule.getSelectionModel().getSelectedItem() == null) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        String cinText = contrat_cin.getText().trim();
        if (!cinText.matches("\\d{8}")) {
            showAlert("Erreur", "Le CIN doit être composé de 8 chiffres", Alert.AlertType.ERROR);
            return;
        }



        // Vérification du format et de la logique des dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateDebut, dateFin;

        try {
            dateDebut = LocalDate.parse(contrat_date_debut.getText().trim(), formatter);
            dateFin = LocalDate.parse(contrat_date_fin.getText().trim(), formatter);

            if (dateDebut.isAfter(dateFin)) {
                showAlert("Erreur", "La date de début doit être avant la date de fin", Alert.AlertType.ERROR);
                return;
            }
        } catch (DateTimeParseException e) {
            showAlert("Erreur", "Les dates doivent être au format yyyy/MM/dd", Alert.AlertType.ERROR);
            return;
        }

        VehiculeService vehiculeService = new VehiculeService();
        int id = vehiculeService.getIdVehiculeByMatricule(contrat_matricule.getSelectionModel().getSelectedItem().getMatricule());

        Contrat C = new Contrat(
                Integer.parseInt(cinText),
                contrat_date_debut.getText().trim(),
                contrat_date_fin.getText().trim(),
                contrat_photo_permit.getText().trim(),
                id
        );

        ContratService contratService = new ContratService();
        contratService.ajouter(C);

        // Affichage de confirmation
        showAlert("Confirmation", "Le contrat a été ajouté avec succès", Alert.AlertType.INFORMATION);

        // Réinitialisation des champs
        contrat_cin.clear();
        contrat_photo_permit.clear();
        contrat_date_debut.clear();
        contrat_date_fin.clear();
        contrat_imageView.setImage(null);
        contrat_matricule.getSelectionModel().clearSelection();
        RefreshTableView_Contrat();
        page_ajouter_contrat.setVisible(false);
        page_contrat.setVisible(true);
    }


    @FXML
    private void AddVehicule(ActionEvent event) throws IOException {
        if (page_vehicule_matricule.getText().isEmpty() || page_vehicule_cylindre.getText().isEmpty() ||
                page_vehicule_image.getText().isEmpty() || page_vehicule_nb_place.getText().isEmpty() ||
                page_vehicule_prix.getText().isEmpty() || page_vehicule_status.getText().isEmpty()) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        if (!page_vehicule_matricule.getText().matches("^\\d{3}TUN\\d{1,4}$")) {
            showAlert("Erreur", "Veuillez entrer un matricule valide (ex: 123TUN1, 456TUN12, 456TUN123, 789TUN1234)", Alert.AlertType.ERROR);
            return;
        }

// Vérification du format du cylindre (uniquement des chiffres)
        if (!page_vehicule_cylindre.getText().matches("^\\d+$")) {
            showAlert("Erreur", "Le cylindre doit contenir uniquement des chiffres", Alert.AlertType.ERROR);
            return;
        }
        // Vérification du format du cylindre (uniquement des chiffres)
        if (!page_vehicule_nb_place.getText().matches("^\\d+$")) {
            showAlert("Erreur", "Le cylindre doit contenir uniquement des chiffres", Alert.AlertType.ERROR);
            return;
        }
        // Vérification du format du cylindre (uniquement des chiffres)
        if (!page_vehicule_prix.getText().matches("^\\d+$")) {
            showAlert("Erreur", "Le cylindre doit contenir uniquement des chiffres", Alert.AlertType.ERROR);
            return;
        }
        VehiculeService V = new VehiculeService();

        if (Integer.parseInt(page_vehicule_cylindre.getText()) == 0) {
            V.ajouter(new Bus(page_vehicule_matricule.getText(), page_vehicule_status.getText(), Integer.parseInt(page_vehicule_prix.getText()), Integer.parseInt(page_vehicule_nb_place.getText())));
        } else {
            // Si toutes les vérifications passent
            V.ajouter(new Voiture(page_vehicule_matricule.getText(), page_vehicule_status.getText(), Integer.parseInt(page_vehicule_prix.getText()), Integer.parseInt(page_vehicule_cylindre.getText()), page_vehicule_image.getText()));
        }
        // Affichage de l'alerte de confirmation
        showAlert("Confirmation", "La vehicule a été ajouté", Alert.AlertType.INFORMATION);
        page_vehicule_matricule.setText("");
        page_vehicule_status.setText("");
        page_vehicule_prix.setText("");
        page_vehicule_cylindre.setText("");
        page_vehicule_nb_place.setText("");
        page_vehicule_image.setText("");

        RefreshTableView_Vehicule();
        ;
        page_ajouter_vehicule.setVisible(false);
        page_vehicule.setVisible(true);

    }

    @FXML
    private void UPDATEVoiture(ActionEvent event) throws IOException {
        if (page_vehicule_matricule.getText().isEmpty() || page_vehicule_cylindre.getText().isEmpty() ||
                page_vehicule_image.getText().isEmpty() || page_vehicule_nb_place.getText().isEmpty() ||
                page_vehicule_prix.getText().isEmpty() || page_vehicule_status.getText().isEmpty()) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        if (!page_vehicule_matricule.getText().matches("^\\d{3}TUN\\d{1,4}$")) {
            showAlert("Erreur", "Veuillez entrer un matricule valide (ex: 123TUN1, 456TUN12,456TUN123, 789TUN1234)", Alert.AlertType.ERROR);
            return;
        }
// Vérification du format du cylindre (uniquement des chiffres)
        if (!page_vehicule_cylindre.getText().matches("^\\d+$")) {
            showAlert("Erreur", "Le cylindre doit contenir uniquement des chiffres", Alert.AlertType.ERROR);
            return;
        }
        // Vérification du format du cylindre (uniquement des chiffres)
        if (!page_vehicule_nb_place.getText().matches("^\\d+$")) {
            showAlert("Erreur", "Le cylindre doit contenir uniquement des chiffres", Alert.AlertType.ERROR);
            return;
        }
        // Vérification du format du cylindre (uniquement des chiffres)
        if (!page_vehicule_prix.getText().matches("^\\d+$")) {
            showAlert("Erreur", "Le cylindre doit contenir uniquement des chiffres", Alert.AlertType.ERROR);
            return;
        }
        VehiculeService V = new VehiculeService();

        if (Integer.parseInt(page_vehicule_cylindre.getText()) == 0) {
            V.modifier(new Bus(Integer.parseInt(page_vehicule_id.getText()), page_vehicule_matricule.getText(), page_vehicule_status.getText(), Integer.parseInt(page_vehicule_prix.getText()), Integer.parseInt(page_vehicule_nb_place.getText())));
        } else {
            // Si toutes les vérifications passent
            V.modifier(new Voiture(Integer.parseInt(page_vehicule_id.getText()), page_vehicule_matricule.getText(), page_vehicule_status.getText(), Integer.parseInt(page_vehicule_prix.getText()), Integer.parseInt(page_vehicule_cylindre.getText()), page_vehicule_image.getText()));
        }
        // Affichage de l'alerte de confirmation
        showAlert("Confirmation", "La vehicule a été modifié", Alert.AlertType.INFORMATION);
        page_vehicule_matricule.setText("");
        page_vehicule_status.setText("");
        page_vehicule_prix.setText("");
        page_vehicule_cylindre.setText("");
        page_vehicule_nb_place.setText("");
        page_vehicule_image.setText("");

        RefreshTableView_Vehicule();
        ;
        page_ajouter_vehicule.setVisible(false);
        page_vehicule.setVisible(true);
    }
    @FXML
    private void UpdateContrat(ActionEvent event) {
        // Check if all fields are filled
        if (contrat_cin.getText().isEmpty() ||
                contrat_photo_permit.getText().isEmpty() ||
                contrat_date_fin.getText().isEmpty()  ||
                contrat_date_debut.getText().isEmpty()  ||
                contrat_matricule.getSelectionModel().getSelectedItem() == null) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }




        // Fetch vehicle ID from database
        VehiculeService vehiculeService = new VehiculeService();
        int id = vehiculeService.getIdVehiculeByMatricule(String.valueOf(contrat_matricule.getSelectionModel().getSelectedItem().getMatricule()));
        // Creating an instance of Contrat with String dates
        Contrat C = new Contrat(Integer.parseInt(contrat_id2.getText()),
                Integer.parseInt(contrat_cin.getText()),
                String.valueOf(contrat_date_debut.getText()),   // String date in yyyy/MM/dd format
                String.valueOf(contrat_date_fin.getText()),     // String date in yyyy/MM/dd format
                String.valueOf(contrat_photo_permit.getText()),
                id
        );
        // Call the service method to add the contract
        ContratService contratService = new ContratService();
        contratService.modifier(C);

        // Show success message after the contract is added
        showAlert("Confirmation", "Le contrat a été modifié avec succès", Alert.AlertType.INFORMATION);

        // Reset fields after successful addition
        contrat_cin.clear();
        contrat_photo_permit.clear();
        contrat_date_debut.clear();
        contrat_date_fin.clear();
        contrat_matricule.getSelectionModel().clearSelection();
        page_ajouter_contrat.setVisible(false);
        page_contrat.setVisible(true);
        RefreshTableView_Contrat();
    }

    @FXML
    private void AddChambre(ActionEvent event) {

        if (chambre_prix.getText().isEmpty() ||
                chambre_description.getText().isEmpty() ||
                chambre_hotel_nom.getSelectionModel().getSelectedItem() == null
                ||
                chambre_disponibilite.getSelectionModel().getSelectedItem() == null
                ||
                chambre_type.getSelectionModel().getSelectedItem() == null) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }




        // Fetch vehicle ID from database
        ChambreService ChambreService = new ChambreService();
        HotelService HS=new HotelService();

        Hotel selectedHotel = chambre_hotel_nom.getSelectionModel().getSelectedItem();
        int id = HS.getIdHotelBynom(selectedHotel.getNom_hotel());  // Pass only the hotel name


        // Creating an instance of Contrat with String dates
        Chambre C = new Chambre(
                chambre_type.getSelectionModel().getSelectedItem(),
                Integer.parseInt(chambre_prix.getText()),
                Boolean.parseBoolean(chambre_disponibilite.getSelectionModel().getSelectedItem()),
                chambre_description.getText(),
                id
        );

        // Call the service method to add the contract
        ChambreService chambreService1= new ChambreService();
        chambreService1.ajouter(C);

        // Show success message after the contract is added
        showAlert("Confirmation", "Le Chambre a été ajouté avec succès", Alert.AlertType.INFORMATION);

        // Reset fields after successful addition
        chambre_prix.clear();
        chambre_type.getSelectionModel().clearSelection();
        chambre_disponibilite.getSelectionModel().clearSelection();
        chambre_hotel_nom.getSelectionModel().clearSelection();
        chambre_description.clear();
        page_ajouter_chambre.setVisible(false);
        page_chambre.setVisible(true);
        RefreshTableView_Chambre();
        RefreshTableView_Hotel();
        loadComboBoxVehicules();
    }


    @FXML
    private void UpdateChambre(ActionEvent event) {
        // Check if all fields are filled
        if (chambre_prix.getText().isEmpty() ||
                chambre_description.getText().isEmpty() ||
                chambre_hotel_nom.getSelectionModel().getSelectedItem() == null
                ||
                chambre_disponibilite.getSelectionModel().getSelectedItem() == null
                ||
                chambre_type.getSelectionModel().getSelectedItem() == null) {

            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }




        // Fetch vehicle ID from database
        ChambreService ChambreService = new ChambreService();
        HotelService HS=new HotelService();

        Hotel selectedHotel = chambre_hotel_nom.getSelectionModel().getSelectedItem();
        int id = HS.getIdHotelBynom(selectedHotel.getNom_hotel());  // Pass only the hotel name


        // Creating an instance of Contrat with String dates
        Chambre C = new Chambre(Integer.parseInt(String.valueOf(chambre_id.getText())),
                chambre_type.getSelectionModel().getSelectedItem(),
                Integer.parseInt(chambre_prix.getText()),
                Boolean.parseBoolean(chambre_disponibilite.getSelectionModel().getSelectedItem()),
                chambre_description.getText(),
                id
        );

        // Call the service method to add the contract
        ChambreService chambreService1= new ChambreService();
        chambreService1.modifier(C);

        // Show success message after the contract is added
        showAlert("Confirmation", "Le Chambre a été ajouté avec succès", Alert.AlertType.INFORMATION);

        // Reset fields after successful addition
        chambre_prix.clear();
        chambre_type.getSelectionModel().clearSelection();
        chambre_disponibilite.getSelectionModel().clearSelection();
        chambre_hotel_nom.getSelectionModel().clearSelection();
        chambre_description.clear();
        page_ajouter_chambre.setVisible(false);
        page_chambre.setVisible(true);
        RefreshTableView_Chambre();
    }



    @FXML
    private void RefreshTableView_Chambre() {
        try {
            ChambreService ChambreService = new ChambreService();
            List<Chambre> chambres = ChambreService.rechercher_1(); // Fetch hotels from DB

            if (chambres != null) {
                // Clear and update ObservableList
                ChambreData.clear();
                ChambreData.addAll(chambres);



                TableView_chambre.setItems(ChambreData);
                TableView_chambre.refresh(); // Force update

            } else {
                showAlert("Information", "Aucun Chambre trouvé.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour de la TableView.", Alert.AlertType.ERROR);
            e.printStackTrace();

        }
    }


    @FXML
    private void RefreshTableView_User() {
        try {
            ClientServices ClientService = new ClientServices();
            List<Client> Clients = ClientService.rechercherUser();

            if (Clients != null) {
                // Clear and update ObservableList
                userData_back.clear();
                userData_back.addAll(Clients);


                TableView_Utilisateur.setItems(userData_back);
                TableView_Utilisateur.refresh();

            } else {
                showAlert("Information", "Aucun User trouvé.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour de la TableView.", Alert.AlertType.ERROR);
            e.printStackTrace();

        }
    }

    private void loadComboBoxVehicules() {
        VehiculeService vehiculeService = new VehiculeService();
        List<Vehicule> vehicules = vehiculeService.getAllVehicules(); // Fetch vehicles from DB

        if (vehicules == null || vehicules.isEmpty()) {
            return;
        }

        // Populate the ComboBox with the full Vehicule objects
        contrat_matricule.setItems(FXCollections.observableArrayList(vehicules));

        // Set the ComboBox to display just the matricule for user selection
        contrat_matricule.setCellFactory(param -> new ListCell<Vehicule>() {
            @Override
            protected void updateItem(Vehicule item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getMatricule());  // Display the vehicle's matricule
                }
            }
        });

        // Set the converter to handle the selected Vehicule
        contrat_matricule.setConverter(new StringConverter<Vehicule>() {
            @Override
            public String toString(Vehicule vehicule) {
                return vehicule == null ? null : vehicule.getMatricule();  // Display matricule when selected
            }

            @Override
            public Vehicule fromString(String string) {
                return null;  // Not needed since we work with Vehicule objects directly
            }
        });
    }



    private void loadComboBox_hotel_nom() {
        HotelService hotelService = new HotelService();
        List<Hotel> hotels = hotelService.rechercher(); // Fetch hotels from DB

        if (hotels == null || hotels.isEmpty()) {
            return;
        }

        // Populate the ComboBox with the list of hotels
        chambre_hotel_nom.setItems(FXCollections.observableArrayList(hotels));

        // Set the ComboBox to display just the names
        chambre_hotel_nom.setCellFactory(param -> new ListCell<Hotel>() {
            @Override
            protected void updateItem(Hotel item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNom_hotel());  // Display only the hotel name
            }
        });

        // Set the converter to handle selection
        chambre_hotel_nom.setConverter(new StringConverter<Hotel>() {
            @Override
            public String toString(Hotel hotel) {
                return (hotel == null) ? "" : hotel.getNom_hotel();  // Display name when selected
            }

            @Override
            public Hotel fromString(String string) {
                // Find a matching hotel by name (useful for user-typed input)
                return hotels.stream()
                        .filter(h -> h.getNom_hotel().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }


    @FXML
    private void refreshTable() {
        ObservableList<Vol> volList = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wolfs", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Vol")) {

            while (rs.next()) {
                Vol vol = new Vol(
                        rs.getInt("FlightID"),
                        rs.getString("Departure"),
                        rs.getString("Destination"),
                        rs.getTimestamp("DepartureTime").toLocalDateTime(),
                        rs.getTimestamp("ArrivalTime").toLocalDateTime(),
                        ClasseChaise.valueOf(rs.getString("ClasseChaise")),
                        rs.getString("Airline"),
                        rs.getInt("FlightPrice"),  // Corrected to use Double
                        rs.getInt("AvailableSeats"),
                        rs.getString("Description")
                );

                volList.add(vol);
            }

            tableViewVols.setItems(volList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addVol() {
        String departure = tfDeparture.getText();
        String destination = tfDestination.getText();
        String departureTimeStr = tfDepartureTime.getText();
        String arrivalTimeStr = tfArrivalTime.getText();
        ClasseChaise classeChaise = cbClasseChaise.getValue();
        String airline = tfAirline.getText();
        double flightPrice;
        int availableSeats;
        String description = tfDescription.getText();

        // Validate inputs
        if (departure.isEmpty() || destination.isEmpty() || departureTimeStr.isEmpty() || arrivalTimeStr.isEmpty() || classeChaise == null || airline.isEmpty() || tfFlightPrice.getText().isEmpty() || tfAvailableSeats.getText().isEmpty() || description.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        try {
            flightPrice = Double.parseDouble(tfFlightPrice.getText()); // Ensuring flight price is Double
            availableSeats = Integer.parseInt(tfAvailableSeats.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Prix du vol et sièges disponibles doivent être des nombres !");
            return;
        }

        // Convert date strings to LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime departureTime, arrivalTime;
        try {
            departureTime = LocalDateTime.parse(departureTimeStr, formatter);
            arrivalTime = LocalDateTime.parse(arrivalTimeStr, formatter);
        } catch (Exception e) {
            showAlert("Erreur", "Format de date incorrect (yyyy-MM-ddTHH:mm) !");
            return;
        }

        // Insert into database
        String query = "INSERT INTO Vol (Departure, Destination, DepartureTime, ArrivalTime, ClasseChaise, Airline, FlightPrice, AvailableSeats, Description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wolfs", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, departure);
            stmt.setString(2, destination);
            stmt.setTimestamp(3, Timestamp.valueOf(departureTime));
            stmt.setTimestamp(4, Timestamp.valueOf(arrivalTime));
            stmt.setString(5, classeChaise.name());
            stmt.setString(6, airline);
            stmt.setDouble(7, flightPrice);
            stmt.setInt(8, availableSeats);
            stmt.setString(9, description);

            stmt.executeUpdate();
            showAlert("Succès", "Vol ajouté avec succès !");
            refreshTable();
            page_ajouter_vol.setVisible(false);
            page_vol.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ajouter le vol !");
        }
    }

    private void editVol(Vol vol) {

        // Create a dialog to edit multiple fields
        Dialog<Vol> dialog = new Dialog<>();
        dialog.setTitle("Edit Flight Information");

        // Create buttons for the dialog
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create a grid for input fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Define the fields to edit
        TextField departureField = new TextField(vol.getDeparture());
        TextField destinationField = new TextField(vol.getDestination());
        TextField airlineField = new TextField(vol.getAirline());
        TextField flightPriceField = new TextField(String.valueOf(vol.getFlightPrice()));
        TextField availableSeatsField = new TextField(String.valueOf(vol.getAvailableSeats()));
        TextField descriptionField = new TextField(vol.getDescription());

        // Add labels and fields to the grid
        grid.add(new Label("Departure:"), 0, 0);
        grid.add(departureField, 1, 0);

        grid.add(new Label("Destination:"), 0, 1);
        grid.add(destinationField, 1, 1);

        grid.add(new Label("Airline:"), 0, 2);
        grid.add(airlineField, 1, 2);

        grid.add(new Label("Flight Price:"), 0, 3);
        grid.add(flightPriceField, 1, 3);

        grid.add(new Label("Available Seats:"), 0, 4);
        grid.add(availableSeatsField, 1, 4);

        grid.add(new Label("Description:"), 0, 5);
        grid.add(descriptionField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Set the result converter (save the changes)
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                // Update the Vol object with the new values
                vol.setDeparture(departureField.getText());
                vol.setDestination(destinationField.getText());
                vol.setAirline(airlineField.getText());
                vol.setFlightPrice(Integer.parseInt(flightPriceField.getText()));
                vol.setAvailableSeats(Integer.parseInt(availableSeatsField.getText()));
                vol.setDescription(descriptionField.getText());

                // Save the updated Vol to the database
                modifierVol(vol);

                // Log the changes (for demonstration purposes)
                refreshTable();
            }
            return vol; // Return the updated Vol object
        });

        // Show the dialog and wait for the user input
        dialog.showAndWait();
    }

    public void modifierVol(Vol vol) {
        String req = "UPDATE vol SET departure='" + vol.getDeparture() + "', destination='" + vol.getDestination() + "', departureTime='" + vol.getDepartureTime() + "', " +
                "arrivalTime='" + vol.getArrivalTime() + "', classeChaise='" + vol.getClasseChaise() + "', airline='" + vol.getAirline() + "', flightPrice=" + vol.getFlightPrice() + ", " +
                "availableSeats=" + vol.getAvailableSeats() + ", description='" + vol.getDescription() + "' WHERE flightID=" + vol.getFlightID();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wolfs", "root", "");
             Statement st = conn.createStatement()) {

            st.executeUpdate(req);
            refreshTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteVol(Vol vol) {
        String req = "DELETE FROM vol WHERE flightID=" + vol.getFlightID();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wolfs", "root", "");
             Statement st = conn.createStatement()) {
            st.executeUpdate(req);
            refreshTable();
            showAlert("Succès", "Vol supprimé avec succès !");
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    /**********************************NEWWWWWWWW******************************************/

    @FXML
    private void back_to_se_connecter_page(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation d annulation de récupiration de mot de passe");
        alert.setHeaderText("Voulez-vous vraiment vous quitter ?");
        alert.setContentText("Cliquez sur OK pour confirmer.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            animateTransition(forget_password,Se_connecter_page,0);
            forget_password_mail.clear();

        }

    }
    @FXML
    private void GoToForgetPassword(ActionEvent event)
    {
            animateTransition(Se_connecter_page,forget_password,0);
    }
    @FXML
    private void EnvoyerMail() {
        String email = forget_password_mail.getText();
        if (email.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer votre email.", Alert.AlertType.INFORMATION);
            return;
        }

        ClientServices C1 = new ClientServices();
        Client cLient = C1.verifierUserByMail(email);

        if (cLient != null) {
             codeEnvoye = genererCodeVerification();
            EmailService.sendPasswordResetEmail(email, codeEnvoye);
            startCountdown(60);
            showAlert("Succès", "Un email avec un code de réinitialisation a été envoyé.", Alert.AlertType.INFORMATION);
            animateTransition(forget_password,forget_password_reset,0);
            code_field.clear();
            password_field_reset.clear();
            confirmer_password_field_reset.clear();
        } else {
            showAlert("Erreur", "Votre mail n'existe pas.", Alert.AlertType.ERROR);
        }
    }

    private String genererCodeVerification() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(caracteres.length());
            code.append(caracteres.charAt(index));
        }

        return code.toString();
    }
    @FXML
    private void handlePasswordReset() {
        String codeSaisi = code_field.getText().trim();
        String newPassword = password_field_reset.getText();
        String confirmPassword = confirmer_password_field_reset.getText();

        // Vérification du code
        if (codeEnvoye == null) {
            showAlert("Erreur", "Veuillez d'abord demander un code de réinitialisation.", Alert.AlertType.ERROR);
            return;
        }

        if (!codeSaisi.equals(codeEnvoye)) {
            showAlert("Erreur", "Le code que vous avez donné est incorrect.", Alert.AlertType.ERROR);
            return;
        }

        // Vérification des champs de mot de passe
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir les champs de mot de passe.", Alert.AlertType.ERROR);
            return;
        }

        if (newPassword.length() < 8 || !newPassword.matches(".*[A-Z].*") || !newPassword.matches(".*\\d.*")) {
            showAlert("Erreur", "Le mot de passe doit contenir au moins 8 caractères, une majuscule et un chiffre.", Alert.AlertType.ERROR);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Erreur", "Le mot de passe ne correspond pas.", Alert.AlertType.ERROR);
            return;
        }

        // Mise à jour du mot de passe
        ClientServices C1 = new ClientServices();
        String haspass = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        if (C1.updatePassword(forget_password_mail.getText(), haspass)) {
            if (timeline != null) {
                timeline.stop();
            }
            showAlert("Succès", "Votre mot de passe a été changé avec succès.", Alert.AlertType.INFORMATION);
            animateTransition(forget_password_reset,RederictToLogin,0);
            return;
        } else {
            showAlert("Erreur", "Une erreur s'est produite lors de la mise à jour du mot de passe.", Alert.AlertType.ERROR);
        }
    }
    @FXML
    public void handleGoogleAuth() {
        try {
            Credential credential = GoogleAuthUtil.authenticate();
            Userinfo userInfo = GoogleAuthUtil.getUserInfo(credential);
            String profilePicUrl = userInfo.getPicture();
            String highResUrl = profilePicUrl.replace("=s96", "=s800");

            String email = userInfo.getEmail();
            String fullName = userInfo.getName();
            String[] parts = fullName.split(" ");

            String firstName = parts[0];
            String lastName = (parts.length > 1) ? parts[1] : "";

            System.out.println("Prénom : " + firstName);
            System.out.println("Nom : " + lastName);



            if (ClientService_user.userExists(email)) {
                email_field_signin.setText(email);
                UserRole(null);
                UserAccount();


            }
            else {
                ClientService_user.ajouterUser(new Client(lastName, firstName, userInfo.getEmail(), "", 22334455, 2, 0, highResUrl));
                email_field_signin.setText(email);
                UserRole(null);
                UserAccount();


            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "L'authentification Google a échoué", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void SwitchToLogin(){
        animateTransition(RederictToLogin,Se_connecter_page,0);
    }

    private void startCountdown(int totalSeconds) {
        final int[] remainingTime = {totalSeconds};

        if (timeline != null) {
            timeline.stop();
        }

        Platform.runLater(() -> countdownLabel.setText("Temps restant : " + String.format("%02d:%02d", 0, 0)));

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    int minutes = remainingTime[0] / 60;
                    int seconds = remainingTime[0] % 60;

                    String timeLeft = String.format("%02d:%02d", minutes, seconds);

                    Platform.runLater(() -> countdownLabel.setText("Temps restant : " + timeLeft));

                    remainingTime[0]--;

                    if (remainingTime[0] <= 0) {
                        timeline.stop();
                        Platform.runLater(() -> {
                            countdownLabel.setText("Temps écoulé.");

                            codeEnvoye = "";

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Votre session a expiré.");
                            alert.setContentText("Le temps imparti pour la réinitialisation est écoulé.");

                            Platform.runLater(() -> {
                                alert.showAndWait();
                            });
                            animateTransition(forget_password_reset, Se_connecter_page, 0);
                        });
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }





}