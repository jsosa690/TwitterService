package com.sproutsocial.homework.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Tweet {

    private String id;

    @NotEmpty(message = "Tweet text cannot be empty")
    @Size(max = 150, message = "Tweet must me less than 150 characters")
    private String text;

    public Tweet() {
        super();
    }

    public Tweet(@JsonProperty("text") final String text) {
        super();
        this.text = text;
    }

    public Tweet(final String id, final String text) {
        super();
        this.id = id;
        this.text = text;
    }

    public Tweet(final String id, final String screenName, final String text, final long timestamp, final String profileImageUrl) {
        super();
        this.id = id;
        this.screenName = screenName;
        this.text = text;
        this.timestamp = timestamp;
        this.profileImageUrl = profileImageUrl;
    }

    @Override
    public String toString() {
        return "Tweet [text=" + text + "]";
    }

    private String screenName;

    private long timestamp;

    private String profileImageUrl;

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public String getScreenName() {
        return screenName;
    }

    @JsonProperty
    public void setScreenName(final String screenName) {
        this.screenName = screenName;
    }

    @JsonProperty
    public String getText() {
        return text;
    }

    @JsonProperty
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty
    public long getTimestamp() {
        return timestamp;
    }

    @JsonProperty
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @JsonProperty
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
