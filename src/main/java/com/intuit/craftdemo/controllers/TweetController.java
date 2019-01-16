package com.intuit.craftdemo.controllers;

import com.intuit.craftdemo.handler.ApiResponse;
import com.intuit.craftdemo.model.Tweet;
import com.intuit.craftdemo.service.ITweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * @author Akshay Sathe
 * @version 1.0
 * Controller class for handling all requests related to a tweet
 */
@RestController
@RequestMapping("/api.intuit.com/1.0")
public class TweetController {

    private final Logger log = LoggerFactory.getLogger(TweetController.class);

    @Autowired
    ITweetService tweetService;

    /**
     * Method mapped for handling posting of a tweet
     *
     * @param tweet : Tweet object to be posted
     * @return :Returns a tweet as JSON object in Response entity
     */
    @RequestMapping(value = "/tweet", method = RequestMethod.POST)
    public ResponseEntity<?> postTweet(@Valid @RequestBody Tweet tweet) {
        log.debug("Request to create a tweet : {}", tweet);
        Tweet newTweet = tweetService.createTweet(tweet);
        if (newTweet == null) {
            ApiResponse apiResponse = new ApiResponse(false, "Cannot post tweet. User not logged in.", HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
        } else
            return new ResponseEntity<>(newTweet, HttpStatus.OK);
    }


    /**
     * Method mapped for returning top 100 tweets as feed
     *
     * @return : Returns a list of tweets as JSON objects in response entity
     */
    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Tweet>> getTweetFeed() {
        log.debug("Request to list top 100 tweets");
        ArrayList<Tweet> topTweets = tweetService.getTweetFeed();
        if (topTweets != null && !topTweets.isEmpty())
            return new ResponseEntity<>(topTweets, HttpStatus.OK);
        else
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }


    /**
     * Method mapped for updating a tweet based on tweet-id
     *
     * @param tweet   :Tweet object to be updated
     * @param tweetId :Id of tweet to be updated
     * @return : Returns updated tweet as JSON object in Response entity
     */
    @RequestMapping(value = "/tweet/{tweet_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTweet(@Valid @RequestBody Tweet tweet, @PathVariable("tweet_id") String tweetId) {

        Tweet updatedTweet = tweetService.updateTweet(tweet, tweetId);
        if (updatedTweet == null) {
            ApiResponse apiResponse = new ApiResponse(false, "Failed to update tweet content", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(tweet, HttpStatus.OK);
    }


    /**
     * Method mapped for deleting a tweet based on tweet-id
     *
     * @param tweetId: Id of tweet to be deleted
     * @return :Returns a string response as response entity
     */
    @RequestMapping(value = "/tweet/{tweet_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTweet(@PathVariable("tweet_id") String tweetId) {
        String message = tweetService.deleteTweet(tweetId);
        if (message == null) {
            ApiResponse apiResponse = new ApiResponse(false, "Failed to delete tweet content", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Tweet deleted", HttpStatus.OK);
    }


    /**
     * Method mapped for fetching all tweets for a specific user
     *
     * @param userName :Username whose tweets are fetched
     * @return: Returns a list of tweets as JSON objects in response entity
     */
    @RequestMapping(value = "/tweets/{user_name}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTweets(@PathVariable("user_name") String userName) {
        ArrayList<Tweet> tweets = tweetService.getAllTweetsForUser(userName);
        if (tweets == null) {
            ApiResponse apiResponse = new ApiResponse(false, "No tweets by user: " + userName, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }


    /**
     * Method mapped for fetching a tweet on tweet-id
     * @param tweetId: Id of tweet to be fetched
     * @return: Returns tweet as JSON object in Response entity
     */
    @RequestMapping(value = "/tweet/{tweet_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTweet(@PathVariable("tweet_id") String tweetId) {
        Tweet tweet = tweetService.getSingleTweet(tweetId);
        if (tweet == null) {
            ApiResponse apiResponse = new ApiResponse(false, "Tweet not found:", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tweet, HttpStatus.OK);
    }


    /**
     * Method mapped for searching tweets on keyword
     * @param keyword : Keyword to search
     * @return: Returns a list of tweets as JSON objects in response entity
     */
    @RequestMapping(value = "/tweet/search/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<?> searchTweets(@PathVariable("keyword") String keyword) {
        ArrayList<Tweet> tweets = tweetService.searchTweets(keyword);
        if (tweets == null) {
            ApiResponse apiResponse = new ApiResponse(false, "No tweets found matching with keyword: " + keyword, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }


}
