package com.tweetapp.service;

import com.tweetapp.model.Comment;
import com.tweetapp.model.Tweet;
import com.tweetapp.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    public List<Tweet> getAllTweets() {
        return tweetRepository.getAllTweets();
    }

    public Tweet add(Tweet tweet) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        tweet.setCreationTime(dtf.format(now).toString());
        return tweetRepository.add(tweet);
    }

    public Tweet getTweetByTweetID(String tweetID, String username) {
        return tweetRepository.getTweetByTweetID(tweetID, username);
    }

    public Boolean delete(String tweetID, String username) {
        return tweetRepository.deleteTweetByTweetID(tweetID, username);
    }

    public Boolean likeTweet(String tweetID, String username) {
        return tweetRepository.likeTweetByTweetID(tweetID, username);
    }

    public Tweet reply(Comment comment, String tweetID, String username) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        comment.setCreationTime(dtf.format(now).toString());
        return tweetRepository.reply(comment, tweetID, username);
    }


    public Boolean update(Tweet tweet, String tweetID, String username) {
        return tweetRepository.updateTweet(tweet, tweetID, username);
    }

    public List<Tweet> getTweetByUsername(String username) {
        return tweetRepository.getTweetByUsername(username);
    }

}
