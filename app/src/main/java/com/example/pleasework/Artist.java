package com.example.pleasework;

import java.util.ArrayList;

public class Artist extends Person implements SongEdit {
    ArrayList<Song> songs;
    String genre;
    ArrayList<User> followers;


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