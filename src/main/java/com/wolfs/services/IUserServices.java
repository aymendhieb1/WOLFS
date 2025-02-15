package com.wolfs.services;

import com.wolfs.models.Client;

import java.util.List;

public interface IUserServices<T>{
    void ajouterUser(T var1);

    void modifierUser(T var1);

    void supprimerUser(T var1);

    List<T> rechercherUser();

    T verifierUser(String email, String password);

}