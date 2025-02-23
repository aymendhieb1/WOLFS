package com.wolfs.models;

import java.util.ArrayList;
import java.util.List;

public class Vehicule  {
    private int idVehicule;
    protected String matricule;
    private String status;
    private int prix ;
    private List<Contrat> contrats = new ArrayList<>();

    // Constructor
    public Vehicule(int idVehicule, String matricule, String status,int prix) {
        this.idVehicule = idVehicule;
        this.matricule = matricule;
        this.status = status;
        this.prix = prix;
    }
    // Constructor
    public Vehicule( String matricule, String status,int prix) {

        this.matricule = matricule;
        this.status = status;
        this.prix = prix;
    }

    public Vehicule() {
        this.idVehicule=idVehicule;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setContrats(List<Contrat> contrats) {
        this.contrats = contrats;
    }

    // Getters and Setters
    public List<Contrat> getContrats() {
        return contrats;
    }

    public void addContrat(Contrat contrat) {
        this.contrats.add(contrat);
    }

    public void consulterVehicule() {
        System.out.println("Véhicule: " + matricule + ", Status: " + status);
        for (Contrat contrat : contrats) {
            contrat.consulterLocation();
        }
    }
}


