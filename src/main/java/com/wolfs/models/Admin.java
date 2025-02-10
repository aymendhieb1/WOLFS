package com.wolfs.models;

public class Admin extends User {


    public Admin(String nom, String prenom, String email, int num_tel,String password, int role) {
        super(nom, prenom, email, password, num_tel, role);
    }

    public Admin(int id, String nom, String prenom, String email, int num_tel,String password, int role) {
        super(id, nom, prenom, email, password, num_tel, role);
    }
}
