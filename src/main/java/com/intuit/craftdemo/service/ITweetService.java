package com.intuit.craftdemo.service;

import com.intuit.craftdemo.model.Tweet;

import java.util.ArrayList;

public interface ITweetService {
    Tweet createTweet(Tweet tweet);

    Tweet updateTweet(Tweet tweet, String tweetId);

    String deleteTweet(String tweetId);

    ArrayList<Tweet> getAllTweetsForUser(String userName);

    Tweet getSingleTweet(String tweetId);

    ArrayList<Tweet> searchTweets(String keyword);

    ArrayList<Tweet> getTweetFeed();


}
