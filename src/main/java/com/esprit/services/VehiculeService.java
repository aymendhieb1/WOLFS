package com.esprit.services;

import com.esprit.models.Bus;
import com.esprit.models.Vehicule;
import com.esprit.models.Voiture;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeService implements IService<Vehicule> {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Vehicule vehicule) {
        String req = "INSERT INTO vehicule (matricule, status, nbPlace, cylinder) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, vehicule.getMatricule());
            pst.setString(2, vehicule.getStatus());
            pst.setInt(3, vehicule instanceof Bus ? ((Bus) vehicule).getNbPlace() : 0);
            pst.setInt(4, vehicule instanceof Voiture ? ((Voiture) vehicule).getCylinder() : 0);
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
        String req = "DELETE FROM vehicule WHERE id_vehicule=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, vehicule.getIdVehicule());
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

                if (cylinder > 0) {
                    vehicule = new Voiture(idVehicule, matricule, status, cylinder);
                } else {
                    vehicule = new Bus(idVehicule, matricule, status, nbPlace);
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

                // Determine if it's a Bus or Voiture based on the available columns
                if (cylinder > 0) {
                    vehicule = new Voiture(idVehicule, matricule, status, cylinder);  // Voiture if cylinder is provided
                } else {
                    vehicule = new Bus(idVehicule, matricule, status, nbPlace);  // Bus if nbPlace is provided
                }

                vehicules.add(vehicule);  // Add vehicle to the list
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des véhicules: " + e.getMessage());
        }

        return vehicules;  // Return the list of vehicles
    }


}
