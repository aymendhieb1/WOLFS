package com.wolfs.services;

import com.wolfs.models.Admin;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminServices implements IUserServices<Admin> {

    Connection connection = DataSource.getInstance().getConnection();

    public AdminServices() {
    }

    @Override
    public void ajouterUser(Admin admin) {
        String req = "INSERT INTO user (nom, prenom, mail, num_tel, mdp, role) VALUES ('"
                + admin.getName() + "', '"
                + admin.getPrenom() + "', '"
                + admin.getEmail() + "', "
                + admin.getNum_tel() + ", '"
                + admin.getPassword() + "', "
                + admin.getRole() + ")";

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Admin ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifierUser(Admin admin) {
        String req = "UPDATE user SET nom=?, prenom=?, mail=?, num_tel=?, mdp=?, role=? WHERE id_user=?";

        try {
            // Préparer la requête
            PreparedStatement ps = this.connection.prepareStatement(req);

            ps.setString(1, admin.getName());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getEmail());
            ps.setInt(4, admin.getNum_tel());
            ps.setString(5, admin.getPassword());
            ps.setInt(6, admin.getRole());
            ps.setInt(7, admin.getId());

            ps.executeUpdate();
            System.out.println("Admin modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimerUser(Admin admin) {
        String req = "DELETE FROM user WHERE id_user=" + admin.getId();

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Admin supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Admin> rechercherUser() {
        List<Admin> Admins = new ArrayList<>();
        String req = "SELECT * FROM user WHERE role=0";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Admins.add(new Admin(rs.getInt("id_user"), rs.getString("nom"), rs.getString("prenom"), rs.getString("mail"), rs.getInt("num_tel"), rs.getString("mdp"), rs.getInt("role")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Admins;
    }
}
