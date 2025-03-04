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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;

import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.security.GeneralSecurityException;

public class CrudUser {

    //NewNajdForumVar
    private ContextMenu suggestionsMenu = new ContextMenu();
    @FXML private ComboBox<String> videoSelector;
    private YouTubeVideoPlayer youtubePlayer;
    @FXML
    private BorderPane BoxTube;
    @FXML
    private Button fullscreenButton;
    @FXML
    private MediaView mediaView;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button page_acceuil_forum_go_to_Tube;
    @FXML
    private AnchorPane page_Tube;
   @FXML TextField ChercherPost;
   @FXML ComboBox<String> ForumComboxFilter;

    int PostingTry =0;
    int coolDownPost=0;
    private Timeline currentTimelinePost;


 private User CurrentUser = new Client(19, "dhieb", "aymen", "aymen.dhieb@esprit.tn", "$2a$10$S67nBnlNHPHqUnTI0.WyDO2OxX2q8X4ZiCRyAPVFgJDLxLwLIhlv.", 56252246, 0, 0, "file:/C:/9raya/java/Projet_Pidev/target/classes/images//aymen.jpg");

    @FXML ComboBox<String> trieComboPost;
    @FXML ComboBox<String> trieASCDESPost;

    @FXML
    private TextField ChercherForum;

    @FXML
    private TextField nameField;
    @FXML
    private TextField createdByField;
    @FXML
    private TextField descriptionField;
    @FXML
    private CheckBox isPrivateCheckBox;
    @FXML
    private Button addForumButton;

    @FXML
    private ListView<String> membersListView;
    @FXML
    private TextField memberEmailField;
    @FXML
    private Button addMemberButton;

    @FXML
    private TableView<Forum> forumTableView;

    @FXML
    private TableColumn<Forum, Integer> forumIdColumn;
    @FXML
    private TableColumn<Forum, String> nameColumn;
    @FXML
    private TableColumn<Forum, String> createdByColumn;
    @FXML
    private TableColumn<Forum, Integer> postCountColumn;
    @FXML
    private TableColumn<Forum, String> membersColumn;
    @FXML
    private TableColumn<Forum, String> descriptionColumn;
    @FXML
    private TableColumn<Forum, LocalDateTime> dateColumn;
    @FXML
    private TableColumn<Forum, Boolean> privateColumn;
    @FXML
    private TableColumn<Forum, String> listMembersColumn;


    private ObservableList<Forum> forumList = FXCollections.observableArrayList();

    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label descriptionErrorLabel;
    @FXML
    private Label memberEmailErrorLabel;

    private boolean isNameValid = false;
    private boolean isEmailValid = false;
    private boolean isDescriptionValid = false;
    private boolean isMemberEmailValid = false;

    private ForumService forumService = new ForumService();

    @FXML
    private WebView webView;
//******************

    public ScrollPane postsContainer;
    @FXML private ComboBox<Forum> forumComboBox;
    @FXML private VBox postsVBox;
    @FXML private VBox commentsSection;
    @FXML private Button backButton;
    @FXML private TextArea forumDescription;
    @FXML private Label forumPrivacyLabel;
    @FXML private Label forumDateLabel;
    @FXML private Label memberCountLabel;
    @FXML private ListView<String> membersListViewFP;
    @FXML private VBox membersSection;
    @FXML private ComboBox<String> postTypeComboBox;
    @FXML private VBox announcementFields;
    @FXML private VBox surveyFields;
    @FXML private TextField announcementTitleField;
    @FXML private TextArea announcementContentField;
    @FXML private TextField announcementTagsField;
    @FXML private TextField surveyQuestionField;
    @FXML private TextField surveyTagsField;
    @FXML private TextArea newCommentField;
    @FXML private VBox commentsVBox;

    @FXML private Label titleErrorLabel;
    @FXML private Label contentErrorLabel;
    @FXML private Label tagsErrorLabel;
    @FXML private Label questionErrorLabel;
    @FXML private Label surveyTagsErrorLabel;
    @FXML private Label commentErrorLabel;
    @FXML private Label choiceErrorLabel;

    @FXML private TabPane leftTabPane;

    @FXML private VBox postDetailsBox;

    @FXML private TextField choiceField;
    @FXML private Button addChoiceButton;
    @FXML private ListView<String> choicesListView;

    @FXML private TextField announcementFileField;
    @FXML private TextField surveyFileField;

    @FXML private TextField surveyUserField;
    @FXML private Button addSurveyUserButton;
    @FXML private ListView<String> surveyUsersListView;
    @FXML private Label surveyUserErrorLabel;
    @FXML private Label announcementFileErrorLabel;
    @FXML private Label surveyFileErrorLabel;

    @FXML private TextField editChoiceField;
    @FXML private Button editAddChoiceButton;
    @FXML private ListView<String> editChoicesListView;

    @FXML private TextField editSurveyQuestionField;
    @FXML private TextField editSurveyTagsField;
    @FXML private TextField editSurveyFileField;
    @FXML private ComboBox<String> editSurveyStatusComboBox;
    @FXML private TextField editSurveyUserField;
    @FXML private Button editAddSurveyUserButton;
    @FXML private ListView<String> editSurveyUsersListView;
    @FXML private Label editSurveyUserErrorLabel;
    @FXML private Label editChoiceErrorLabel;

    @FXML private TextField editAnnouncementTitleField;
    @FXML private TextArea editAnnouncementContentField;
    @FXML private TextField editAnnouncementTagsField;
    @FXML private TextField editAnnouncementFileField;
    @FXML private ComboBox<String> editAnnouncementStatusComboBox;
    @FXML private VBox editAnnouncementFields;
    @FXML private VBox editSurveyFields;

    @FXML private Label editTitleErrorLabel;
    @FXML private Label editContentErrorLabel;
    @FXML private Label editSurveyQuestionErrorLabel;

    @FXML private TextField announcementUserField;
    @FXML private TextField surveyAuthorField;
    @FXML private TextField editAnnouncementUserField;
    @FXML private TextField editSurveyAuthorField;
    @FXML private Label announcementUserErrorLabel;
    @FXML private Label surveyAuthorErrorLabel;
    @FXML private Label editAnnouncementUserErrorLabel;
    @FXML private Label editSurveyAuthorErrorLabel;

    @FXML private Button addPostButton;

    @FXML private Label editTagsErrorLabel;
    @FXML private Label editSurveyTagsErrorLabel;

    private ForumService forumServiceFP = new ForumService();
    private PostService postService = new PostService();
    private ChoixService choixService = new ChoixService();

    private Post currentPost;

    private Map<String, Long> userLastPostTime = new HashMap<>();
    @FXML private TabPane rightTabPane;

    @FXML
    private HBox mediaControls;
    //finNAJD

    @FXML
    private Label nonBloquer_label;
    @FXML
    private Label bloquer_label;

    @FXML
    private Label count_client;
    @FXML
    private Label count_bloque;

