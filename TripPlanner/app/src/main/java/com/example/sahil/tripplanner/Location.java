package com.example.sahil.tripplanner;

import java.io.Serializable;

public class Location implements Serializable {
    String latitude;
    String longitude;



    @Override
    public String toString() {
        return "Location{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
