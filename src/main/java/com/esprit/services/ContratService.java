package com.esprit.services;

import com.esprit.models.Bus;
import com.esprit.models.Contrat;
import com.esprit.models.Vehicule;
import com.esprit.models.Voiture;
import com.esprit.utils.DataSource;

import java.sql.*;
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
            stmt.setDate(2, new java.sql.Date(dateF.getTime()));
            stmt.setDate(3, new java.sql.Date(dateD.getTime()));

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
        String sql = "INSERT INTO Contrat (dateD, dateF, cinLocateur, photo_permit, id_vehicule) VALUES (?, ?, ?, ?, ?)";
        java.sql.Date sqlDateD = new java.sql.Date(contrat.getDateD().getTime());
        java.sql.Date sqlDateF = new java.sql.Date(contrat.getDateF().getTime());

        boolean verif = isVehiculeAvailable(contrat.getVehicule().getIdVehicule(), sqlDateD, sqlDateF);
        if (!verif) {
            System.out.println("❌ Vehicle is already booked. Contract not added.");
            return;
        }

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, sqlDateD);
            stmt.setDate(2, sqlDateF);
            stmt.setInt(3, contrat.getCinLocateur());
            stmt.setString(4, contrat.getPhotoPermit());
            stmt.setInt(5, contrat.getVehicule().getIdVehicule());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Contract added successfully!");
                updateVehiculeStatus(contrat.getVehicule().getIdVehicule(), "indisponible");
            } else {
                System.out.println("❌ Contract insertion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a contract
    public void modifier(Contrat contrat) {
        String sql = "UPDATE contrat SET dateD=?, dateF=?, cinLocateur=?, photo_permit=?, id_vehicule=? WHERE id_location=?";
        java.sql.Date sqlDateD = new java.sql.Date(contrat.getDateD().getTime());
        java.sql.Date sqlDateF = new java.sql.Date(contrat.getDateF().getTime());

        boolean verif = isVehiculeAvailable(contrat.getVehicule().getIdVehicule(), sqlDateD, sqlDateF);
        if (!verif) {
            System.out.println("❌ Vehicle is already booked. Contract not updated.");
            return;
        }

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, sqlDateD);
            stmt.setDate(2, sqlDateF);
            stmt.setInt(3, contrat.getCinLocateur());
            stmt.setString(4, contrat.getPhotoPermit());
            stmt.setInt(5, contrat.getVehicule().getIdVehicule());
            stmt.setInt(6, contrat.getIdLocation());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Contract updated successfully!");
            } else {
                System.out.println("❌ Contract update failed.");
            }
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
                updateVehiculeStatus(contrat.getVehicule().getIdVehicule(), "disponible");
            } else {
                System.out.println("❌ Contract deletion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all contracts
    public List<Contrat> getAllContrats() {
        List<Contrat> contrats = new ArrayList<>();
        String sql = "SELECT c.*, v.id_vehicule, v.matricule, v.status, v.nbPlace, v.cylinder " +
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

                // Check the type of vehicle (Bus or Voiture) and set its specific attribute
                if (rs.getInt("nbPlace") > 0) { // If nbPlace is greater than 0, it's a Bus
                    vehicule = new Bus(
                            vehicule.getIdVehicule(),
                            vehicule.getMatricule(),
                            vehicule.getStatus(),
                            rs.getInt("nbPlace")
                    );
                } else if (rs.getInt("cylinder") > 0) { // If cylinder is greater than 0, it's a Voiture
                    vehicule = new Voiture(
                            vehicule.getIdVehicule(),
                            vehicule.getMatricule(),
                            vehicule.getStatus(),
                            rs.getInt("cylinder")
                    );
                }

                // Create a Contrat object and set its attributes
                Contrat contrat = new Contrat();
                contrat.setIdLocation(rs.getInt("id_location"));
                contrat.setDateD(rs.getDate("dateD"));
                contrat.setDateF(rs.getDate("dateF"));
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicule;
    }

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
}
