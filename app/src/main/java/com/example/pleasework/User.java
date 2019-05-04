package com.example.pleasework;

import java.util.ArrayList;

public class User extends Person {
    ArrayList<Artist> follows;
    ArrayList<Song> likes;

    public User() {
    }

    public User(String name, String username, String password, int age, ArrayList<Artist> follows, ArrayList<Song> likes) {
        super(name, username, password, age);
        this.follows = follows;
        this.likes = likes;
    }

    public User(User x) {
        this.name = x.name;
        this.username = x.username;
        this.password = x.password;
        this.age = x.age;
        this.follows = x.follows;
        this.likes = x.likes;
    }

    public String toString() {
        return super.toString() + follows + likes;
    }
}