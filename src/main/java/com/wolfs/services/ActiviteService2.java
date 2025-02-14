package com.wolfs.services;

import com.wolfs.models.Activite;
import com.wolfs.models.Hotel;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiviteService2 implements IService<Activite> {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Activite activite) {
        String req = "INSERT INTO activite (nom_act, descript,localisation,type,prix_act) VALUES ('"+ activite.getNom_act()+"', '"+activite.getDescript()+"', '"+activite.getLocalisation()+"', '"+activite.getType()+"', '"+activite.getPrix_act()+"')";
        try {

            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("activite ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Activite activite) {
        String req = "UPDATE Activite SET nom_act='"+activite.getNom_act()+"' ,descript='"+activite.getDescript()+"',localisation='"+activite.getLocalisation()+"',type='"+activite.getType()+"',prix_act='"+activite.getPrix_act()+"' WHERE id_act="+activite.getId_act();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("activite modifiée");
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

    public int getIdByNom(String nomAct) {
        String req = "SELECT id_act FROM activite WHERE nom_act = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, nomAct);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_act");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'ID : " + e.getMessage());
        }
        return -1;
    }


    @Override
    public List<Activite> rechercher() {
        List<Activite> act = new ArrayList<>();


        String req = "SELECT * FROM activite";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                act.add(new Activite(rs.getString("nom_act"), rs.getString("descript"), rs.getString("localisation"), rs.getString("type"), rs.getFloat("prix_act")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return act;
    }
}
