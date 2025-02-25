package com.wolfs.services;

import com.google.api.services.oauth2.model.Userinfo;
import com.wolfs.models.Client;
import com.wolfs.utils.DataSource;
import javafx.scene.image.Image;

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
        String req = "UPDATE user SET nom=?, prenom=?, mail=?, num_tel=?, mdp=? WHERE id_user=?";

        try (PreparedStatement ps = this.connection.prepareStatement(req)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setInt(4, client.getNum_tel());
            ps.setString(5, client.getPassword());
            ps.setInt(6, client.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Client modifié avec succès.");
            } else {
                System.out.println("Aucun client trouvé.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du client : " + e.getMessage());
            e.printStackTrace();
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

    public Client verifierUser(String email) {
        String req = "SELECT id_user, nom, prenom, mail, mdp, num_tel, role, status, photo_profil FROM user WHERE mail=?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(req);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                System.out.println("Utilisateur trouvé : " + rs.getString("mail"));




                    System.out.println("Mot de passe correct !");
                    return new Client(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("mail"),
                            rs.getString("mdp"),
                            rs.getInt("num_tel"),
                            rs.getInt("role"),
                            rs.getInt("status"),
                            rs.getString("photo_profil"));}
            else {
                System.out.println("Email non trouvé !");
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return null;
    }
    public int getUserIdByEmail(String email) {
        String req = "SELECT id_user FROM user WHERE mail = ?";
        int userId = -1;

        try (PreparedStatement ps = this.connection.prepareStatement(req)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id_user");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'ID : " + e.getMessage());
        }

        return userId;
    }
    public Image loadImageFromDatabase(String mail) {
        try {
            String query = "SELECT photo_profil FROM user WHERE mail = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String imageUrl = resultSet.getString("photo_profil");

                if (imageUrl != null && !imageUrl.isEmpty()) {

                    return new Image(imageUrl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'image pour l'email: " + mail);
        }

        // Retourner une image par défaut en cas d'échec
        return new Image("file:images/user_icon_001.jpg"); // Assurez-vous que ce chemin est correct
    }

    public Client verifierUserByMail(String email) {
        String req = "SELECT id_user, nom, prenom, mail, mdp, num_tel, role, status, photo_profil FROM user WHERE mail=?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(req);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                System.out.println("Utilisateur trouvé : " + rs.getString("mail"));




                System.out.println("Mot de passe correct !");
                return new Client(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("mail"),
                        rs.getString("mdp"),
                        rs.getInt("num_tel"),
                        rs.getInt("role"),
                        rs.getInt("status"),
                        rs.getString("photo_profil"));}
            else {
                System.out.println("Email non trouvé !");
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return null;
    }

    public void bloquer_client(int id) {
        String req = "UPDATE user SET status = 1 WHERE id_user = ?";

        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, id); // Replaces `?` with the value of `id`
            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Client with ID " + id + " has been blocked.");
            } else {
                System.out.println("No client found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Erreur  : " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void debloquer_client(int id) {
        String req = "UPDATE user SET status = 0 WHERE id_user = ?";

        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, id); // Replaces `?` with the value of `id`
            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Client with ID " + id + " has been blocked.");
            } else {
                System.out.println("No client found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Erreur  : " + e.getMessage());
            e.printStackTrace();
        }}

    public boolean updatePassword(String mail, String mdp) {
        String req = "UPDATE user SET mdp=? WHERE mail=?";

        try (PreparedStatement ps = this.connection.prepareStatement(req)) {
            ps.setString(1, mdp);
            ps.setString(2, mail);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Mot de passe modifié avec succès.");
                return true;
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du mot de passe : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExists(String email) {
        String req = "SELECT COUNT(*) FROM user WHERE mail=?";
        try (PreparedStatement ps = this.connection.prepareStatement(req)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return false;
    }

    public int UserAccountBlocked() {
        int count = 0;
        String req = "SELECT COUNT(*) FROM user WHERE status='1'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int UserAccountNotBlocked() {
        int count = 0;
        String req = "SELECT COUNT(*) FROM user WHERE status='0'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int CountUsers() {
        int count = 0;
        String req = "SELECT COUNT(*) FROM user";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }



}
