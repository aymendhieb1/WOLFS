package com.wolfs.models;

public class Moderator extends User {
    public Moderator(String nom, String prenom, String email, int num_tel,String password, int role) {
        super(nom, prenom, email, password, num_tel, role);
    }

    public Moderator(int id, String nom, String prenom, String email, int num_tel,String password, int role) {
        super(id, nom, prenom, email, password, num_tel, role);
    }
}
