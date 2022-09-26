package com.tweetapp.controller;

import com.tweetapp.model.Comment;
import com.tweetapp.model.Tweet;
import com.tweetapp.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@CrossOrigin(origins = "*")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @PostMapping("/{username}/add")
    public Tweet saveTweet(@RequestBody Tweet tweet, @PathVariable String username) {
        return tweetService.add(tweet);
    }

    @GetMapping("/user/{username}")
    public List<Tweet> getTweetByUsername(@PathVariable String username) {
        return tweetService.getTweetByUsername(username);
    }

    @PutMapping("/{username}/update/{tweetID}")
    public Boolean updateTweet(@RequestBody Tweet tweet, @PathVariable String username, @PathVariable String tweetID) {
        return tweetService.update(tweet, tweetID, username);
    }

    @DeleteMapping("/{username}/delete/{tweetID}")
    public Boolean deleteTweet(@PathVariable String username, @PathVariable String tweetID) {
        return tweetService.delete(tweetID, username);
    }

    @PutMapping("/{username}/like/{tweetID}")
    public Boolean likeTweet(@PathVariable String username, @PathVariable String tweetID) {
        return tweetService.likeTweet(tweetID, username);
    }

    @PostMapping("/{username}/reply/{tweetID}")
    public Tweet reply(@RequestBody Comment comment, @PathVariable String username, @PathVariable String tweetID) {
        return tweetService.reply(comment, tweetID, username);
    }

    @GetMapping("/{tweetID}/user/{username}")
    public Tweet getTweet(@PathVariable String tweetID, @PathVariable String username) {
        return tweetService.getTweetByTweetID(tweetID, username);
    }

}
