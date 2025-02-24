
package com.wolfs.models;


import java.time.LocalDate;
import java.time.LocalTime;

public class Session {

    private int id_sess;
    private LocalDate date_sess;
    private LocalTime time_sess;
    private int cap_sess;
    private int nbr_places_sess;
    private int id_act ;

    public Session(int id_sess, LocalDate date_sess, LocalTime time_sess, int cap_sess, int nbr_places_sess,int id_act ) {
        this.id_sess = id_sess;
        this.date_sess = date_sess;
        this.time_sess = time_sess;
        this.cap_sess = cap_sess;
        this.nbr_places_sess = nbr_places_sess;
        this.id_act  = id_act ;
    }

    public Session( LocalDate date_sess, LocalTime time_sess, int cap_sess, int nbr_places_sess,int id_act ) {

        this.date_sess = date_sess;
        this.time_sess = time_sess;
        this.cap_sess = cap_sess;
        this.nbr_places_sess = nbr_places_sess;
        this.id_act  = id_act ;
    }

    public int getId_sess() {
        return id_sess;
    }

    public LocalDate getDate_sess() {
        return date_sess;
    }

    public LocalTime getTime_sess() {
        return time_sess;
    }

    public int getCap_sess() {
        return cap_sess;
    }

    public int getNbr_places_sess() {
        return nbr_places_sess;
    }

    public void setDate_sess(LocalDate date_sess) {
        this.date_sess = date_sess;
    }

    public int getIdAct() {
        return id_act ;
    }

    public void setIdAct(int idAct) {
        this.id_act  = idAct;
    }
    public Session() {
        // No-argument constructor required for CalendarView
    }

    public void setTime_sess(LocalTime time_sess) {
        this.time_sess = time_sess;
    }

    public void setCap_sess(int cap_sess) {
        this.cap_sess = cap_sess;
    }

    public void setNbr_places_sess(int nbr_places_sess) {
        this.nbr_places_sess = nbr_places_sess;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id_sess=" + id_sess +
                ", date_sess=" + date_sess +
                ", time_sess=" + time_sess +
                ", cap_sess=" + cap_sess +
                ", nbr_places_sess=" + nbr_places_sess +
                '}';
    }
}

