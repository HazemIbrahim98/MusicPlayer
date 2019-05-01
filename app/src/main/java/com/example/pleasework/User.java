package com.example.pleasework;

import java.util.ArrayList;

public class User extends Person {
    ArrayList<Artist> follows;
    ArrayList<Song> likes;

    public String toString() {
        return super.toString() + follows + likes;
    }
}