    @FXML
    private PieChart ChatStatus;

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
        cl_user_status.setCellFactory(col -> new TableCell<Client, Integer>() {
            @Override
            protected void updateItem(Integer status, boolean empty) {
                super.updateItem(status, empty);
                if (empty) {
                    setText(null);
                    setStyle("");
                } else {
                    // Vérifier le statut
                    if (status != null) {
                        if (status == 0) {
                            setText("Active");
                            setStyle("-fx-text-fill: green;"); // Couleur verte pour "Active"
                        } else if (status == 1) {
                            setText("Bloqué");
                            setStyle("-fx-text-fill: red;"); // Couleur rouge pour "Bloqué"
                        }
                    }
                }
            }
        });
        cl_user_bloquer.setCellFactory(column -> {
            return new TableCell<Client, Void>() {
                private final Button btn = new Button();
                private final ImageView imageView = new ImageView(new Image("/images/bloquer.png")); // Chemin de l'image
                private final HBox hbox = new HBox(btn);

                {
                    // Configuration de l'image
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);

                    btn.setGraphic(imageView);

                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);

                    btn.setStyle(
                            "-fx-background-color:transparent; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-border-radius: 5px; " +
                                    "-fx-cursor: hand;"
                    );

                    btn.setOnAction(event -> {
                        Client client = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de Bloquer");
                        alert.setHeaderText("Voulez-vous vraiment bloquer " + client.getName() + " " + client.getPrenom() + " ?");
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            ClientServices clientServices = new ClientServices();
                            clientServices.bloquer_client(clientServices.getUserIdByEmail(client.getEmail()));
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
                        setGraphic(hbox);
                    }
                }
            };
        });

        cl_user_debloquer.setCellFactory(column -> {
            return new TableCell<Client, Void>() {
                private final Button btn = new Button();
                private final ImageView imageView = new ImageView(new Image("/images/debloque.png")); // Chemin de l'image
                private final HBox hbox = new HBox(btn);

                {
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);

                    btn.setGraphic(imageView);

                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);

                    btn.setStyle(
                            "-fx-background-color: transparent; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-border-radius: 5px; " +
                                    "-fx-cursor: hand;"
                    );

                    btn.setOnAction(event -> {
                        Client client = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de Débloquer");
                        alert.setHeaderText("Voulez-vous vraiment débloquer " + client.getName() + " " + client.getPrenom() + " ?");
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            ClientServices clientServices = new ClientServices();
                            clientServices.debloquer_client(clientServices.getUserIdByEmail(client.getEmail()));
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
                        setGraphic(hbox);
                    }
                }
            };
        });
        cl_user_supprimer.setCellFactory(column -> {
            return new TableCell<Client, Void>() {
                private final Button btn = new Button();
                private final ImageView imageView = new ImageView(new Image("/images/supprimer.png"));
                private final HBox hbox = new HBox(btn);

                {
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);

                    btn.setGraphic(imageView);

                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);

                    btn.setStyle(
                            "-fx-background-color: transparent; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-border-radius: 5px; " +
                                    "-fx-cursor: hand;"
                    );

                    // Gestion de l'action du bouton
                    btn.setOnAction(event -> {
                        Client client = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de Suppression");
                        alert.setHeaderText("Voulez-vous vraiment supprimer " + client.getName() + " " + client.getPrenom() + " ?");
                        alert.setContentText("Cliquez sur OK pour confirmer.");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            ClientServices clientServices = new ClientServices();
                            Client clientToDelete = new Client(
                                    clientServices.getUserIdByEmail(client.getEmail()), "", "", "", "", 0, 0, 0, ""
                            );
                            clientServices.supprimerUser(clientToDelete);
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
        //NEWWWW
        refreshData();

        {
            //najdForumI
            //--------------------------------------------------------------------------------------------------------
            {
                forumTableView.setEditable(true);

                forumIdColumn.setCellValueFactory(new PropertyValueFactory<>("forumId"));

                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                nameColumn.setOnEditCommit(this::onNameEdit);

                createdByColumn.setCellValueFactory(cellData -> {
                    int userId = cellData.getValue().getCreatedBy();
                    String email = forumService.getEmailById(userId);
                    return new SimpleStringProperty(email);
                });
                createdByColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                createdByColumn.setOnEditCommit(event -> {
                    Forum forum = event.getRowValue();
                    if (validateTableEdit(forum, "createdBy", event.getNewValue())) {
                        int userId = forumService.getUserByEmail(event.getNewValue());
                        forum.setCreatedBy(userId);
                        updateForum(forum);
                    }
                });

                postCountColumn.setCellValueFactory(new PropertyValueFactory<>("postCount"));
                postCountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
                postCountColumn.setOnEditCommit(this::onPostCountEdit);
                membersColumn.setCellValueFactory(cellData -> {
                    Forum forum = cellData.getValue();
                    if (forum.isPrivate()) {

                        return new SimpleStringProperty(forum.getNbrMembers()+" Members");
                    }
                    return new SimpleStringProperty("Private Forum");
                });
                membersColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                membersColumn.setEditable(false);

                descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                descriptionColumn.setOnEditCommit(this::onDescriptionEdit);

                dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));

                privateColumn.setCellValueFactory(new PropertyValueFactory<>("private"));
                privateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
                privateColumn.setOnEditCommit(event -> {
                    Forum forum = event.getRowValue();
                    forum.setPrivate(event.getNewValue());
                    if (!event.getNewValue()) {
                        forum.setListMembers("");
                        forum.setNbrMembers(0);
                    }
                    updateForum(forum);
                });

                listMembersColumn.setCellValueFactory(new PropertyValueFactory<>("listMembers"));
                listMembersColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                listMembersColumn.setOnEditCommit(event -> {
                    Forum forum = event.getRowValue();
                    String newValue = event.getNewValue();

                    if (validateTableEdit(forum, "listMembers", newValue)) {
                        CrudUser crudUser = new CrudUser();

                        String[] emails = newValue.split(",");
                        boolean allValid = Arrays.stream(emails)
                                .map(String::trim)
                                .allMatch(crudUser::validateUserEmail2);

                        if (allValid) {
                            forum.setListMembers(newValue);
                            forum.setNbrMembers((int) Arrays.stream(emails).count());
                            updateForum(forum);
                        } else {
                            refreshTableFB();
                        }
                    }
                });

                addButtonsToTable();

                refreshTableFB();
                refreshForums();

                addForumButton.setOnAction(event -> addForum());
                isPrivateCheckBox.setOnAction(event -> handlePrivateCheckbox());
                addMemberButton.setOnAction(event -> addMember());

                nameField.textProperty().addListener((observable, oldValue, newValue) -> validateName(newValue));
                createdByField.textProperty().addListener((observable, oldValue, newValue) -> validateEmail(newValue));
                descriptionField.textProperty().addListener((observable, oldValue, newValue) -> validateDescription(newValue));
                memberEmailField.textProperty().addListener((observable, oldValue, newValue) -> validateMemberEmail(newValue));
                ChercherForum.textProperty().addListener((observable, oldValue, newValue) -> refreshTableFB(newValue));

                nameErrorLabel.setVisible(false);
                emailErrorLabel.setVisible(false);
                descriptionErrorLabel.setVisible(false);
                memberEmailErrorLabel.setVisible(false);
            }

            {
                postTypeComboBox.getItems().addAll("Announcement", "Survey");
                postTypeComboBox.setOnAction(e -> handlePostTypeChange());

                loadForums();

                forumComboBox.setOnAction(e -> updateForumInfo());

                setupValidationListeners();

                refreshPosts();

                choiceField.textProperty().addListener((obs, old, newValue) ->
                        validateChoice(newValue));

                surveyUserField.textProperty().addListener((obs, old, newValue) ->
                        validateSurveyUser(newValue));

                editChoiceField.textProperty().addListener((obs, old, newValue) ->
                        validateEditChoice(newValue));
                editSurveyUserField.textProperty().addListener((obs, old, newValue) ->
                        validateEditSurveyUser(newValue));
                editAnnouncementTitleField.textProperty().addListener((obs, old, newValue) ->
                        validateEditTitle(newValue));
                editAnnouncementContentField.textProperty().addListener((obs, old, newValue) ->
                        validateEditContent(newValue));
                editSurveyQuestionField.textProperty().addListener((obs, old, newValue) ->
                        validateEditQuestion(newValue));

                editChoicesListView.setOnContextMenuRequested(e -> {
                    if (editChoicesListView.getSelectionModel().getSelectedItem() != null) {
                        ContextMenu menu = new ContextMenu();
                        MenuItem removeItem = new MenuItem("Remove");
                        removeItem.setOnAction(event ->
                                editChoicesListView.getItems().remove(
                                        editChoicesListView.getSelectionModel().getSelectedItem()
                                )
                        );
                        menu.getItems().add(removeItem);
                        menu.show(editChoicesListView, e.getScreenX(), e.getScreenY());
                    }
                });

                editSurveyUsersListView.setOnContextMenuRequested(e -> {
                    if (editSurveyUsersListView.getSelectionModel().getSelectedItem() != null) {
                        ContextMenu menu = new ContextMenu();
                        MenuItem removeItem = new MenuItem("Remove");
                        removeItem.setOnAction(event ->
                                editSurveyUsersListView.getItems().remove(
                                        editSurveyUsersListView.getSelectionModel().getSelectedItem()
                                )
                        );
                        menu.getItems().add(removeItem);
                        menu.show(editSurveyUsersListView, e.getScreenX(), e.getScreenY());
                    }
                });

                announcementUserField.textProperty().addListener((obs, old, newValue) ->
                        validateUserEmail(announcementUserField, announcementUserErrorLabel, newValue));
                surveyAuthorField.textProperty().addListener((obs, old, newValue) ->
                        validateUserEmail(surveyAuthorField, surveyAuthorErrorLabel, newValue));
                editAnnouncementUserField.textProperty().addListener((obs, old, newValue) ->
                        validateUserEmail(editAnnouncementUserField, editAnnouncementUserErrorLabel, newValue));
                editSurveyAuthorField.textProperty().addListener((obs, old, newValue) ->
                        validateUserEmail(editSurveyAuthorField, editSurveyAuthorErrorLabel, newValue));


                forumComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                    addPostButton.setDisable(newVal == null);
                });

                announcementTagsField.textProperty().addListener((obs, old, newValue) ->
                        validateTags(newValue, tagsErrorLabel));

                surveyTagsField.textProperty().addListener((obs, old, newValue) ->
                        validateTags(newValue, surveyTagsErrorLabel));

                editAnnouncementTagsField.textProperty().addListener((obs, old, newValue) ->
                        validateEditTags(newValue, editTagsErrorLabel));

                editSurveyTagsField.textProperty().addListener((obs, old, newValue) ->
                        validateEditTags(newValue, editSurveyTagsErrorLabel));

                List<TextInputControl> fields = Arrays.asList(
                        announcementTitleField, announcementContentField, announcementTagsField,
                        surveyQuestionField, surveyTagsField, choiceField, surveyUserField,
                        editAnnouncementTitleField, editAnnouncementContentField, editAnnouncementTagsField,
                        editSurveyQuestionField, editSurveyTagsField, editChoiceField, editSurveyUserField
                );

                for (TextInputControl field : fields) {
                    field.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                        if (!isNowFocused && !field.getStyleClass().contains("valid-fieldFP")
                                && !field.getStyleClass().contains("text-field-errorFP")) {
                            field.getStyleClass().add("text-field-errorFP");
                        }
                    });
                }
            }

            {

                Asma3niField(ChercherForum);
                Asma3niField(ChercherPost);


            ChercherPost.textProperty().addListener((observable, oldValue, newValue) -> {

                    //showSuggestions(newValue);
                    refreshPosts();

            });


               ForumComboxFilter.getItems().addAll("nom","description","créé par","nombre de posts","nombre de membres","date de création","confidentialité","liste des membres" );
               ForumComboxFilter.setValue("nom");

                trieComboPost.getItems().addAll("Date", "Title", "Author", "Vote");
                trieComboPost.setValue("Vote");

                trieASCDESPost.getItems().addAll("Ascending", "Descending");
                trieASCDESPost.setValue("Descending");

                trieComboPost.valueProperty().addListener((observable, oldValue, newValue) -> refreshPosts());
                trieASCDESPost.valueProperty().addListener((observable, oldValue, newValue) -> refreshPosts());

                page_Tube.visibleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        try {
                            webView.getEngine().setJavaScriptEnabled(true);
                            youtubePlayer = new YouTubeVideoPlayer(webView, videoSelector, BoxTube, fullscreenButton,
                                    (Stage) BoxTube.getScene().getWindow());

                            nextButton.setOnAction(event -> youtubePlayer.loadNextVideo());
                            prevButton.setOnAction(event -> youtubePlayer.loadPreviousVideo());

                            webView.getEngine().setOnError(event -> {
                                System.out.println("WebView error: " + event.getMessage());
                            });

                            webView.getEngine().setOnAlert(event -> {
                                System.out.println("WebView alert: " + event.getData());
                            });

                        } catch (IOException | GeneralSecurityException e) {
                            System.out.println("An error occurred: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        youtubePlayer.clearResources();
                    }
                });


            }

            //--------------------------------------------------------------------------------------------------------
        }


    }




    private static void extractSuggestions(String json, List<String> suggestions) {
        // Utilisation d'une regex pour extraire les mots entre guillemets après "keywords":[
        Pattern pattern = Pattern.compile("\"keywords\":\\[\"([^\"]+)\"(,\"([^\"]+)\")*]");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            // Extraction des mots-clés trouvés
            String[] words = matcher.group().replace("\"keywords\":[", "").replace("]", "").replace("\"", "").split(",");
            for (String word : words) {
                suggestions.add(word.trim());
            }
        }
    }
    //NEWWWWWWWWWWWW


    public void refreshData() {
        ClientServices C1 = new ClientServices();
        int blockedUsers = C1.UserAccountBlocked();
        int notBlockedUsers = C1.UserAccountNotBlocked();
        int totalUsers = blockedUsers + notBlockedUsers;

        count_client.setText(String.valueOf(C1.CountUsers()));
        count_bloque.setText(String.valueOf(blockedUsers));

        double blockedPercentage = (blockedUsers / (double) totalUsers) * 100;
        double notBlockedPercentage = (notBlockedUsers / (double) totalUsers) * 100;

        bloquer_label.setText("Bloqués: " + String.format("%.1f", blockedPercentage) + "%");
        nonBloquer_label.setText("Non Bloqués: " + String.format("%.1f", notBlockedPercentage) + "%");

        bloquer_label.setTextFill(Color.web("#E78D1E"));
        nonBloquer_label.setTextFill(Color.web("#132a3e"));

        bloquer_label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        nonBloquer_label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Bloqués", blockedUsers),
                new PieChart.Data("Non Bloqués", notBlockedUsers)
        );
        ChatStatus.setData(pieChartData);

        for (PieChart.Data data : pieChartData) {
            double percentage = (data.getPieValue() / totalUsers) * 100;
            data.setName(data.getName() + " (" + String.format("%.1f", percentage) + "%)");

            if (data.getName().startsWith("Bloqués")) {
                data.getNode().setStyle("-fx-pie-color: #E78D1E;");
            } else if (data.getName().startsWith("Non Bloqués")) {
                data.getNode().setStyle("-fx-pie-color:#132a3e;");
            }

            data.getNode().setStyle(data.getNode().getStyle() + "-fx-border-color: #FFFFFF; -fx-border-width: 2;");

            Tooltip tooltip = new Tooltip(data.getName());
            Tooltip.install(data.getNode(), tooltip);

            data.getNode().setOnMouseEntered(event -> {
                data.getNode().setStyle(data.getNode().getStyle() + "-fx-effect: dropshadow(gaussian, #000000, 10, 0, 0, 0);");
                tooltip.show(data.getNode(), event.getScreenX(), event.getScreenY());
            });

            data.getNode().setOnMouseExited(event -> {
                data.getNode().setStyle(data.getNode().getStyle().replace("-fx-effect: dropshadow(gaussian, #000000, 10, 0, 0, 0);", ""));
                tooltip.hide();
            });

            if (data.getName().startsWith("Bloqués")) {
                data.getNode().setTranslateX(10);
                data.getNode().setTranslateY(5);
            }

            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), data.getNode());
            rotateTransition.setByAngle(360);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
        }

    }


    //ENDNEWWWWWWWWWWWW


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
            //NEWW
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
            CurrentUser = new Client(client.getId(), client.getName(), client.getPrenom(), client.getEmail(), client.getPassword(), client.getNum_tel(), client.getRole(), client.getStatus(), client.getPhoto_profile());
       if(CurrentUser.getRole()==0) {
          trieASCDESPost.getItems().addAll("inactive");
          trieComboPost.getItems().addAll("Nombre singal");
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
            refreshData();
            page_chambre.setVisible(false);
            page_hotel.setVisible(false);
            page_forum.setVisible(false);
            page_Tube.setVisible(false);

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
            page_Tube.setVisible(false);

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
            page_Tube.setVisible(false);

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
            page_Tube.setVisible(false);

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
            page_Tube.setVisible(false);

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
            page_Tube.setVisible(false);

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
            page_Tube.setVisible(false);

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
        } else if (actionEvent.getSource() == page_acceuil_forum_go_to_Tube) {


            page_acceuil_gestion_forum.setVisible(false);
            page_Tube.setVisible(true);
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
        createdByField.setText(CurrentUser.getEmail());

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




//--------------------------------NajdForumF------------------------------------------------------------------------

    //Integration dont forgot code in 3253 (end of void changeForum)  createdByField.setText(CurrentUser.getEmail());

 private void refreshTableFB() {
  forumList.clear();
  forumList.addAll(forumService.rechercher());
  forumTableView.setItems(forumList);
 }

 private void refreshTableFB(String query) {
  List<Forum> allForums;
  allForums = forumService.rechercher();
  allForums = rechercherForum(query,allForums);

  forumList.setAll(allForums);
  forumTableView.setItems(forumList);
 }

   public List<Forum> rechercherForum(String keyword, List<Forum> forums) {
      if (keyword == null || keyword.isEmpty()) {
         return forums;
      }

      String searchLower = keyword.toLowerCase();
      List<Forum> results = new ArrayList<>();

      for (Forum forum : forums) {
         boolean matchFound = false;

         String filterValue = (ForumComboxFilter.getValue() != null) ? ForumComboxFilter.getValue().toString().toLowerCase() : "";

         switch (filterValue) {
            case "nom":
               matchFound = forum.getName().toLowerCase().contains(searchLower);
               break;
            case "description":
               matchFound = forum.getDescription().toLowerCase().contains(searchLower);
               break;
            case "créé par":
               matchFound = forumService.getEmailById(forum.getCreatedBy()).toLowerCase().contains(searchLower);
               break;
            case "nombre de posts":
               matchFound = Integer.toString(forum.getPostCount()).contains(keyword);
               break;
            case "nombre de membres":
               matchFound = Integer.toString(forum.getNbrMembers()).contains(keyword);
               break;
            case "date de création":
               matchFound = forum.getDateCreation().toString().replace("T", " ").contains(keyword);
               break;
            case "confidentialité":
               matchFound = (forum.isPrivate()
                       ? Arrays.asList("privé", "true").contains(searchLower)
                       : Arrays.asList("public", "false").contains(searchLower));
               break;
            case "liste des membres":
               if (forum.getListMembers() != null) {
                  matchFound = forum.getListMembers().toLowerCase().contains(searchLower);
               }
               break;
            default:

               break;
         }

         if (matchFound) {
            results.add(forum);
         }
      }

      return results;
   }

 @FXML
 private void clearFieldsFP() {

  nameField.clear();
  createdByField.clear();
  descriptionField.clear();
  memberEmailField.clear();

  nameErrorLabel.setText("");
  emailErrorLabel.setText("");
  descriptionErrorLabel.setText("");
  memberEmailErrorLabel.setText("");

  membersListView.getItems().clear();

  isPrivateCheckBox.setSelected(false);

  isNameValid = false;
  isEmailValid = false;
  isDescriptionValid = false;
 }

 private void refreshForums() {
  try {

   List<Forum> forums = forumServiceFP.rechercher();

   forumComboBox.getItems().clear();

   forumComboBox.getItems().addAll(forums);

   if (!forums.isEmpty()) {
    forumComboBox.getSelectionModel().select(0);
   }

   updateForumInfo();

   forumComboBox.setConverter(new StringConverter<Forum>() {
    @Override
    public String toString(Forum forum) {
     return forum != null ? forum.getName() : "";
    }

    @Override
    public Forum fromString(String string) {
     return null;
    }
   });
  } catch (Exception e) {
   showAlertFP("Error", "Failed to refresh forums: " + e.getMessage(), Alert.AlertType.ERROR);
  }
 }

 @FXML
 private void handlePrivateCheckbox() {
  boolean isPrivate = isPrivateCheckBox.isSelected();
  membersListView.setDisable(!isPrivate);
  memberEmailField.setDisable(!isPrivate);

  if (!isPrivate) {
   membersListView.getItems().clear();
   updateMemberCount();
  }
 }

 @FXML
 private void addMember() {
  String email = memberEmailField.getText().trim();
  if (!email.isEmpty() && !membersListView.getItems().contains(email)) {
   if (forumService.getUserByEmail(email) != -1) {
    membersListView.getItems().add(email);
    memberEmailField.clear();
    hideError(memberEmailField, memberEmailErrorLabel);
    isMemberEmailValid = true;

    updateMemberCount();
   }
  }
 }


   private void addButtonsToTable() {
      TableColumn<Forum, Void> actionsCol = new TableColumn<>("Action");

      Callback<TableColumn<Forum, Void>, TableCell<Forum, Void>> cellFactory = new Callback<>() {
         @Override
         public TableCell<Forum, Void> call(final TableColumn<Forum, Void> param) {
            return new TableCell<>() {
               private final Button deleteButton = new Button("Supprimer");
               private final HBox buttons = new HBox(5, deleteButton);

               {
                  deleteButton.setOnAction(event -> {
                     Forum forum = getTableView().getItems().get(getIndex());
                     handleDelete(forum);
                  });

                  deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                  buttons.setAlignment(Pos.CENTER);
               }

               @Override
               protected void updateItem(Void item, boolean empty) {
                  super.updateItem(item, empty);
                  if (empty) {
                     setGraphic(null);
                  } else {
                     setGraphic(buttons);
                  }
               }
            };
         }
      };

      actionsCol.setCellFactory(cellFactory);
      forumTableView.getColumns().add(actionsCol);
   }


 private void handleDelete(Forum forum) {
  forumService.supprimer(forum);
  refreshTableFB();
  refreshForums();
 }

 private void onNameEdit(TableColumn.CellEditEvent<Forum, String> event) {
  Forum forum = event.getRowValue();
  if (validateTableEdit(forum, "name", event.getNewValue())) {
   forum.setName(event.getNewValue());
   updateForum(forum);
  }
 }

 private void onPostCountEdit(TableColumn.CellEditEvent<Forum, Integer> event) {
  Forum forum = event.getRowValue();
  forum.setPostCount(event.getNewValue());
  updateForum(forum);
 }

 private void onDescriptionEdit(TableColumn.CellEditEvent<Forum, String> event) {
  Forum forum = event.getRowValue();
  if (validateTableEdit(forum, "description", event.getNewValue())) {
   forum.setDescription(event.getNewValue());
   updateForum(forum);
  }
 }

 private void updateForum(Forum forum) {
  forumService.modifier(forum);
  refreshTableFB();
  refreshForums();
 }

 private void validateName(String name) {
  if (name.isEmpty()) {
   showError(nameField, nameErrorLabel, "Le nom ne peut pas être vide");
   isNameValid = false;
  } else if (name.length() > 20) {
   showError(nameField, nameErrorLabel, "Le nom ne doit pas dépasser 20 caractères");
   isNameValid = false;
  } else if (!name.matches("[a-zA-Z ]+")) {
   showError(nameField, nameErrorLabel, "Le nom ne doit contenir que des lettres et des espaces");
   isNameValid = false;
  } else {
   hideError(nameField, nameErrorLabel);
   isNameValid = true;
  }
 }

 private void validateEmail(String email) {
  if (email.isEmpty()) {
   showError(createdByField, emailErrorLabel, "L'email ne peut pas être vide");
   isEmailValid = false;
  } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
   showError(createdByField, emailErrorLabel, "Format d'email invalide");
   isEmailValid = false;
  } else if (forumService.getUserByEmail(email) == -1) {
   showError(createdByField, emailErrorLabel, "Cet utilisateur n'existe pas");
   isEmailValid = false;
  } else {
   hideError(createdByField, emailErrorLabel);
   isEmailValid = true;
  }
 }

 private void validateDescription(String description) {
  if (description.isEmpty()) {
   showError(descriptionField, descriptionErrorLabel, "La description ne peut pas être vide");
   isDescriptionValid = false;
  } else if (description.length() > 100) {
   showError(descriptionField, descriptionErrorLabel, "La description ne doit pas dépasser 100 caractères");
   isDescriptionValid = false;
  } else {
   hideError(descriptionField, descriptionErrorLabel);
   isDescriptionValid = true;
  }
 }

 private void validateMemberEmail(String email) {
  addMemberButton.setDisable(true);

  if (email.isEmpty()) {
   showError(memberEmailField, memberEmailErrorLabel, "L'email ne peut pas être vide");
   isMemberEmailValid = false;
  } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
   showError(memberEmailField, memberEmailErrorLabel, "Format d'email invalide");
   isMemberEmailValid = false;
  } else if (forumService.getUserByEmail(email) == -1) {
   showError(memberEmailField, memberEmailErrorLabel, "Cet utilisateur n'existe pas");
   isMemberEmailValid = false;
  } else {
   hideError(memberEmailField, memberEmailErrorLabel);
   isMemberEmailValid = true;
   addMemberButton.setDisable(false);
  }
 }

 private void showError(TextField field, Label errorLabel, String message) {
  field.getStyleClass().clear();
  field.getStyleClass().addAll("text-field", "text-field-error");
  errorLabel.setText(message);
  errorLabel.setVisible(true);
 }

 private void hideError(TextField field, Label errorLabel) {
  field.getStyleClass().clear();
  field.getStyleClass().addAll("text-field", "valid-field");
  errorLabel.setVisible(false);
 }

 @FXML
 private void addForum() {
  validateName(nameField.getText());
  validateEmail(createdByField.getText());
  validateDescription(descriptionField.getText());

  if (!isNameValid || !isEmailValid || !isDescriptionValid) {
   Alert alert = new Alert(Alert.AlertType.ERROR);
   alert.setTitle("Erreur de validation");
   alert.setHeaderText("Veuillez corriger les erreurs suivantes:");

   StringBuilder content = new StringBuilder();
   if (!isNameValid) content.append("- Erreur dans le nom du forum\n");
   if (!isEmailValid) content.append("- Erreur dans l'email du créateur\n");
   if (!isDescriptionValid) content.append("- Erreur dans la description\n");

   alert.setContentText(content.toString());
   alert.showAndWait();
   return;
  }

  String name = nameField.getText();
  String createdBy = createdByField.getText();
  String description = descriptionField.getText();
  boolean isPrivate = isPrivateCheckBox.isSelected();

  String listMembers = isPrivate ? String.join(",", membersListView.getItems()) : "";

  Forum forum = new Forum();
  forum.setName(name);
  forum.setDescription(description);
  forum.setDateCreation(LocalDateTime.now());
  forum.setPrivate(isPrivate);
  forum.setListMembers(listMembers);
  forum.setCreatedBy(forumService.getUserByEmail(createdBy));

  forum.setNbrMembers(membersListView.getItems().size());

  forumService.ajouter(forum);

  refreshTableFB();
  clearFieldsFP();
  refreshForums();

  Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
  successAlert.setTitle("Succès");
  successAlert.setHeaderText("Forum ajouté avec succès");
  successAlert.setContentText("Le forum a été ajouté à la base de données.");
  successAlert.showAndWait();
 }

 private boolean validateTableEdit(Forum forum, String field, String newValue) {
  StringBuilder errors = new StringBuilder();
  boolean isValid = true;

  switch(field) {
   case "name":
    if (newValue.isEmpty()) {
     errors.append("- Le nom ne peut pas être vide\n");
     isValid = false;
    }
    if (newValue.length() > 20) {
     errors.append("- Le nom ne doit pas dépasser 20 caractères\n");
     isValid = false;
    }
    if (!newValue.matches("[a-zA-Z ]+")) {
     errors.append("- Le nom ne doit contenir que des lettres et des espaces\n");
     isValid = false;
    }
    break;

   case "createdBy":
    if (newValue.isEmpty()) {
     errors.append("- L'email ne peut pas être vide\n");
     isValid = false;
    } else if (!newValue.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
     errors.append("- Format d'email invalide\n");
     isValid = false;
    } else if (forumService.getUserByEmail(newValue) == -1) {
     errors.append("- Cet utilisateur n'existe pas\n");
     isValid = false;
    }
    break;

   case "description":
    if (newValue.isEmpty()) {
     errors.append("- La description ne peut pas être vide\n");
     isValid = false;
    }
    if (newValue.length() > 100) {
     errors.append("- La description ne doit pas dépasser 100 caractères\n");
     isValid = false;
    }
    break;
  }

  if (!isValid) {
   Alert alert = new Alert(Alert.AlertType.ERROR);
   alert.setTitle("Erreur de modification");
   alert.setHeaderText("Veuillez corriger les erreurs suivantes:");
   alert.setContentText(errors.toString());
   alert.showAndWait();
   refreshTableFB();
   return false;
  }

  return true;
 }

 private void updateMemberCount() {
  if (isPrivateCheckBox.isSelected()) {
   int memberCount = membersListView.getItems().size();
   Forum selectedForum = forumTableView.getSelectionModel().getSelectedItem();
   if (selectedForum != null) {
    selectedForum.setNbrMembers(memberCount);
    selectedForum.setListMembers(String.join(",", membersListView.getItems()));
    updateForum(selectedForum);
   }
  }
 }

