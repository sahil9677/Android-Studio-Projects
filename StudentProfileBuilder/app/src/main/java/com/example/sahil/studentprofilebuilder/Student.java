package com.example.sahil.studentprofilebuilder;

import java.io.Serializable;

public class Student implements Serializable{
    String first, last, dept;
    long id;



    public Student(String first, String last, String dept, long id) {
        this.first = first;
        this.last = last;
        this.dept = dept;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}
