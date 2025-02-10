package com.wolfs.models;


public class Chambre {
    private int id_Chambre;
    private String type_Chambre; //' "Standard", "Deluxe", "Suite", "Executive Room"
    private int prix_Chambre;
    private boolean disponibilite_Chambre;
    private int id_hotel_Chambre; // Association avec l'hôtel (clé étrangère)

    // Constructeur
    public Chambre(int id_Chambre, String type_Chambre, int prix_Chambre, boolean disponibilite_Chambre, int id_hotel_Chambre) {
        this.id_Chambre = id_Chambre;
        this.type_Chambre = type_Chambre;
        this.prix_Chambre = prix_Chambre;
        this.disponibilite_Chambre = disponibilite_Chambre;
        this.id_hotel_Chambre = id_hotel_Chambre;
    }

    public Chambre(String type_Chambre, int prix_Chambre, boolean disponibilite_Chambre, int id_hotel_Chambre) {
        this.type_Chambre = type_Chambre;
        this.prix_Chambre = prix_Chambre;
        this.disponibilite_Chambre = disponibilite_Chambre;
        this.id_hotel_Chambre = id_hotel_Chambre;
    }
// Getters et Setters

    public boolean getDisponibilite_Chambre() {
        return disponibilite_Chambre;
    }

    public int getId_Chambre() {
        return id_Chambre;
    }

    public int getId_hotel_Chambre() {
        return id_hotel_Chambre;
    }

    public int getPrix_Chambre() {
        return prix_Chambre;
    }

    public String getType_Chambre() {
        return type_Chambre;
    }

    public void setDisponibilite_Chambre(boolean disponibilite_Chambre) {
        this.disponibilite_Chambre = disponibilite_Chambre;
    }

    public void setType_Chambre(String type_Chambre) {
        this.type_Chambre = type_Chambre;
    }

    public void setId_hotel_Chambre(int id_hotel_Chambre) {
        this.id_hotel_Chambre = id_hotel_Chambre;
    }

    public void setId_Chambre(int id_Chambre) {
        this.id_Chambre = id_Chambre;
    }

    public void setPrix_Chambre(int prix_Chambre) {
        this.prix_Chambre = prix_Chambre;
    }

    @Override
    public String toString() {
        return "Chambre{" +
                "disponibilite_Chambre=" + disponibilite_Chambre +
                ", id_Chambre=" + id_Chambre +
                ", type_Chambre='" + type_Chambre + '\'' +
                ", prix_Chambre=" + prix_Chambre +
                ", id_hotel_chambre=" + id_hotel_Chambre +
                '}';
    }
}
