package com.wolfs.models;

import java.time.LocalDateTime;

public class CheckoutVol {

    private int CheckoutID;
    private int FlightID;
    private int UserID;
    private LocalDateTime ReservationDate;
    private int TotalPassengers;
    private ReservationStatus ReservationStatus;  // Now it's the ENUM
    private int TotalPrice;

    // Constructor for retrieval (includes CheckoutID)
    public CheckoutVol(int checkoutID, int flightID, int userID, LocalDateTime reservationDate,
                       int totalPassengers, ReservationStatus reservationStatus, int totalPrice) {
        this.CheckoutID = checkoutID;
        this.FlightID = flightID;
        this.UserID = userID;
        this.ReservationDate = reservationDate;
        this.TotalPassengers = totalPassengers;
        this.ReservationStatus = reservationStatus;
        this.TotalPrice = totalPrice;
    }



    // Constructor for creation (without CheckoutID)
    public CheckoutVol(int flightID, int userID, LocalDateTime reservationDate,
                       int totalPassengers, ReservationStatus reservationStatus, int totalPrice) {
        this.FlightID = flightID;
        this.UserID = userID;
        this.ReservationDate = reservationDate;
        this.TotalPassengers = totalPassengers;
        this.ReservationStatus = reservationStatus;
        this.TotalPrice = totalPrice;
    }

    // Enum for ReservationStatus
    public enum ReservationStatus {
        CONFIRMEE, EN_ATTENTE, En_Attente, ANNULEE
    }

    // Getter and Setter methods for all fields
    public int getCheckoutID() {
        return CheckoutID;
    }

    public void setCheckoutID(int checkoutID) {
        this.CheckoutID = checkoutID;
    }

    public int getFlightID() {
        return FlightID;
    }

    public void setFlightID(int flightID) {
        this.FlightID = flightID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        this.UserID = userID;
    }

    public LocalDateTime getReservationDate() {
        return ReservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.ReservationDate = reservationDate;
    }

    public int getTotalPassengers() {
        return TotalPassengers;
    }

    public void setTotalPassengers(int totalPassengers) {
        this.TotalPassengers = totalPassengers;
    }

    public ReservationStatus getReservationStatus() {
        return ReservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.ReservationStatus = reservationStatus;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.TotalPrice = totalPrice;
    }

    // Override toString method for displaying the CheckoutVol details
    @Override
    public String toString() {
        return "CheckoutVol{" +
                "CheckoutID=" + CheckoutID +
                ", FlightID=" + FlightID +
                ", UserID=" + UserID +
                ", ReservationDate=" + ReservationDate +
                ", TotalPassengers=" + TotalPassengers +
                ", ReservationStatus=" + ReservationStatus +
                ", TotalPrice=" + TotalPrice +
                '}';
    }
}
