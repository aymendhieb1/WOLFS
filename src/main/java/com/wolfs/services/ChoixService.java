package com.wolfs.services;

import com.wolfs.models.Choix;
import com.wolfs.utils.DataSource;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import java.sql.*;
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import java.util.ArrayList;
import java.util.List;

public class ChoixService implements IService<Choix> {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Choix choix) {
        String sql = "INSERT INTO Choix (post_id, choix, pourcentage, choice_votes_count) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, choix.getPostId());
            stmt.setString(2, choix.getChoix());
            stmt.setFloat(3, choix.getPourcentage());
            stmt.setInt(4, choix.getChoiceVotesCount());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Choix added successfully!");
            } else {
                System.out.println("❌ Choix insertion failed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Choix choix) {
        String sql = "UPDATE Choix SET post_id=?, choix=?, pourcentage=?, choice_votes_count=? WHERE id_choix=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, choix.getPostId());
            stmt.setString(2, choix.getChoix());
            stmt.setFloat(3, choix.getPourcentage());
            stmt.setInt(4, choix.getChoiceVotesCount());
            stmt.setInt(5, choix.getIdChoix());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Choix updated successfully!");
            } else {
                System.out.println("❌ Choix update failed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Choix choix) {
        String sql = "DELETE FROM Choix WHERE id_choix=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, choix.getIdChoix());

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Choix deleted successfully!");
            } else {
                System.out.println("❌ Choix deletion failed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Choix> rechercher() {
        List<Choix> choixList = new ArrayList<>();
        String sql = "SELECT * FROM Choix";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Choix choix = new Choix(
                        rs.getInt("id_choix"),
                        rs.getInt("post_id"),
                        rs.getString("choix"),
                        rs.getFloat("pourcentage"),
                        rs.getInt("choice_votes_count")
                );
                choixList.add(choix);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return choixList;
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    @Override
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    public void afficher() {
        List<Choix> choixList = rechercher();
        System.out.println("📜 All Choices:");
        for (Choix choix : choixList) {
            System.out.println("ID: " + choix.getIdChoix() + ", Post ID: " + choix.getPostId() + ", Choix: " + choix.getChoix() + ", Pourcentage: " + choix.getPourcentage() + ", Votes: " + choix.getChoiceVotesCount());
        }
    }

    public void supprimerParPostId(int postId) {
        String req = "DELETE FROM choix WHERE post_id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, postId);
            pst.executeUpdate();
            System.out.println("✅ Choices deleted successfully for post ID: " + postId);
        } catch (SQLException e) {
            System.out.println("❌ Error deleting choices: " + e.getMessage());
        }
    }
}
