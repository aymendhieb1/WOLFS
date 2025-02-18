package com.wolfs.services;

import com.wolfs.models.Vehicule;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import  com.wolfs.models.Chambre;
import com.wolfs.utils.DataSource;

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
        String req = "INSERT INTO chambre ( type_Chambre,prix_Chambre,disponibilite_Chambre,description_Chambre,id_hotel_Chambre) VALUES ('"+ch.getType_Chambre()+"','"+ch.getPrix_Chambre()+"','"+ch.getDisponibilite_Chambre()+"','"+ch.getDescription_Chambre()+"','"+ch.getId_hotel_Chambre()+"')";
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
        String req = "UPDATE chambre SET type_Chambre='"+ch.getType_Chambre()+"' ,prix_Chambre='"+ch.getPrix_Chambre()+"',disponibilite_Chambre='"+ch.getDisponibilite_Chambre()+"',description_Chambre='"+ch.getDescription_Chambre()+"',id_hotel_chambre='"+ch.getId_hotel_Chambre()+"' WHERE id_Chambre="+ch.getId_Chambre();
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
        int id=chercher_id(ch);
        System.out.println("ch "+id);
        String req = "DELETE FROM chambre WHERE id_Chambre="+id;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Chambre supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Chambre> rechercher() {
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

    public List<Chambre> rechercher_1() {
        List<Chambre> ch = new ArrayList<>();

        String req = "SELECT * FROM chambre";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                HotelService hotel = new HotelService();
                ch.add(new Chambre(rs.getInt("id_Chambre"), rs.getString("type_Chambre"), rs.getInt("prix_Chambre"),rs.getBoolean("disponibilite_Chambre"),rs.getString("description_Chambre"),String.valueOf(hotel.getnomHotelByid(rs.getInt("id_hotel_Chambre")))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ch;
    }
    public int chercher_id(Chambre ch) {
        Chambre chambre = null;
        // String sql = "SELECT (id_Chambre) FROM chambre WHERE type_Chambre = ? AND prix_Chambre = ? AND disponibilite_Chambre = ? AND description_Chambre = ? AND id_hotel_Chambre = ?";
        String sql = "SELECT (id_Chambre) FROM chambre WHERE type_Chambre = ? AND prix_Chambre = ? AND disponibilite_Chambre = ? AND description_Chambre = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, ch.getType_Chambre());
            stmt.setInt(2, ch.getPrix_Chambre());
            stmt.setBoolean(3, ch.getDisponibilite_Chambre());
            stmt.setString(4, ch.getDescription_Chambre());
            //  stmt.setInt(5, ch.getId_hotel_Chambre());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_Chambre");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
