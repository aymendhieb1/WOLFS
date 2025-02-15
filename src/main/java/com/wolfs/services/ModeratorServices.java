package com.wolfs.services;

import com.wolfs.models.Client;
import com.wolfs.models.Moderator;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeratorServices implements IUserServices <Moderator> {
    Connection connection = DataSource.getInstance().getConnection();

    public ModeratorServices() {}

    @Override
    public void ajouterUser(Moderator moderator) {
        String req = "INSERT INTO user (nom, prenom, mail, num_tel, mdp, role) VALUES ('"
                + moderator.getName() + "', '"
                + moderator.getPrenom() + "', '"
                + moderator.getEmail() + "', "
                + moderator.getNum_tel() + ", '"
                + moderator.getPassword() + "', "
                + moderator.getRole() + ")";

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Moderator ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierUser(Moderator moderator) {
        String req = "UPDATE user SET nom=?, prenom=?, mail=?, num_tel=?, mdp=?, role=? WHERE id_user=?";

        try {
            // Préparer la requête
            PreparedStatement ps = this.connection.prepareStatement(req);

            ps.setString(1, moderator.getName());
            ps.setString(2, moderator.getPrenom());
            ps.setString(3, moderator.getEmail());
            ps.setInt(4, moderator.getNum_tel());
            ps.setString(5, moderator.getPassword());
            ps.setInt(6, moderator.getRole());
            ps.setInt(7, moderator.getId());

            ps.executeUpdate();
            System.out.println("Moderator modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimerUser(Moderator moderator) {
        String req = "DELETE FROM user WHERE id_user=" + moderator.getId();

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Moderator supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Moderator> rechercherUser() {
        List<Moderator> Moderators = new ArrayList<>();
        String req = "SELECT * FROM user WHERE role=1";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Moderators.add(new Moderator(rs.getInt("id_user"), rs.getString("nom"), rs.getString("prenom"), rs.getString("mail"), rs.getInt("num_tel"), rs.getString("mdp"), rs.getInt("role")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Moderators;    }

    @Override
    public Moderator verifierUser(String email, String password) {
        return null;
    }
}
