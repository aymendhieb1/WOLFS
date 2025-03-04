package com.wolfs.controllers;

import com.wolfs.models.CheckoutVol;
import com.wolfs.models.Vol;
import com.wolfs.models.ExchangeRate;
import com.wolfs.services.CheckoutVolService;
import com.wolfs.services.VolService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.application.Platform;

public class CheckoutVolController {

    @FXML private ComboBox<Integer> cbFlightID;         // ComboBox for FlightID (for adding)
    @FXML private TextField tfAircraft;                   // Auto-filled and non-editable
    @FXML private TextField tfFlightCrew;                 // Auto-filled and non-editable
    @FXML private TextField tfGate;                       // Auto-filled and non-editable
    @FXML private DatePicker dpReservationDate;           // DatePicker for reservation date
    @FXML private TextField tfReservationTime;            // TextField for reservation time (HH:mm)
    @FXML private ComboBox<Integer> cbTotalPassengers;    // ComboBox for total passengers
    @FXML private ComboBox<CheckoutVol.ReservationStatus> cbReservationStatus;
    @FXML private TextField tfTotalPrice;                 // Auto-calculated and non-editable

    @FXML private TableView<CheckoutVol> tableViewCheckoutVols;
    @FXML private TableColumn<CheckoutVol, Integer> colCheckoutID;
    @FXML private TableColumn<CheckoutVol, Integer> colFlightID;
    @FXML private TableColumn<CheckoutVol, String> colAircraft;
    @FXML private TableColumn<CheckoutVol, Integer> colFlightCrew;
    @FXML private TableColumn<CheckoutVol, String> colGate;
    @FXML private TableColumn<CheckoutVol, LocalDateTime> colReservationDate;
    @FXML private TableColumn<CheckoutVol, Integer> colTotalPassengers;
    @FXML private TableColumn<CheckoutVol, CheckoutVol.ReservationStatus> colReservationStatus;
    @FXML private TableColumn<CheckoutVol, Integer> colTotalPrice;
    @FXML private TableColumn<CheckoutVol, Void> colModify;
    @FXML
    private TableView<ExchangeRate> tblExchangeRates;
    @FXML
    private TableColumn<ExchangeRate, String> colCurrency;
    @FXML
    private TableColumn<ExchangeRate, Double> colExchangeRate;
    @FXML
    private Button btnRefreshRate;

    private final CheckoutVolService checkoutVolService = new CheckoutVolService();
    private final VolService volService = new VolService();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final Random random = new Random();
    private static final String APIEXCHANGE_KEY = "f54c960056da53ceb08868c69cf477dc"; // Replace with your API key
    private static final String APIEXCHANGE_URL = "https://api.forexrateapi.com/v1/latest?api_key=" + APIEXCHANGE_KEY;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Map<String, Double> exchangeRates = new HashMap<>();
    private ObservableList<ExchangeRate> exchangeRateData = FXCollections.observableArrayList();
    @FXML
    private WebView mapView;

    @FXML
    private Button btnRefreshMap;

    private static final String APIMAP_KEY = "e85780260be8389ae00578b45d40763e";  // Replace with your key
    private static final String APIMAP_URL = "http://api.aviationstack.com/v1/flights?access_key=" + APIMAP_KEY;

    @FXML
    private ComboBox<String> cbCurrency;
    @FXML
    private Label lblExchangeRate;

    // List of modern aircraft types
    private final String[] aircraftTypes = {
            "Boeing 737-800", "Boeing 737 MAX 8", "Boeing 777-300ER", "Boeing 787-9 Dreamliner",
            "Boeing 747-8", "Airbus A320neo", "Airbus A321XLR", "Airbus A330-900neo",
            "Airbus A350-900", "Airbus A380-800", "Embraer E195-E2", "Bombardier CS300",
            "Airbus A319", "Bombardier CRJ900", "ATR 72-600", "Sukhoi Superjet 100"
    };

