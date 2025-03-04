package com.wolfs.controllers;

import com.wolfs.models.ClasseChaise;
import com.wolfs.models.Vol;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

// Remove the incorrect import
// import javax.swing.text.Document;
// Keep only the Jsoup Document import
import org.jsoup.nodes.Document;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VolController {

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
    private TextField tfDeparture, tfDestination, tfAirline, tfFlightPrice, tfAvailableSeats, tfDescription;

    // Replace plain text fields for time with a DatePicker + TextField combo
    @FXML
    private DatePicker dpDepartureDate, dpArrivalDate;
    @FXML
    private TextField tfDepartureTime, tfArrivalTime; // Expect time input in "HH:mm"

    @FXML
    private Button btnAddVol;

    @FXML
    private ListView<String> lvNews;  // ListView to display news articles

    @FXML
    private Button btnRefreshNews;   // Button to trigger news fetch

    @FXML
    private TextField txtCountry;  // Country name input field
    @FXML
    private Button btnFetchCountry; // Fetch button
    @FXML
    private Label lblCountryName;  // Display country name
    @FXML
    private Label lblCapital;     // Display capital name
    @FXML
    private ImageView imgFlag;    // Display flag image

    // News API URL (Replace YOUR_API_KEY with your actual key)
    private static final String APINEWS_URL = "https://newsapi.org/v2/everything?q=aviation&apiKey=17cb667ee1404ba2ab6cf550a0bf78e5";

    private HttpClient httpClient;

    @FXML
    private void initialize() {
        // Initialize ComboBox with ClasseChaise enum values
        cbClasseChaise.setItems(FXCollections.observableArrayList(ClasseChaise.values()));

        // Bind columns to Vol properties
        colDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDepartureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        colArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        colClasseChaise.setCellValueFactory(new PropertyValueFactory<>("classeChaise"));
        colAirline.setCellValueFactory(new PropertyValueFactory<>("airline"));
        colFlightPrice.setCellValueFactory(new PropertyValueFactory<>("flightPrice"));
        colAvailableSeats.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Modify Button in table
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

        // Delete Button in table
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
                        rs.getInt("FlightPrice"),
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
        ClasseChaise classeChaise = cbClasseChaise.getValue();
        String airline = tfAirline.getText();
        double flightPrice;
        int availableSeats;
        String description = tfDescription.getText();

        // Validate that all required fields are filled
        if (departure.isEmpty() || destination.isEmpty() ||
                dpDepartureDate.getValue() == null || tfDepartureTime.getText().isEmpty() ||
                dpArrivalDate.getValue() == null || tfArrivalTime.getText().isEmpty() ||
                classeChaise == null || airline.isEmpty() ||
                tfFlightPrice.getText().isEmpty() || tfAvailableSeats.getText().isEmpty() ||
                description.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        // Parse numeric fields
        try {
            flightPrice = Double.parseDouble(tfFlightPrice.getText());
            availableSeats = Integer.parseInt(tfAvailableSeats.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Prix du vol et sièges disponibles doivent être des nombres !");
            return;
        }

        // Combine date and time fields to form LocalDateTime values
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime departureTime, arrivalTime;
        try {
            LocalTime depTime = LocalTime.parse(tfDepartureTime.getText(), timeFormatter);
            departureTime = LocalDateTime.of(dpDepartureDate.getValue(), depTime);

            LocalTime arrTime = LocalTime.parse(tfArrivalTime.getText(), timeFormatter);
            arrivalTime = LocalDateTime.of(dpArrivalDate.getValue(), arrTime);
        } catch (Exception e) {
            showAlert("Erreur", "Format de l'heure incorrect (doit être HH:mm) !");
            return;
        }

        // Input control: Departure date and time must not be in the past.
        if (departureTime.isBefore(LocalDateTime.now())) {
            showAlert("Erreur", "La date et l'heure de départ ne peuvent pas être dans le passé !");
            return;
        }

        // Input control: Arrival date and time must not be in the past.
        if (arrivalTime.isBefore(LocalDateTime.now())) {
            showAlert("Erreur", "La date et l'heure d'arrivée ne peuvent pas être dans le passé !");
            return;
        }

        // Input control: Arrival must be after departure.
        if (arrivalTime.isBefore(departureTime)) {
            showAlert("Erreur", "La date et l'heure d'arrivée doivent être postérieures à celles de départ !");
            return;
        }

        // Insert new Vol record into the database
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
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ajouter le vol !");
        }
    }


    private void editVol(Vol vol) {
        System.out.println("Modifier: " + vol);

        // Create a dialog to edit flight information (for simplicity, date/time editing is omitted)
        Dialog<Vol> dialog = new Dialog<>();
        dialog.setTitle("Edit Flight Information");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField departureField = new TextField(vol.getDeparture());
        TextField destinationField = new TextField(vol.getDestination());
        TextField airlineField = new TextField(vol.getAirline());
        TextField flightPriceField = new TextField(String.valueOf(vol.getFlightPrice()));
        TextField availableSeatsField = new TextField(String.valueOf(vol.getAvailableSeats()));
        TextField descriptionField = new TextField(vol.getDescription());

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

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                vol.setDeparture(departureField.getText());
                vol.setDestination(destinationField.getText());
                vol.setAirline(airlineField.getText());
                vol.setFlightPrice(Integer.parseInt(flightPriceField.getText()));
                vol.setAvailableSeats(Integer.parseInt(availableSeatsField.getText()));
                vol.setDescription(descriptionField.getText());

                modifierVol(vol);
                System.out.println("Updated Vol: " + vol);
            }
            return vol;
        });

        dialog.showAndWait();
    }

    public void modifierVol(Vol vol) {
        String req = "UPDATE vol SET departure='" + vol.getDeparture() + "', destination='" + vol.getDestination() +
                "', departureTime='" + vol.getDepartureTime() + "', arrivalTime='" + vol.getArrivalTime() +
                "', classeChaise='" + vol.getClasseChaise() + "', airline='" + vol.getAirline() +
                "', flightPrice=" + vol.getFlightPrice() + ", availableSeats=" + vol.getAvailableSeats() +
                ", description='" + vol.getDescription() + "' WHERE flightID=" + vol.getFlightID();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wolfs", "root", "");
             Statement st = conn.createStatement()) {

            st.executeUpdate(req);
            System.out.println("Vol modifié");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void fetchNews() {
        // Make sure to print a message when the button is clicked for debugging
        System.out.println("Fetching News...");

        // Disable the button to prevent multiple clicks
        btnRefreshNews.setDisable(true);

        // Make the API call asynchronously
        httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APINEWS_URL))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::processApiResponse)
                .exceptionally(e -> {
                    handleApiError("Error fetching news: " + e.getMessage());
                    return null;
                });
    }

    private void processApiResponse(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray articles = jsonObject.getJSONArray("articles");

            // List to hold news titles
            ObservableList<String> newsList = FXCollections.observableArrayList();

            // Loop through the articles and add them to the list
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title"); // Get article title
                newsList.add(title);
            }

            // Update ListView on the JavaFX Application Thread
            Platform.runLater(() -> {
                lvNews.setItems(newsList);
                btnRefreshNews.setDisable(false);  // Re-enable button after data is loaded
            });

        } catch (Exception e) {
            handleApiError("Error processing news data: " + e.getMessage());
        }
    }

    private void handleApiError(String errorMessage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to fetch news");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            btnRefreshNews.setDisable(false); // Re-enable button in case of an error
        });
    }

    // Method to fetch country information
    @FXML
    private void fetchCountryInfo(javafx.event.ActionEvent event) {
        String countryName = txtCountry.getText().trim();

        if (!countryName.isEmpty()) {
            try {
                // Create HTTP client and request
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://restcountries.com/v3.1/name/" + countryName))
                        .GET()
                        .build();

                // Send request asynchronously
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenAccept(responseBody -> {
                            try {
                                // Parse the JSON response using JSONArray and JSONObject
                                JSONArray countries = new JSONArray(responseBody);
                                if (countries.length() > 0) {
                                    JSONObject country = countries.getJSONObject(0);

                                    // Extract country name from JSON
                                    String name = country.getJSONObject("name").getString("common");

                                    // Extract capital (safely, as some countries might not have a capital)
                                    String capital = "N/A";
                                    if (country.has("capital") && country.getJSONArray("capital").length() > 0) {
                                        capital = country.getJSONArray("capital").getString(0);
                                    }

                                    // Extract flag URL
                                    String flagUrl = country.getJSONObject("flags").getString("png");

                                    // Update UI on JavaFX thread
                                    final String finalName = name;
                                    final String finalCapital = capital;
                                    final String finalFlagUrl = flagUrl;

                                    Platform.runLater(() -> {
                                        lblCountryName.setText("Country: " + finalName);
                                        lblCapital.setText("Capital: " + finalCapital);

                                        // Load and display the country flag image
                                        Image flagImage = new Image(finalFlagUrl);
                                        imgFlag.setImage(flagImage);
                                    });
                                } else {
                                    Platform.runLater(() -> {
                                        lblCountryName.setText("Country not found");
                                        lblCapital.setText("");
                                        imgFlag.setImage(null);
                                    });
                                }
                            } catch (Exception e) {
                                Platform.runLater(() -> {
                                    lblCountryName.setText("Error parsing data: " + e.getMessage());
                                    lblCapital.setText("");
                                    imgFlag.setImage(null);
                                });
                            }
                        })
                        .exceptionally(e -> {
                            Platform.runLater(() -> {
                                lblCountryName.setText("Error fetching data: " + e.getMessage());
                                lblCapital.setText("");
                                imgFlag.setImage(null);
                            });
                            return null;
                        });

            } catch (Exception e) {
                lblCountryName.setText("Error: " + e.getMessage());
                lblCapital.setText("");
                imgFlag.setImage(null);
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}