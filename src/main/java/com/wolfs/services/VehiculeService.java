package com.wolfs.services;

import com.wolfs.models.Bus;
import com.wolfs.models.Vehicule;
import com.wolfs.models.Voiture;
import com.wolfs.utils.DataSource;

import javax.swing.plaf.IconUIResource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeService implements IService<Vehicule> {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Vehicule vehicule) {
        // Corrected SQL query with 6 placeholders
        String req = "INSERT INTO vehicule (matricule, status, prix, nbPlace, cylinder, image_vehicule) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);

            // Set common fields for all vehicles
            pst.setString(1, vehicule.getMatricule());
            pst.setString(2, vehicule.getStatus());
            pst.setInt(3, vehicule.getPrix());

            // Set vehicle-specific fields
            if (vehicule instanceof Bus) {
                Bus bus = (Bus) vehicule;
                pst.setInt(4, bus.getNbPlace()); // Set nbPlace for Bus
                pst.setNull(5, java.sql.Types.INTEGER); // Set cylinder as NULL for Bus
                pst.setNull(6, java.sql.Types.VARCHAR); // Set image_vehicule as NULL for Bus
            } else if (vehicule instanceof Voiture) {
                Voiture voiture = (Voiture) vehicule;
                pst.setNull(4, java.sql.Types.INTEGER); // Set nbPlace as NULL for Voiture
                pst.setInt(5, voiture.getCylinder()); // Set cylinder for Voiture
                pst.setString(6, voiture.getImage_vehicule()); // Set image_vehicule for Voiture
            } else {
                // Handle other types of vehicles (if any)
                pst.setNull(4, java.sql.Types.INTEGER); // Set nbPlace as NULL
                pst.setNull(5, java.sql.Types.INTEGER); // Set cylinder as NULL
                pst.setNull(6, java.sql.Types.VARCHAR); // Set image_vehicule as NULL
            }

            // Execute the query
            pst.executeUpdate();
            System.out.println("✅ Véhicule ajouté avec succès!");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du véhicule: " + e.getMessage());
        }
    }
    @Override
    public void modifier(Vehicule vehicule) {
        String req = "UPDATE vehicule SET matricule=?, status=?, nbPlace=?, cylinder=? WHERE id_vehicule=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, vehicule.getMatricule());
            pst.setString(2, vehicule.getStatus());
            pst.setInt(3, vehicule instanceof Bus ? ((Bus) vehicule).getNbPlace() : 0);
            pst.setInt(4, vehicule instanceof Voiture ? ((Voiture) vehicule).getCylinder() : 0);
            pst.setInt(5, vehicule.getIdVehicule());
            pst.executeUpdate();
            System.out.println("✅ Véhicule modifié avec succès!");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la modification du véhicule: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(Vehicule vehicule) {
        String req = "DELETE FROM vehicule WHERE id_vehicule=? OR matricule=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, vehicule.getIdVehicule());
            pst.setString(2, vehicule.getMatricule());
            pst.executeUpdate();
            System.out.println("✅ Véhicule supprimé avec succès!");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du véhicule: " + e.getMessage());
        }
    }

    @Override
    public List<Vehicule> rechercher() {
        List<Vehicule> vehicules = new ArrayList<>();
        String req = "SELECT * FROM vehicule";

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Vehicule vehicule;
                int idVehicule = rs.getInt("idVehicule");
                String matricule = rs.getString("matricule");
                String status = rs.getString("status");
                int nbPlace = rs.getInt("nbPlace");
                int cylinder = rs.getInt("cylinder");
                int prix= rs.getInt("prix");
                String image_vehicule =rs.getString("image_vehicule");
                if (cylinder > 0) {
                    vehicule = new Voiture(idVehicule, matricule, status,prix, cylinder,image_vehicule);
                } else {
                    vehicule = new Bus(idVehicule, matricule, status,prix,nbPlace);
                }

                vehicules.add(vehicule);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des véhicules: " + e.getMessage());
        }

        return vehicules;
    }

    public List<Vehicule> getAllVehicules() {
        List<Vehicule> vehicules = new ArrayList<>();
        String req = "SELECT * FROM vehicule";  // Query to fetch all vehicles

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Vehicule vehicule;
                int idVehicule = rs.getInt("id_vehicule");
                String matricule = rs.getString("matricule");
                String status = rs.getString("status");
                int nbPlace = rs.getInt("nbPlace");
                int cylinder = rs.getInt("cylinder");
                int prix= rs.getInt("prix");
                String image_vehicule =rs.getString("image_vehicule");
                // Determine if it's a Bus or Voiture based on the available columns
                if (cylinder > 0) {
                    vehicule = new Voiture(idVehicule, matricule, status,prix, cylinder,image_vehicule);
                } else {
                    vehicule = new Bus(idVehicule, matricule, status,prix,nbPlace);
                }

                vehicules.add(vehicule);  // Add vehicle to the list
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des véhicules: " + e.getMessage());
        }

        return vehicules;  // Return the list of vehicles
    }


    // Fetch vehicle by ID
    public Vehicule getVehiculeByMatricule(String Matricule) {
        Vehicule vehicule = null;
        String sql = "SELECT * FROM Vehicule WHERE matricule = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, Matricule);
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
    }
    // Fetch vehicle by ID
    public int getIdVehiculeByMatricule(String Matricule) {
        Vehicule vehicule = null;
        String sql = "SELECT (id_vehicule) FROM Vehicule WHERE matricule = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, Matricule);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt("id_vehicule"));
                return rs.getInt("id_vehicule");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // Fetch vehicle by ID
    public String getMatriculeVehiculeById(int id) {
        Vehicule vehicule = null;
        String sql = "SELECT (matricule) FROM Vehicule WHERE id_vehicule = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("matricule");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