    @FXML
    public void initialize() {
        loadFlightIDs();
        loadReservationStatus();
        setupTotalPassengersComboBox();
        setupAutoGeneratedFields();
        loadDefaultMap();
        // Set up columns
        colCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        colExchangeRate.setCellValueFactory(new PropertyValueFactory<>("rate"));

        // Set up periodic updates of exchange rates
        startExchangeRateUpdates();

        // Load initial data
        updateExchangeRate();

        // Map TableView columns to CheckoutVol properties
        colCheckoutID.setCellValueFactory(new PropertyValueFactory<>("CheckoutID"));
        colFlightID.setCellValueFactory(new PropertyValueFactory<>("FlightID"));
        colAircraft.setCellValueFactory(new PropertyValueFactory<>("Aircraft"));
        colFlightCrew.setCellValueFactory(new PropertyValueFactory<>("FlightCrew"));
        colGate.setCellValueFactory(new PropertyValueFactory<>("Gate"));
        colReservationDate.setCellValueFactory(new PropertyValueFactory<>("ReservationDate"));
        colTotalPassengers.setCellValueFactory(new PropertyValueFactory<>("TotalPassengers"));
        colReservationStatus.setCellValueFactory(new PropertyValueFactory<>("ReservationStatus"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));

        // Add "Modifier" button into colModify using a cell factory
        colModify.setCellFactory(param -> new TableCell<CheckoutVol, Void>() {
            private final Button modifyButton = new Button("Modifier");

            {
                modifyButton.setOnAction(event -> {
                    CheckoutVol checkoutVol = getTableView().getItems().get(getIndex());
                    openModifyDialog(checkoutVol);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : modifyButton);
            }
        });

        // Update total price when total passengers changes
        cbTotalPassengers.setOnAction(event -> calculateTotalPrice());

        refreshTable();
    }

    // Setup ComboBox for total passengers (1-10)
    private void setupTotalPassengersComboBox() {
        ObservableList<Integer> passengerOptions = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) {
            passengerOptions.add(i);
        }
        cbTotalPassengers.setItems(passengerOptions);
        cbTotalPassengers.setValue(1); // Default value
    }

    // Setup auto-generated fields (non-editable fields and random generation on FlightID change)
    private void setupAutoGeneratedFields() {
        tfAircraft.setEditable(false);
        tfFlightCrew.setEditable(false);
        tfGate.setEditable(false);
        tfTotalPrice.setEditable(false);

        // Generate random values when FlightID is selected
        cbFlightID.setOnAction(event -> generateRandomValues());

        // Initial generation
        generateRandomValues();
    }

    // Generate random values for Aircraft, FlightCrew, and Gate
    private void generateRandomValues() {
        // Random aircraft from list
        String randomAircraft = aircraftTypes[random.nextInt(aircraftTypes.length)];
        tfAircraft.setText(randomAircraft);

        // Random flight crew size (4-20)
        int randomFlightCrew = random.nextInt(17) + 4;
        tfFlightCrew.setText(String.valueOf(randomFlightCrew));

        // Random gate (A-Z + 000-999)
        char randomLetter = (char) (random.nextInt(26) + 'A');
        int randomNumber = random.nextInt(1000);
        String randomGate = String.format("%c%03d", randomLetter, randomNumber);
        tfGate.setText(randomGate);

        calculateTotalPrice();
    }

    // Calculate total price based on number of passengers (1250 per passenger)
    private void calculateTotalPrice() {
        Integer passengers = cbTotalPassengers.getValue();
        if (passengers != null) {
            int price = passengers * 1250;
            tfTotalPrice.setText(String.valueOf(price));
        }
    }

    // Load FlightIDs from the Vol table into the ComboBox
    private void loadFlightIDs() {
        List<Vol> vols = volService.rechercherVol();
        ObservableList<Integer> flightIDs = FXCollections.observableArrayList();
        for (Vol vol : vols) {
            flightIDs.add(vol.getFlightID());
        }
        cbFlightID.setItems(flightIDs);
    }

    // Load the ReservationStatus enum values into the ComboBox
    private void loadReservationStatus() {
        cbReservationStatus.setItems(FXCollections.observableArrayList(CheckoutVol.ReservationStatus.values()));
    }

