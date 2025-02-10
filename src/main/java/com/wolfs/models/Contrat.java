package com.wolfs.models;

import java.util.Date;

public class Contrat {
    private int idLocation;
    private Date dateD;
    private Date dateF;
    private int cinLocateur;
    private String photoPermit;
    private Vehicule vehicule; // Relationship with Vehicule

    public Contrat() {
    }

    // Constructor
    public Contrat(int idLocation, Date dateD, Date dateF, int cinLocateur, String photoPermit, Vehicule vehicule) {
        this.idLocation = idLocation;
        this.dateD = dateD;
        this.dateF = dateF;
        this.cinLocateur = cinLocateur;
        this.photoPermit = photoPermit;
        this.vehicule = vehicule;
    }

    // Getters and Setters
    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public Date getDateD() {
        return dateD;
    }

    public void setDateD(Date dateD) {
        this.dateD = dateD;
    }

    public Date getDateF() {
        return dateF;
    }

    public void setDateF(Date dateF) {
        this.dateF = dateF;
    }

    public int getCinLocateur() {
        return cinLocateur;
    }

    public void setCinLocateur(int cinLocateur) {
        this.cinLocateur = cinLocateur;
    }

    public String getPhotoPermit() {
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

