package com.example.pleasework;

import java.util.ArrayList;

public class Admin extends Person implements SongEdit {

    public Admin() {
    }

    public Admin(String name, String username, String password, int age) {
        super(name, username, password, age);
    }

    public Admin(Person x) {
        this.name = x.name;
        this.username = x.username;
        this.password = x.password;
        this.age = x.age;
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
        return super.toString();
    }
}