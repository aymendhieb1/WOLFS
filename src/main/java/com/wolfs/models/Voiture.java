package com.wolfs.models;

public class Voiture extends Vehicule {
    private int cylinder;

    // Constructor
    public Voiture(int idVehicule, String matricule, String status, int cylinder) {
        super(idVehicule, matricule, status);
        this.cylinder = cylinder;
    }

    // Getter and Setter
    public int getCylinder() {
        return cylinder;
    }

    public void setCylinder(int cylinder) {
        this.cylinder = cylinder;
    }

    // Override method (if needed)
    @Override
    public void consulterVehicule() {
        super.consulterVehicule();
        System.out.println("Cylindrée: " + cylinder);
    }
}

