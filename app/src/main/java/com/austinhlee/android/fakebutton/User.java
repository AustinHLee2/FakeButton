package com.austinhlee.android.fakebutton;

/**
 * Created by Austin Lee on 3/5/2018.
 */

public class User {

    String candidate;
    int id;
    String email;
    String name;

    public User(String name, String email, String candidate) {
        this.candidate = candidate;
        this.email = email;
        this.name = name;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
