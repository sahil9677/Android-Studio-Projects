package com.example.sahil.expensemanager;

import android.net.Uri;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class Expense implements Serializable, Comparable {
    String name,  date;
    Double cost;
    String expenseID;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(String expenseID) {
        this.expenseID = expenseID;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", cost=" + cost +
                ", ExpenseID='" + expenseID + '\'' +
                ", image=" + image +
                '}';
    }

    @Override
    public int compareTo(Object o){
        return 0;
    }

    public static Comparator<Expense> expenseCostComparator = new Comparator<Expense>() {

        public int compare(Expense e1, Expense e2) {
            Double expenseCost1 = e1.getCost();
            Double expenseCost2 = e2.getCost();

            //ascending order
            return expenseCost1.compareTo(expenseCost2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};

    /*Comparator for sorting the list by roll no*/
    public static Comparator<Expense> expenseDateComparator = new Comparator<Expense>() {

        public int compare(Expense e1, Expense e2) {

            String date = e1.getDate();
            String date1 = e2.getDate();

            /*For ascending order*/
            return date.compareTo(date1);

            /*For descending order*/
            //rollno2-rollno1;
        }};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
