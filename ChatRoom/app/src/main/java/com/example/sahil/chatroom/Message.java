package com.example.sahil.chatroom;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    String text, firstName, LastName;
    Date date;
    String image;
    String messageID;

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", date=" + date +
                ", image='" + image + '\'' +
                ", messageID='" + messageID + '\'' +
                '}';
    }
}
