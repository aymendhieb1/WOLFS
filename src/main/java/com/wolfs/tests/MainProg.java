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

        // Initialize CheckoutVolService
        CheckoutVolService checkoutVolService = new CheckoutVolService();

        /*CheckoutVol checkoutVolToAdd = new CheckoutVol(
                2,  // Flight ID
                1,  // User ID
                LocalDateTime.parse("12/12/2025 10:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                2,  // Total passengers
                CheckoutVol.ReservationStatus.Confirmee,  // Correct enum reference with CheckoutVol prefix
                3600  // Total price
        );

        // Print the checkoutVol object to verify
        System.out.println(checkoutVolToAdd);
        checkoutVolService.ajouterCheckoutVol(checkoutVolToAdd);
        System.out.println("Checkout Vol ajouté: " + checkoutVolToAdd);*/

        // Uncomment this section to modify a CheckoutVol
        /*CheckoutVol checkoutVolToModify = new CheckoutVol(
                2,  // Flight ID (new value)
                1,  // User ID (new value)
                LocalDateTime.parse("12/12/2025 15:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), // Modified reservation date
                30,  // Modified total passengers
                CheckoutVol.ReservationStatus.En_Attente,  // Modified reservation status (use the nested enum)
                4500  // Modified total price
        );


// (The null-check is optional if you’re sure the status is set.)
        if (checkoutVolToModify.getReservationStatus() == null) {
            checkoutVolToModify.setReservationStatus(CheckoutVol.ReservationStatus.En_Attente);
        }

// Call the service method to modify the checkout
        checkoutVolService.modifierCheckoutVol(checkoutVolToModify);
        System.out.println("Checkout Vol modifié: " + checkoutVolToModify);*/


        // Uncomment this section to delete a CheckoutVol
        CheckoutVol checkoutVolToDelete = new CheckoutVol(9, 0, 0, null, 0, CheckoutVol.ReservationStatus.ANNULEE, 0);
        checkoutVolService.supprimerCheckoutVol(checkoutVolToDelete);
        System.out.println("Checkout Vol supprimé: " + checkoutVolToDelete);

        // List all CheckoutVol entries from the checkout table
        List<CheckoutVol> checkoutVols = checkoutVolService.rechercherCheckoutVol();
        System.out.println("-----------------------------------");
        System.out.println("List of Checkout Flights");
        System.out.println("-----------------------------------");
        for (CheckoutVol checkoutVol : checkoutVols) {
            System.out.println("Checkout ID: " + checkoutVol.getCheckoutID());
            System.out.println("Flight ID: " + checkoutVol.getFlightID());
            System.out.println("User ID: " + checkoutVol.getUserID());
            System.out.println("Reservation Date: " + checkoutVol.getReservationDate());
            System.out.println("Total Passengers: " + checkoutVol.getTotalPassengers());
            System.out.println("Reservation Status: " + checkoutVol.getReservationStatus());
            System.out.println("Total Price: " + checkoutVol.getTotalPrice());
            System.out.println("-----------------------------------");
        }

        /* ------------------------------------------- Fin Partie Amine ------------------------------------ */
    }
}
