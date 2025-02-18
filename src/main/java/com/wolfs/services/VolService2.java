package com.wolfs.services;

import com.wolfs.models.Vol;
import com.wolfs.models.ClasseChaise;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class VolService2 implements IVolService {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouterVol(Vol vol) {
        String req = "INSERT INTO vol (departure, destination, departureTime, arrivalTime, classeChaise, airline, flightPrice, availableSeats, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, vol.getDeparture());
            pst.setString(2, vol.getDestination());
            pst.setObject(3, vol.getDepartureTime());
            pst.setObject(4, vol.getArrivalTime());
            pst.setString(5, vol.getClasseChaise().name());  // Assuming the enum is stored as a string
            pst.setString(6, vol.getAirline());
            pst.setInt(7, vol.getFlightPrice());
            pst.setInt(8, vol.getAvailableSeats());
            pst.setString(9, vol.getDescription());
            pst.executeUpdate();
            System.out.println("Vol ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierVol(Vol vol) {
        String req = "UPDATE vol SET departure=?, destination=?, departureTime=?, arrivalTime=?, classeChaise=?, airline=?, flightPrice=?, availableSeats=?, description=? WHERE flightID=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, vol.getDeparture());
            pst.setString(2, vol.getDestination());
            pst.setObject(3, vol.getDepartureTime());
            pst.setObject(4, vol.getArrivalTime());
            pst.setString(5, vol.getClasseChaise().name());
            pst.setString(6, vol.getAirline());
            pst.setInt(7, vol.getFlightPrice());
            pst.setInt(8, vol.getAvailableSeats());
            pst.setString(9, vol.getDescription());
            pst.setInt(10, vol.getFlightID());
            pst.executeUpdate();
            System.out.println("Vol modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimerVol(Vol vol) {
        String req = "DELETE FROM vol WHERE flightID=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, vol.getFlightID());
            pst.executeUpdate();
            System.out.println("Vol supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Vol> rechercherVol() {
        List<Vol> vols = new ArrayList<>();

        String req = "SELECT * FROM vol";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("Classe Chaise :"+ ClasseChaise.valueOf(rs.getString("classeChaise")));
                vols.add(new Vol(
                        rs.getInt("flightID"),
                        rs.getString("departure"),
                        rs.getString("destination"),
                        rs.getObject("departureTime", LocalDateTime.class),
                        rs.getObject("arrivalTime", LocalDateTime.class),
                        ClasseChaise.valueOf(rs.getString("classeChaise")),
                        rs.getString("airline"),
                        rs.getInt("flightPrice"),
                        rs.getInt("availableSeats"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return vols;
    }
}