//***************************************
private void handlePostTypeChange() {
 String selectedType = postTypeComboBox.getValue();
 announcementFields.setVisible("Announcement".equals(selectedType));
 surveyFields.setVisible("Survey".equals(selectedType));

 if ("Announcement".equals(selectedType)) {
  clearSurveyFields();
  announcementUserField.setText(CurrentUser.getEmail());

 } else {
  clearAnnouncementFields();
  surveyUserField.setText(CurrentUser.getEmail());
  surveyAuthorField.setText(CurrentUser.getEmail());

 }

}

 private void loadForums() {
  List<Forum> forums = forumServiceFP.rechercher();
  forumComboBox.getItems().clear();
  forumComboBox.getItems().addAll(forums);
  forumComboBox.getSelectionModel().select(0);
  updateForumInfo();
  forumComboBox.setConverter(new StringConverter<Forum>() {
   @Override
   public String toString(Forum forum) {
    return forum != null ? forum.getName() : "";
   }

   @Override
   public Forum fromString(String string) {
    return null;
   }
  });
 }

 private void updateForumInfo() {
  Forum selectedForum = forumComboBox.getValue();
  if (selectedForum != null) {
   forumDescription.setText(selectedForum.getDescription());
   forumPrivacyLabel.setText(selectedForum.isPrivate() ? "Private" : "Public");
   forumDateLabel.setText("Created: " + formatDateTime(selectedForum.getDateCreation()).toString());

   if (selectedForum.isPrivate()) {
    memberCountLabel.setText("Members: " + selectedForum.getNbrMembers());
    membersSection.setVisible(true);

    membersListViewFP.getItems().clear();
    if (selectedForum.getListMembers() != null && !selectedForum.getListMembers().isEmpty()) {
     String[] members = selectedForum.getListMembers().split(",");
     for (String member : members) {
      membersListViewFP.getItems().add(member);
     }
    }
   } else {
    memberCountLabel.setText("");
    membersSection.setVisible(false);
   }
   handleBackButton();
   refreshPosts();
  }
 }

 private void setupValidationListeners() {

  announcementTitleField.textProperty().addListener((obs, old, newValue) ->
          validateAnnouncementTitle(newValue));

  announcementContentField.textProperty().addListener((obs, old, newValue) ->
          validateAnnouncementContent(newValue));

  announcementTagsField.textProperty().addListener((obs, old, newValue) ->
          validateTags(newValue, tagsErrorLabel));

  surveyQuestionField.textProperty().addListener((obs, old, newValue) ->
          validateSurveyQuestion(newValue));

  surveyTagsField.textProperty().addListener((obs, old, newValue) ->
          validateTags(newValue, surveyTagsErrorLabel));

  newCommentField.textProperty().addListener((obs, old, newValue) ->
          validateComment(newValue));
 }

   private void refreshPosts() {
      postsVBox.getChildren().clear();
      Forum selectedForum = forumComboBox.getValue();

      if (selectedForum != null &&
              ((selectedForum.isPrivate() && selectedForum.getListMembers().replace(",", " ").contains(CurrentUser.getEmail())) ||
                      !selectedForum.isPrivate() ||
                      CurrentUser.getRole() == 0)) {

         String sortCriteria = trieComboPost.getValue();
         String sortOrder = trieASCDESPost.getValue();
         String searchQuery = ChercherPost.getText().trim();

         List<Post> posts = postService.rechercher().stream()
                 .filter(p -> p.getForumId() == selectedForum.getForumId())
                 .filter(p -> "inactive".equals(trieASCDESPost.getValue())
                         ? p.getStatus().equals("inactive")
                         : p.getStatus().equals("active"))
                 .filter(p -> (p instanceof Announcement) || (p instanceof Survey))
                 .toList();

         posts = chercherPost(searchQuery, posts);

         if (sortCriteria == null || sortOrder == null) {
            System.err.println("Les critères ou l'ordre de tri sont nuls. Utilisation des valeurs par défaut.");
            sortCriteria = "Vote";
            sortOrder = "Descending";
         }

         posts = sortPosts(posts, sortCriteria, sortOrder);

         if (posts.isEmpty()) {
            Post newPost = new Announcement(
                    0,
                    selectedForum.getForumId(),
                    0,
                    0,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "",
                    "active",
                    0,
                    "Aucun publication disponible",
                    "Aucun publication pour le moment.\n\nSoyez le premier à publier !\n\n",
                    "",
                    "",
                    "",
                    ""
            );

            VBox postBox = displayFakePost(newPost);
            postsVBox.getChildren().add(postBox);
         } else {

            for (Post post : posts) {
               VBox postBox = displayPost(post);
               postsVBox.getChildren().add(postBox);
            }
         }
      } else {
          Post newPost = new Announcement(
                    0,
                    selectedForum.getForumId(),
                    0,
                    0,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "",
                    "active",
                    0,
                    "Aucun publication disponible",
                    "Ce forum est privé. Tu n'es actuellement pas membre de ce forum.",
                    "",
                    "",
                    "",
                    ""
            );

            VBox postBox = displayFakePost(newPost);
            postsVBox.getChildren().add(postBox);

      }
   }

   private VBox displayFakePost(Post post) {
      VBox postBox = new VBox(10);
      postBox.getStyleClass().add("post-box");
      postBox.setStyle("-fx-background-color: rgba(19,42,62,0.8); -fx-background-radius: 10; -fx-padding: 15;");
      postBox.setAlignment(Pos.CENTER);

      Label postTitle = new Label(post instanceof Announcement ? ((Announcement) post).getAnnouncementTitle() : "");
      postTitle.getStyleClass().add("post-title");
      postTitle.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");

      Label postContent = new Label(post instanceof Announcement ? ((Announcement) post).getAnnouncementContent() : "");
      postContent.getStyleClass().add("post-content");
      postContent.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-wrap-text: true; " +
              "-fx-alignment: center; -fx-text-alignment: center;");

      Label postDate = new Label("Posté le : " + formatDateTime(post.getDateCreation()));
      postDate.getStyleClass().add("post-metadata");
      postDate.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 22px;");

      postBox.getChildren().addAll(postTitle, postContent, postDate);
      return postBox;
   }
   
   private List<Post> sortPosts(List<Post> posts, String sortCriteria, String sortOrder) {
  Comparator<Post> comparator = null;

  switch (sortCriteria) {
   case "Date":
    comparator = Comparator.comparing(post -> {

     if (post.getDateModification() != null && post.getDateModification().isAfter(post.getDateCreation())) {
      return post.getDateModification();
     } else {
      return post.getDateCreation();
     }
    }, Comparator.nullsLast(Comparator.naturalOrder()));
    break;
   case "Title":
    comparator = Comparator.comparing(post -> {
     if (post instanceof Announcement) {
      return ((Announcement) post).getAnnouncementTitle();
     } else if (post instanceof Survey) {
      return ((Survey) post).getSurveyQuestion();
     } else {
      return "";
     }
    }, Comparator.nullsLast(Comparator.naturalOrder()));
    break;
   case "Author":
    comparator = Comparator.comparing(Post::getIdUser, Comparator.nullsLast(Comparator.naturalOrder()));
    break;
   case "Vote":
    comparator = Comparator.comparing(Post::getVotes, Comparator.nullsLast(Comparator.naturalOrder()));
    break;
   case "Nombre singal":
    comparator = Comparator.comparing(Post::getNbrSignal, Comparator.nullsLast(Comparator.reverseOrder()));
    break;
   default:
    comparator = Comparator.comparing(Post::getVotes, Comparator.nullsLast(Comparator.naturalOrder()));
  }

  if ("Descending".equals(sortOrder)) {
   comparator = comparator.reversed();
  }

  return posts.stream()
          .sorted(comparator)
          .toList();
 }

 private VBox displayPost(Post post) {
  VBox postBox = new VBox(10);
  postBox.getStyleClass().add("post-box");
  postBox.setStyle("-fx-background-color: rgba(19,42,62,0.8); -fx-background-radius: 10; -fx-padding: 15;");
  postBox.setAlignment(Pos.CENTER);

  Label postTitle = new Label();
  postTitle.getStyleClass().add("post-title");
  postTitle.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");

  Label postTags = new Label();
  postTags.getStyleClass().add("post-tags");

  Label postDate = new Label();
  postDate.getStyleClass().add("post-metadata");
  postDate.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 22px;");

  HBox voteBox = new HBox(5);
  voteBox.getStyleClass().add("vote-box");
  voteBox.setAlignment(Pos.CENTER);

  Button upVoteBtn = new Button("↑");
  upVoteBtn.getStyleClass().add("vote-button");
  upVoteBtn.setStyle("-fx-font-size: 16px; -fx-min-width: 40px; -fx-min-height: 40px; -fx-background-color: #e78d1e; -fx-text-fill: #ffffff; -fx-background-radius: 5;");

  Button downVoteBtn = new Button("↓");
  downVoteBtn.getStyleClass().add("vote-button");
  downVoteBtn.setStyle("-fx-font-size: 16px; -fx-min-width: 40px; -fx-min-height: 40px; -fx-background-color: #e78d1e; -fx-text-fill: #ffffff; -fx-background-radius: 5;");

  Label voteCount = new Label(String.valueOf(post.getVotes()));
  voteCount.getStyleClass().add("vote-count");
  voteCount.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");

  upVoteBtn.setDisable(isUserInList(post.getUpVoteList(), CurrentUser.getEmail()));
  downVoteBtn.setDisable(isUserInList(post.getDownVoteList(), CurrentUser.getEmail()));

  upVoteBtn.setOnAction(e -> {
   String currentUserEmail = CurrentUser.getEmail();

   if (isUserInList(post.getDownVoteList(), currentUserEmail)) {
    post.setDownVoteList(removeUserFromList(post.getDownVoteList(), currentUserEmail));
   }

   if (!isUserInList(post.getUpVoteList(), currentUserEmail)) {
    post.setUpVoteList(addUserToList(post.getUpVoteList(), currentUserEmail));
    post.setVotes(post.getVotes() + 1);
   }

   postService.modifier(post, false);
   refreshPosts();
  });

  downVoteBtn.setOnAction(e -> {
   String currentUserEmail = CurrentUser.getEmail();

   if (isUserInList(post.getUpVoteList(), currentUserEmail)) {
    post.setUpVoteList(removeUserFromList(post.getUpVoteList(), currentUserEmail));
   }

   if (!isUserInList(post.getDownVoteList(), currentUserEmail)) {
    post.setDownVoteList(addUserToList(post.getDownVoteList(), currentUserEmail));
    post.setVotes(post.getVotes() - 1);
   }

   postService.modifier(post, false);
   refreshPosts();
  });

  voteBox.getChildren().addAll(upVoteBtn, voteCount, downVoteBtn);

  Button viewCommentsButton = new Button("Voir Plus des détails");
  viewCommentsButton.getStyleClass().add("view-comments-button");
  viewCommentsButton.setStyle("-fx-font-size: 19px; -fx-background-color: rgba(42,131,45,0.6); -fx-text-fill: white; -fx-background-radius: 5;");
  viewCommentsButton.setOnAction(e -> showComments(post));

  Button signalButton = new Button("Signaler");
  signalButton.getStyleClass().add("signal-button");
  signalButton.setStyle("-fx-font-size: 19px; -fx-background-color: #d80f5d; -fx-text-fill: white; -fx-background-radius: 5;");
  signalButton.setDisable(isUserInList(post.getSignalList(), CurrentUser.getEmail()));

  signalButton.setOnAction(e -> {
   String currentUserEmail = CurrentUser.getEmail();

   if (!isUserInList(post.getSignalList(), currentUserEmail)) {
    post.setSignalList(addUserToList(post.getSignalList(), currentUserEmail));
    post.setNbrSignal(post.getNbrSignal() + 1);
   }

   signalButton.setDisable(true);

   postService.modifier(post, false);
   refreshPosts();
  });

  Button archiveButton = new Button();
  archiveButton.getStyleClass().add("archive-button");

  if (post.getStatus().equals("active")) {
   archiveButton.setText("Archiver");
   archiveButton.setStyle("-fx-font-size: 19px; -fx-background-color: rgba(255,168,60,0.8); -fx-text-fill: white; -fx-background-radius: 5;");
  } else {
   archiveButton.setText("Désarchiver");
   archiveButton.setStyle("-fx-font-size: 19px; -fx-background-color: #00A862FF; -fx-text-fill: white; -fx-background-radius: 5;");
  }

  archiveButton.setVisible(CurrentUser.getRole() == 0);

  archiveButton.setOnAction(e -> {

   if (post.getStatus().equals("active")) {
    post.setStatus("inactive");
    archiveButton.setText("Désarchiver");
    archiveButton.setStyle("-fx-font-size: 19px; -fx-background-color: #00A862FF; -fx-text-fill: white; -fx-background-radius: 5;");
   } else {
    post.setStatus("active");
    archiveButton.setText("Archiver");
    archiveButton.setStyle("-fx-font-size: 19px; -fx-background-color: rgba(255,168,60,0.8); -fx-text-fill: white; -fx-background-radius: 5;");
   }

   postService.modifier(post, false);
   refreshPosts();
  });

  HBox bottomRightControls = new HBox(10);
  bottomRightControls.setAlignment(Pos.BOTTOM_RIGHT);

  Button editPostButton = new Button("Modifier");
  editPostButton.setStyle("-fx-font-size: 19px; -fx-background-color: #4444ff; -fx-text-fill: white; -fx-background-radius: 5;");

  Button deletePostButton = new Button("Supprimer");
  deletePostButton.setStyle("-fx-font-size: 19px; -fx-background-color: #ff4444; -fx-text-fill: white; -fx-background-radius: 5;");

  editPostButton.setOnAction(e -> {
   editPost(post);
   handleEditButton(post);
  });

  deletePostButton.setOnAction(e -> {
   currentPost = post;
   handleDeletePost();
  });
  if( (CurrentUser.getEmail().toString().equals(forumService.getEmailById(post.getIdUser())) ||  CurrentUser.getRole() == 0   ))
  {bottomRightControls.getChildren().addAll(editPostButton, deletePostButton);}

  if(CurrentUser.getRole() == 0) {
   bottomRightControls.getChildren().add(archiveButton);

  }
  bottomRightControls.getChildren().add(signalButton);

  ImageView imageView = null;
  if (post.getCheminFichier() != null && !post.getCheminFichier().isEmpty()) {
   try {
    imageView = new ImageView(new Image(new File(post.getCheminFichier()).toURI().toString()));
    imageView.setFitWidth(600);
    imageView.setFitHeight(400);
    imageView.setPreserveRatio(true);
    imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0); -fx-background-radius: 10;");
   } catch (Exception e) {
    System.out.println("Erreur de chargement de l'image : " + e.getMessage());
   }
  }

  VBox centerContent = new VBox(10);
  centerContent.setAlignment(Pos.CENTER);

  if (post instanceof Announcement announcement) {
   postTitle.setText(announcement.getAnnouncementTitle());

   String tags = announcement.getAnnouncementTags();
   if (tags != null && !tags.isEmpty()) {
    String[] tagArray = tags.replace(",", " ").split(" ");
    StringBuilder formattedTags = new StringBuilder("Tags : ");
    for (String tag : tagArray) {
     if (!tag.isEmpty()) {
      if (!tag.startsWith("#")) {
       formattedTags.append("#").append(tag).append(" ");
      } else {
       formattedTags.append(tag).append(" ");
      }
     }
    }
    postTags.setText(formattedTags.toString().trim());
   } else {
    postTags.setText("Tags : ");
   }

   postTags.setStyle("-fx-text-fill: #e78d1e; -fx-font-size: 22px; -fx-font-weight: bold;");

   String dateText;
   if (post.getDateModification() != null && !post.getDateModification().equals(post.getDateCreation())) {
    dateText = "Edité le : " + formatDateTime(post.getDateModification());
   } else {
    dateText = "Posté le : " + formatDateTime(post.getDateCreation());
   }
   postDate.setText(dateText);

   if (imageView != null) {
    centerContent.getChildren().add(imageView);
   }
   centerContent.getChildren().addAll(postTitle);

  } else if (post instanceof Survey survey) {
   postTitle.setText(survey.getSurveyQuestion());

   String tags = survey.getSurveyTags();
   if (tags != null && !tags.isEmpty()) {
    String[] tagArray = tags.replace(",", " ").split(" ");
    StringBuilder formattedTags = new StringBuilder("Tags : ");
    for (String tag : tagArray) {
     if (!tag.isEmpty()) {
      if (!tag.startsWith("#")) {
       formattedTags.append("#").append(tag).append(" ");
      } else {
       formattedTags.append(tag).append(" ");
      }
     }
    }
    postTags.setText(formattedTags.toString().trim());
   } else {
    postTags.setText("Tags : ");
   }

   postTags.setStyle("-fx-text-fill: #e78d1e; -fx-font-size: 22px; -fx-font-weight: bold;");

   String dateText;
   if (post.getDateModification() != null && !post.getDateModification().equals(post.getDateCreation())) {
    dateText = "Edité le : " + formatDateTime(post.getDateModification());
   } else {
    dateText = "Posté le : " + formatDateTime(post.getDateCreation());
   }
   postDate.setText(dateText);

   if (imageView != null) {
    centerContent.getChildren().add(imageView);
   }
   centerContent.getChildren().add(postTitle);

   VBox choicesBox = new VBox(5);
   choicesBox.setAlignment(Pos.CENTER);

   List<Choix> choices = choixService.rechercher().stream()
           .filter(c -> c.getPostId() == survey.getPostId())
           .toList();

   int totalVotes = choices.stream()
           .mapToInt(Choix::getChoiceVotesCount)
           .sum();

   boolean hasVoted = false;
   if (survey.getSurveyUserList() != null && !survey.getSurveyUserList().isEmpty() && CurrentUser != null) {
    String currentUserId = String.valueOf(CurrentUser.getEmail());
    String[] votedUsers = survey.getSurveyUserList().split(",");
    for (String userId : votedUsers) {
     if (userId.trim().equals(currentUserId)) {
      hasVoted = true;
      break;
     }
    }
   }

   for (Choix choice : choices) {
    HBox choiceBox = new HBox(10);
    choiceBox.setAlignment(Pos.CENTER);

    Button voteBtn = new Button(choice.getChoix());
    voteBtn.setStyle("-fx-font-size: 24px; -fx-background-color: #e78d1e; -fx-text-fill: #ffffff; -fx-background-radius: 5;");

    if (!hasVoted) {
     voteBtn.setOnAction(e -> {
      handleChoiceVote(choice);

      String updatedList = survey.getSurveyUserList();
      if (updatedList == null || updatedList.isEmpty()) {
       updatedList = String.valueOf(CurrentUser.getEmail());
      } else {
       updatedList += "," + CurrentUser.getEmail();
      }
      survey.setSurveyUserList(updatedList);

      postService.modifier(survey, false);

      voteBtn.setDisable(true);
      refreshPosts();
     });
    } else {
     voteBtn.setDisable(true);
    }

    Label voteInfo = new Label(String.format("%d votes (%.1f%%)",
            choice.getChoiceVotesCount(),
            totalVotes > 0 ? (choice.getChoiceVotesCount() * 100.0f) / totalVotes : 0));
    voteInfo.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");

    choiceBox.getChildren().addAll(voteBtn, voteInfo);
    choicesBox.getChildren().add(choiceBox);
   }

   centerContent.getChildren().add(choicesBox);
  }

  VBox leftContent = new VBox(10);
  leftContent.setAlignment(Pos.CENTER_LEFT);
  leftContent.getChildren().addAll(postTags, postDate, viewCommentsButton);

  postBox.getChildren().addAll(centerContent, leftContent, voteBox, bottomRightControls);

  return postBox;
 }

 private void showPostDetails(Post post) {
  postDetailsBox.getChildren().clear();
  postDetailsBox.setStyle("-fx-background-color: rgba(19,42,62,0.8); -fx-background-radius: 10; -fx-padding: 15;");
  postDetailsBox.setAlignment(Pos.CENTER);

  HBox userInfoBox = new HBox(10);
  userInfoBox.setAlignment(Pos.CENTER_LEFT);

  ImageView userImageView = new ImageView();
  userImageView.setFitWidth(40);
  userImageView.setFitHeight(40);
  userImageView.setPreserveRatio(true);
  userImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0); -fx-background-radius: 20;");

  ClientServices clientServices = new ClientServices();
  String userEmail = forumService.getEmailById(post.getIdUser());
  Image userImage = clientServices.loadImageFromDatabase(userEmail);
  if (userImage != null) {
   userImageView.setImage(userImage);
  }

  Label userNameLabel = new Label(forumService.getUserFullNameById(post.getIdUser()));
  userNameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");

  userInfoBox.getChildren().addAll(userImageView, userNameLabel);

  Label titleLabel = new Label();
  titleLabel.getStyleClass().add("post-title");
  titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");

  Label contentLabel = new Label();
  contentLabel.getStyleClass().add("post-content");
  contentLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");
  contentLabel.setWrapText(true);

  Label tagsLabel = new Label();
  tagsLabel.getStyleClass().add("post-tags");

  Label postDate = new Label();
  postDate.getStyleClass().add("post-metadata");
  postDate.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 22px;");

  HBox votingBox = new HBox(5);
  votingBox.getStyleClass().add("vote-box");
  votingBox.setAlignment(Pos.CENTER);

  Button upVoteBtn = new Button("↑");
  upVoteBtn.getStyleClass().add("vote-button");
  upVoteBtn.setStyle("-fx-font-size: 16px; -fx-min-width: 40px; -fx-min-height: 40px; -fx-background-color: #e78d1e; -fx-text-fill: #ffffff; -fx-background-radius: 5;");

  Button downVoteBtn = new Button("↓");
  downVoteBtn.getStyleClass().add("vote-button");
  downVoteBtn.setStyle("-fx-font-size: 16px; -fx-min-width: 40px; -fx-min-height: 40px; -fx-background-color: #e78d1e; -fx-text-fill: #ffffff; -fx-background-radius: 5;");

  Label voteCount = new Label(String.valueOf(post.getVotes()));
  voteCount.getStyleClass().add("vote-count");
  voteCount.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");

  upVoteBtn.setDisable(isUserInList(post.getUpVoteList(), CurrentUser.getEmail()));
  downVoteBtn.setDisable(isUserInList(post.getDownVoteList(), CurrentUser.getEmail()));

  upVoteBtn.setOnAction(e -> {
   String currentUserEmail = CurrentUser.getEmail();

   if (isUserInList(post.getDownVoteList(), currentUserEmail)) {
    post.setDownVoteList(removeUserFromList(post.getDownVoteList(), currentUserEmail));
   }

   if (!isUserInList(post.getUpVoteList(), currentUserEmail)) {
    post.setUpVoteList(addUserToList(post.getUpVoteList(), currentUserEmail));
    post.setVotes(post.getVotes() + 1);
   }

   postService.modifier(post, false);
   showPostDetails(post);
  });

  downVoteBtn.setOnAction(e -> {
   String currentUserEmail = CurrentUser.getEmail();

   if (isUserInList(post.getUpVoteList(), currentUserEmail)) {
    post.setUpVoteList(removeUserFromList(post.getUpVoteList(), currentUserEmail));
   }

   if (!isUserInList(post.getDownVoteList(), currentUserEmail)) {
    post.setDownVoteList(addUserToList(post.getDownVoteList(), currentUserEmail));
    post.setVotes(post.getVotes() - 1);
   }

   postService.modifier(post, false);
   showPostDetails(post);
  });

  votingBox.getChildren().addAll(upVoteBtn, voteCount, downVoteBtn);

  Button viewCommentsButton = new Button("Voir Plus des détails");
  viewCommentsButton.getStyleClass().add("view-comments-button");
  viewCommentsButton.setStyle("-fx-font-size: 19px; -fx-background-color: rgba(42,131,45,0.6); -fx-text-fill: white; -fx-background-radius: 5;");
  viewCommentsButton.setOnAction(e -> showComments(post));

  Button signalButton = new Button("Signaler");
  signalButton.getStyleClass().add("signal-button");
  signalButton.setStyle("-fx-font-size: 19px; -fx-background-color: #d80f5d; -fx-text-fill: white; -fx-background-radius: 5;");
  signalButton.setDisable(isUserInList(post.getSignalList(), CurrentUser.getEmail()));

  signalButton.setOnAction(e -> {
   String currentUserEmail = CurrentUser.getEmail();

   if (!isUserInList(post.getSignalList(), currentUserEmail)) {
    post.setSignalList(addUserToList(post.getSignalList(), currentUserEmail));
    post.setNbrSignal(post.getNbrSignal() + 1);
   }

   signalButton.setDisable(true);

   postService.modifier(post, false);
   showPostDetails(post);
  });

  Button archiveButton = new Button();
  archiveButton.getStyleClass().add("archive-button");

  if (post.getStatus().equals("active")) {
   archiveButton.setText("Archiver");
   archiveButton.setStyle("-fx-font-size: 19px; -fx-background-color: rgba(255,168,60,0.8); -fx-text-fill: white; -fx-background-radius: 5;");
  } else {
   archiveButton.setText("Désarchiver");
   archiveButton.setStyle("-fx-font-size: 19px; -fx-background-color: #00A862FF; -fx-text-fill: white; -fx-background-radius: 5;");
  }

  archiveButton.setVisible(CurrentUser.getRole() == 0);

  archiveButton.setOnAction(e -> {
   if (post.getStatus().equals("active")) {
    post.setStatus("inactive");
    archiveButton.setText("Désarchiver");
    archiveButton.setStyle("-fx-font-size: 19px; -fx-background-color: #00A862FF; -fx-text-fill: white; -fx-background-radius: 5;");
   } else {
    post.setStatus("active");
    archiveButton.setText("Archiver");
    archiveButton.setStyle("-fx-font-size: 19px; -fx-background-color: rgba(255,168,60,0.8); -fx-text-fill: white; -fx-background-radius: 5;");
   }

   postService.modifier(post, false);
   showPostDetails(post);
  });

  HBox bottomRightControls = new HBox(10);
  bottomRightControls.setAlignment(Pos.BOTTOM_RIGHT);
  Button editBtn = new Button("Modifier");
  editBtn.setStyle("-fx-font-size: 19px; -fx-background-color: #4444ff; -fx-text-fill: white; -fx-background-radius: 5;");

  Button deleteBtn = new Button("Supprimer");
  deleteBtn.setStyle("-fx-font-size: 19px; -fx-background-color: #ff4444; -fx-text-fill: white; -fx-background-radius: 5;");

  editBtn.setOnAction(e -> handleEditButton(post));
  deleteBtn.setOnAction(e -> handleDeletePost(post));
  if( (CurrentUser.getEmail().toString().equals(forumService.getEmailById(post.getIdUser())) ||  CurrentUser.getRole() == 0   ))
  {bottomRightControls.getChildren().addAll(editBtn, deleteBtn);}

  if(CurrentUser.getRole() == 0) {
   bottomRightControls.getChildren().add(archiveButton);

  }
  bottomRightControls.getChildren().add(signalButton);

  ImageView imageView = null;
  if (post.getCheminFichier() != null && !post.getCheminFichier().isEmpty()) {
   try {
    imageView = new ImageView(new Image(new File(post.getCheminFichier()).toURI().toString()));
    imageView.setFitWidth(600);
    imageView.setFitHeight(400);
    imageView.setPreserveRatio(true);
    imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0); -fx-background-radius: 10;");
   } catch (Exception e) {
    System.out.println("Erreur de chargement de l'image : " + e.getMessage());
   }
  }

  VBox centerContent = new VBox(10);
  centerContent.setAlignment(Pos.CENTER);

  if (post instanceof Announcement announcement) {
   titleLabel.setText(announcement.getAnnouncementTitle());
   contentLabel.setText(announcement.getAnnouncementContent());

   String tags = announcement.getAnnouncementTags();
   if (tags != null && !tags.isEmpty()) {
    String[] tagArray = tags.replace(",", " ").split(" ");
    StringBuilder formattedTags = new StringBuilder("Tags : ");
    for (String tag : tagArray) {
     if (!tag.isEmpty()) {
      if (!tag.startsWith("#")) {
       formattedTags.append("#").append(tag).append(" ");
      } else {
       formattedTags.append(tag).append(" ");
      }
     }
    }
    tagsLabel.setText(formattedTags.toString().trim());
   } else {
    tagsLabel.setText("Tags : ");
   }

   tagsLabel.setStyle("-fx-text-fill: #e78d1e; -fx-font-size: 22px; -fx-font-weight: bold;");

   String dateText;
   if (post.getDateModification() != null && !post.getDateModification().equals(post.getDateCreation())) {
    dateText = "Edité le : " + formatDateTime(post.getDateModification());
   } else {
    dateText = "Posté le : " + formatDateTime(post.getDateCreation());
   }
   postDate.setText(dateText);

   if (imageView != null) {
    centerContent.getChildren().add(imageView);
   }
   centerContent.getChildren().addAll(titleLabel, contentLabel);

  } else if (post instanceof Survey survey) {
   titleLabel.setText(survey.getSurveyQuestion());

   String tags = survey.getSurveyTags();
   if (tags != null && !tags.isEmpty()) {
    String[] tagArray = tags.replace(",", " ").split(" ");
    StringBuilder formattedTags = new StringBuilder("Tags : ");
    for (String tag : tagArray) {
     if (!tag.isEmpty()) {
      if (!tag.startsWith("#")) {
       formattedTags.append("#").append(tag).append(" ");
      } else {
       formattedTags.append(tag).append(" ");
      }
     }
    }
    tagsLabel.setText(formattedTags.toString().trim());
   } else {
    tagsLabel.setText("Tags : ");
   }
   tagsLabel.setStyle("-fx-text-fill: #e78d1e; -fx-font-size: 22px; -fx-font-weight: bold;");

   String dateText;
   if (post.getDateModification() != null && !post.getDateModification().equals(post.getDateCreation())) {
    dateText = "Edité le : " + formatDateTime(post.getDateModification());
   } else {
    dateText = "Posté le : " + formatDateTime(post.getDateCreation());
   }
   postDate.setText(dateText);

   if (imageView != null) {
    centerContent.getChildren().add(imageView);
   }
   centerContent.getChildren().add(titleLabel);

   VBox choicesBox = new VBox(5);
   choicesBox.setAlignment(Pos.CENTER);

   List<Choix> choices = choixService.rechercher().stream()
           .filter(c -> c.getPostId() == survey.getPostId())
           .toList();
   int totalVotes = choices.stream()
           .mapToInt(Choix::getChoiceVotesCount)
           .sum();

   boolean hasVoted = false;
   if (survey.getSurveyUserList() != null && !survey.getSurveyUserList().isEmpty() && CurrentUser != null) {
    String currentUserEmail = CurrentUser.getEmail();
    String[] votedUsers = survey.getSurveyUserList().split(",");
    for (String userEmailV : votedUsers) {
     if (userEmailV.trim().equals(currentUserEmail)) {
      hasVoted = true;
      break;
     }
    }
   }

   for (Choix choice : choices) {
    HBox choiceBox = new HBox(10);
    choiceBox.setAlignment(Pos.CENTER);

    Button voteBtn = new Button(choice.getChoix());
    voteBtn.setStyle("-fx-font-size: 24px; -fx-background-color: #e78d1e; -fx-text-fill: #ffffff; -fx-background-radius: 5;");

    if (!hasVoted) {
     voteBtn.setOnAction(e -> {
      handleChoiceVote(choice);

      String updatedList = survey.getSurveyUserList();
      if (updatedList == null || updatedList.isEmpty()) {
       updatedList = CurrentUser.getEmail();
      } else {
       updatedList += "," + CurrentUser.getEmail();
      }
      survey.setSurveyUserList(updatedList);

      postService.modifier(survey, false);

      voteBtn.setDisable(true);
      showComments(post);
      refreshPosts();
     });
    } else {
     voteBtn.setDisable(true);
    }

    Label voteInfo = new Label(String.format("%d votes (%.1f%%)",
            choice.getChoiceVotesCount(),
            totalVotes > 0 ? (choice.getChoiceVotesCount() * 100.0f) / totalVotes : 0));
    voteInfo.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");

    choiceBox.getChildren().addAll(voteBtn, voteInfo);
    choicesBox.getChildren().add(choiceBox);
   }
   centerContent.getChildren().add(choicesBox);
  }

  VBox leftContent = new VBox(10);
  leftContent.setAlignment(Pos.CENTER_LEFT);
  leftContent.getChildren().addAll(tagsLabel, postDate);

  VBox mainContent = new VBox(10);
  mainContent.getChildren().addAll(userInfoBox, centerContent);

  postDetailsBox.getChildren().addAll(mainContent, leftContent, votingBox, bottomRightControls);
 }

 private boolean isUserInList(String userList, String userEmail) {
  if (userList == null || userList.isEmpty()) {
   return false;
  }
  String[] users = userList.split(",");
  for (String user : users) {
   if (user.trim().equals(userEmail)) {
    return true;
   }
  }
  return false;
 }

 private String addUserToList(String userList, String userEmail) {
  if (userList == null || userList.isEmpty()) {
   return userEmail;
  }
  return userList + "," + userEmail;
 }

 private String removeUserFromList(String userList, String userEmail) {
  if (userList == null || userList.isEmpty()) {
   return "";
  }
  String[] users = userList.split(",");
  StringBuilder newList = new StringBuilder();
  for (String user : users) {
   if (!user.trim().equals(userEmail)) {
    if (!newList.isEmpty()) {
     newList.append(",");
    }
    newList.append(user);
   }
  }
  return newList.toString();
 }
    //+++++++++++
    private void showComments(Post post) {
     currentPost = post;

     leftTabPane.getSelectionModel().select(1);

     showPostDetails(post);

     loadComments();
    }

 private void loadComments() {

  commentsVBox.getChildren().clear();

  List<Comment> comments = postService.rechercher().stream()
          .filter(p -> p instanceof Comment)
          .map(p -> (Comment) p)
          .filter(c -> c.getParentId() == currentPost.getPostId())
          .toList();

  for (Comment comment : comments) {
   displayComment(comment);
  }
 }

 private void displayComment(Comment comment) {
  HBox commentBox = new HBox(10);
  commentBox.getStyleClass().add("comment-box");
  commentBox.setStyle("-fx-background-color: rgba(19,42,62,0.8); -fx-background-radius: 10; -fx-padding: 10;");

  HBox topSection = new HBox(10);
  topSection.setAlignment(Pos.TOP_LEFT);

  Button replyBtn = new Button("Répondre");
  replyBtn.getStyleClass().add("reply-button");
  replyBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #e78d1e; -fx-text-fill: white; -fx-background-radius: 5;");
  replyBtn.setOnAction(e -> showReplyInput(comment, commentBox));

  Button signalButton = new Button("Signaler");
  signalButton.getStyleClass().add("signal-button");
  signalButton.setStyle("-fx-font-size: 14px; -fx-background-color: #d80f5d; -fx-text-fill: white; -fx-background-radius: 5;");
  signalButton.setDisable(isUserInList(comment.getSignalList(), CurrentUser.getEmail()));

  signalButton.setOnAction(e -> {
   String currentUserEmail = CurrentUser.getEmail();

   if (!isUserInList(comment.getSignalList(), currentUserEmail)) {
    comment.setSignalList(addUserToList(comment.getSignalList(), currentUserEmail));
    comment.setNbrSignal(comment.getNbrSignal() + 1);
   }

   signalButton.setDisable(true);

   postService.modifier(comment, false);
   showPostDetails(comment);
  });

  Button archiveButton = new Button();
  archiveButton.getStyleClass().add("archive-button");

  if (comment.getStatus().equals("active")) {
   archiveButton.setText("Archiver");
   archiveButton.setStyle("-fx-font-size: 14px; -fx-background-color: rgba(255,168,60,0.8); -fx-text-fill: white; -fx-background-radius: 5;");
  } else {
   archiveButton.setText("Désarchiver");
   archiveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #00A862FF; -fx-text-fill: white; -fx-background-radius: 5;");
  }

  archiveButton.setVisible(CurrentUser.getRole() == 0);

  archiveButton.setOnAction(e -> {
   if (comment.getStatus().equals("active")) {
    comment.setStatus("inactive");
    archiveButton.setText("Désarchiver");
    archiveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #00A862FF; -fx-text-fill: white; -fx-background-radius: 5;");
   } else {
    comment.setStatus("active");
    archiveButton.setText("Archiver");
    archiveButton.setStyle("-fx-font-size: 14px; -fx-background-color: rgba(255,168,60,0.8); -fx-text-fill: white; -fx-background-radius: 5;");
   }

   postService.modifier(comment, false);
   showPostDetails(comment);
  });

  HBox buttonsBox = new HBox(5);
  buttonsBox.setAlignment(Pos.BASELINE_LEFT);
  buttonsBox.getChildren().addAll(replyBtn);

  Button editBtn = new Button("Editer");
  editBtn.getStyleClass().add("edit-button");
  editBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #4444ff; -fx-text-fill: white; -fx-background-radius: 5;");

  Button deleteBtn = new Button("Supprimer");
  deleteBtn.getStyleClass().add("delete-button");
  deleteBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #ff4444; -fx-text-fill: white; -fx-background-radius: 5;");

  editBtn.setOnAction(e -> handleEditComment(comment));
  deleteBtn.setOnAction(e -> handleDeleteComment(comment));

  if( (CurrentUser.getEmail().toString().equals(forumService.getEmailById(comment.getIdUser())) ||  CurrentUser.getRole() == 0   ))
  {buttonsBox.getChildren().addAll(editBtn, deleteBtn);}

  if(CurrentUser.getRole() == 0) {
   buttonsBox.getChildren().add(archiveButton);

  }
  buttonsBox.getChildren().add(signalButton);

  HBox userInfoBox = new HBox(10);
  userInfoBox.setAlignment(Pos.TOP_RIGHT);

  ImageView userImageView = new ImageView();
  userImageView.setFitWidth(30);
  userImageView.setFitHeight(30);
  userImageView.setPreserveRatio(true);
  userImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0); -fx-background-radius: 15;");

  ClientServices clientServices = new ClientServices();
  String userEmail = forumService.getEmailById(comment.getIdUser());
  Image userImage = clientServices.loadImageFromDatabase(userEmail);
  if (userImage != null) {
   userImageView.setImage(userImage);
  }

  Label userNameLabel = new Label(forumService.getUserFullNameById(comment.getIdUser()));
  userNameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

  userInfoBox.getChildren().addAll(userImageView, userNameLabel);

  topSection.getChildren().addAll(userInfoBox);

  Label contentLabel = new Label(comment.getCommentContent());
  contentLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-wrap-text: true; -fx-pref-width: 1500 ");

  Label dateLabel = new Label(formatDateTime(comment.getDateCreation()).toString());
  dateLabel.getStyleClass().add("comment-date");
  dateLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");

  VBox contentBox = new VBox(5);
  contentBox.setAlignment(Pos.CENTER_LEFT);
  contentBox.getChildren().addAll(topSection, contentLabel, dateLabel, buttonsBox);

  commentBox.getChildren().add(contentBox);

  commentsVBox.getChildren().add(commentBox);

  displayReplies(comment,0,commentsVBox);
 }

 private void showReplyInput(Comment parentComment, HBox parentCommentBox) {

  TextField replyField = new TextField();
  replyField.setPromptText("Écrivez votre réponse...");
  replyField.setStyle("-fx-font-size: 14px; -fx-background-radius: 5;");

  Button confirmBtn = new Button("Confirmer");
  confirmBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5;");

  Button cancelBtn = new Button("Annuler");
  cancelBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white; -fx-background-radius: 5;");

  HBox replyInputBox = new HBox(10);
  replyInputBox.setAlignment(Pos.CENTER_LEFT);
  replyInputBox.setStyle("-fx-padding: 10 0 0 20;");
  replyInputBox.getChildren().addAll(replyField, confirmBtn, cancelBtn);

  VBox parentContainer = (VBox) parentCommentBox.getParent();
  int parentIndex = parentContainer.getChildren().indexOf(parentCommentBox);

  parentContainer.getChildren().add(parentIndex + 1, replyInputBox);

  confirmBtn.setOnAction(e -> {
   String replyContent = replyField.getText().trim();
   if (replyContent.isEmpty()) {
    showAlertFP("Erreur", "La réponse ne peut pas être vide", Alert.AlertType.ERROR);
    return;
   }

   Comment reply = new Comment(
           0,
           parentComment.getForumId(),
           CurrentUser.getId(),
           0,
           LocalDateTime.now(),
           LocalDateTime.now(),
           "",
           "active",
           0,
           replyContent,
           parentComment.getPostId(),
           "",
           "",
           ""
   );

   if (containsBadWords(reply)) {
    showAlertFP("Detecter", "Votre réponse contient du contenu inapproprié. Veuillez réviser votre réponse.", Alert.AlertType.ERROR);
    return;
   }

   if (isPostingTooFrequently(reply)) {
      showAlertWithCountdown("Temps d'attente", "Veuillez patienter un moment avant de répondez  à nouveau.", Alert.AlertType.ERROR);
      return;
   }

   postService.ajouter(reply);
   PostingTry=0;
   parentContainer.getChildren().remove(replyInputBox);

   showComments(currentPost);
  });

  cancelBtn.setOnAction(e -> parentContainer.getChildren().remove(replyInputBox));
 }

 private void displayReplies(Comment parentComment, int layer, VBox parentRepliesVBox) {
  List<Comment> replies = postService.rechercher().stream()
          .filter(p -> p instanceof Comment)
          .map(p -> (Comment) p)
          .filter(c -> c.getParentId() == parentComment.getPostId())
          .toList();

  if (replies.isEmpty()) {
   return;
  }

  VBox repliesVBox = new VBox(5);
  repliesVBox.setStyle("-fx-padding: 0 0 0 " + (20 + (layer * 5)) + ";");

  parentRepliesVBox.getChildren().add(repliesVBox);

  for (Comment reply : replies) {
   HBox replyBox = new HBox(10);
   replyBox.getStyleClass().add("comment-box");
   replyBox.setStyle("-fx-background-color: rgba(19,42,62,0.8); -fx-background-radius: 10; -fx-padding: 10;");

   HBox topSection = new HBox(10);
   topSection.setAlignment(Pos.TOP_LEFT);

   Button signalButton = new Button("Signaler");
   signalButton.getStyleClass().add("signal-button");
   signalButton.setStyle("-fx-font-size: 14px; -fx-background-color: #d80f5d; -fx-text-fill: white; -fx-background-radius: 5;");
   signalButton.setDisable(isUserInList(parentComment.getSignalList(), CurrentUser.getEmail()));

   signalButton.setOnAction(e -> {
    String currentUserEmail = CurrentUser.getEmail();

    if (!isUserInList(parentComment.getSignalList(), currentUserEmail)) {
     parentComment.setSignalList(addUserToList(parentComment.getSignalList(), currentUserEmail));
     parentComment.setNbrSignal(parentComment.getNbrSignal() + 1);
    }

    signalButton.setDisable(true);

    postService.modifier(parentComment, false);
    showPostDetails(parentComment);
   });

   Button archiveButton = new Button();
   archiveButton.getStyleClass().add("archive-button");

   if (parentComment.getStatus().equals("active")) {
    archiveButton.setText("Archiver");
    archiveButton.setStyle("-fx-font-size: 14px; -fx-background-color: rgba(255,168,60,0.8); -fx-text-fill: white; -fx-background-radius: 5;");
   } else {
    archiveButton.setText("Désarchiver");
    archiveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #00A862FF; -fx-text-fill: white; -fx-background-radius: 5;");
   }

   archiveButton.setVisible(CurrentUser.getRole() == 0);

   archiveButton.setOnAction(e -> {
    if (parentComment.getStatus().equals("active")) {
     parentComment.setStatus("inactive");
     archiveButton.setText("Désarchiver");
     archiveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #00A862FF; -fx-text-fill: white; -fx-background-radius: 5;");
    } else {
     parentComment.setStatus("active");
     archiveButton.setText("Archiver");
     archiveButton.setStyle("-fx-font-size: 14px; -fx-background-color: rgba(255,168,60,0.8); -fx-text-fill: white; -fx-background-radius: 5;");
    }

    postService.modifier(parentComment, false);
    showPostDetails(parentComment);
   });

   HBox buttonsBox = new HBox(5);
   buttonsBox.setAlignment(Pos.BASELINE_LEFT);

   Button replyBtn = new Button("Répondre");
   replyBtn.getStyleClass().add("reply-button");
   replyBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #e78d1e; -fx-text-fill: white; -fx-background-radius: 5;");
   buttonsBox.getChildren().addAll(replyBtn);
   Button editBtn = new Button("Editer");
   editBtn.getStyleClass().add("edit-button");
   editBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #4444ff; -fx-text-fill: white; -fx-background-radius: 5;");

   Button deleteBtn = new Button("Supprimer");
   deleteBtn.getStyleClass().add("delete-button");
   deleteBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #ff4444; -fx-text-fill: white; -fx-background-radius: 5;");

   replyBtn.setOnAction(e -> showReplyInput(reply, replyBox));

   editBtn.setOnAction(e -> handleEditComment(reply));

   deleteBtn.setOnAction(e -> handleDeleteComment(reply));
   if( (CurrentUser.getEmail().toString().equals(forumService.getEmailById(parentComment.getIdUser())) ||  CurrentUser.getRole() == 0   ))
   {buttonsBox.getChildren().addAll(editBtn, deleteBtn);}

   if(CurrentUser.getRole() == 0) {
    buttonsBox.getChildren().add(archiveButton);

   }
   buttonsBox.getChildren().add(signalButton);

   HBox userInfoBox = new HBox(10);
   userInfoBox.setAlignment(Pos.TOP_RIGHT);

   ImageView userImageView = new ImageView();
   userImageView.setFitWidth(30);
   userImageView.setFitHeight(30);
   userImageView.setPreserveRatio(true);
   userImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0); -fx-background-radius: 15;");

   ClientServices clientServices = new ClientServices();
   String userEmail = forumService.getEmailById(reply.getIdUser());
   Image userImage = clientServices.loadImageFromDatabase(userEmail);
   if (userImage != null) {
    userImageView.setImage(userImage);
   }

   Label userNameLabel = new Label(forumService.getUserFullNameById(reply.getIdUser()));
   userNameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

   userInfoBox.getChildren().addAll(userImageView, userNameLabel);

   topSection.getChildren().addAll(userInfoBox);

   Label contentLabel = new Label(reply.getCommentContent());
   contentLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-wrap-text: true;");

   Label dateLabel = new Label(formatDateTime(reply.getDateCreation()).toString());
   dateLabel.getStyleClass().add("comment-date");
   dateLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");

   VBox contentBox = new VBox(5);
   contentBox.setAlignment(Pos.CENTER_LEFT);
   contentBox.getChildren().addAll(topSection, contentLabel, dateLabel, buttonsBox);

   replyBox.getChildren().add(contentBox);

   repliesVBox.getChildren().add(replyBox);

   displayReplies(reply, layer + 1, repliesVBox);
  }
 }

 private void handleEditComment(Comment comment) {
  TextInputDialog dialog = new TextInputDialog(comment.getCommentContent());
  dialog.setTitle("Modifier le commentaire");
  dialog.setHeaderText(null);
  dialog.setContentText("Entrer un nouveau commentaire:");

  dialog.showAndWait().ifPresent(newContent -> {
   comment.setCommentContent(newContent);
   postService.modifier(comment,true);
   loadComments();
  });
 }

 private void handleDeleteComment(Comment comment) {
  Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
  confirm.setTitle("Supprimer le commentaire");
  confirm.setHeaderText("Etes-vous sûr de vouloir supprimer ce commentaire?");
  confirm.setContentText("Cette action ne peut pas être annulée.");

  if (confirm.showAndWait().get() == ButtonType.OK) {
   postService.supprimer(comment);
   loadComments();
  }
 }
 @FXML
 private void handleAddComment() {
  if (currentPost == null) return;

  String content = newCommentField.getText().trim();
  if (content.isEmpty()) {
   showError(newCommentField, commentErrorLabel, "Le commentaire ne peut pas être vide");
   return;
  }

  Comment comment = new Comment(
          0,
          currentPost.getForumId(),
          CurrentUser.getId(),
          0,
          LocalDateTime.now(),
          LocalDateTime.now(),
          "",
          "active",
          0,
          content,
          currentPost.getPostId(),
          "",
          "",
          ""
  );

  if (containsBadWords(comment)) {
   showAlertFP("Detecter", "Votre commentaire contient du contenu inapproprié. Veuillez réviser votre commentaire.", Alert.AlertType.ERROR);
   return;
  }

  if (isPostingTooFrequently(comment)) {
     showAlertWithCountdown("Temps d'attente", "Veuillez patienter un moment avant de commenter à nouveau.", Alert.AlertType.ERROR);
     return;
  }

  postService.ajouter(comment);
  PostingTry=0;
  newCommentField.clear();
  showComments(currentPost);
 }
    //+++++++++++
    private void clearSurveyFields() {
     surveyQuestionField.clear();
     surveyTagsField.clear();
     choicesListView.getItems().clear();
     choiceField.clear();
     addChoiceButton.setDisable(true);
    }

 private void clearAnnouncementFields() {
  announcementTitleField.clear();
  announcementContentField.clear();
  announcementTagsField.clear();
 }

 private void clearFieldsFB() {

  announcementTitleField.clear();
  announcementContentField.clear();
  announcementTagsField.clear();
  announcementFileField.clear();

  surveyQuestionField.clear();
  surveyTagsField.clear();
  surveyFileField.clear();

  surveyUserField.clear();
  surveyUsersListView.getItems().clear();
  choicesListView.getItems().clear();
  choiceField.clear();
  addChoiceButton.setDisable(true);

  titleErrorLabel.setVisible(false);
  contentErrorLabel.setVisible(false);
  tagsErrorLabel.setVisible(false);
  questionErrorLabel.setVisible(false);
  surveyTagsErrorLabel.setVisible(false);
  commentErrorLabel.setVisible(false);
  choiceErrorLabel.setVisible(false);
  surveyUserErrorLabel.setVisible(false);
  announcementFileErrorLabel.setVisible(false);
  surveyFileErrorLabel.setVisible(false);

  postTypeComboBox.getSelectionModel().clearSelection();
  announcementFields.setVisible(false);
  surveyFields.setVisible(false);

  newCommentField.clear();
  commentsVBox.getChildren().clear();

  clearEditFields();

  announcementUserField.clear();
  surveyAuthorField.clear();
  editAnnouncementUserField.clear();
  editSurveyAuthorField.clear();

  announcementUserErrorLabel.setVisible(false);
  surveyAuthorErrorLabel.setVisible(false);
  editAnnouncementUserErrorLabel.setVisible(false);
  editSurveyAuthorErrorLabel.setVisible(false);
 }

 private void clearEditFields() {
  editAnnouncementTitleField.clear();
  editAnnouncementContentField.clear();
  editAnnouncementTagsField.clear();
  editAnnouncementFileField.clear();
  editAnnouncementStatusComboBox.setValue("active");

  editSurveyQuestionField.clear();
  editSurveyTagsField.clear();
  editChoiceField.clear();
  editChoicesListView.getItems().clear();
  editSurveyFileField.clear();
  editSurveyStatusComboBox.setValue("active");
  editSurveyUserField.clear();
  editSurveyUsersListView.getItems().clear();

  editAnnouncementFields.setVisible(false);
  editSurveyFields.setVisible(false);
 }

    //+++++++++++
    private void validateUserEmail(TextField field, Label errorLabel, String email) {
     if (email.isEmpty()) {
      field.getStyleClass().remove("valid-field");
      field.getStyleClass().add("text-field-error");
      showError(field, errorLabel, "L'email ne peut pas être vide");
     } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
      field.getStyleClass().remove("valid-field");
      field.getStyleClass().add("text-field-error");
      showError(field, errorLabel, "Format d'email invalide");
     } else if (forumServiceFP.getUserByEmail(email) == -1) {
      field.getStyleClass().remove("valid-field");
      field.getStyleClass().add("text-field-error");
      showError(field, errorLabel, "Cet utilisateur n'existe pas");
     } else {
      field.getStyleClass().remove("text-field-error");
      field.getStyleClass().add("valid-field");
      hideError(field, errorLabel);
     }
    }

 private boolean validateUserEmail2(String email) {
  if (email.isEmpty()) {
   return false;
  } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
   return false;
  } else if (forumServiceFP.getUserByEmail(email) == -1) {
   return false;
  }
  return true;
 }

 private boolean validateImageFile(TextField fileField, Label errorLabel) {
  String filePath = fileField.getText().trim();

  if (filePath.isEmpty()) {
   hideError(fileField, errorLabel);
   return true;
  }

  String extension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();

  if (!extension.equals("jpg") && !extension.equals("png")) {
   showError(fileField, errorLabel, "Seuls les fichiers JPG ou PNG sont autorisés.");
   showAlertFP("Ce n'est pas autorisé","Seuls les fichiers JPG ou PNG sont autorisés.",Alert.AlertType.ERROR);
   return false;
  }

  hideError(fileField, errorLabel);
  return true;
 }

 private void validateAnnouncementTitle(String title) {
  if (title.isEmpty()) {
   showError(announcementTitleField, titleErrorLabel, "Le titre ne peut pas être vide");
  } else if (title.length() > 50) {
   showError(announcementTitleField, titleErrorLabel, "Le titre ne doit pas dépasser 50 caractères");
  } else {
   hideError(announcementTitleField, titleErrorLabel);
  }
 }

 private void validateAnnouncementContent(String content) {
  if (content.isEmpty()) {
   showError(announcementContentField, contentErrorLabel, "Le contenu ne peut pas être vide");
  } else if (content.length() > 500) {
   showError(announcementContentField, contentErrorLabel, "Le contenu ne doit pas dépasser 500 caractères");
  } else {
   hideError(announcementContentField, contentErrorLabel);
  }
 }

 private void validateTags(String tags, Label errorLabel) {
  TextField field = tags.equals(surveyTagsField.getText()) ? surveyTagsField : announcementTagsField;

  if (tags.length() > 100) {
   showError(field, errorLabel, "Les tags ne doivent pas dépasser 100 caractères");
   return;
  }

  if (!tags.isEmpty()) {

   if (!tags.matches("^[a-zA-Z0-9,\\s]+$")) {
    showError(field, errorLabel, "Les tags ne doivent contenir que des lettres, chiffres et virgules");
    return;
   }

   String[] tagArray = tags.split(",");
   Set<String> uniqueTags = new HashSet<>();

   for (String tag : tagArray) {
    String trimmedTag = tag.trim();
    if (!trimmedTag.isEmpty()) {
     if (!uniqueTags.add(trimmedTag)) {
      showError(field, errorLabel, "Tag '" + trimmedTag + "' est en double");
      return;
     }
    }
   }

   for (String tag : tagArray) {
    String trimmedTag = tag.trim();
    if (trimmedTag.length() > 20) {
     showError(field, errorLabel, "Chaque tag ne doit pas dépasser 20 caractères");
     return;
    }
   }
  }

  hideError(field, errorLabel);
 }

 private void validateComment(String comment) {
  if (comment.isEmpty()) {
   showError(newCommentField, commentErrorLabel, "Le commentaire ne peut pas être vide");
  } else if (comment.length() > 200) {
   showError(newCommentField, commentErrorLabel, "Le commentaire ne doit pas dépasser 200 caractères");
  } else {
   hideError(newCommentField, commentErrorLabel);
  }
 }

 private void validateChoice(String choice) {
  if (choice.isEmpty()) {
   addChoiceButton.setDisable(true);
   showError(choiceField, choiceErrorLabel, "Le choix ne peut pas être vide");
  } else if (choice.length() > 50) {
   addChoiceButton.setDisable(true);
   showError(choiceField, choiceErrorLabel, "Le choix ne doit pas dépasser 50 caractères");
  } else if (choicesListView.getItems().contains(choice)) {
   addChoiceButton.setDisable(true);
   showError(choiceField, choiceErrorLabel, "Ce choix existe déjà");
  } else {
   hideError(choiceField, choiceErrorLabel);
   addChoiceButton.setDisable(false);
  }
 }

 private void validateSurveyQuestion(String question) {
  if (question.isEmpty()) {
   showError(surveyQuestionField, questionErrorLabel, "La question ne peut pas être vide");
  } else if (question.length() > 200) {
   showError(surveyQuestionField, questionErrorLabel, "La question ne doit pas dépasser 200 caractères");
  } else {
   hideError(surveyQuestionField, questionErrorLabel);
  }
 }

 private void validateSurveyUser(String email) {
  if (email.isEmpty()) {
   addSurveyUserButton.setDisable(true);
   showError(surveyUserField, surveyUserErrorLabel, "L'email ne peut pas être vide");
  } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
   addSurveyUserButton.setDisable(true);
   showError(surveyUserField, surveyUserErrorLabel, "Format d'email invalide");
  } else if (forumServiceFP.getUserByEmail(email) == -1) {
   addSurveyUserButton.setDisable(true);
   showError(surveyUserField, surveyUserErrorLabel, "Cet utilisateur n'existe pas");
  } else if (surveyUsersListView.getItems().contains(email)) {
   addSurveyUserButton.setDisable(true);
   showError(surveyUserField, surveyUserErrorLabel, "Cet utilisateur est déjà dans la liste");
  } else {
   hideError(surveyUserField, surveyUserErrorLabel);
   addSurveyUserButton.setDisable(false);
  }
 }

 private void validateEditChoice(String choice) {
  if (choice.isEmpty()) {
   editChoiceField.getStyleClass().remove("valid-field");
   editChoiceField.getStyleClass().add("text-field-error");
   editAddChoiceButton.setDisable(true);
   showError(editChoiceField, editChoiceErrorLabel, "Le choix ne peut pas être vide");
  } else if (choice.length() > 50) {
   editChoiceField.getStyleClass().remove("valid-field");
   editChoiceField.getStyleClass().add("text-field-error");
   editAddChoiceButton.setDisable(true);
   showError(editChoiceField, editChoiceErrorLabel, "Le choix ne doit pas dépasser 50 caractères");
  } else if (editChoicesListView.getItems().contains(choice)) {
   editChoiceField.getStyleClass().remove("valid-field");
   editChoiceField.getStyleClass().add("text-field-error");
   editAddChoiceButton.setDisable(true);
   showError(editChoiceField, editChoiceErrorLabel, "Ce choix existe déjà");
  } else {
   editChoiceField.getStyleClass().remove("text-field-error");
   editChoiceField.getStyleClass().add("valid-field");
   hideError(editChoiceField, editChoiceErrorLabel);
   editAddChoiceButton.setDisable(false);
  }
 }

 private void validateEditSurveyUser(String email) {
  if (email.isEmpty()) {
   editAddSurveyUserButton.setDisable(true);
   showError(editSurveyUserField, editSurveyUserErrorLabel, "L'email ne peut pas être vide");
  } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
   editAddSurveyUserButton.setDisable(true);
   showError(editSurveyUserField, editSurveyUserErrorLabel, "Format d'email invalide");
  } else if (forumServiceFP.getUserByEmail(email) == -1) {
   editAddSurveyUserButton.setDisable(true);
   showError(editSurveyUserField, editSurveyUserErrorLabel, "Cet utilisateur n'existe pas");
  } else if (editSurveyUsersListView.getItems().contains(email)) {
   editAddSurveyUserButton.setDisable(true);
   showError(editSurveyUserField, editSurveyUserErrorLabel, "Cet utilisateur est déjà dans la liste");
  } else {
   hideError(editSurveyUserField, editSurveyUserErrorLabel);
   editAddSurveyUserButton.setDisable(false);
  }
 }

 private void validateEditTitle(String title) {
  if (title.isEmpty()) {
   showError(editAnnouncementTitleField, editTitleErrorLabel, "Le titre ne peut pas être vide");
  } else if (title.length() > 50) {
   showError(editAnnouncementTitleField, editTitleErrorLabel, "Le titre ne doit pas dépasser 50 caractères");
  } else {
   hideError(editAnnouncementTitleField, editTitleErrorLabel);
  }
 }

 private void validateEditContent(String content) {
  if (content.isEmpty()) {
   showError(editAnnouncementContentField, editContentErrorLabel, "Le contenu ne peut pas être vide");
  } else if (content.length() > 500) {
   showError(editAnnouncementContentField, editContentErrorLabel, "Le contenu ne doit pas dépasser 500 caractères");
  } else {
   hideError(editAnnouncementContentField, editContentErrorLabel);
  }
 }

 private void validateEditQuestion(String question) {
  if (question.isEmpty()) {
   showError(editSurveyQuestionField, editSurveyQuestionErrorLabel, "La question ne peut pas être vide");
  } else if (question.length() > 200) {
   showError(editSurveyQuestionField, editSurveyQuestionErrorLabel, "La question ne doit pas dépasser 200 caractères");
  } else {
   hideError(editSurveyQuestionField, editSurveyQuestionErrorLabel);
  }
 }

 private void validateEditTags(String tags, Label errorLabel) {
  TextField field = tags.equals(editSurveyTagsField.getText()) ? editSurveyTagsField : editAnnouncementTagsField;

  if (tags.length() > 100) {
   showError(field, errorLabel, "Les tags ne doivent pas dépasser 100 caractères");
   return;
  }

  if (!tags.isEmpty()) {
   if (!tags.matches("^[a-zA-Z0-9,\\s]+$")) {
    showError(field, errorLabel, "Les tags ne doivent contenir que des lettres, chiffres et virgules");
    return;
   }

   String[] tagArray = tags.split(",");
   Set<String> uniqueTags = new HashSet<>();

   for (String tag : tagArray) {
    String trimmedTag = tag.trim();
    if (!trimmedTag.isEmpty()) {
     if (!uniqueTags.add(trimmedTag)) {
      showError(field, errorLabel, "Tag '" + trimmedTag + "' est en double");
      return;
     }
    }
   }

   for (String tag : tagArray) {
    String trimmedTag = tag.trim();
    if (trimmedTag.length() > 20) {
     showError(field, errorLabel, "Chaque tag ne doit pas dépasser 20 caractères");
     return;
    }
   }
  }

  hideError(field, errorLabel);
 }
    //+++++++++++
    private void showError(TextInputControl field, Label errorLabel, String message) {
     if (field != null) {
      field.getStyleClass().removeAll("valid-fieldFP", "text-field-errorFP");
      field.getStyleClass().addAll("text-fieldFP", "text-field-errorFP");
     }
     if (errorLabel != null) {
      errorLabel.getStyleClass().add("error-labelFP");
      errorLabel.setText(message);
      errorLabel.setVisible(true);
     }
    }

 private void hideError(TextInputControl field, Label errorLabel) {
  if (field != null) {
   field.getStyleClass().removeAll("text-field-errorFP", "valid-fieldFP");
   field.getStyleClass().addAll("text-fieldFP", "valid-fieldFP");
  }
  if (errorLabel != null) {
   errorLabel.setVisible(false);
  }
 }
