package com.wolfs.services;

import com.wolfs.models.CheckoutVol;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckoutVolService2 {

    Connection connection = DataSource.getInstance().getConnection();

    // Ajouter un CheckoutVol
    public void ajouterCheckoutVol(CheckoutVol checkoutVol) {
        String req = "INSERT INTO checkoutvol (FlightID, UserID, ReservationDate, TotalPassengers, ReservationStatus, TotalPrice) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, checkoutVol.getFlightID());
            pst.setInt(2, checkoutVol.getUserID());
            pst.setObject(3, checkoutVol.getReservationDate());
            pst.setInt(4, checkoutVol.getTotalPassengers());
            pst.setString(5, checkoutVol.getReservationStatus().name());  // Enum as string
            pst.setInt(6, checkoutVol.getTotalPrice());  // Keep as int
            pst.executeUpdate();
            System.out.println("CheckoutVol ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifierCheckoutVol(CheckoutVol checkoutVol) {
        String req = "UPDATE checkoutvol SET FlightID=?, UserID=?, ReservationDate=?, TotalPassengers=?, ReservationStatus=?, TotalPrice=? WHERE CheckoutID=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, checkoutVol.getFlightID());
            pst.setInt(2, checkoutVol.getUserID());
            pst.setObject(3, checkoutVol.getReservationDate());
            pst.setInt(4, checkoutVol.getTotalPassengers());
            pst.setString(5, checkoutVol.getReservationStatus().name().replace("_", " ")); // Replace underscores with spaces
            pst.setInt(6, checkoutVol.getTotalPrice());
            pst.setInt(7, checkoutVol.getCheckoutID());

            // Debug: Print the SQL query and parameters
            System.out.println("Executing SQL: " + pst.toString());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("CheckoutVol updated successfully. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No rows updated. Check if CheckoutID=" + checkoutVol.getCheckoutID() + " exists.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    // Supprimer un CheckoutVol
    public void supprimerCheckoutVol(CheckoutVol checkoutVol) {
        String req = "DELETE FROM checkoutvol WHERE CheckoutID=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, checkoutVol.getCheckoutID());
            pst.executeUpdate();
            System.out.println("CheckoutVol supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<CheckoutVol> rechercherCheckoutVol() {
        List<CheckoutVol> checkoutVols = new ArrayList<>();
        String req = "SELECT * FROM checkoutvol";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int checkoutID = rs.getInt("CheckoutID");
                int flightID = rs.getInt("FlightID");
                int userID = rs.getInt("UserID");
                Timestamp ts = rs.getTimestamp("ReservationDate");
                LocalDateTime reservationDate = (ts != null) ? ts.toLocalDateTime() : null;
                int totalPassengers = rs.getInt("TotalPassengers");
                String reservationStatusString = rs.getString("ReservationStatus");
                int totalPrice = rs.getInt("TotalPrice");

                // Convert database value to Java enum
                CheckoutVol.ReservationStatus reservationStatusEnum = null;
                try {
                    if (reservationStatusString != null) {
                        reservationStatusEnum = CheckoutVol.ReservationStatus.valueOf(reservationStatusString.replace(" ", "_"));
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Unknown reservation status: " + reservationStatusString);
                    reservationStatusEnum = CheckoutVol.ReservationStatus.En_Attente;
                }

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
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        return checkoutVols;
    }

}