    // Refresh the TableView by loading all rows from the checkoutvol table
    @FXML
    private void refreshTable() {
        ObservableList<CheckoutVol> checkoutVolList = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wolfs", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM checkoutvol")) {

            while (rs.next()) {
                int checkoutID = rs.getInt("CheckoutID");
                int flightID = rs.getInt("FlightID");
                String aircraft = rs.getString("Aircraft");
                int flightCrew = rs.getInt("FlightCrew");
                String gate = rs.getString("Gate");
                LocalDateTime reservationDate = rs.getTimestamp("ReservationDate").toLocalDateTime();
                int totalPassengers = rs.getInt("TotalPassengers");
                String reservationStatusStr = rs.getString("ReservationStatus").toUpperCase();
                int totalPrice = rs.getInt("TotalPrice");

                CheckoutVol.ReservationStatus reservationStatus;
                try {
                    reservationStatus = CheckoutVol.ReservationStatus.valueOf(reservationStatusStr);
                } catch (IllegalArgumentException e) {
                    reservationStatus = CheckoutVol.ReservationStatus.EN_ATTENTE;
                }

                checkoutVolList.add(new CheckoutVol(
                        checkoutID, flightID, aircraft, flightCrew, gate,
                        reservationDate, totalPassengers, reservationStatus, totalPrice
                ));
            }

            tableViewCheckoutVols.setItems(checkoutVolList);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les données!", Alert.AlertType.ERROR);
        }
    }

    // Add a new CheckoutVol using the values from the FXML fields
    @FXML
    private void addCheckoutVol(ActionEvent event) {
        try {
            // Validation de l'ID du vol
            if (cbFlightID.getValue() == null) {
                showAlert("Erreur", "Veuillez sélectionner un ID de vol!", Alert.AlertType.ERROR);
                return;
            }

            // Validation de la date de réservation
            if (dpReservationDate.getValue() == null) {
                showAlert("Erreur", "Veuillez sélectionner une date de réservation!", Alert.AlertType.ERROR);
                return;
            }

            // Validation de l'heure de réservation
            if (tfReservationTime.getText() == null || tfReservationTime.getText().trim().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir une heure de réservation (HH:mm)!", Alert.AlertType.ERROR);
                return;
            }

            // Validation du statut de réservation
            if (cbReservationStatus.getValue() == null) {
                showAlert("Erreur", "Veuillez sélectionner un statut de réservation!", Alert.AlertType.ERROR);
                return;
            }

            // Contrôle de saisie: s'assurer que la date et l'heure de réservation ne sont pas dans le passé.
            LocalDate reservationDate = dpReservationDate.getValue();
            String reservationTimeStr = tfReservationTime.getText().trim();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime reservationTime;
            try {
                reservationTime = LocalTime.parse(reservationTimeStr, timeFormatter);
            } catch (DateTimeParseException e) {
                showAlert("Erreur", "Format d'heure invalide. Veuillez utiliser le format HH:mm!", Alert.AlertType.ERROR);
                return;
            }

            LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationTime);
            if (reservationDateTime.isBefore(LocalDateTime.now())) {
                showAlert("Erreur", "La date et l'heure de réservation ne peuvent pas être dans le passé!", Alert.AlertType.ERROR);
                return;
            }

            int flightID = cbFlightID.getValue();
            Vol vol = volService.getVolByID(flightID);
            if (vol == null) {
                showAlert("Erreur", "Vol non trouvé!", Alert.AlertType.ERROR);
                return;
            }

            // Parse the time entered by the user

            try {
                reservationTime = LocalTime.parse(tfReservationTime.getText(), timeFormatter);
            } catch (Exception e) {
                showAlert("Erreur", "Veuillez saisir une heure valide (HH:mm)!", Alert.AlertType.ERROR);
                return;
            }



            CheckoutVol newCheckoutVol = new CheckoutVol(
                    flightID,
                    tfAircraft.getText(),
                    Integer.parseInt(tfFlightCrew.getText()),
                    tfGate.getText(),
                    reservationDateTime,
                    cbTotalPassengers.getValue(),
                    cbReservationStatus.getValue(),
                    Integer.parseInt(tfTotalPrice.getText())
            );
            checkoutVolService.ajouterCheckoutVol(newCheckoutVol);
            showAlert("Succès", "CheckoutVol ajouté!", Alert.AlertType.INFORMATION);
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Données invalides!", Alert.AlertType.ERROR);
        }
    }

    // Open a modify dialog for the selected CheckoutVol


    private void openModifyDialog(CheckoutVol selected) {
        Dialog<CheckoutVol> dialog = new Dialog<>();
        dialog.setTitle("Modifier CheckoutVol");

        ButtonType saveButtonType = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Local ComboBox for FlightID selection
        ComboBox<Integer> flightIDComboBox = new ComboBox<>();
        List<Vol> vols = volService.rechercherVol();
        ObservableList<Integer> flightIDs = FXCollections.observableArrayList();
        for (Vol vol : vols) {
            flightIDs.add(vol.getFlightID());
        }
        flightIDComboBox.setItems(flightIDs);
        flightIDComboBox.setValue(selected.getFlightID());

        // Display-only fields for non-editable values
        TextField aircraftField = new TextField(selected.getAircraft());
        aircraftField.setEditable(false);
        TextField flightCrewField = new TextField(String.valueOf(selected.getFlightCrew()));
        flightCrewField.setEditable(false);
        TextField gateField = new TextField(selected.getGate());
        gateField.setEditable(false);

        // DatePicker for reservation date
        DatePicker reservationDatePicker = new DatePicker();
        reservationDatePicker.setValue(selected.getReservationDate().toLocalDate());

        // TimeFormatter for time display/parsing
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // TextField for reservation time
        TextField timeField = new TextField(selected.getReservationDate().toLocalTime().format(timeFormatter));

        // ComboBox for total passengers
        ComboBox<Integer> totalPassengersComboBox = new ComboBox<>();
        ObservableList<Integer> passengerOptions = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) {
            passengerOptions.add(i);
        }
        totalPassengersComboBox.setItems(passengerOptions);
        totalPassengersComboBox.setValue(selected.getTotalPassengers());

        // ComboBox for reservation status
        ComboBox<CheckoutVol.ReservationStatus> statusComboBox = new ComboBox<>();
        statusComboBox.setItems(FXCollections.observableArrayList(CheckoutVol.ReservationStatus.values()));
        statusComboBox.setValue(selected.getReservationStatus());

        // Non-editable field for total price
        TextField totalPriceField = new TextField(String.valueOf(selected.getTotalPrice()));
        totalPriceField.setEditable(false);

        // Update total price when total passengers changes
        totalPassengersComboBox.setOnAction(event -> {
            int price = totalPassengersComboBox.getValue() * 1250;
            totalPriceField.setText(String.valueOf(price));
        });

        grid.add(new Label("FlightID:"), 0, 0);
        grid.add(flightIDComboBox, 1, 0);
        grid.add(new Label("Avion:"), 0, 1);
        grid.add(aircraftField, 1, 1);
        grid.add(new Label("Équipage du vol:"), 0, 2);
        grid.add(flightCrewField, 1, 2);
        grid.add(new Label("Porte:"), 0, 3);
        grid.add(gateField, 1, 3);
        grid.add(new Label("Date de réservation:"), 0, 4);
        grid.add(reservationDatePicker, 1, 4);
        grid.add(new Label("Heure:"), 0, 5);
        grid.add(timeField, 1, 5);
        grid.add(new Label("Total passagers:"), 0, 6);
        grid.add(totalPassengersComboBox, 1, 6);
        grid.add(new Label("Statut de réservation:"), 0, 7);
        grid.add(statusComboBox, 1, 7);
        grid.add(new Label("Prix total:"), 0, 8);
        grid.add(totalPriceField, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    selected.setFlightID(flightIDComboBox.getValue());

                    // Parse and validate new reservation time from dialog input
                    LocalTime newTime;
                    try {
                        newTime = LocalTime.parse(timeField.getText(), timeFormatter);
                    } catch (Exception e) {
                        showAlert("Erreur", "Veuillez saisir une heure valide (HH:mm)!", Alert.AlertType.ERROR);
                        return null;
                    }

                    LocalDate selectedDate = reservationDatePicker.getValue();
                    if (selectedDate == null) {
                        showAlert("Erreur", "Veuillez sélectionner une date de réservation!", Alert.AlertType.ERROR);
                        return null;
                    }

                    LocalDateTime newReservationDateTime = LocalDateTime.of(selectedDate, newTime);

                    // Control de saisie: vérifier que la date et l'heure de réservation ne sont pas dans le passé
                    if (newReservationDateTime.isBefore(LocalDateTime.now())) {
                        showAlert("Erreur", "La date et l'heure de réservation ne peuvent pas être dans le passé!", Alert.AlertType.ERROR);
                        return null;
                    }

                    selected.setReservationDate(newReservationDateTime);
                    selected.setTotalPassengers(totalPassengersComboBox.getValue());
                    selected.setReservationStatus(statusComboBox.getValue());
                    selected.setTotalPrice(Integer.parseInt(totalPriceField.getText()));

                    // Save modifications and refresh table
                    checkoutVolService.modifierCheckoutVol(selected);
                    refreshTable();
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Erreur", "Données invalides!", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


    @FXML
    private void deleteCheckoutVol(ActionEvent event) {
        CheckoutVol selected = tableViewCheckoutVols.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Erreur", "Sélectionnez un CheckoutVol à supprimer!", Alert.AlertType.ERROR);
            return;
        }
        checkoutVolService.supprimerCheckoutVol(selected.getCheckoutID());
        showAlert("Succès", "CheckoutVol supprimé!", Alert.AlertType.INFORMATION);
        refreshTable();
    }

    @FXML
    private void refreshCheckoutVols(ActionEvent event) {
        refreshTable();
    }


    // Method to start periodic exchange rate updates every minute
    private void startExchangeRateUpdates() {
        // Periodically update exchange rates every 60 seconds
        javafx.animation.KeyFrame keyFrame = new javafx.animation.KeyFrame(Duration.minutes(1), event -> updateExchangeRate());
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(keyFrame);
        timeline.setCycleCount(javafx.animation.Timeline.INDEFINITE);
        timeline.play();
    }

    // Fetch exchange rates from the API
    public void updateExchangeRate() {
        // Run network request in background to avoid blocking UI thread
        Task<Void> fetchExchangeRatesTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                fetchExchangeRates();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Platform.runLater(() -> {
                    // Set items and refresh the TableView after data is fetched
                    tblExchangeRates.setItems(exchangeRateData);
                    tblExchangeRates.refresh();
                    btnRefreshRate.setDisable(false);
                });
            }

            @Override
            protected void failed() {
                super.failed();
                handleApiError("Error fetching exchange rates.");
            }
        };

        new Thread(fetchExchangeRatesTask).start();
    }

    // Fetch exchange rates from the API
    private void fetchExchangeRates() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(APIEXCHANGE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Process the API response and update the exchangeRates map
            processApiResponse(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            handleApiError("Connection error: " + e.getMessage());
        }
    }

    // Process the API response
    private void processApiResponse(String responseBody) {
        System.out.println("API Response: " + responseBody); // Debug print

        try {
            // Try parsing the response
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Print the structure of the response to the console
            System.out.println("API Response Structure: " + jsonResponse.toString(4)); // Pretty print

            // Check if the "rates" field exists
            if (jsonResponse.has("rates")) {
                JSONObject rates = jsonResponse.getJSONObject("rates");

                // Clear previous rates
                exchangeRates.clear();
                exchangeRateData.clear();

                // Add the new rates to the map and list
                for (String currency : rates.keySet()) {
                    double rate = rates.getDouble(currency);
                    exchangeRates.put(currency, rate);
                    exchangeRateData.add(new ExchangeRate(currency, rate));
                }
            } else {
                handleApiError("API Error: 'rates' not found in the response.");
            }

        } catch (Exception e) {
            handleApiError("Error processing the API response: " + e.getMessage());
        }
    }



    // Handle API errors
    private void handleApiError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("API Error");
            alert.setHeaderText("Error fetching exchange rates");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }





    // Load a default map when the app starts
    private void loadDefaultMap() {
        if (mapView != null) {
            WebEngine webEngine = mapView.getEngine();
            if (webEngine != null) {
                Platform.runLater(() -> {
                    // Ensure the WebView engine is loaded before trying to navigate
                    webEngine.load("https://www.flightaware.com/live/"); // Default flight map
                });
            } else {
                System.out.println("WebEngine is null.");
            }
        } else {
            System.out.println("mapView is null.");
        }
    }

    // Fetch live flight data and update WebView
    @FXML
    public void updateFlightData() {
        // Run network requests in a background thread to prevent UI freeze
        Task<String> fetchDataTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                return fetchFlightData(); // This will run on a background thread
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                String jsonResponse = getValue();
                if (jsonResponse != null && !jsonResponse.isEmpty()) {
                    updateMapViewWithFlightData(jsonResponse);
                } else {
                    System.out.println("Empty or null response from API.");
                }
            }

            @Override
            protected void failed() {
                super.failed();
                System.out.println("Error occurred while fetching flight data.");
            }
        };

        // Execute the background task
        new Thread(fetchDataTask).start();
    }

    // Update the map with the fetched flight data
    private void updateMapViewWithFlightData(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray flights = jsonObject.getJSONArray("data");

            if (flights.length() > 0) {
                JSONObject firstFlight = flights.getJSONObject(0);
                String latitude = firstFlight.getJSONObject("live").getString("latitude");
                String longitude = firstFlight.getJSONObject("live").getString("longitude");

                // Generate Google Maps URL with flight location
                String mapUrl = "https://www.google.com/maps?q=" + latitude + "," + longitude;

                // Load map with the flight location
                if (mapView != null) {
                    WebEngine webEngine = mapView.getEngine();
                    if (webEngine != null) {
                        Platform.runLater(() -> {
                            webEngine.load(mapUrl);
                        });
                    } else {
                        System.out.println("WebEngine is null while updating flight data.");
                    }
                } else {
                    System.out.println("mapView is null while updating flight data.");
                }
            } else {
                System.out.println("No live flights found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch live flight tracking data from AviationStack API
    private String fetchFlightData() {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(APIMAP_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Ensure the connection is successful
            int statusCode = conn.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            } else {
                System.out.println("Error: Unable to fetch data. HTTP status code: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }






    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
