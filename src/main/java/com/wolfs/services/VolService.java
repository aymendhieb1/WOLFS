package com.wolfs.services;

import com.wolfs.models.Vol;
import com.wolfs.models.ClasseChaise;
import com.wolfs.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VolService implements IVolService {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouterVol(Vol vol) {
        String req = "INSERT INTO vol (departure, destination, departureTime, arrivalTime, classeChaise, airline, flightPrice, availableSeats, description) " +
                "VALUES ('"+vol.getDeparture()+"', '"+vol.getDestination()+"', '"+vol.getDepartureTime()+"', '"+vol.getArrivalTime()+"', '"+vol.getClasseChaise()+"', " +
                "'"+vol.getAirline()+"', "+vol.getFlightPrice()+", "+vol.getAvailableSeats()+", '"+vol.getDescription()+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Vol ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierVol(Vol vol) {
        String req = "UPDATE vol SET departure='"+vol.getDeparture()+"', destination='"+vol.getDestination()+"', departureTime='"+vol.getDepartureTime()+"', " +
                "arrivalTime='"+vol.getArrivalTime()+"', classeChaise='"+vol.getClasseChaise()+"', airline='"+vol.getAirline()+"', flightPrice="+vol.getFlightPrice()+", " +
                "availableSeats="+vol.getAvailableSeats()+", description='"+vol.getDescription()+"' WHERE flightID="+vol.getFlightID();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Vol modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimerVol(Vol vol) {
        String req = "DELETE FROM vol WHERE flightID="+vol.getFlightID();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
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
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                String classeChaiseStr = rs.getString("classeChaise");
                ClasseChaise classeChaiseEnum = null;

                try {
                    classeChaiseEnum = ClasseChaise.valueOf(classeChaiseStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Unknown class for seat: " + classeChaiseStr);
                }

                vols.add(new Vol(
                        rs.getInt("flightID"),
                        rs.getString("departure"),
                        rs.getString("destination"),
                        rs.getObject("departureTime", LocalDateTime.class),
                        rs.getObject("arrivalTime", LocalDateTime.class),
                        classeChaiseEnum,
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
