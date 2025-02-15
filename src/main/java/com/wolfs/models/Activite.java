package com.wolfs.models;

public class Activite {

    private int id_act;
    private String nom_act;
    private String descript;
    private String lacalisation;
    private String type;
    private float prix_act;

    public Activite(int id_act, String nom_act, String descript, String lacalisation, String type,float prix_act) {
        this.id_act = id_act;
        this.nom_act = nom_act;
        this.descript = descript;
        this.lacalisation = lacalisation;
        this.type = type;
        this.prix_act = prix_act;
    }

    public Activite( String nom_act, String descript, String lacalisation, String type,float prix_act) {
        this.nom_act = nom_act;
        this.descript = descript;
        this.lacalisation = lacalisation;
        this.type = type;
        this.prix_act = prix_act;
    }

    public int getId_act() {
        return id_act;
    }

    public String getNom_act() {
        return nom_act;
    }

    public String getDescript() {
        return descript;
    }

    public String getLacalisation() {
        return lacalisation;
    }

    public String getType() {
        return type;
    }

    public float getPrix_act() {
        return prix_act;
    }

    public void setNom_act(String nom_act) {
        this.nom_act = nom_act;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setLacalisation(String lacalisation) {
        this.lacalisation = lacalisation;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrix_act(float prix_act) {
        this.prix_act = prix_act;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "id_act=" + id_act +
                ", nom_act='" + nom_act + '\'' +
                ", descript='" + descript + '\'' +
                ", lacalisation='" + lacalisation + '\'' +
                ", type='" + type + '\'' +
                ", prix_act=" + prix_act +
                '}';
    }
}
