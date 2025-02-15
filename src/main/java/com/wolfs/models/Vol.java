package com.wolfs.models;

import java.time.LocalDateTime;

public class Vol {
    private int flightID;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private ClasseChaise classeChaise;
    private String airline;
    private int flightPrice;
    private int availableSeats;
    private String description;

    // Constructors
    public Vol(int flightID, String departure, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime,
               ClasseChaise classeChaise, String airline, int flightPrice, int availableSeats, String description) {
        this.flightID = flightID;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.classeChaise = classeChaise;
        this.airline = airline;
        this.flightPrice = flightPrice;
        this.availableSeats = availableSeats;
        this.description = description;
    }

    public Vol(String departure, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime,
               ClasseChaise classeChaise, String airline, int flightPrice, int availableSeats, String description) {

        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.classeChaise = classeChaise;
        this.airline = airline;
        this.flightPrice = flightPrice;
        this.availableSeats = availableSeats;
        this.description = description;
    }

    // Getters and Setters
    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public ClasseChaise getClasseChaise() {
        return classeChaise;
    }

    public void setClasseChaise(ClasseChaise classeChaise) {
        this.classeChaise = classeChaise;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(int flightPrice) {
        this.flightPrice = flightPrice;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Vol{" +
                "flightID=" + flightID +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", classeChaise=" + classeChaise +
                ", airline='" + airline + '\'' +
                ", flightPrice=" + flightPrice +
                ", availableSeats=" + availableSeats +
                ", description='" + description + '\'' +
                '}';
    }
}

