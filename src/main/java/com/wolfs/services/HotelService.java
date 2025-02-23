package com.wolfs.services;

import com.wolfs.models.Chambre;
import com.wolfs.models.Hotel;
import com.wolfs.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



import com.wolfs.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HotelService implements IService<Hotel> {

    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Hotel hot) {
        String req = "INSERT INTO hotel (nom_hotel, localisation_hotel,num_telephone_hotel,email_hotel,image_hotel,description_hotel) VALUES ('"+hot.getNom_hotel()+"', '"+hot.getLocalisation_hotel()+"', '"+hot.getNum_telephone_hotel()+"', '"+hot.getEmail_hotel()+"', '"+hot.getImage_hotel()+"', '"+hot.getDescription_hotel()+"')";
        try {

            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Hotel ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Hotel hot) {
        String req = "UPDATE hotel SET nom_hotel='"+hot.getNom_hotel()+"' ,localisation_hotel='"+hot.getLocalisation_hotel()+"',num_telephone_hotel='"+hot.getNum_telephone_hotel()+"',email_hotel='"+hot.getEmail_hotel()+"',image_hotel='"+hot.getImage_hotel()+"',description_hotel='"+hot.getDescription_hotel()+"' WHERE id_hotel="+hot.getId_hotel();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Hotel modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Hotel hot) {
        String req = "DELETE FROM hotel WHERE id_hotel="+hot.getId_hotel();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("hotel supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Hotel> rechercher() {
        List<Hotel> hot = new ArrayList<>();

        String req = "SELECT * FROM hotel";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                hot.add(new Hotel(rs.getInt("id_hotel"), rs.getString("nom_hotel"), rs.getString("localisation_hotel"), rs.getString("num_telephone_hotel"), rs.getString("email_hotel"), rs.getString("image_hotel"), rs.getString("description_hotel")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return hot;
    }

    public int ChercherId(String email) {
        String req = "SELECT id_hotel FROM hotel WHERE email_hotel = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_hotel");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }
    public int getIdHotelBynom(String nom) {
        Chambre chambre = null;
        String sql = "SELECT (id_hotel) FROM hotel WHERE nom_hotel = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt("id_hotel"));
                return rs.getInt("id_hotel");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public String getnomHotelByid(int id) {
        Chambre chambre = null;
        String sql = "SELECT (nom_hotel) FROM hotel WHERE id_hotel = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom_hotel");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
