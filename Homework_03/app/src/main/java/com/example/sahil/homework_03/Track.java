package com.example.sahil.homework_03;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Track implements Serializable, Comparable {
    String trackName, artistName, primaryGenreName,collectionName, trackViewURL;
    Double trackPrice, collectionPrice;
    Date releaseDate;

    @Override
    public String toString() {
        return "Track{" +
                "trackName='" + trackName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", primaryGenreName='" + primaryGenreName + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", trackViewURL='" + trackViewURL + '\'' +
                ", trackPrice=" + trackPrice +
                ", collectionPrice=" + collectionPrice +
                ", releaseDate=" + releaseDate +
                '}';
    }

    public Track() {


    }

    public Double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(Double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int compareTo(Object o) {
        return 0;

    }
    public static Comparator<Track> trackPriceComparator = new Comparator<Track>() {

        public int compare(Track t1, Track t2) {
            Double trackPrice1 = t1.getTrackPrice();
            Double trackPrice2 = t2.getTrackPrice();

            //ascending order
            return trackPrice1.compareTo(trackPrice2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};

    /*Comparator for sorting the list by roll no*/
    public static Comparator<Track> trackDateComparator = new Comparator<Track>() {

        public int compare(Track t1, Track t2) {

            Date releaseDate1 = t1.getReleaseDate();
            Date releaseDate2 = t2.getReleaseDate();

            /*For ascending order*/
            return releaseDate1.compareTo(releaseDate2);

            /*For descending order*/
            //rollno2-rollno1;
        }};
}
