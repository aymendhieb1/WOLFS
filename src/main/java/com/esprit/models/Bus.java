package com.esprit.models;

public class Bus extends Vehicule {
    private int nbPlace;

    // Constructor
    public Bus(int idVehicule, String matricule, String status, int nbPlace) {
        super(idVehicule, matricule, status);
        this.nbPlace = nbPlace;
    }

    public Bus() {

    }

    // Getter and Setter
    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    // Override method (if needed)
    @Override
    public void consulterVehicule() {
        super.consulterVehicule();
        System.out.println("Nombre de places: " + nbPlace);
    }
}

