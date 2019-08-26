package com.example.sahil.polymaps;

import java.util.ArrayList;

public class Location {
    ArrayList<LocationParameters> points = new ArrayList<>();
    String title;

    @Override
    public String toString() {
        return "Location{" +
                "points=" + points +
                ", title='" + title + '\'' +
                '}';
    }

    public ArrayList<LocationParameters> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<LocationParameters> points) {
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Location(ArrayList<LocationParameters> points, String title) {

        this.points = points;
        this.title = title;
    }
}
