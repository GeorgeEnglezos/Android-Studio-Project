package com.example.sqlitedbcontentprovider;


public class User {
    String longitude;
    String latitude;
    String timestamp;

    public User(String userID, String longtitude, String latitude, String timestamp) {
        this.longitude = longtitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
