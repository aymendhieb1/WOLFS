package com.wolfs.services;

import com.wolfs.models.Client;
import com.wolfs.utils.DataSource;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientServices implements IUserServices<Client> {
    Connection connection = DataSource.getInstance().getConnection();

    public ClientServices() {

    }

    public void ajouterUser(Client client) {
        String req = "INSERT INTO user (nom, prenom, mail, num_tel, mdp, status, photo_profil, role) VALUES ('"
                + client.getName() + "', '"
                + client.getPrenom() + "', '"
                + client.getEmail() + "', "
                + client.getNum_tel() + ", '"
                + client.getPassword() + "', "
                + client.getStatus() + ", '"
                + client.getPhoto_profile() + "', "
                + client.getRole() + ")";

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Client ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifierUser(Client client) {
        String req = "UPDATE user SET nom=?, prenom=?, mail=?, num_tel=?, mdp=?, status=?, photo_profil=?, role=? WHERE id_user=?";

        try {
            // Préparer la requête
            PreparedStatement ps = this.connection.prepareStatement(req);

            ps.setString(1, client.getName());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setInt(4, client.getNum_tel());
            ps.setString(5, client.getPassword());
            ps.setInt(6, client.getStatus());
            ps.setString(7, client.getPhoto_profile());
            ps.setInt(8, client.getRole());
            ps.setInt(9, client.getId());

            ps.executeUpdate();
            System.out.println("Client modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimerUser(Client client) {
        String req = "DELETE FROM user WHERE id_user=" + client.getId();

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Client supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

   public List<Client> rechercherUser() {
        List<Client> clients = new ArrayList<>();
        String req = "SELECT * FROM user WHERE role=2";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int id = rs.getInt("id_user");
                String name = rs.getString("nom");
                int role = rs.getInt("role");
                String photoProfile = rs.getString("photo_profil");

                System.out.println("ID: " + id + ", Name: " + name + ", Role: " + role + "Photo Profil: " + photoProfile);
                clients.add(new Client(rs.getInt("id_user"), rs.getString("nom"), rs.getString("prenom"), rs.getString("mail"), rs.getString("mdp"), rs.getInt("num_tel"), rs.getInt("role"), rs.getInt("status"), rs.getString("photo_profil")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return clients;
    }

    public Client verifierUser(String email, String password) {
        String req = "SELECT id_user, nom, prenom, mail, mdp, num_tel, role, status, photo_profil FROM user WHERE mail=?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(req);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Utilisateur trouvé : " + rs.getString("mail"));

                String hashedPassword = rs.getString("mdp");  // Récupérer le hash
                System.out.println("Mot de passe hashé en base : " + hashedPassword);

                // Vérifier si le mot de passe entré correspond au hash stocké
                if (BCrypt.checkpw(password, hashedPassword)) {
                    System.out.println("Mot de passe correct !");
                    return new Client(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("mail"),
                            hashedPassword,
                            rs.getInt("num_tel"),
                            rs.getInt("role"),
                            rs.getInt("status"),
                            rs.getString("photo_profil")
                    );
                } else {
                    System.out.println("Mot de passe incorrect !");
                    return null;
                }
            } else {
                System.out.println("Email non trouvé !");
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return null;
    }



}
