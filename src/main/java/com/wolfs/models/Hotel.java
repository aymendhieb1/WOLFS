package com.wolfs.models;

public class Hotel {
    private int id_hotel;
    private String nom_hotel;
    private String localisation_hotel;
    private String num_telephone_hotel;
    private String email_hotel;
    private String image_hotel;
    private String description_hotel;

    public Hotel(int id_hotel, String nom_hotel, String localisation_hotel, String num_telephone_hotel, String email_hotel, String image_hotel, String description_hotel) {
        this.id_hotel = id_hotel;
        this.nom_hotel = nom_hotel;
        this.localisation_hotel = localisation_hotel;
        this.num_telephone_hotel = num_telephone_hotel;
        this.email_hotel = email_hotel;
        this.image_hotel = image_hotel;
        this.description_hotel = description_hotel;
    }

    public Hotel(String nom_hotel, String localisation_hotel, String num_telephone_hotel, String email_hotel, String image_hotel, String description_hotel) {
        this.nom_hotel = nom_hotel;
        this.localisation_hotel = localisation_hotel;
        this.num_telephone_hotel = num_telephone_hotel;
        this.email_hotel = email_hotel;
        this.image_hotel = image_hotel;
        this.description_hotel = description_hotel;
    }

    public String getDescription_hotel() {
        return description_hotel;
    }

    public String getEmail_hotel() {
        return email_hotel;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public String getImage_hotel() {
        return image_hotel;
    }

    public String getLocalisation_hotel() {
        return localisation_hotel;
    }

    public String getNom_hotel() {
        return nom_hotel;
    }

    public String getNum_telephone_hotel() {
        return num_telephone_hotel;
    }

    public void setDescription_hotel(String description_hotel) {
        this.description_hotel = description_hotel;
    }

    public void setNum_telephone_hotel(String num_telephone_hotel) {
        this.num_telephone_hotel = num_telephone_hotel;
    }

    public void setNom_hotel(String nom_hotel) {
        this.nom_hotel = nom_hotel;
    }

    public void setLocalisation_hotel(String localisation_hotel) {
        this.localisation_hotel = localisation_hotel;
    }

    public void setImage_hotel(String image_hotel) {
        this.image_hotel = image_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public void setEmail_hotel(String email_hotel) {
        this.email_hotel = email_hotel;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id_hotel=" + id_hotel +
                ", email_hotel='" + email_hotel + '\'' +
                ", description_hotel='" + description_hotel + '\'' +
                ", image_hotel='" + image_hotel + '\'' +
                ", localisation_hotel='" + localisation_hotel + '\'' +
                ", nom_hotel='" + nom_hotel + '\'' +
                ", num_telephone_hotel='" + num_telephone_hotel + '\'' +
                '}';
    }
}