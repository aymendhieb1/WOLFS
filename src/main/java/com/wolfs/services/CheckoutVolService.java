package com.wolfs.services;

import com.wolfs.models.CheckoutVol;
import com.wolfs.models.Vol;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckoutVolService {

    Connection connection = DataSource.getInstance().getConnection();

    // Helper method: Retrieve the full Vol object from the Vol table given a FlightID
    private Vol getVolById(int flightID) {
        String query = "SELECT * FROM vol WHERE FlightID = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, flightID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Vol vol = new Vol(
                        rs.getInt("FlightID"),
                        rs.getString("Departure"),
                        rs.getString("Destination"),
                        rs.getTimestamp("DepartureTime").toLocalDateTime(),
                        rs.getTimestamp("ArrivalTime").toLocalDateTime(),
                        com.wolfs.models.ClasseChaise.valueOf(rs.getString("ClasseChaise")),
                        rs.getString("Airline"),
                        rs.getInt("FlightPrice"),
                        rs.getInt("AvailableSeats"),
                        rs.getString("Description")
                );
                return vol;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Ajouter un CheckoutVol
    public void ajouterCheckoutVol(CheckoutVol checkoutVol) {
        String req = "INSERT INTO checkoutvol (FlightID, Aircraft, FlightCrew, Gate, ReservationDate, TotalPassengers, ReservationStatus, TotalPrice) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, checkoutVol.getFlightID());
            pst.setString(2, checkoutVol.getAircraft());
            pst.setInt(3, checkoutVol.getFlightCrew());
            pst.setString(4, checkoutVol.getGate());
            pst.setTimestamp(5, Timestamp.valueOf(checkoutVol.getReservationDate()));
            pst.setInt(6, checkoutVol.getTotalPassengers());
            pst.setString(7, checkoutVol.getReservationStatus().toString());
            pst.setInt(8, checkoutVol.getTotalPrice());

            pst.executeUpdate();
            System.out.println("CheckoutVol ajouté");
        } catch (SQLException e) {
            System.out.println("Error during insert: " + e.getMessage());
        }
    }

    // Modifier un CheckoutVol
    public void modifierCheckoutVol(CheckoutVol checkoutVol) {
        String req = "UPDATE checkoutvol SET " +
                "FlightID = ?, " +
                "Aircraft = ?, " +
                "FlightCrew = ?, " +
                "Gate = ?, " +
                "ReservationDate = ?, " +
                "TotalPassengers = ?, " +
                "ReservationStatus = ?, " +
                "TotalPrice = ? " +
                "WHERE CheckoutID = ?";

        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, checkoutVol.getFlightID());
            pst.setString(2, checkoutVol.getAircraft());
            pst.setInt(3, checkoutVol.getFlightCrew());
            pst.setString(4, checkoutVol.getGate());
            pst.setTimestamp(5, Timestamp.valueOf(checkoutVol.getReservationDate()));
            pst.setInt(6, checkoutVol.getTotalPassengers());
            pst.setString(7, checkoutVol.getReservationStatus().toString());
            pst.setInt(8, checkoutVol.getTotalPrice());
            pst.setInt(9, checkoutVol.getCheckoutID());

            pst.executeUpdate();
            System.out.println("CheckoutVol modifié");
        } catch (SQLException e) {
            System.out.println("Error during update: " + e.getMessage());
        }
    }

    // Supprimer un CheckoutVol
    public void supprimerCheckoutVol(int checkoutVol) {
        String req = "DELETE FROM checkoutvol WHERE CheckoutID = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, checkoutVol);
            pst.executeUpdate();
            System.out.println("CheckoutVol supprimé");
        } catch (SQLException e) {
            System.out.println("Error during delete: " + e.getMessage());
        }
    }

    // Rechercher tous les CheckoutVol
    public List<CheckoutVol> rechercherCheckoutVol() {
        List<CheckoutVol> checkoutVols = new ArrayList<>();
        String req = "SELECT * FROM checkoutvol";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                int checkoutID = rs.getInt("CheckoutID");
                int flightID = rs.getInt("FlightID");
                String aircraft = rs.getString("Aircraft");
                int flightCrew = rs.getInt("FlightCrew");
                String gate = rs.getString("Gate");
                Timestamp ts = rs.getTimestamp("ReservationDate");
                LocalDateTime reservationDate = (ts != null) ? ts.toLocalDateTime() : null;
                int totalPassengers = rs.getInt("TotalPassengers");
                String reservationStatusString = rs.getString("ReservationStatus");
                int totalPrice = rs.getInt("TotalPrice");

                CheckoutVol.ReservationStatus reservationStatusEnum = CheckoutVol.ReservationStatus.valueOf(reservationStatusString.toUpperCase());

                CheckoutVol checkoutVol = new CheckoutVol(
                        checkoutID,
                        flightID,
                        aircraft,
                        flightCrew,
                        gate,
                        reservationDate,
                        totalPassengers,
                        reservationStatusEnum,
                        totalPrice
                );
                checkoutVols.add(checkoutVol);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        return checkoutVols;
    }
}
