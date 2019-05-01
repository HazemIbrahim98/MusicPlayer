package com.example.pleasework;

import java.util.ArrayList;

public interface SongEdit {

    public void deleteSong(Song s, ArrayList<Song> songs);

    public void uploadSong(Song s, ArrayList<Song> songs);
}