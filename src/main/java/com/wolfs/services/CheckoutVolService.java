package com.wolfs.services;

import com.wolfs.models.CheckoutVol;
import com.wolfs.models.ReservationStatus;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckoutVolService {

    Connection connection = DataSource.getInstance().getConnection();

    // Ajouter un CheckoutVol
    public void ajouterCheckoutVol(CheckoutVol checkoutVol) {
        String req = "INSERT INTO checkoutvol (FlightID, UserID, ReservationDate, TotalPassengers, ReservationStatus, TotalPrice) " +
                "VALUES (" + checkoutVol.getFlightID() + ", " + checkoutVol.getUserID() + ", '" + checkoutVol.getReservationDate() + "', " +
                checkoutVol.getTotalPassengers() + ", '" + checkoutVol.getReservationStatus().toString() + "', " + checkoutVol.getTotalPrice() + ")";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("CheckoutVol ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void modifierCheckoutVol(CheckoutVol checkoutVol) {
        // Build the update SQL query
        String req = "UPDATE checkoutVol SET " +
                "FlightID=" + checkoutVol.getFlightID() + ", " +
                "UserID=" + checkoutVol.getUserID() + ", " +
                "ReservationDate='" + checkoutVol.getReservationDate() + "', " +
                "TotalPassengers=" + checkoutVol.getTotalPassengers() + ", " +
                "ReservationStatus='" + checkoutVol.getReservationStatus().toString() + "', " +
                "TotalPrice=" + checkoutVol.getTotalPrice() +
                " WHERE CheckoutID=" + checkoutVol.getCheckoutID();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("CheckoutVol modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    // Supprimer un CheckoutVol
    public void supprimerCheckoutVol(CheckoutVol checkoutVol) {
        String req = "DELETE FROM checkoutvol WHERE CheckoutID=" + checkoutVol.getCheckoutID();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("CheckoutVol supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<CheckoutVol> rechercherCheckoutVol() {
        List<CheckoutVol> checkoutVols = new ArrayList<>();
        String req = "SELECT * FROM checkoutvol";  // Ensure this matches your actual table and column names
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                // Fetch values from the ResultSet
                int checkoutID = rs.getInt("CheckoutID");
                int flightID = rs.getInt("FlightID");
                int userID = rs.getInt("UserID");
                Timestamp ts = rs.getTimestamp("ReservationDate");
                LocalDateTime reservationDate = (ts != null) ? ts.toLocalDateTime() : null;
                int totalPassengers = rs.getInt("TotalPassengers");
                String reservationStatusString = rs.getString("ReservationStatus");
                int totalPrice = rs.getInt("TotalPrice");

                // Debug output
                System.out.println("Fetched from DB:");
                System.out.println("CheckoutID: " + checkoutID);
                System.out.println("FlightID: " + flightID);
                System.out.println("UserID: " + userID);
                System.out.println("ReservationDate: " + reservationDate);
                System.out.println("TotalPassengers: " + totalPassengers);
                System.out.println("ReservationStatus: " + reservationStatusString);
                System.out.println("TotalPrice: " + totalPrice);

                // Convert the string to the nested enum type
                CheckoutVol.ReservationStatus reservationStatusEnum = null;
                try {
                    if (reservationStatusString != null) {
                        reservationStatusEnum = CheckoutVol.ReservationStatus.valueOf(reservationStatusString.toUpperCase());
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Unknown reservation status: " + reservationStatusString);
                    reservationStatusEnum = CheckoutVol.ReservationStatus.En_Attente;
                }

                // Only add valid rows (assuming IDs > 0 are valid)
                if (checkoutID > 0 && flightID > 0 && userID > 0) {
                    CheckoutVol checkoutVol = new CheckoutVol(
                            checkoutID,
                            flightID,
                            userID,
                            reservationDate,
                            totalPassengers,
                            reservationStatusEnum,
                            totalPrice
                    );
                    checkoutVols.add(checkoutVol);
                } else {
                    System.out.println("Skipping invalid row: " + checkoutID + ", " + flightID + ", " + userID);
                }
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        // Debug output for list contents
        System.out.println("Checkout Vols List Size: " + checkoutVols.size());
        for (CheckoutVol cv : checkoutVols) {
            System.out.println("CheckoutVol: " + cv);
        }

        return checkoutVols;
    }



}
