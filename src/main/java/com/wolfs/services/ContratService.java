package com.wolfs.services;

import com.wolfs.models.Bus;
import com.wolfs.models.Contrat;
import com.wolfs.models.Vehicule;
import com.wolfs.models.Voiture;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContratService implements IService<Contrat> {
    Connection connection = DataSource.getInstance().getConnection();

    // Check if a vehicle is available
    private boolean isVehiculeAvailable(int idVehicule, Date dateD, Date dateF) {
        String sql = "SELECT COUNT(*) FROM Contrat WHERE id_vehicule = ? " +
                "AND (dateD <= ? AND dateF >= ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idVehicule);
            stmt.setDate(2, new Date(dateF.getTime()));
            stmt.setDate(3, new Date(dateD.getTime()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Vehicle is already booked
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // Vehicle is available
    }

    // Add a contract
    public void ajouter(Contrat contrat) {
        String sql = "INSERT INTO contrat (dateD, dateF, cinLocateur, photo_permit, id_vehicule) VALUES (?, ?, ?, ?, ?)";

        // Ensure dateD and dateF are Strings


        try {
            // Prepare the SQL statement and set parameters
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, contrat.getDateD()); // Set dateD as String
            stmt.setString(2, contrat.getDateF()); // Set dateF as String
            stmt.setInt(3, contrat.getCinLocateur());
            stmt.setString(4, contrat.getPhotoPermit());
            stmt.setInt(5, contrat.getIdVehicule());
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Update a contract
    public void modifier(Contrat contrat) {
        // SQL update query
        String sql = "UPDATE contrat SET dateD=?, dateF=?, cinLocateur=?, photo_permit=?, id_vehicule=? WHERE id_location=?";




        try {
            // Prepare the SQL statement and set parameters
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, contrat.getDateD()); // Set dateD
            stmt.setString(2, contrat.getDateF()); // Set dateF
            stmt.setInt(3, contrat.getCinLocateur());
            stmt.setString(4, contrat.getPhotoPermit());
            stmt.setInt(5, contrat.getIdVehicule());
            stmt.setInt(6, contrat.getIdLocation()); // Assuming getIdLocation() returns the contract's ID
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Delete a contract
    public void supprimer(Contrat contrat) {
        // Null check for the vehicle associated with the contract
        if (contrat == null) {
            System.out.println("❌ Cannot delete contract:the list is empty.");
            return; // Exit early if there's no vehicle associated with the contract
        }

        String sql = "DELETE FROM Contrat WHERE id_location = ?";
        try {
            // Prepare statement to delete the contract
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, contrat.getIdLocation());
            int rowsDeleted = pst.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✅ Contract deleted successfully!");

                // Now you can safely call getIdVehicule() because you have verified that the vehicle is not null
                //updateVehiculeStatus(contrat.getVehicule().getIdVehicule(), "disponible");
            } else {
                System.out.println("❌ Contract deletion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all contracts
    /*
    public List<Contrat> getAllContrats() {
        List<Contrat> contrats = new ArrayList<>();
        String sql = "SELECT c.*, v.id_vehicule, v.matricule, v.status, v.prix, v.nbPlace, v.cylinder, v.image_vehicule " +
                "FROM Contrat c " +
                "JOIN Vehicule v ON c.id_vehicule = v.id_vehicule";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Create a Vehicule object and set its attributes
                Vehicule vehicule = new Vehicule();
                vehicule.setIdVehicule(rs.getInt("id_vehicule"));
                vehicule.setMatricule(rs.getString("matricule"));
                vehicule.setStatus(rs.getString("status"));
                vehicule.setPrix(rs.getInt("prix"));

                // Check the type of vehicle (Bus or Voiture) and set its specific attribute
                if (rs.getInt("nbPlace") > 0) { // If nbPlace is greater than 0, it's a Bus
                    vehicule = new Bus(
                            vehicule.getIdVehicule(),
                            vehicule.getMatricule(),
                            vehicule.getStatus(),
                            vehicule.getPrix(),
                            rs.getInt("nbPlace")
                    );
                } else if (rs.getInt("cylinder") > 0) { // If cylinder is greater than 0, it's a Voiture
                    vehicule = new Voiture(
                            vehicule.getIdVehicule(),
                            vehicule.getMatricule(),
                            vehicule.getStatus(),
                            vehicule.getPrix(),
                            rs.getInt("cylinder"),
                            rs.getString("image_vehicule")
                    );
                }

                // Create a Contrat object and set its attributes
                Contrat contrat = new Contrat(String.valueOf(Contrat.getDateD()), String.valueOf(Contrat.getDateF()), Integer.valueOf(String.valueOf(Contrat.getCinLocateur())), String.valueOf(Contrat.getPhotoPermit()));
                contrat.setIdLocation(rs.getInt("id_location"));

                // Get the date values and check if they are not null before setting
                LocalDate dateDebut = rs.getObject("dateD", LocalDate.class);
                LocalDate dateFin = rs.getObject("dateF", LocalDate.class);



                contrat.setCinLocateur(rs.getInt("cinLocateur"));
                contrat.setPhotoPermit(rs.getString("photo_permit"));
                contrat.setVehicule(vehicule); // Attach the full vehicle data

                // Add to the list
                contrats.add(contrat);
            }

            // Log if no contracts were found
            if (contrats.isEmpty()) {
                System.out.println("❌ No contracts found in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of contracts
        return contrats;
    }

    // Fetch vehicle by ID
    public Vehicule getVehiculeById(int vehiculeId) {
        Vehicule vehicule = null;
        String sql = "SELECT * FROM Vehicule WHERE id_vehicule = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, vehiculeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vehicule = new Vehicule();
                vehicule.setIdVehicule(rs.getInt("id_vehicule"));
                vehicule.setMatricule(rs.getString("matricule"));
                vehicule.setStatus(rs.getString("status"));
                vehicule.setPrix(rs.getInt("prix"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicule;
    }*/

    // Update vehicle status
    private void updateVehiculeStatus(int idVehicule, String status) {
        String sql = "UPDATE Vehicule SET status = ? WHERE id_vehicule = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, idVehicule);
            stmt.executeUpdate();
            System.out.println("✅ Vehicle status updated to " + status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contrat> rechercher() {
        return new ArrayList<>();
    }

    // Get all contracts
    public List<Contrat> getListContrats() {
        List<Contrat> contrats = new ArrayList<>();
        String sql = "SELECT id_location, dateD, dateF, cinLocateur, photo_permit, id_vehicule FROM Contrat";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            VehiculeService vehiculeService = new VehiculeService(); // Create it once

            while (rs.next()) {
                Contrat contrat = new Contrat(); // Create an empty object

                // Set attributes from the ResultSet
                contrat.setIdLocation(rs.getInt("id_location"));
                contrat.setDateD(rs.getString("dateD"));
                contrat.setDateF(rs.getString("dateF"));
                contrat.setCinLocateur(rs.getInt("cinLocateur"));
                contrat.setPhotoPermit(rs.getString("photo_permit"));

                // Get vehicle matricule using its ID
                int vehiculeId = rs.getInt("id_vehicule");
                String matricule = vehiculeService.getMatriculeVehiculeById(vehiculeId);
                contrat.setNomVehicule(matricule);

                // Add to list
                contrats.add(contrat);
            }

            if (contrats.isEmpty()) {
                System.out.println("❌ No contracts found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contrats;
    }

    public int chercher_id(Contrat C) {
        String sql = "SELECT id_location FROM contrat WHERE dateD = ? AND dateF = ? AND cinLocateur = ? AND photo_permit = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, C.getDateD());
            stmt.setString(2, C.getDateF());
            stmt.setInt(3, C.getCinLocateur());
            stmt.setString(4, C.getPhotoPermit());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_location");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if not found
    }


}