//+++++++++++
private void editPost(Post post) {
     currentPost = post;

     if (post instanceof Announcement announcement) {
      editAnnouncementFields.setVisible(true);
      editSurveyFields.setVisible(false);

      editAnnouncementTitleField.setText(announcement.getAnnouncementTitle());
      editAnnouncementContentField.setText(announcement.getAnnouncementContent());
      editAnnouncementTagsField.setText(announcement.getAnnouncementTags());
      editAnnouncementFileField.setText(announcement.getCheminFichier());
      editAnnouncementStatusComboBox.setValue(announcement.getStatus());
      editAnnouncementUserField.setText(forumServiceFP.getEmailById(post.getIdUser()));

     } else if (post instanceof Survey survey) {
      editAnnouncementFields.setVisible(false);
      editSurveyFields.setVisible(true);

      editSurveyQuestionField.setText(survey.getSurveyQuestion());
      editSurveyTagsField.setText(survey.getSurveyTags());
      editSurveyFileField.setText(survey.getCheminFichier());
      editSurveyStatusComboBox.setValue(survey.getStatus());
      editSurveyAuthorField.setText(forumServiceFP.getEmailById(post.getIdUser()));

      editChoicesListView.getItems().clear();
      List<Choix> choices = choixService.rechercher().stream()
              .filter(c -> c.getPostId() == survey.getPostId())
              .toList();

      for (Choix choice : choices) {
       editChoicesListView.getItems().add(choice.getChoix());
      }

      editSurveyUsersListView.getItems().clear();
      if (survey.getSurveyUserList() != null && !survey.getSurveyUserList().isEmpty()) {
       editSurveyUsersListView.getItems().addAll(
               survey.getSurveyUserList().split(",")
       );
      }
     }
    }

 private void handleEditButton(Post post) {
  currentPost = post;

  rightTabPane.getSelectionModel().select(2);
  editPost(post);

 }

 private void handleDeletePost(Post post) {
  if (currentPost == null) return;

  Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
  confirm.setTitle("Delete Post");
  confirm.setHeaderText("Etes-vous sûr de vouloir supprimer ce publication?");
  confirm.setContentText("Cette action ne peut pas être annulée.");

  if (confirm.showAndWait().get() == ButtonType.OK) {
   postService.supprimer(currentPost);
   refreshPosts();
   leftTabPane.getSelectionModel().select(0);
  }
 }

 @FXML private void handleDeletePost() {
  if (currentPost == null) return;

  Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
  confirm.setTitle("Delete Post");
  confirm.setHeaderText("Etes-vous sûr de vouloir supprimer ce publication?");
  confirm.setContentText("Cette action ne peut pas être annulée.");

  if (confirm.showAndWait().get() == ButtonType.OK) {
   postService.supprimer(currentPost);
   refreshPosts();
   leftTabPane.getSelectionModel().select(0);
  }
 }

 @FXML private void handleUpdatePost() {
  if (currentPost == null) return;
  String upvoteList = currentPost.getUpVoteList();
  String downvoteList = currentPost.getDownVoteList();
  String signalvoteList= currentPost.getSignalList();

  if (currentPost instanceof Announcement announcement) {
   String title = editAnnouncementTitleField.getText().trim();
   String content = editAnnouncementContentField.getText().trim();
   String tags = editAnnouncementTagsField.getText().trim();
   String filePath = editAnnouncementFileField.getText().trim();
   String status = editAnnouncementStatusComboBox.getValue();
   String userEmail = editAnnouncementUserField.getText().trim();

   StringBuilder errors = new StringBuilder();
   if (title.isEmpty()) errors.append("- Le titre ne peut pas être vide\n");
   if (title.length() > 50) errors.append("- Le titre ne doit pas dépasser 50 caractères\n");
   if (content.isEmpty()) errors.append("- Le contenu ne peut pas être vide\n");
   if (content.length() > 500) errors.append("- Le contenu ne doit pas dépasser 500 caractères\n");
   if (userEmail.isEmpty()) errors.append("- L'e-mail de l'utilisateur ne peut pas être vide\n");
   if (!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) errors.append("- Format d'e-mail non valide\n");

   int userId = forumServiceFP.getUserByEmail(userEmail);
   if (userId == -1) errors.append("- Utilisateur non trouvé\n");

   if (!errors.isEmpty()) {
    showAlertFP("Erreur de validation", errors.toString(), Alert.AlertType.ERROR);
    return;
   }

   try {
    announcement.setAnnouncementTitle(title);
    announcement.setAnnouncementContent(content);
    announcement.setAnnouncementTags(tags);
    announcement.setCheminFichier(filePath);
    announcement.setStatus(status);
    announcement.setDateModification(LocalDateTime.now());
    announcement.setIdUser(userId);

    announcement.setUpVoteList(upvoteList);
    announcement.setDownVoteList(downvoteList);
    announcement.setSignalList(signalvoteList);
    System.out.println(announcement.getUpVoteList());
    System.out.println(announcement.getDownVoteList());
    System.out.println(announcement.getSignalList());

    postService.modifier(announcement,true);
    showAlertFP("Success", "Annonce mise à jour avec succès", Alert.AlertType.INFORMATION);
    refreshPosts();
    clearEditFields();
    leftTabPane.getSelectionModel().select(0);
    handleBackLeft();
   } catch (Exception e) {
    showAlertFP("Erreur", "Impossible de mettre à jour l'annonce: " + e.getMessage(), Alert.AlertType.ERROR);
   }

  }
  else if (currentPost instanceof Survey survey) {
   String question = editSurveyQuestionField.getText().trim();
   String tags = editSurveyTagsField.getText().trim();
   String filePath = editSurveyFileField.getText().trim();
   String status = editSurveyStatusComboBox.getValue();
   String userEmail = editSurveyAuthorField.getText().trim();
   List<String> choices = new ArrayList<>(editChoicesListView.getItems());
   List<String> users = new ArrayList<>(editSurveyUsersListView.getItems());

   StringBuilder errors = new StringBuilder();
   if (question.isEmpty()) errors.append("- La question ne peut pas être vide\n");
   if (question.length() > 200) errors.append("- La question ne doit pas dépasser 200 caractères\n");
   if (choices.size() < 2) errors.append("- L'enquête doit comporter au moins deux choix\n");
   if (userEmail.isEmpty()) errors.append("- L'e-mail de l'utilisateur ne peut pas être vide\n");
   if (!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) errors.append("- Format d'e-mail non valide\n");

   int userId = forumServiceFP.getUserByEmail(userEmail);
   if (userId == -1) errors.append("- Utilisateur non trouvé\n");

   if (!errors.isEmpty()) {
    showAlertFP("Erreur de validation", errors.toString(), Alert.AlertType.ERROR);
    return;
   }

   try {
    survey.setSurveyQuestion(question);
    survey.setSurveyTags(tags);
    survey.setCheminFichier(filePath);
    survey.setStatus(status);
    survey.setDateModification(LocalDateTime.now());
    survey.setIdUser(userId);
    survey.setSurveyUserList(String.join(",", users));
    survey.setUpVoteList(upvoteList);
    survey.setDownVoteList(downvoteList);
    survey.setSignalList(signalvoteList);

    List<Choix> existingChoices = choixService.rechercher().stream()
            .filter(c -> c.getPostId() == survey.getPostId())
            .toList();

    for (Choix existingChoice : existingChoices) {
     if (!choices.contains(existingChoice.getChoix())) {
      choixService.supprimer(existingChoice);
     }
    }

    for (String choiceText : choices) {
     if (existingChoices.stream().noneMatch(c -> c.getChoix().equals(choiceText))) {
      Choix newChoice = new Choix(0, survey.getPostId(), choiceText, 0, 0);
      choixService.ajouter(newChoice);
     }
    }

    postService.modifier(survey,true);
    showAlertFP("Success", "Enquête mise à jour avec succès", Alert.AlertType.INFORMATION);
    refreshPosts();
    clearEditFields();
    leftTabPane.getSelectionModel().select(0);
    handleBackLeft();
   } catch (Exception e) {
    showAlertFP("Erreur de validation", "Impossible de mettre à jour l'enquête: " + e.getMessage(), Alert.AlertType.ERROR);
   }
  }
 }

 //+++++++++++
 @FXML
 private void handleBackButton() {
  leftTabPane.getSelectionModel().select(0);
  currentPost = null;
  newCommentField.clear();
 }

 @FXML
 private void handleBackLeft() {
  rightTabPane.getSelectionModel().select(0);
 }

 @FXML
 private void handleAddPostButton() {
  rightTabPane.getSelectionModel().select(1);
  postTypeComboBox.getSelectionModel().select(0);
  handlePostTypeChange();
  announcementUserField.setText(CurrentUser.getEmail());
 }
