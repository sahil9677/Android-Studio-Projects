package com.example.sahil.tripplanner;

import java.io.Serializable;

public class Places implements Serializable {
    Location location;
    String name;


    @Override
    public String toString() {
        return "Places{" +
                "location=" + location +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
