package com.example.pleasework;

public abstract class Person {

    String name, username, password;
    int age;

    public Person() {
    }

    public Person(String name, String username, String password, int age) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public Person(Person x) {
        this.name = x.name;
        this.username = x.username;
        this.password = x.password;
        this.age = x.age;
    }

    public String toString() {
        return name + username + password + age;
    }
}