//+++++++++++
private String formatDateTime(LocalDateTime dateTime) {
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
     return dateTime.format(formatter);
    }

 private void handleVote(Post post, boolean isUpvote) {
  if (isUpvote) {
   post.setVotes(post.getVotes() + 1);
  } else {
   post.setVotes(post.getVotes() - 1);
  }
  postService.modifier(post,false);
  refreshPosts();

  if (leftTabPane.getSelectionModel().getSelectedIndex() == 1) {
   showPostDetails(post);
  }
 }

 private void handleChoiceVote(Choix choice) {
  choice.setChoiceVotesCount(choice.getChoiceVotesCount() + 1);
  choixService.modifier(choice);
  refreshPosts();

  if (leftTabPane.getSelectionModel().getSelectedIndex() == 1) {
   showPostDetails(currentPost);
  }
 }

 private void addRemoveButton(ListView<String> listView, String itemToRemove) {
  listView.setCellFactory(lv -> new ListCell<String>() {
   @Override
   protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (empty || item == null) {
     setGraphic(null);
    } else {
     HBox cell = new HBox(10);
     Label label = new Label(item);
     Button deleteBtn = new Button("Supprimer");
     deleteBtn.getStyleClass().add("delete-button");
     deleteBtn.setOnAction(e -> listView.getItems().remove(item));
     cell.getChildren().addAll(label, deleteBtn);
     setGraphic(cell);
    }
   }
  });

  listView.getItems().add(itemToRemove);

  listView.setOnKeyPressed(e -> {
   if (e.getCode() == KeyCode.DELETE) {
    String selectedItem = listView.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
     listView.getItems().remove(selectedItem);
    }
   }
  });
 }

