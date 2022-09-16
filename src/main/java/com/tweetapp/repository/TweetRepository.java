package com.tweetapp.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.model.Comment;
import com.tweetapp.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TweetRepository {

    @Autowired
    private DynamoDBMapper mapper;

    public Tweet add(Tweet tweet) {
        mapper.save(tweet);
        return tweet;
    }

    public Tweet getTweetByTweetID(String tweetID, String username) {
        try {
            return mapper.load(Tweet.class, tweetID, username);
        } catch (Exception e) {
            throw new TweetNotFoundException("Tweet with tweetID = " + tweetID + " not found!!");
        }
    }

    public List<Tweet> getTweetByUsername(String username) {
        try {
            Tweet tweet = new Tweet();
            tweet.setUsername(username);
            DynamoDBQueryExpression<Tweet> queryExpression = new DynamoDBQueryExpression<Tweet>().withHashKeyValues(tweet).withIndexName("username-index").withConsistentRead(false);
            return mapper.query(Tweet.class, queryExpression);
        } catch (Exception e) {
            throw new UserNotFoundException("User with username = " + username + " not found!!");
        }
    }

    public Boolean deleteTweetByTweetID(String tweetID, String username) {
        try {
            Tweet tweet = mapper.load(Tweet.class, tweetID, username);
            mapper.delete(tweet);
            return true;
        } catch (Exception e) {
            throw new TweetNotFoundException("Tweet with tweetID = " + tweetID + " not found!!");
        }
    }

    public Boolean likeTweetByTweetID(String tweetID, String username) {
        try {
            Tweet tweet = mapper.load(Tweet.class, tweetID, username);
            tweet.setLikeCount(tweet.getLikeCount() + 1);
            mapper.save(tweet, new DynamoDBSaveExpression().withExpectedEntry("tweetID", new ExpectedAttributeValue(new AttributeValue().withS(tweetID))));
            return true;
        } catch (Exception e) {
            throw new TweetNotFoundException("Tweet with tweetID = " + tweetID + " not found!!");
        }
    }

    public Boolean updateTweet(Tweet tweet, String tweetID, String username) {
        try {
            Tweet fetchedTweet = mapper.load(Tweet.class, tweetID, username);
            fetchedTweet.setMessage(tweet.getMessage());
            mapper.save(fetchedTweet, new DynamoDBSaveExpression().withExpectedEntry("tweetID", new ExpectedAttributeValue(new AttributeValue().withS(tweetID))));
            return true;
        } catch (Exception e) {
            throw new TweetNotFoundException("Tweet with tweetID = " + tweetID + " not found!!");
        }
    }

    public Tweet reply(Comment comment, String tweetID, String username) {
        try {
            Tweet fetchedTweet = mapper.load(Tweet.class, tweetID, username);
            fetchedTweet.getComments().add(comment);
            mapper.save(fetchedTweet, new DynamoDBSaveExpression().withExpectedEntry("tweetID", new ExpectedAttributeValue(new AttributeValue().withS(tweetID))));
            return fetchedTweet;
        } catch (Exception e) {
            throw new TweetNotFoundException("Tweet with tweetID = " + tweetID + " not found!!");
        }
    }

    private DynamoDBSaveExpression buildSaveExpression(Tweet tweet) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("tweetID", new ExpectedAttributeValue(new AttributeValue().withS(tweet.getTweetID())));
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }
}
