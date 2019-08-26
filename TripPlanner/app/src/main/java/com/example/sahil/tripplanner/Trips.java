package com.example.sahil.tripplanner;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;

public class Trips implements Serializable {
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Places> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Places> places) {
        this.places = places;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    ArrayList<Places> places = new ArrayList<>();
    String city, trip;
    String tripID;

    @Override
    public String toString() {
        return "Trips{" +
                "date='" + date + '\'' +
                ", places=" + places +
                ", city='" + city + '\'' +
                ", trip='" + trip + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }
}