//+++++++++++
@FXML
private void handleAddChoice() {
 String choice = choiceField.getText().trim();
 if (!choice.isEmpty() && !choicesListView.getItems().contains(choice)) {
  addRemoveButton(choicesListView, choice);
  choiceField.clear();
  addChoiceButton.setDisable(true);
 }
}

 @FXML
 private void handleAddSurveyUser() {
  String email = surveyUserField.getText().trim();
  if (!email.isEmpty() && !surveyUsersListView.getItems().contains(email)) {
   addRemoveButton(surveyUsersListView, email);
   surveyUserField.clear();
   addSurveyUserButton.setDisable(true);
  }
 }

 @FXML
 private void handleBrowseFile(ActionEvent event) {
  FileChooser fileChooser = new FileChooser();
  fileChooser.setTitle("Sélectionner le fichier");

  FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Fichiers d'images", "*.jpg", "*.png");
  fileChooser.getExtensionFilters().add(imageFilter);

  File file = fileChooser.showOpenDialog(null);
  if (file != null) {
   String fileName = file.getName();
   String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

   if (!extension.equals("jpg") && !extension.equals("png")) {
    showAlertFP("Fichier invalide", "Seuls les fichiers JPG ou PNG sont autorisés.", Alert.AlertType.ERROR);
    return;
   }

   Button button = (Button) event.getSource();
   HBox hbox = (HBox) button.getParent();
   TextField fileField = (TextField) hbox.getChildren().get(0);
   fileField.setText(file.getAbsolutePath());
  }
 }

 @FXML
 private void handleAddEditChoice() {
  String choice = editChoiceField.getText().trim();
  if (!choice.isEmpty() && !editChoicesListView.getItems().contains(choice)) {
   addRemoveButton(editChoicesListView, choice);
   editChoiceField.clear();
   editAddChoiceButton.setDisable(true);
  }
 }

 @FXML
 private void handleAddEditSurveyUser() {
  String email = editSurveyUserField.getText().trim();
  if (!email.isEmpty() && !editSurveyUsersListView.getItems().contains(email)) {
   addRemoveButton(editSurveyUsersListView, email);
   editSurveyUserField.clear();
   editAddSurveyUserButton.setDisable(true);
  }
 }

 @FXML
 private void handleBrowseAnnouncementFile(ActionEvent actionEvent) {
  handleBrowseFile(actionEvent);
 }

 @FXML
 private void handleBrowseSurveyFile(ActionEvent actionEvent) {
  handleBrowseFile(actionEvent);
 }



