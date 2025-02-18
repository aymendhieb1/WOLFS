package com.wolfs.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Bus extends Vehicule {
    private IntegerProperty nbPlace; // Change to IntegerProperty

    // Constructor
    public Bus(int idVehicule, String matricule, String status, int prix, int nbPlace) {
        super(idVehicule, matricule, status, prix);
        this.nbPlace = new SimpleIntegerProperty(nbPlace); // Initialize as IntegerProperty
    }

    public Bus(String matricule, String status, int prix, int nbPlace) {
        super(matricule, status, prix);
        this.nbPlace = new SimpleIntegerProperty(nbPlace); // Initialize as IntegerProperty
    }

    public Bus() {
        this.nbPlace = new SimpleIntegerProperty(); // Default constructor
    }

    // Getter and Setter for nbPlace (JavaFX property)
    public int getNbPlace() {
        return nbPlace.get();
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace.set(nbPlace);
    }

    public IntegerProperty nbPlaceProperty() {
        return nbPlace;
    }

    // Override method (if needed)
    @Override
    public void consulterVehicule() {
        super.consulterVehicule();
        System.out.println("Nombre de places: " + nbPlace.get());
    }
}