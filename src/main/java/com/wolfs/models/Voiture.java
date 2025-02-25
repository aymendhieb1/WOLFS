package com.wolfs.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class Voiture extends Vehicule {
    private IntegerProperty cylinder; // Change to IntegerProperty
    private String image_vehicule;

    // Constructor
    public Voiture(int idVehicule, String matricule, String status, int prix, int cylinder, String image_vehicule) {
        super(idVehicule, matricule, status, prix);
        this.cylinder = new SimpleIntegerProperty(cylinder); // Initialize as IntegerProperty
        this.image_vehicule = image_vehicule;
    }

    public Voiture(String matricule, String status, int prix, int cylinder, String image_vehicule) {
        super(matricule, status, prix);
        this.cylinder = new SimpleIntegerProperty(cylinder); // Initialize as IntegerProperty
        this.image_vehicule = image_vehicule;
    }

    // Getter and Setter for cylinder (JavaFX property)
    public int getCylinder() {
        return cylinder.get();
    }

    public void setCylinder(int cylinder) {
        this.cylinder.set(cylinder);
    }

    public IntegerProperty cylinderProperty() {
        return cylinder;
    }

    // Getter and Setter for image_vehicule
    public String getImage_vehicule() {
        return image_vehicule;
    }

    public void setImage_vehicule(String image_vehicule) {
        this.image_vehicule = image_vehicule;
    }

    // Override method (if needed)
    @Override
    public void consulterVehicule() {
        super.consulterVehicule();
        System.out.println("Cylindrée: " + cylinder.get());
        System.out.println("image: " + image_vehicule);
    }
}