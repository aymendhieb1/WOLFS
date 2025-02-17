package com.wolfs.controllers;

import com.wolfs.models.ClasseChaise;
import com.wolfs.models.Vol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.time.LocalDateTime;
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
    private TextField tfDeparture, tfDestination, tfDepartureTime, tfArrivalTime, tfAirline, tfFlightPrice, tfAvailableSeats, tfDescription;

    @FXML
    private Button btnAddVol;

    @FXML
    private void initialize() {
        // Initialize ComboBox with ClasseChaise enum values
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
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ajouter le vol !");
        }
    }

    private void editVol(Vol vol) {
        System.out.println("Modifier: " + vol);

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
                System.out.println("Updated Vol: " + vol);
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
