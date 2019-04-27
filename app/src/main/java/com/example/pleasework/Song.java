package com.example.pleasework;

public class Song {
    private String name;
    private String location;
    private String artist;
    //private Uri albumArtUri;
    //private Bitmap bitmap;
    //private String albumArtLocation;
    private long duration;


    //Constructors
    public Song() {
        this.name = "Not Scanned";
        this.location = null;
    }

    public Song(String _name, String _loc) {
        this.name = _name;
        this.location = _loc;
    }

    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}