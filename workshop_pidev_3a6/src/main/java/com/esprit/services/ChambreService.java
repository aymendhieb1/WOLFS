package com.esprit.services;

import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import  com.esprit.models.Chambre;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChambreService implements IService<Chambre> {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Chambre ch) {
        String req = "INSERT INTO chambre ( type_Chambre,prix_Chambre,disponibilite_Chambre,id_hotel_chambre) VALUES ('"+ch.getType_Chambre()+"','"+ch.getPrix_Chambre()+"','"+ch.getDisponibilite_Chambre()+"','"+ch.getId_hotel_Chambre()+"')";
        try {

            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Chambre ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Chambre ch) {
        String req = "UPDATE chambre SET type_Chambre='"+ch.getType_Chambre()+"' ,prix_Chambre='"+ch.getPrix_Chambre()+"',disponibilite_Chambre='"+ch.getDisponibilite_Chambre()+"',id_hotel_chambre='"+ch.getId_hotel_Chambre()+"' WHERE id_Chambre="+ch.getId_Chambre();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Chambre modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Chambre ch) {
        String req = "DELETE FROM chambre WHERE id_Chambre="+ch.getId_Chambre();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Chambre supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Chambre> afficher() {
        List<Chambre> ch = new ArrayList<>();

        String req = "SELECT * FROM chambre";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ch.add(new Chambre(rs.getInt("id_Chambre"), rs.getString("type_Chambre"), rs.getInt("prix_Chambre"),rs.getBoolean("disponibilite_Chambre"),rs.getInt("id_hotel_Chambre")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ch;
    }
}
