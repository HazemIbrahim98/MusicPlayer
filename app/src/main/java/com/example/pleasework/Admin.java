package com.example.pleasework;

import java.util.ArrayList;

public class Admin extends Person implements SongEdit {

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