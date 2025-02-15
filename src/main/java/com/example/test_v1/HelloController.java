package com.example.test_v1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class HelloController {

    // Hyperlinks for navigation
    @FXML
    private Hyperlink Chambre_to_Hotel;
    @FXML
    private Hyperlink Hotel_to_Chambre;
    @FXML
    private Hyperlink account_to_login;
    @FXML
    private Hyperlink login_to_account;

    // Buttons
    @FXML
    private Button account_bt_enregistrer;
    @FXML

    private Button login_bt_login;


    // TextFields and PasswordFields for user input
    @FXML
    private TextField account_email;
    @FXML
    private PasswordField account_password;
    @FXML
    private TextField account_username;
    @FXML
    private PasswordField login_password;
    @FXML
    private TextField login_username;

    // AnchorPanes for different pages
    @FXML
    private AnchorPane page_account;
    @FXML
    private AnchorPane page_chambre;
    @FXML
    private AnchorPane page_hotel;
    @FXML
    private AnchorPane page_login;
    @FXML
    private AnchorPane menu;

    // Labels for displaying text
    @FXML
    private Label welcomeText;
    @FXML
    private Label welcomeText1;
    @FXML
    private Label welcomeText2;
    @FXML
    private Label welcomeText21;
    @FXML
    private Label welcomeText211;
    @FXML
    private Label welcomeText2111;

@FXML
private Button dashboard_button_hotel;
    @FXML
    private Button dashboard_button_chambre;

    @FXML
    private MenuBar menuBar;
    @FXML
    protected void onHelloButtonClick() {
        // Add functionality here
    }

    // Method to change the visible page based on the event source
    @FXML
    public void changeForum(javafx.event.ActionEvent actionEvent) {
        if (actionEvent.getSource() == Chambre_to_Hotel) {
            page_chambre.setVisible(false);
            page_login.setVisible(false);
            page_account.setVisible(false);
            page_hotel.setVisible(true);
        } else if (actionEvent.getSource() == Hotel_to_Chambre) {
            page_login.setVisible(false);
            page_account.setVisible(false);
            page_hotel.setVisible(false);
            page_chambre.setVisible(true);
        } else if (actionEvent.getSource() == login_to_account) {
            page_chambre.setVisible(false);
            page_login.setVisible(false);
            page_hotel.setVisible(false);
            page_account.setVisible(true);
        } else if (actionEvent.getSource() == account_to_login) {
            page_chambre.setVisible(false);
            page_account.setVisible(false);
            page_hotel.setVisible(false);
            page_login.setVisible(true);
        }
    }


    @FXML
    protected void on_bt_login_clicked() {

        //recupéere les donnes
        //controle saisie
        //connexion base de donne
        //insertion donner
        //changement du interface

        page_chambre.setVisible(false);
        page_account.setVisible(false);
        page_hotel.setVisible(false);
        page_login.setVisible(false);
        menu.setVisible(true);

    }
    @FXML
    protected void first_page() {

        page_chambre.setVisible(false);
        page_account.setVisible(false);
        page_hotel.setVisible(false);
        page_login.setVisible(true);
        menu.setVisible(false);


    }

    @FXML
    protected void f_dashboard_button_hotel() {

        page_chambre.setVisible(false);
        page_account.setVisible(false);
        page_hotel.setVisible(true);
        page_login.setVisible(false);


    }
    @FXML
    protected void f_dashboard_button_chambre() {

        page_chambre.setVisible(true);
        page_account.setVisible(false);
        page_hotel.setVisible(false);
        page_login.setVisible(false);


    }
    @FXML
    protected void f_dashboard_button_user() {

        page_chambre.setVisible(false);
        page_account.setVisible(false);
        page_hotel.setVisible(false);
        page_login.setVisible(true);


    }
}
