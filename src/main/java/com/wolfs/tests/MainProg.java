package com.wolfs.tests;

import com.wolfs.models.CheckoutVol;
import com.wolfs.models.ReservationStatus;
import com.wolfs.models.ClasseChaise;
import com.wolfs.models.Vol;
import com.wolfs.services.CheckoutVolService;
import com.wolfs.services.VolService;

import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class MainProg {
    public static void main(String[] args) {

        /* ------------------------------------------- Debut Partie Amine ------------------------------------ */

        // Initialize VolService
        VolService volService = new VolService();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Uncomment this section to add a new flight (Vol)
        /*Vol volToAdd = new Vol(
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

        // Uncomment this section to modify a flight (Vol)
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

        // Uncomment this section to delete a flight (Vol)
        /*Vol volToDelete = new Vol(4, "", "", null, null, null, null, 0, 0, "");
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

        /* ------------------------------------------- Debut Partie Amine ------------------------------------ */

        // Initialize the services
        CheckoutVolService checkoutVolService = new CheckoutVolService();



        // -------------------
        // Fetch an existing Vol from the database
        // -------------------
        int flightID = 1; // Change this ID to one that exists in your database
        Vol volFromDB = volService.getVolByID(flightID);
        if (volFromDB == null) {
            System.out.println("No Vol found with ID " + flightID);
            return; // Stop execution if no Vol is found
        }

        // -------------------
        // Test: Adding a new CheckoutVol
        // -------------------
        CheckoutVol checkoutVolToAdd = new CheckoutVol(
                flightID,                           // Use fetched Vol object
                "A320",                               // Aircraft
                5,                                    // Flight crew
                "Gate 1",                             // Gate
                LocalDateTime.parse("12/12/2025 10:00", formatter), // Reservation date
                200,                                  // Total passengers
                CheckoutVol.ReservationStatus.CONFIRMEE,  // Reservation status
                3600                                  // Total price
        );

        System.out.println("Adding CheckoutVol: " + checkoutVolToAdd);
        checkoutVolService.ajouterCheckoutVol(checkoutVolToAdd);
        System.out.println("CheckoutVol ajouté.");

        // -------------------
        // Test: Modifying an existing CheckoutVol
        // -------------------
        int checkoutID = 2; // Change this to a valid ID from your database
        Vol newVolFromDB = volService.getVolByID(2); // Fetch another Vol for modification
        if (newVolFromDB == null) {
            System.out.println("No Vol found with ID 2");
            return;
        }

        CheckoutVol checkoutVolToModify = new CheckoutVol(
                checkoutID,                           // Existing CheckoutID
                flightID,                         // New Vol object with FlightID = 2
                "B737",                               // Modified Aircraft
                10,                                   // Modified Flight crew
                "Gate 2",                             // Modified Gate
                LocalDateTime.parse("12/12/2025 15:00", formatter), // Modified Reservation date
                150,                                  // Modified Total passengers
                CheckoutVol.ReservationStatus.EN_ATTENTE,  // Modified Reservation status
                4500                                  // Modified Total price
        );

        checkoutVolService.modifierCheckoutVol(checkoutVolToModify);
        System.out.println("CheckoutVol modifié: " + checkoutVolToModify);

        // -------------------
        // Test: Deleting a CheckoutVol
        // -------------------
        int checkoutToDeleteID = 9; // Change to an actual ID that exists
        checkoutVolService.supprimerCheckoutVol(checkoutToDeleteID);
        System.out.println("CheckoutVol supprimé avec ID: " + checkoutToDeleteID);

        // -------------------
        // Test: Retrieving and listing all CheckoutVol entries
        // -------------------
        List<CheckoutVol> checkoutVols = checkoutVolService.rechercherCheckoutVol();
        System.out.println("-----------------------------------");
        System.out.println("List of Checkout Vols");
        System.out.println("-----------------------------------");
        for (CheckoutVol cv : checkoutVols) {
            System.out.println("Checkout ID: " + cv.getCheckoutID());
            System.out.println("Flight ID: " + cv.getFlightID());
            System.out.println("Aircraft: " + cv.getAircraft());
            System.out.println("Flight Crew: " + cv.getFlightCrew());
            System.out.println("Gate: " + cv.getGate());
            System.out.println("Reservation Date: " + cv.getReservationDate());
            System.out.println("Total Passengers: " + cv.getTotalPassengers());
            System.out.println("Reservation Status: " + cv.getReservationStatus());
            System.out.println("Total Price: " + cv.getTotalPrice());
            System.out.println("-----------------------------------");
        }



        /* ------------------------------------------- Fin Partie Amine ------------------------------------ */
    }
}
