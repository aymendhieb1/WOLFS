package com.esprit.tests;

import com.esprit.models.Bus;
import com.esprit.models.Contrat;
import com.esprit.models.Vehicule;
import com.esprit.models.Voiture;
import com.esprit.services.ContratService;
import com.esprit.services.VehiculeService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainProg {
    public static void main(String[] args) throws ParseException {
        ContratService contratService = new ContratService();
        VehiculeService vehiculeService = new VehiculeService(); // Vehicle service

        // Test: Add a contract
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Contrat contratToAdd = new Contrat();
        contratToAdd.setDateD(sdf.parse("2024-02-04")); // Setting dateD
        contratToAdd.setDateF(sdf.parse("2024-02-06")); //
        contratToAdd.setCinLocateur(1234);
        contratToAdd.setPhotoPermit("photoPermit.jpg");

        // Create a Bus and assign it to the contract (You can also use Voiture if needed)
        Bus bus = new Bus(1, "222tu5115", "Available", 50); // Bus with 40 seats
        contratToAdd.setVehicule(bus);

        //contratService.ajouter(contratToAdd); // Add contract
        System.out.println("--------------------------!\n");

        // Test: Modify a contract
        Contrat contratToUpdate = new Contrat();
        contratToUpdate.setIdLocation(18); // Assuming contract ID to modify is 7
        contratToUpdate.setDateD(sdf.parse("2024-02-3")); // New Start Date
        contratToUpdate.setDateF(sdf.parse("2024-02-4")); // New End Date
        contratToUpdate.setCinLocateur(1122);
        contratToUpdate.setPhotoPermit("newPhotoPermit1.jpg");

        // Assuming you want to update the bus vehicle or replace it with a new one
        Bus updatedBus = new Bus(1, "222tu5114", "Available", 50); // Update Bus with new number of seats
        contratToUpdate.setVehicule(updatedBus);

        //contratService.modifier(contratToUpdate); // Modify the contract
        System.out.println("------------------------------\n");

        // Test: Delete a contract
        Contrat contratToDelete = new Contrat();
        contratToDelete.setIdLocation(28);
        //contratService.supprimer(contratToDelete); // Delete contract
        System.out.println("-------------------------------------\n");

        // Test: Get all contracts and display them along with vehicle info
        System.out.println("📜 Listing all contracts...\n");

        List<Contrat> contratsList = contratService.getAllContrats(); // Get all contracts
        for (Contrat contrat : contratsList) {
            Vehicule vehicule = contrat.getVehicule();
            System.out.println("Contract ID: " + contrat.getIdLocation());
            System.out.println("Contract Date Start: " + contrat.getDateD());
            System.out.println("Contract Date End: " + contrat.getDateF());
            System.out.println("Locataire CIN: " + contrat.getCinLocateur());
            System.out.println("Photo Permit: " + contrat.getPhotoPermit());

            System.out.println("Vehicule Matricule: " + vehicule.getMatricule());
            if (vehicule instanceof Voiture) {
                System.out.println("Vehicule Type: Voiture");
                System.out.println("Vehicule Cylinder: " + ((Voiture) vehicule).getCylinder());
            } else if (vehicule instanceof Bus) {
                System.out.println("Vehicule Type: Bus");
                System.out.println("Vehicule nbPlace: " + ((Bus) vehicule).getNbPlace());
            }
            System.out.println("---------------------------------");
        }

        // Example to add, modify, and delete a vehicle
        // Add a vehicle
        Vehicule newVehicule = new Voiture(0, "AAA123", "Available", 1600);
       // vehiculeService.ajouter(newVehicule);

        // Modify a vehicle (Assuming the vehicle ID is 1)
        Vehicule modifiedVehicule = new Bus(1, "BBB123", "available", 30);
        //vehiculeService.modifier(modifiedVehicule);

        // Delete a vehicle
        Vehicule vehiculeToDelete = new Voiture(2 ,"221TN1000" , "disponible", 5); // Create a dummy bus with the ID
        //vehiculeService.supprimer(vehiculeToDelete);

        List<Vehicule> vehiculesList = vehiculeService.getAllVehicules();  // Get all vehicles

        // Display each vehicle in the list
        System.out.println("🚗 Listing all vehicles...\n");
        for (Vehicule vehicule : vehiculesList) {
            System.out.println("Vehicule ID: " + vehicule.getIdVehicule());
            System.out.println("Matricule: " + vehicule.getMatricule());
            System.out.println("Status: " + vehicule.getStatus());
            if (vehicule instanceof Voiture) {
                System.out.println("Vehicule Type: Voiture");
                System.out.println("Cylinder: " + ((Voiture) vehicule).getCylinder());
            } else if (vehicule instanceof Bus) {
                System.out.println("Vehicule Type: Bus");
                System.out.println("Seats: " + ((Bus) vehicule).getNbPlace());
            }
            System.out.println("---------------------------------");
        }
    }
}


