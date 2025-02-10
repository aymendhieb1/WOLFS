package com.wolfs.models;

public class User {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String email;
    protected String password;
    protected int num_tel;
    protected int role;

    public User(String name, String prenom, String email, String password, int num_tel, int role) {
        this.nom = name;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.num_tel = num_tel;
        this.role = role;
    }

    public User(int id, String nom, String prenom, String email, String password, int num_tel, int role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.num_tel = num_tel;
        this.role = role;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return nom;
    }

    public void setName(String name) {
        this.nom = name;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", num_tel=" + num_tel +
                ", role=" + role +
                '}';
    }
}
