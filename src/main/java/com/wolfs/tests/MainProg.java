package com.wolfs.tests;

import com.wolfs.models.ClasseChaise;
import com.wolfs.models.Vol;
import com.wolfs.services.VolService;

import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class MainProg {
    public static void main(String[] args) {

        /* ------------------------------------------- Debut Partie Amine ------------------------------------ */

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
        /* ------------------------------------------- Fin Partie Amine ------------------------------------ */
    }
}
