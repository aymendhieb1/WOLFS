package com.wolfs.tests;

import com.wolfs.models.*;
import com.wolfs.services.*;
import com.wolfs.models.Contrat;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MainProg {
    public MainProg() {
    }

    /* ------------------------------------------- Gestion Users ------------------------------------ */

    public static void main(String[] args) throws ParseException {
        Client clientajout1 = new Client( "yaakoubi", "Ben Ali", "ahmed.benali@example.com", "password123", 98765432, 2, 1, "ahmed_profile.jpg");

        Client clientajout2 = new Client( "Sophie", "Dupont", "sophie.dupont@example.com", "securePass!", 92345678, 2, 1, "sophie_avatar.png");

        Client clientajout3 = new Client( "Karim", "Jlassi","karim.jlassi@example.com", "karim2024", 96543210, 2, 1, "karim_profile.png");

        Client clientupdate1 = new Client( 1,"yaakoubi", "Ben Ali", "ahmed.benali@example.com", "password123", 98765432, 2, 1, "ahmed_profile.jpg");

        ClientServices s1=new ClientServices();
        //add client

        //s1.ajouterUser( client1);
        //s1.ajouterUser( client2);
        //s1.ajouterUser( client3);

        //update client

        //s1.modifierUser(clientupdate1);

        //delete client
        //s1.supprimerUser(clientupdate1);
/********************************************/
        Admin adminAjout1 = new Admin("Dupont", "Jean", "jean.dupont@email.com", 98765432, "securePass123", 0);

        Admin adminUpdate1 = new Admin(6,"Dupont", "Jean", "jean.dupont@esprit.tn", 98765432, "securePass123", 0);

        AdminServices s2=new AdminServices();


        //add admin
        //s2.ajouterUser(adminAjout1);

        //update admin
        //s2.modifierUser(adminUpdate1);

        //supprimer admin
        //s2.supprimerUser(adminUpdate1);
        /********************************************/
        Moderator moderatorAjout1 = new Moderator("Dhib", "Youssef", "youssef.dhib@email.com", 98765432, "securePass123", 1);

        Moderator moderatorUpdate1 = new Moderator(9,"Dhib", "Youssef", "youssef.dhib@esprit.tn", 98765432, "securePass123", 1);

        ModeratorServices s3=new ModeratorServices();

        //add moderator
       // s3.ajouterUser(moderatorAjout1);

        //update moderator
        //s3.modifierUser(moderatorUpdate1);

        //supprimer moderator
        //s3.supprimerUser(moderatorUpdate1);



        //recherche Users
        System.out.println("Clients:");
        System.out.println(s1.rechercherUser());
        System.out.println("Admins:");
        System.out.println(s2.rechercherUser());
        System.out.println("Moderators:");
        System.out.println(s3.rechercherUser());

        /* ------------------------------------------- Gestion Contrat ------------------------------------ */


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
        contratToDelete.setIdLocation(37);
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
        /* ------------------------------------------- Gestion vol ------------------------------------ */

        // Initialize VolService
        VolService volService = new VolService();

        /*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Vol volToAdd = new Vol(
                "Tunis",
                "Morocco",
                LocalDateTime.parse("12/12/2025 10:00", formatter),
                LocalDateTime.parse("12/12/2025 14:00", formatter),
                ClasseChaise.ECONOMY,  // Use the Enum here
                "Air Morocco",
                1800,
                24,
                "Test flight"
        );
        volService.ajouterVol(volToAdd);
        System.out.println("Vol ajouté: " + volToAdd);*/


        /*Vol volToModify = new Vol(
                1,
                "Tunis",
                "Italy",
                LocalDateTime.parse("12/12/2025 15:00", formatter),
                LocalDateTime.parse("12/12/2025 17:00", formatter),
                ClasseChaise.BUSINESS,  // Use the Enum here
                "Tunisair",
                2000,
                10,
                "Modified flight"
        );
        volService.modifierVol(volToModify);
        System.out.println("Vol modifié: " + volToModify);*/

        /*
        Vol volToDelete = new Vol(4, "", "", null, null, null, null, 0, 0, "");
        volService.supprimerVol(volToDelete);
        System.out.println("Vol supprimé: " + volToDelete);*/

        List<Vol> vols = volService.rechercherVol();
        System.out.println("-----------------------------------");
        System.out.println("List of Flights");
        System.out.println("-----------------------------------");
        for (Vol vol : vols) {
            System.out.println("Flight ID: " + vol.getFlightID());
            System.out.println("Departure: " + vol.getDeparture());
            System.out.println("Destination: " + vol.getDestination());
            System.out.println("Departure Time: " + vol.getDepartureTime());
            System.out.println("Arrival Time: " + vol.getArrivalTime());
            System.out.println("Classe Chaise: " + vol.getClasseChaise());
            System.out.println("Airline: " + vol.getAirline());
            System.out.println("Price: " + vol.getFlightPrice());
            System.out.println("Available Seats: " + vol.getAvailableSeats());
            System.out.println("Description: " + vol.getDescription());
            System.out.println("-----------------------------------");
        }


        /* ------------------------------------------- Gestion Hotels ------------------------------------ */

        HotelService hot = new HotelService();
        // hot.ajouter(new Hotel("Mouradi5", "xx","23456789","mouradi.mnayka@gmail.com","url","t7eb tetnak fi flousek reservi 3anaaa"));
        //  hot.modifier(new Hotel(1,"MouradiHubb", "xx","23456789","mouradi.mnayka@gmail.com","url","t7eb tetnak fi flousek reservi 3anaaa"));
        //  hot.supprimer(new Hotel(1,"","","","","","" ));
        System.out.println(hot.rechercher());

        // ChambreService ch = new ChambreService();
        //ch.ajouter(new Chambre(1,"exclusive",300,true,1));
        // System.out.println(ch.afficher());

        /* ------------------------------------------- Gestion Circuis ------------------------------------ */

        ActiviteService2 activiteService = new ActiviteService2();
        SessionService sessionService = new SessionService();

         Activite activite1 = new Activite("Yoga", "A relaxing activity", "Beach", "Fitness", 20);
         //activiteService.ajouter(activite1);
         Activite activite2= new Activite(1,"Yoga", "A relaxing activity", "Beach", "Fitness", 20);


        //activite2.setNom_act("Advanced Yoga");
        //activiteService.modifier(activite2);


         //activiteService.supprimer(activite2);


           LocalDate date = LocalDate.now(); // Current date
           LocalTime time = LocalTime.now(); // Current time
           Session session1 = new Session(date, time, 50, 100);
           Session session2 = new Session(1,date, time, 50, 100);

        //sessionService.ajouter(session1);



          //session2.setNbr_places_sess(120);
          //sessionService.modifier(session2);

          //    sessionService.supprimer(session2);



        System.out.println(activiteService.rechercher());

        System.out.println(sessionService.rechercher());


    }


}
