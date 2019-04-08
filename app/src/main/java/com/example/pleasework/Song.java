package com.example.pleasework;

import android.graphics.Bitmap;
import android.net.Uri;

public class Song {
    private String name;
    private String genre;
    private String location;
    private String artist;
    private Uri albumArtUri;
    private Bitmap bitmap;
    private String albumArtLocation;

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    private float duration;

    //Constructors
    public Song() {
        this.name = "Not Scanned";
        this.location = null;
        this.genre = "Not Scanned";
    }

    public Song(String _name, String _loc, String _genre) {
        this.name = _name;
        this.location = _loc;
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

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }

    public void setAlbumArtUri(Uri albumArt) {
        this.albumArtUri = albumArt;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getAlbumArtLocation() {
        return albumArtLocation;
    }

    public void setAlbumArtLocation(String albumArtLocation) {
        this.albumArtLocation = albumArtLocation;
    }
}