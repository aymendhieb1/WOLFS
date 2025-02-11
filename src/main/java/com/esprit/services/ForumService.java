package com.esprit.services;

import com.esprit.models.Forum;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ForumService implements IService<Forum> {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Forum forum) {
        String sql = "INSERT INTO Forum (name, created_by, description, date_creation, is_private) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, forum.getName());
            stmt.setInt(2, forum.getCreatedBy());
            stmt.setString(3, forum.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(forum.getDateCreation()));
            stmt.setBoolean(5, forum.isPrivate());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Forum added successfully!");
            } else {
                System.out.println("❌ Forum insertion failed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Forum forum) {
        String sql = "UPDATE Forum SET name=?, created_by=?, description=?, date_creation=?, is_private=? WHERE forum_id=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, forum.getName());
            stmt.setInt(2, forum.getCreatedBy());
            stmt.setString(3, forum.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(forum.getDateCreation()));
            stmt.setBoolean(5, forum.isPrivate());
            stmt.setInt(6, forum.getForumId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Forum updated successfully!");
            } else {
                System.out.println("❌ Forum update failed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Forum forum) {
        String sql = "DELETE FROM Forum WHERE forum_id=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, forum.getForumId());

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Forum deleted successfully!");
            } else {
                System.out.println("❌ Forum deletion failed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Forum> rechercher() {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT * FROM Forum";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Forum forum = new Forum(
                        rs.getInt("forum_id"),
                        rs.getString("name"),
                        rs.getInt("created_by"),
                        rs.getInt("post_count"),
                        rs.getInt("nbr_members"),
                        rs.getString("description"),
                        rs.getTimestamp("date_creation").toLocalDateTime(),
                        rs.getBoolean("is_private")
                );
                forums.add(forum);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return forums;
    }
    public void afficher() {
        List<Forum> forums = rechercher();
        System.out.println(" 📜 All Forums:");
        
        for (Forum forum : forums) {
            System.out.print("Forum ID: " + forum.getForumId() + " ");
            System.out.print("Name: " + forum.getName() + " ");
            System.out.print("Created By (User ID): " + forum.getCreatedBy() + " ");
            System.out.print("Post Count: " + forum.getPostCount() + " ");
            System.out.print("Number of Members: " + forum.getNbrMembers() + " ");
            System.out.print("Description: " + forum.getDescription() + " ");
            System.out.print("Creation Date: " + forum.getDateCreation() + " ");
            System.out.print("Is Private: " + forum.isPrivate() + " ");

            System.out.println();
        }
    }

}
