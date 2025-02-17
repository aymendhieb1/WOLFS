package com.wolfs.services;

import com.wolfs.models.Activite;
import com.wolfs.models.Hotel;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiviteService2 implements IService<Activite> {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Activite activite) {
        String req = "INSERT INTO activite (nom_act, descript,localisation,type,prix_act) VALUES ('"+ activite.getNom_act()+"', '"+activite.getDescript()+"', '"+activite.getLocalisation()+"', '"+activite.getType()+"', '"+activite.getPrix_act()+"')";
        try {

            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("activite ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(Activite activite) {
        String req = "UPDATE Activite SET nom_act='"+activite.getNom_act()+"' ,descript='"+activite.getDescript()+"',localisation='"+activite.getLocalisation()+"',type='"+activite.getType()+"',prix_act='"+activite.getPrix_act()+"' WHERE id_act="+activite.getId_act();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("activite modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


        public void supprimer(Activite activite) {
            // Use nom_act to delete the activity from the database
            String req = "DELETE FROM activite WHERE nom_act = ?";  // Change to search by nom_act

            try {
                // Check if the nom_act is being passed correctly
                System.out.println("Attempting to delete activity with name: " + activite.getNom_act());

                PreparedStatement pst = connection.prepareStatement(req);
                pst.setString(1, activite.getNom_act());  // Set the nom_act to the prepared statement
                int rowsAffected = pst.executeUpdate();  // Execute the delete

                // Log how many rows were affected
                System.out.println("Rows affected: " + rowsAffected);

                if (rowsAffected > 0) {
                    System.out.println("Activité supprimée avec succès.");
                } else {
                    System.out.println("Aucune activité trouvée avec ce nom.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression : " + e.getMessage());
            }
        }



        public int getIdByNom(String nomAct) {
        String req = "SELECT id_act FROM activite WHERE nom_act = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, nomAct);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_act");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'ID : " + e.getMessage());
        }
        return -1;
    }
    public Activite getActiviteById(int idAct) {
        String req = "SELECT * FROM activite WHERE id_act = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, idAct);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Activite(
                        rs.getInt("id_act"),  // Ensure column name matches DB
                        rs.getString("nom_act"),
                        rs.getString("descript"),
                        rs.getString("localisation"),
                        rs.getString("type"),
                        rs.getFloat("prix_act") // Ensure column name matches DB
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching activity by ID: " + e.getMessage());
        }
        return null;  // Return null if no activity found
    }
    public int getIdByName(String nomAct) {
        String req = "SELECT id_act FROM activite WHERE nom_act = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, nomAct);  // Set the activity name in the query
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_act");  // Return the id_act if found
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching activity ID by name: " + e.getMessage());
        }
        return -1;  // Return -1 if no id found
    }



    public Activite getActiviteByNom(String nomAct) {
        String query = "SELECT * FROM activite WHERE nom_act = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, nomAct);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Activite(
                        rs.getInt("id_act"),
                        rs.getString("nom_act"),
                        rs.getString("descript"),
                        rs.getString("localisation"),
                        rs.getString("type"),
                        rs.getFloat("prix_act")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Return null if no matching activity is found
    }



    @Override
    public List<Activite> rechercher() {
        List<Activite> act = new ArrayList<>();


        String req = "SELECT * FROM activite";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                act.add(new Activite(rs.getString("nom_act"), rs.getString("descript"), rs.getString("localisation"), rs.getString("type"), rs.getFloat("prix_act")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return act;
    }
}
