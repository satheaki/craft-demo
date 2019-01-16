package com.intuit.craftdemo.service;

import com.intuit.craftdemo.model.Tweet;
import com.intuit.craftdemo.repositories.TweetRepository;
import com.intuit.craftdemo.security.jwt.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TweetServiceImplementation implements ITweetService {

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    IUserService userService;

    @Autowired
    JwtTokenManager jwtTokenManager;


    /**
     * Method for creating a new tweet
     *
     * @param tweet : JSON object having tweet data
     * @return :Returns new tweet object
     */
    @Override
    public Tweet createTweet(Tweet tweet) {
        String userName = jwtTokenManager.getUserNameFromContext();
        if (userName != null) {
            Date currentTimeStamp = new Date();
            tweet.setTimestamp(currentTimeStamp.toString());
            tweet.setCreatedByUser(userName);
            tweetRepository.save(tweet);
            return tweet;
        } else
            return null;
    }


    /**
     * Method for fetching top 100 tweets as feed
     *
     * @return : Returns a list of top 100 tweets
     */
    @Override
    public ArrayList<Tweet> getTweetFeed() {
        String userName = jwtTokenManager.getUserNameFromContext();
        ArrayList<String> following = userService.getFollowing(userName);
        if (following == null) {
            following = new ArrayList<>();
            /*If the user has not sent any tweets to his followers,then users own tweets will be displayed*/
            following.add(userName);
        }
        ArrayList<Tweet> topTweets = tweetRepository.findFirst100ByCreatedByUserInOrderByTimestampDesc(following);
        return topTweets;

    }


    /**
     * Method for updating a existing tweet
     *
     * @param newTweet: JSON object having tweet data
     * @param tweetId   : Id of tweet to be updated
     * @return: Returns updated tweet object
     */
    @Override
    public Tweet updateTweet(Tweet newTweet, String tweetId) {
        Tweet tweet = tweetRepository.findOneById(tweetId);
        if (tweet == null)
            return null;
        else {
            tweet.setMessage(newTweet.getMessage());
            tweet.setTimestamp(new Date().toString());
            tweetRepository.save(tweet);
        }
        return tweet;

    }


    /**
     * Method for deleting a existing tweet
     *
     * @param tweetId : Id of tweet to be deleted
     * @return: Returns a string response if successful, otherwise null
     */
    @Override
    public String deleteTweet(String tweetId) {
        Tweet tweet = tweetRepository.findOneById(tweetId);
        if (tweet == null)
            return null;
        else {
            tweetRepository.delete(tweet);
            return "Tweet deleted";
        }
    }


    /**
     * Method for fetching all tweets of a user
     *
     * @param userName: username of user
     * @return: Returns a list of tweets for a specified user
     */
    @Override
    public ArrayList<Tweet> getAllTweetsForUser(String userName) {
        ArrayList<Tweet> userTweets = tweetRepository.findByCreatedByUser(userName);
        if (userTweets == null)
            return null;
        else
            return userTweets;
    }


    /**
     * Method for fetching single tweet based on tweet id
     *
     * @param tweetId :Id of tweet to be fetched
     * @return: Returns a tweet object
     */
    @Override
    public Tweet getSingleTweet(String tweetId) {
        Tweet tweet = tweetRepository.findOneById(tweetId);
        if (tweet == null)
            return null;
        else
            return tweet;
    }


    /**
     * Method for searching all tweets based on keyword
     *
     * @param keyword: keyword to be searched
     * @return: Returns a list of tweets containing the keyword
     */
    @Override
    public ArrayList<Tweet> searchTweets(String keyword) {
        ArrayList<Tweet> tweets = tweetRepository.findTweetsByMessageContaining(keyword);
        if (tweets == null)
            return null;
        else return tweets;
    }


}
