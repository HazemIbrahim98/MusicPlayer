package com.example.pleasework;

import java.util.ArrayList;

public interface SongEdit {

    void deleteSong(Song s, ArrayList<Song> songs);

    void uploadSong(Song s, ArrayList<Song> songs);
}