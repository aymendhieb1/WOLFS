package com.esprit.tests;

import com.esprit.models.Activite;
import com.esprit.models.Session;
import com.esprit.services.ActiviteService2;
import com.esprit.services.SessionService;

import java.time.LocalDate;
import java.time.LocalTime;
public class MainProg {
    public static void main(String[] args) {
        ActiviteService2 activiteService = new ActiviteService2();
        SessionService sessionService = new SessionService();

        Activite activite1 = new Activite("Yoga", "A relaxing activity", "Beach", "Fitness", 20);
       //activiteService.ajouter(activite1);

        //Activite activite3 = new Activite("cours", "A relaxing activity", "sa7raaa", "jawww", 30);
        //activiteService.ajouter(activite3);

        activite1.setId_act(1);
         activite1.setNom_act("Advanced Yoga");
   // activiteService.modifier(activite1);
        //activite3.setId_act(4);
        //activiteService.supprimer(activite3);


      LocalDate date = LocalDate.now(); // Current date
       LocalTime time = LocalTime.now(); // Current time
     Session session1 = new Session(date, time, 60, 150);
      // sessionService.ajouter(session1);


        session1.setId_sess(1);
        session1.setNbr_places_sess(130);
       sessionService.modifier(session1);


        session1.setId_sess(1);
       sessionService.supprimer(session1);



        System.out.println(activiteService.rechercher());

        System.out.println(sessionService.rechercher());

    }
}
