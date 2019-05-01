package com.example.pleasework;

public abstract class Person {

    String name, username, password;
    int age;

    public String toString() {
        return name + username + password + age;
    }
}