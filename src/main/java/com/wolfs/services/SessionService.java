package com.wolfs.services;

import com.wolfs.models.Session;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SessionService implements IService<Session> {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Session session) {
        String req = "INSERT INTO Session (date_sess, time_sess, cap_sess, nbr_places_sess) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(1, Date.valueOf(session.getDate_sess())); // Convert LocalDate to SQL Date
            pst.setTime(2, Time.valueOf(session.getTime_sess())); // Convert LocalTime to SQL Time
            pst.setInt(3, session.getCap_sess());
            pst.setInt(4, session.getNbr_places_sess());
            pst.executeUpdate();
            System.out.println("Session ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Session session) {
        String req = "UPDATE Session SET date_sess=?, time_sess=?, cap_sess=?, nbr_places_sess=? WHERE id_sess=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(1, Date.valueOf(session.getDate_sess())); // Convert LocalDate to SQL Date
            pst.setTime(2, Time.valueOf(session.getTime_sess())); // Convert LocalTime to SQL Time
            pst.setInt(3, session.getCap_sess());
            pst.setInt(4, session.getNbr_places_sess());
            pst.setInt(5, session.getId_sess());
            pst.executeUpdate();
            System.out.println("Session modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Session session) {
        String req = "DELETE FROM Session WHERE id_sess=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, session.getId_sess());
            pst.executeUpdate();
            System.out.println("Session supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Session> rechercher() {
        List<Session> sessions = new ArrayList<>();

        String req = "SELECT * FROM Session";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                sessions.add(new Session(
                        rs.getInt("id_sess"),
                        rs.getDate("date_sess").toLocalDate(),
                        rs.getTime("time_sess").toLocalTime(),
                        rs.getInt("cap_sess"),
                        rs.getInt("nbr_places_sess")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return sessions;
    }
}