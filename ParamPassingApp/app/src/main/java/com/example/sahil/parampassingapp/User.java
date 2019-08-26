package com.example.sahil.parampassingapp;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    double age;

    public User(String name, double age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