//****** New ForumNajdAvance
private static boolean containsBadWords(String text) {
 if(true) {
  if (text.isEmpty() || text.isBlank()) {
   return false;
  }

  try {

   String apiKey = "AIzaSyD2NWsmP1XlVcqptTeLGmvpbB-MDM57vAo";

   URL url = new URL("https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=" + apiKey);

   HttpURLConnection conn = (HttpURLConnection) url.openConnection();
   conn.setRequestMethod("POST");
   conn.setRequestProperty("Content-Type", "application/json");
   conn.setDoOutput(true);

   String jsonInput = "{\n" +
           "  \"comment\": {\n" +
           "    \"text\": \"" + text + "\"\n" +
           "  },\n" +
           "  \"languages\": [\"fr\"],\n" +
           "  \"requestedAttributes\": {\n" +
           "    \"TOXICITY\": {},\n" +
           "    \"SEVERE_TOXICITY\": {},\n" +
           "    \"INSULT\": {},\n" +
           "    \"PROFANITY\": {}\n" +
           "  }\n" +
           "}";

   try (OutputStream os = conn.getOutputStream()) {
    byte[] input = jsonInput.getBytes("utf-8");
    os.write(input, 0, input.length);
   }

   Scanner scanner = new Scanner(conn.getInputStream());
   StringBuilder response = new StringBuilder();
   while (scanner.hasNext()) {
    response.append(scanner.nextLine());
   }
   scanner.close();

   JSONObject jsonResponse = new JSONObject(response.toString());
   JSONObject attributeScores = jsonResponse.getJSONObject("attributeScores");

   double toxicityThreshold = 0.7;

   for (String attribute : attributeScores.keySet()) {
    double score = attributeScores.getJSONObject(attribute)
            .getJSONObject("summaryScore")
            .getDouble("value");
    if (score > toxicityThreshold) {
     return true;
    }
   }

   return false;
  } catch (Exception e) {
   System.out.println(e.getMessage());
   return false;
  }
 }else return false;
}

 private static boolean containsBadWords(Post post) {
  if (post instanceof Announcement announcement) {
   return containsBadWords(announcement.getAnnouncementTitle()) ||
           containsBadWords(announcement.getAnnouncementContent()) ||
           containsBadWords(announcement.getAnnouncementTags());
  } else if (post instanceof Survey survey) {
   return containsBadWords(survey.getSurveyQuestion()) ||
           containsBadWords(survey.getSurveyTags());
  } else if (post instanceof Comment comment) {
   return containsBadWords(comment.getCommentContent());
  }
  return false;
 }

 private boolean isPostingTooFrequently(Post post) {
  int SecPOst=1000;
  int TryingChanse=3;

  int POST_COOLDOWN = ( 30 * SecPOst )* ((PostingTry + TryingChanse) / TryingChanse);
  if( (PostingTry + TryingChanse) % TryingChanse == 0)
  {
   coolDownPost = POST_COOLDOWN/SecPOst;
  }
  PostingTry++;

  if (post == null) {
   return false;
  }

  String userEmail = forumServiceFP.getEmailById(post.getIdUser());
  if (userEmail == null || userEmail.isEmpty()) {
   return false;
  }

  Long lastPostTime = userLastPostTime.get(userEmail);
  long currentTime = System.currentTimeMillis();

  if (lastPostTime != null && (currentTime - lastPostTime) < (coolDownPost * SecPOst) || IsPostExist(post , 40 ) ) {
   return true;
  }

  userLastPostTime.put(userEmail, currentTime);

  return false;
 }

 private void showAlertWithCountdown(String title, String content, Alert.AlertType type) {

  if (currentTimelinePost != null) {
   currentTimelinePost.stop();
  }

  Alert alert = new Alert(type);
  alert.setTitle(title);
  alert.setHeaderText(null);
    alert.setContentText(content + "\nFermeture dans " + coolDownPost + " secondes...");

  alert.getDialogPane().getStyleClass().add("modern-alert");

    ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/images/bloquer.png")));
    currentTimelinePost = new Timeline();

    icon.setFitWidth(32);
    icon.setFitHeight(32);
    alert.setGraphic(icon);

    KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
   coolDownPost--;
   alert.setContentText(content + "\nFermeture dans " + coolDownPost + " secondes...");
   if (coolDownPost <= 0) {
    coolDownPost = 0;
    PostingTry =0;
    alert.close();
    currentTimelinePost.stop();
    currentTimelinePost= new Timeline();
   }
  });

  currentTimelinePost.setCycleCount(Timeline.INDEFINITE);
  currentTimelinePost.getKeyFrames().add(frame);
  currentTimelinePost.play();

  alert.showAndWait();
 }

 private void showAlertFP(String title, String content, Alert.AlertType type) {
  Alert alert = new Alert(type);
  alert.setTitle(title);
  alert.setHeaderText(null);
  alert.setContentText(content);

  alert.getDialogPane().getStyleClass().add("modern-alert");
    ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/images/primary.png")));
    currentTimelinePost = new Timeline();

    icon.setFitWidth(128);
    icon.setFitHeight(128);
    alert.setGraphic(icon);
  alert.showAndWait();
 }

 private boolean IsPostExist(Post post, int Persantage) {
  if (post == null || Persantage < 0 ) {
   return false;
  }

  String postContent = getPostContent(post);
  if (postContent == null || postContent.isEmpty()) {
   return false;
  }

  String[] postWords = postContent.toLowerCase().split("\\s+");

  List<Post> existingPosts = postService.rechercher();

  for (Post existingPost : existingPosts) {
   if (existingPost == null) {
    continue;
   }

   String existingPostContent = getPostContent(existingPost);
   if (existingPostContent == null || existingPostContent.isEmpty()) {
    continue;
   }

   String[] existingPostWords = existingPostContent.toLowerCase().split("[\\s,]+");

   int commonWords = 0;
   for (String word : postWords) {
    for (String existingWord : existingPostWords) {
     if (word.equals(existingWord)) {
      commonWords++;
      break;
     }
    }
   }

   double similarityPercentage = (double) commonWords / postWords.length * 100;

     if (similarityPercentage >= Persantage) {
        showAlertFP("Post similaire détecté",
                "Un publication avec un contenu similaire existe déjà. Veuillez modifier votre contenu ou consulter les publication existants.",
                Alert.AlertType.ERROR);
        return true;
     }

  }

  return false;
 }

 private String getPostContent(Post post) {

  if (post instanceof Survey survey) {
   return survey.getSurveyQuestion()+" " +survey.getSurveyTags();
  } else if (post instanceof Announcement announcement) {
   return announcement.getAnnouncementTitle()+" "+ announcement.getAnnouncementContent()+""+announcement.getAnnouncementTags();
  } else {
   Comment comment=(Comment) post;
   return comment.getCommentContent();
  }
 }

 private void Asma3niField(TextField textField){
  textField.textProperty().addListener((observable, oldValue, newValue) -> {
   if (newValue.isEmpty()) {
    suggestionsMenu.hide();
   } else {
    showSuggestions(textField,newValue);
   }
  });

 }

 private void showSuggestions(TextField textField, String input) {
  if (input.isEmpty()) {
   return;
  }

  suggestionsMenu.getItems().clear();

  List<String> suggestions = getSuggestions(input);

  if (!suggestions.isEmpty()) {
   int count = 0;
   for (String suggestion : suggestions) {
    if (count >= 7) break;
    MenuItem item = new MenuItem(suggestion);
    item.setOnAction(event -> textField.setText(suggestion));
    suggestionsMenu.getItems().add(item);
    count++;
   }

   suggestionsMenu.show(textField,
           textField.localToScreen(0, textField.getHeight()).getX(),
           textField.localToScreen(0, textField.getHeight()).getY());
  }
 }

 public static List<String> getSuggestions(String query) {
  List<String> suggestions = new ArrayList<>();
  try {
   String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
   String apiUrl = "http://suggestqueries.google.com/complete/search?client=firefox&hl=fr&q=" + encodedQuery;

   HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
   conn.setRequestMethod("GET");
   conn.setRequestProperty("Accept", "application/json");

   BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
   StringBuilder response = new StringBuilder();
   String line;
   while ((line = reader.readLine()) != null) {
    response.append(line);
   }
   reader.close();

   String jsonResponse = response.toString();
   int start = jsonResponse.indexOf('[') + 1;
   int end = jsonResponse.lastIndexOf(']');
   if (start > 0 && end > start) {
    String[] results = jsonResponse.substring(start, end).split(",");
    for (String result : results) {
     suggestions.add(result.replaceAll("[\\[\\]\"]", "").trim());
    }
   }
  } catch (Exception e) {
   e.printStackTrace();
  }
  return suggestions;
 }

 public List<Post> chercherPost(String query, List<Post> posts) {
  if (query == null || query.isEmpty()) {
   return posts;
  }

  List<Post> results = new ArrayList<>();

  for (Post post : posts) {
   boolean matchFound = false;

   String dateString = post.getDateCreation().toString().replace("T", " ");
   String dateMString = post.getDateModification().toString().replace("T", " ");

   if (dateString.contains(query) || dateMString.contains(query)) {
    matchFound = true;
   }

   if (!matchFound) {
    if (post instanceof Announcement announcement) {
     matchFound = (announcement.getAnnouncementTitle() != null && announcement.getAnnouncementTitle().toLowerCase().contains(query.toLowerCase())) ||
             (announcement.getAnnouncementTags() != null && announcement.getAnnouncementTags().toLowerCase().contains(query.toLowerCase())) ||
             (announcement.getAnnouncementContent() != null && announcement.getAnnouncementContent().toLowerCase().contains(query.toLowerCase()));
    } else if (post instanceof Survey survey) {
     matchFound = (survey.getSurveyQuestion() != null && survey.getSurveyQuestion().toLowerCase().contains(query.toLowerCase())) ||
             (survey.getSurveyTags() != null && survey.getSurveyTags().toLowerCase().contains(query.toLowerCase()));
    }
   }

   if (matchFound) {
    results.add(post);
   }
  }

  return results;
 }

 @FXML
 private void handleSubmitPost() {
  String selectedType = postTypeComboBox.getValue();
  Forum selectedForum = forumComboBox.getValue();

  if (selectedType == null) {
   showAlertFP("Erreur", "Type de poste requis", Alert.AlertType.ERROR);
   return;
  }

  if (selectedForum == null) {
   showAlertFP("Erreur", "Forum requis", Alert.AlertType.ERROR);
   return;
  }

  Post newPost = null;
  String userEmail = "";

  if ("Announcement".equals(selectedType)) {
   String title = announcementTitleField.getText().trim();
   String content = announcementContentField.getText().trim();
   String tags = announcementTagsField.getText().trim();

   StringBuilder errors = new StringBuilder();
   if (title.isEmpty()) errors.append("- Le titre ne peut pas être vide\n");
   if (title.length() > 50) errors.append("- Le titre ne doit pas dépasser 50 caractères\n");
   if (content.isEmpty()) errors.append("- Le contenu ne peut pas être vide\n");
   if (content.length() > 500) errors.append("- Le contenu ne doit pas dépasser 500 caractères\n");

   if (!errors.isEmpty()) {
    showAlertFP("Erreur de validation", errors.toString(), Alert.AlertType.ERROR);
    return;
   }

   userEmail = announcementUserField.getText().trim();

   if (userEmail.isEmpty()) {
    showAlertFP("Erreur de validation", "L'e-mail de l'utilisateur ne peut pas être vide", Alert.AlertType.ERROR);
    return;
   }

   int userId = forumServiceFP.getUserByEmail(userEmail);
   if (userId == -1) {
    showAlertFP("Erreur de validation", "Utilisateur non trouvé", Alert.AlertType.ERROR);
    return;
   }

   newPost = new Announcement(
           0,
           selectedForum.getForumId(),
           userId,
           0,
           LocalDateTime.now(),
           LocalDateTime.now(),
           announcementFileField.getText(),
           "active",
           0,
           title,
           content,
           tags,
           "",
           "",
           ""
   );

  } else if ("Survey".equals(selectedType)) {
   String question = surveyQuestionField.getText().trim();
   String tags = surveyTagsField.getText().trim();
   List<String> choices = new ArrayList<>(choicesListView.getItems());
   List<String> users = new ArrayList<>(surveyUsersListView.getItems());

   StringBuilder errors = new StringBuilder();
   if (question.isEmpty()) errors.append("- La question ne peut pas être vide\n");
   if (question.length() > 200) errors.append("- La question ne doit pas dépasser 200 caractères\n");
   if (choices.size() < 2) errors.append("- L'enquête doit comporter au moins deux choix\n");

   if (!errors.isEmpty()) {
    showAlertFP("Erreur de validation", errors.toString(), Alert.AlertType.ERROR);
    return;
   }

   userEmail = surveyAuthorField.getText().trim();

   if (userEmail.isEmpty()) {
    showAlertFP("Erreur de validation", "L'e-mail de l'utilisateur ne peut pas être vide", Alert.AlertType.ERROR);
    return;
   }

   int userId = forumServiceFP.getUserByEmail(userEmail);
   if (userId == -1) {
    showAlertFP("Erreur de validation", "Utilisateur non trouvé", Alert.AlertType.ERROR);
    return;
   }

   newPost = new Survey(
           0,
           selectedForum.getForumId(),
           userId,
           0,
           LocalDateTime.now(),
           LocalDateTime.now(),
           surveyFileField.getText(),
           "active",
           0,
           question,
           tags,
           String.join(",", users),
           "",
           "",
           ""

   );
  }

  if (containsBadWords(newPost)) {
   showAlertFP("Detecter", "Votre publication contient du contenu inapproprié. Veuillez réviser votre publication.", Alert.AlertType.ERROR);
   return;
  }

  if (isPostingTooFrequently(newPost)) {
     showAlertWithCountdown("Temps d'attente", "Vous avez violé nos normes communautaires. Veuillez patienter un moment avant de publier à nouveau.", Alert.AlertType.ERROR);     return;
  }

  try {
   postService.ajouter(newPost);
   PostingTry=0;
   if (newPost instanceof Survey) {
    Survey survey = (Survey) newPost;
    for (String choiceText : choicesListView.getItems()) {
     Choix choice = new Choix(0, survey.getPostId(), choiceText, 0, 0);
     choixService.ajouter(choice);
    }
   }

   showAlertFP("Success", "Message envoyé avec succès!", Alert.AlertType.INFORMATION);
   refreshPosts();
   clearFieldsFB();
   handleBackLeft();
   AutoBotCommentOnPost(newPost);
  } catch (Exception e) {
   showAlertFP("Error", "Failed to post: " + e.getMessage(), Alert.AlertType.ERROR);
  }
 }

 private void AutoBotCommentOnPost(Post newPost) {

  String postContent = "";
  String botComment="";
  String prompt_ai = "Imagine Tu es un bot d'agence de voyage. Commenter bref dans ce (Annonce ou Sondage) ";
  int botId=0;

  if (newPost instanceof Announcement announcement) {

   postContent = "Annonce Titre: " + announcement.getAnnouncementTitle() + " "
           + "Annonce Contenu: " + announcement.getAnnouncementContent();
  } else if (newPost instanceof Survey survey) {

   List<String> choices = choixService.rechercher().stream()
           .filter(c -> c.getPostId() == survey.getPostId())
           .map(Choix::getChoix)
           .toList();

   postContent = "Sondage Question: " + survey.getSurveyQuestion() + " "
           + "Choix: " + String.join(", ", choices);
  }

  botComment = DeepSeekService.generateComment(prompt_ai + postContent);

  String FinalComment =
          "Bonjour et bienvenue sur le forum " + forumComboBox.getValue().getName() + " !\n"
                  + "TripToGo, votre agence de voyage, vous souhaite une excellente expérience.\n\n"
                  + botComment + "\n\n"
                  + "Je suis un bot, et cette action a été effectuée automatiquement.\n"
                  + "Veuillez contacter les modérateurs du forum pour toute question ou préoccupation.";

  if (FinalComment.isEmpty()) {
   return;
  }

  Comment comment = new Comment(
          0,
          newPost.getForumId(),
          botId,
          0,
          LocalDateTime.now(),
          LocalDateTime.now(),
          "",
          "active",
          0,
          FinalComment,
          newPost.getPostId()
          ,"",
          "",
          ""
  );

  postService.ajouter(comment);
 }

 //--------------------------------------------------------------------------------------------------------




}