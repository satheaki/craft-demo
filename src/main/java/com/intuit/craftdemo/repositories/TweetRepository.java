package com.intuit.craftdemo.repositories;

import com.intuit.craftdemo.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {

    ArrayList<Tweet> findByCreatedByUser(String userName);
    Tweet findOneById(String id);
    ArrayList<Tweet> findTweetsByMessageContaining(String keyword);
    ArrayList<Tweet> findFirst100ByCreatedByUserInOrderByTimestampDesc(ArrayList<String> following);
}
