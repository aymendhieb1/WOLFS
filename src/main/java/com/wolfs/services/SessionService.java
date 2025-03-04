package com.wolfs.services;

import com.wolfs.models.Session;
import com.wolfs.utils.DataSource;
import java.time.LocalDate;

import java.sql.*;
import java.time.LocalTime;
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
        String req = "UPDATE session SET date_sess='" + session.getDate_sess() + "', " +
                "time_sess='" + session.getTime_sess() + "', " +
                "cap_sess=" + session.getCap_sess() + ", " +
                "nbr_places_sess=" + session.getNbr_places_sess() + ", " +
                "id_act=" + session.getIdAct() + " WHERE id_sess=" + session.getId_sess();

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Session modifiée avec succès, id_sess: " + session.getId_sess());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la session: " + e.getMessage());
        }
    }


    public int getIdByDate(LocalDate dateSess) {
        String req = "SELECT id_sess FROM session WHERE date_sess = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(1, java.sql.Date.valueOf(dateSess));  // Convert LocalDate to SQL Date

            System.out.println("Executing query with date_sess: " + dateSess);  // Debugging output

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("Found session with id_sess: " + rs.getInt("id_sess"));  // Debugging output
                return rs.getInt("id_sess");
            } else {
                System.out.println("No session found with the provided date.");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving session: " + e.getMessage());
        }
        return -1; // Return -1 if no matching session is found
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

    private List<Session> getSessionsFromDatabase() {
        SessionService sessionService = new SessionService(); // Instantiate session service
        return sessionService.getAllSessions();  // Fetch all sessions correctly
    }
    public List<Session> getAllSessions() {
        // Create some sample session data for demonstration, with correct types
        List<Session> sessions = new ArrayList<>();

        // Example session data, replace with actual data fetching logic
        sessions.add(new Session(LocalDate.of(2025, 3, 5), LocalTime.of(10, 0), 30, 10, 1)); // Sample session 1
        sessions.add(new Session(LocalDate.of(2025, 3, 6), LocalTime.of(14, 0), 25, 15, 2)); // Sample session 2

        return sessions;
    }
}
