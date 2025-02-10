package com.esprit.tests;

import com.esprit.models.Chambre;
import com.esprit.models.Hotel;

import com.esprit.services.ChambreService;
import com.esprit.services.HotelService;
import com.esprit.utils.DataSource;

public class MainProg {
    public static void main(String[] args) {
        HotelService hot = new HotelService();
      // hot.ajouter(new Hotel("Mouradi5", "xx","23456789","mouradi.mnayka@gmail.com","url","t7eb tetnak fi flousek reservi 3anaaa"));
       //  hot.modifier(new Hotel(1,"MouradiHubb", "xx","23456789","mouradi.mnayka@gmail.com","url","t7eb tetnak fi flousek reservi 3anaaa"));
      //  hot.supprimer(new Hotel(1,"","","","","","" ));
        System.out.println(hot.afficher());

       // ChambreService ch = new ChambreService();
        //ch.ajouter(new Chambre(1,"exclusive",300,true,1));
       // System.out.println(ch.afficher());


    }
}
