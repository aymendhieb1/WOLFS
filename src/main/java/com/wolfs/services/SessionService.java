package com.wolfs.services;

import com.wolfs.models.Session;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionService implements IService<Session> {

    Connection connection = DataSource.getInstance().getConnection();

    public void ajouter(Session session) {
        // Check if the activity ID exists
        int idAct = session.getIdAct();
        if (!isValidActivityId(idAct)) {
            System.out.println("Error: Activity ID does not exist.");
            return; // Exit the method if the ID doesn't exist
        }

        // Proceed with session insertion
        String req = "INSERT INTO session (date_sess, time_sess, cap_sess, nbr_places_sess, id_act) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(1, Date.valueOf(session.getDate_sess()));  // Convert LocalDate to SQL Date
            pst.setTime(2, Time.valueOf(session.getTime_sess()));  // Convert LocalTime to SQL Time
            pst.setInt(3, session.getCap_sess());
            pst.setInt(4, session.getNbr_places_sess());
            pst.setInt(5, session.getIdAct());  // Get the selected activity ID

            pst.executeUpdate();
            System.out.println("Session ajoutée avec succès!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la session: " + e.getMessage());
        }
    }

    private boolean isValidActivityId(int idAct) {
        String query = "SELECT COUNT(*) FROM activite WHERE id_act = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, idAct);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // If count > 0, the ID exists
            }
        } catch (SQLException e) {
            System.out.println("Error checking activity ID: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void modifier(Session session) {
        String req = "UPDATE session SET date_sess=?, time_sess=?, cap_sess=?, nbr_places_sess=?, id_act =? WHERE id_sess=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(1, Date.valueOf(session.getDate_sess())); // Convert LocalDate to SQL Date
            pst.setTime(2, Time.valueOf(session.getTime_sess())); // Convert LocalTime to SQL Time
            pst.setInt(3, session.getCap_sess());
            pst.setInt(4, session.getNbr_places_sess());
            pst.setInt(5, session.getIdAct()); // Update id_act (foreign key)
            pst.setInt(6, session.getId_sess()); // Target session ID for update

            pst.executeUpdate();
            System.out.println("Session modifiée avec succès, id_sess: " + session.getId_sess());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la session: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(Session session) {
        String req = "DELETE FROM session WHERE id_sess=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, session.getId_sess());
            pst.executeUpdate();
            System.out.println("Session supprimée, id_sess: " + session.getId_sess());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la session: " + e.getMessage());
        }
    }

    @Override
    public List<Session> rechercher() {
        List<Session> sessions = new ArrayList<>();
        String req = "SELECT * FROM session";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sessions.add(new Session(
                        rs.getInt("id_sess"),
                        rs.getDate("date_sess").toLocalDate(),
                        rs.getTime("time_sess").toLocalTime(),
                        rs.getInt("cap_sess"),
                        rs.getInt("nbr_places_sess"),
                        rs.getInt("id_act") // Retrieve id_act (foreign key)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des sessions: " + e.getMessage());
        }
        return sessions;
    }
}
