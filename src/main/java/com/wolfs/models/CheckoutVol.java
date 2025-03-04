package com.wolfs.models;

import java.time.LocalDateTime;

public class CheckoutVol {

    private int CheckoutID;
    private int FlightID;  // Now stores the FlightID instead of the Vol object
    private String Aircraft;
    private int FlightCrew;
    private String Gate;
    private LocalDateTime ReservationDate;
    private int TotalPassengers;
    private ReservationStatus ReservationStatus;
    private int TotalPrice;

    // Constructor for retrieval (includes CheckoutID and FlightID)
    public CheckoutVol(int checkoutID, int flightID, String aircraft, int flightCrew, String gate,
                       LocalDateTime reservationDate, int totalPassengers,
                       ReservationStatus reservationStatus, int totalPrice) {
        this.CheckoutID = checkoutID;
        this.FlightID = flightID;  // Changed from Vol to FlightID
        this.Aircraft = aircraft;
        this.FlightCrew = flightCrew;
        this.Gate = gate;
        this.ReservationDate = reservationDate;
        this.TotalPassengers = totalPassengers;
        this.ReservationStatus = reservationStatus;
        this.TotalPrice = totalPrice;
    }

    // Constructor for creation (without CheckoutID)
    public CheckoutVol(int flightID, String aircraft, int flightCrew, String gate,
                       LocalDateTime reservationDate, int totalPassengers,
                       ReservationStatus reservationStatus, int totalPrice) {
        this.FlightID = flightID;  // Changed from Vol to FlightID
        this.Aircraft = aircraft;
        this.FlightCrew = flightCrew;
        this.Gate = gate;
        this.ReservationDate = reservationDate;
        this.TotalPassengers = totalPassengers;
        this.ReservationStatus = reservationStatus;
        this.TotalPrice = totalPrice;
    }

    // Enum for ReservationStatus
    public enum ReservationStatus {
        CONFIRMEE, EN_ATTENTE, ANNULEE
    }

    // Getter and Setter methods
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

    public String getAircraft() {
        return Aircraft;
    }

    public void setAircraft(String aircraft) {
        this.Aircraft = aircraft;
    }

    public int getFlightCrew() {
        return FlightCrew;
    }

    public void setFlightCrew(int flightCrew) {
        this.FlightCrew = flightCrew;
    }

    public String getGate() {
        return Gate;
    }

    public void setGate(String gate) {
        this.Gate = gate;
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

    // Override toString() to show the flight route ("Departure to Destination") for end-user display.
    @Override
    public String toString() {
        return "CheckoutVol{" +
                "CheckoutID=" + CheckoutID +
                ", FlightID=" + FlightID +
                ", Aircraft='" + Aircraft + '\'' +
                ", FlightCrew=" + FlightCrew +
                ", Gate='" + Gate + '\'' +
                ", ReservationDate=" + ReservationDate +
                ", TotalPassengers=" + TotalPassengers +
                ", ReservationStatus=" + ReservationStatus +
                ", TotalPrice=" + TotalPrice +
                '}';
    }
}
