package com.tweetapp.service;

import com.tweetapp.model.Comment;
import com.tweetapp.model.Tweet;
import com.tweetapp.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    public Tweet add(Tweet tweet) {
        String date = ZonedDateTime
                .now(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss"));
        tweet.setCreationTime(date);
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
        String date = ZonedDateTime
                .now(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss"));
        comment.setCreationTime(date);
        return tweetRepository.reply(comment, tweetID, username);
    }


    public Boolean update(Tweet tweet, String tweetID, String username) {
        return tweetRepository.updateTweet(tweet, tweetID, username);
    }

    public List<Tweet> getTweetByUsername(String username) {
        return tweetRepository.getTweetByUsername(username);
    }

}
