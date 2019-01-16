package com.intuit.craftdemo.service;

import com.intuit.craftdemo.model.User;

import java.util.ArrayList;

public interface IUserService {
     User follow(String followerUserName);
     User unfollow(String followerUserName);
     ArrayList<String> getFollowing(String userName);
     User checkUser(String userName);
     User registerUser(String userName);
}
