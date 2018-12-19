package com.cham.akkaspringboot;

import org.springframework.stereotype.Component;

@Component
public class TweetService {

    public String tweet(String message) {
        return "Hello, " + message;
    }
}
