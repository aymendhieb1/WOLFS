package com.wolfs.services;

import com.wolfs.models.Forum;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ForumService implements IService<Forum> {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Forum forum) {
        String sql = "INSERT INTO Forum (name, created_by, description, date_creation, is_private, list_members, post_count, nbr_members) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, forum.getName());
            stmt.setInt(2, forum.getCreatedBy());
            stmt.setString(3, forum.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(forum.getDateCreation()));
            stmt.setBoolean(5, forum.isPrivate());
            stmt.setString(6, forum.getListMembers());
            stmt.setInt(7, forum.getPostCount());
            stmt.setInt(8, forum.getNbrMembers());

            int rowsInserted = stmt.executeUpdate();
            System.out.println(rowsInserted > 0 ? "✅ Forum added successfully!" : "❌ Forum insertion failed.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Forum forum) {
        String sql = "UPDATE Forum SET name=?, created_by=?, description=?, list_members=?, date_creation=?, is_private=?, post_count=?, nbr_members=? WHERE forum_id=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, forum.getName());
            stmt.setInt(2, forum.getCreatedBy());
            stmt.setString(3, forum.getDescription());
            stmt.setString(4, forum.getListMembers());
            stmt.setTimestamp(5, Timestamp.valueOf(forum.getDateCreation()));
            stmt.setBoolean(6, forum.isPrivate());
            stmt.setInt(7, forum.getPostCount());
            stmt.setInt(8, forum.getNbrMembers());
            stmt.setInt(9, forum.getForumId());

            int rowsUpdated = stmt.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "✅ Forum updated successfully!" : "❌ Forum update failed.");
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
                        rs.getInt("post_count"),   // Fix: Make sure the column exists in the database
                        rs.getInt("nbr_members"),  // Fix: Make sure the column exists in the database
                        rs.getString("description"),
                        rs.getTimestamp("date_creation").toLocalDateTime(),
                        rs.getBoolean("is_private"),
                        rs.getString("list_members") // retrieve list_members
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
            System.out.print("Post Count: " + forum.getPostCount() + " ");  // Fix: Ensure post_count is displayed
            System.out.print("Number of Members: " + forum.getNbrMembers() + " ");  // Fix: Ensure nbr_members is displayed
            System.out.print("Description: " + forum.getDescription() + " ");
            System.out.print("List Members: " + forum.getListMembers() + " ");
            System.out.print("Creation Date: " + forum.getDateCreation() + " ");
            System.out.print("Is Private: " + forum.isPrivate() + " ");
            System.out.println();
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

    public int getUserByEmail(String email) {
        String sql = "SELECT id_user FROM User WHERE mail = ?";
        int userId = -1;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id_user");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user by email: " + e.getMessage());
        }

        return userId;
    }

    public String getEmailById(int userId) {
        String sql = "SELECT mail FROM User WHERE id_user = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("mail");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving email by user ID: " + e.getMessage());
        }
        return "Unknown User";
    }

}
