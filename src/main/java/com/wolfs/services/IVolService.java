package com.wolfs.services;

import com.wolfs.models.Vol;
import java.util.List;

public interface IVolService {

    void ajouterVol(Vol vol);
    void modifierVol(Vol vol);
    void supprimerVol(Vol vol);
    List<Vol> rechercherVol();
}
