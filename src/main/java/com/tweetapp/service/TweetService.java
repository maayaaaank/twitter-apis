package com.tweetapp.service;

import com.tweetapp.model.Comment;
import com.tweetapp.model.Tweet;

import java.util.List;

public interface TweetService {
    Tweet add(Tweet tweet);

    Tweet getTweetByTweetID(String tweetID, String username);

    Boolean delete(String tweetID, String username);

    Boolean update(Tweet tweet, String tweetID, String username);

    List<Tweet> getTweetByUsername(String username);

    Boolean likeTweet(String tweetID, String username);

    Tweet reply(Comment comment, String tweetID, String username);
}
