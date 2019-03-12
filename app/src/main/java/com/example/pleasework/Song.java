package com.example.pleasework;

public class Song {
    private String name;
    private String genre;
    private String loc;
    private int duration;
    private String artist;

    //Constructors
    public Song() {
        name = "Wanabe";
        loc = null;
        genre = "Jazz";
    }

    public Song(String _name, String _loc, String _genre, int _sec, int _min, int _hr) {
        this.name = _name;
        this.loc = _loc;
        this.genre = _genre;
    }

    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String location) {
        this.loc = location;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}