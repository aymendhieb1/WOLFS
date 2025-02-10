package com.wolfs.services;

import com.wolfs.models.Activite;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiviteService2 implements IService<Activite> {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Activite Activite) {
        String req = "INSERT INTO Activite ( nom_act,  descript,  localisation,  type, prix_act) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, Activite.getNom_act());
            pst.setString(2, Activite.getDescript()) ;
            pst.setString(3, Activite.getLacalisation());
            pst.setString(4, Activite.getType());
            pst.setFloat(5, Activite.getPrix_act());
            pst.executeUpdate();
            System.out.println("Activite ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Activite Activite) {
        String req = "UPDATE activite SET nom_act=? ,descript=?,localisation=?,type=?,prix_act=? WHERE id_act=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, Activite.getNom_act());
            pst.setString(2, Activite.getDescript()) ;
            pst.setString(3, Activite.getLacalisation());
            pst.setString(4, Activite.getType());
            pst.setFloat(5, Activite.getPrix_act());
            pst.setInt(6, Activite.getId_act());
            pst.executeUpdate();
            System.out.println("Activite modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Activite Activite) {
        String req = "DELETE FROM activite WHERE id_act=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, Activite.getId_act());
            pst.executeUpdate();
            System.out.println("Personne supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Activite> rechercher() {
        List<Activite> Activites = new ArrayList<>();

        String req = "SELECT * FROM Activite";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Activites.add(new Activite(rs.getInt("id_act"), rs.getString("nom_act"), rs.getString("descript"),rs.getString("type"), rs.getString("localisation"), rs.getInt("prix_act")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Activites;
    }
}
