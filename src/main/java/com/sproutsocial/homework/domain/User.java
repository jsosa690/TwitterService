package com.sproutsocial.homework.domain;


public class User {

    private String id;
    private String twitter_id;
    private String oauth_token;
    private String oauth_secret;

    public User(String id, String twitter_id, String oauth_token, String oauth_secret) {
        this.id = id;
        this.twitter_id = twitter_id;
        this.oauth_token = oauth_token;
        this.oauth_secret = oauth_secret;
    }

    public String getId() {
        return id;
    }

    public String getTwitterId() {
        return twitter_id;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public String getOauth_secret() {
        return oauth_secret;
    }
}
