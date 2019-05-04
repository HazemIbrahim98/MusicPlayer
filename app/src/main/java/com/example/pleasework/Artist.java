package com.example.pleasework;

import java.util.ArrayList;

public class Artist extends Person implements SongEdit {
    ArrayList<Song> songs;
    String genre;
    ArrayList<User> followers;


    public Artist() {
    }

    public Artist(String name, String username, String password, int age, ArrayList<Song> songs, String genre, ArrayList<User> followers) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.songs = songs;
        this.genre = genre;
        this.followers = followers;
    }

    public Artist(Artist x) {
        this.name = x.name;
        this.username = x.username;
        this.password = x.password;
        this.age = x.age;
        this.songs = x.songs;
        this.genre = x.genre;
        this.followers = x.followers;
    }


    @Override
    public void deleteSong(Song s, ArrayList<Song> songs) {
        for (Song tmp : songs)
            if (tmp == s)
                songs.remove(tmp);
    }

    @Override
    public void uploadSong(Song s, ArrayList<Song> songs) {
        songs.add(s);
    }

    public String toString() {
        return super.toString() + songs + genre + followers;
    }
}