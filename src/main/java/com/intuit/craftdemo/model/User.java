package com.intuit.craftdemo.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class User {
    @Id
    private String id;
    private String userName;
    private ArrayList<String> following = new ArrayList<>();

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }
}
