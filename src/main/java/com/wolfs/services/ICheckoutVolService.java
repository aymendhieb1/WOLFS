package com.wolfs.services;

import com.wolfs.models.CheckoutVol;
import java.util.List;

public interface ICheckoutVolService {

    void ajouterCheckoutVol(CheckoutVol checkoutVol);
    void modifierCheckoutVol(CheckoutVol checkoutVol);
    void supprimerCheckoutVol(CheckoutVol checkoutVol);
    List<CheckoutVol> rechercherCheckoutVol();
}
