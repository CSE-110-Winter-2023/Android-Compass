package edu.ucsd.cse110.projects110;

import java.util.UUID;

public class User {
    private String UID;
    private String Name;
    private double userLong;
    private double userLat;

    public User(String name,double Long,double lat) {
        this.UID= UUID.randomUUID().toString();
        this.Name = name;
        this.userLat=lat;
        this.userLong=Long;
    }

    public double getUserLong() {
        return userLong;
    }

    public String getUID() {
        return UID;
    }

    public String getName() {
        return Name;
    }

    public double getUserLat() {
        return userLat;
    }

    public void setUserLong(double userLong) {
        this.userLong = userLong;
    }



    public void setUserLat(double userLat) {
        this.userLat = userLat;
    }

    @Override
    public String toString() {
        return "User{" +
                "UID='" + UID + '\'' +
                ", Name='" + Name + '\'' +
                ", userLong=" + userLong +
                ", userLat=" + userLat +
                '}';
    }
}
