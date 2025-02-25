package com.wolfs.models;

import java.time.format.DateTimeFormatter;

public class Contrat {
    private int idLocation;
    private String dateD;  // Change to String
    private String dateF;  // Change to String
    private int cinLocateur;
    private String photoPermit;
    private Vehicule vehicule; // Relationship with Vehicule
    private int idVehicule;
    private String nomVehicule;

    public Contrat(String dateD, String dateF, int cinLocateur, String photoPermit) {
        this.dateD = dateD;
        this.dateF = dateF;
        this.cinLocateur = cinLocateur;
        this.photoPermit = photoPermit;
    }

    public Contrat() {

    }

    public void setDateD(String dateD) {
        this.dateD = dateD;
    }

    public void setDateF(String dateF) {
        this.dateF = dateF;
    }

    public String getNomVehicule() {
        return nomVehicule;
    }

    public void setNomVehicule(String nomVehicule) {
        this.nomVehicule = nomVehicule;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");  // Date format


    // Constructor with String dates formatted as yyyy/MM/dd
    public Contrat(int idLocation, String dateD, String dateF, int cinLocateur, String photoPermit, Vehicule vehicule) {
        this.idLocation = idLocation;
        this.dateD = dateD;
        this.dateF = dateF;
        this.cinLocateur = cinLocateur;
        this.photoPermit = photoPermit;
        this.vehicule = vehicule;
    }

    // Constructor with String dates formatted as yyyy/MM/dd
    public Contrat(int cinLocateur, String dateD, String dateF, String photoPermit, int idVehicule) {
        this.cinLocateur = cinLocateur;
        this.dateD =dateD;
        this.dateF = dateF;
        this.photoPermit = photoPermit;
        this.idVehicule = idVehicule;
    }

    public Contrat(int idLocation, int cinLocateur, String dateD, String dateF, String photoPermit, int idVehicule) {
        this.idLocation = idLocation;
        this.cinLocateur = cinLocateur;
        this.dateD = dateD;
        this.dateF = dateF;
        this.photoPermit = photoPermit;
        this.idVehicule = idVehicule;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    // Getters and Setters for String dates
    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public  String getDateD() {
        return dateD;
    }


    public  String getDateF() {
        return dateF;
    }


    public  int getCinLocateur() {
        return cinLocateur;
    }

    public void setCinLocateur(int cinLocateur) {
        this.cinLocateur = cinLocateur;
    }

    public  String getPhotoPermit() {
        return photoPermit;
    }

    public void setPhotoPermit(String photoPermit) {
        this.photoPermit = photoPermit;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }



    // Methods
    public void ajouterLocation() {
        System.out.println("Ajout du contrat de location ID: " + idLocation);
    }

    public void modifierLocation() {
        System.out.println("Modification du contrat ID: " + idLocation);
    }

    public void supprimerLocation() {
        System.out.println("Suppression du contrat ID: " + idLocation);
    }

    public void consulterLocation() {
        System.out.println("Contrat ID: " + idLocation + ", Locateur CIN: " + cinLocateur);
    }

    public int getIdLocationMethod() {
        return this.idLocation;
    }
}
