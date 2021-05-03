package com.sproutsocial.homework.mapper;

import com.sproutsocial.homework.domain.Tweet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TweetMapper {

    private static final Logger LOG = LoggerFactory.getLogger(TweetMapper.class);

    private static final SimpleDateFormat SDF = new SimpleDateFormat("EEE MMM dd HH:MM:ss Z yyyy");

    private String json;

    public TweetMapper(String json) {
        this.json = json;
    }

    /**
     * Maps the response from TwitterClient to a Tweet
     * @return a tweet as a receipt of the tweet posted by a user
     */
    public Tweet parseTweet() throws Exception {
        final JSONObject jsonObject = (JSONObject) new JSONTokener(this.json).nextValue();
        return new Tweet(jsonObject.getString("id"), jsonObject.getString("text"));
    }

    /**
     * Maps the return from TwitterClient to a list of Tweets, representing the user's timeline
     * @return a list of Tweets
     */
    public List<Tweet> parseTimeline() throws Exception {
        List<Tweet> timelineList = new ArrayList<Tweet>();

        JSONArray timelineArray = (JSONArray) new JSONTokener(this.json).nextValue();

        for (int i = 0; i < timelineArray.length(); i++) {
            final JSONObject timelineObject = timelineArray.getJSONObject(i);
            final JSONObject user = (JSONObject) timelineObject.getJSONObject("user");
            timelineList.add(new Tweet(
                    timelineObject.getString("id"),
                    user.getString("screen_name"),
                    timelineObject.getString("text"),
                    parseDateStringToUnixTimestamp(timelineObject.getString("created_at")),
                    user.getString("profile_image_url")
            ));
        }

        return timelineList;
    }

    private long parseDateStringToUnixTimestamp(final String dateTimeString) throws ParseException {
        long timestamp;

        try {
            Date date = SDF.parse(dateTimeString);
            timestamp = date.getTime();
        } catch (ParseException e) {
            LOG.error("error parsing dateTimeString " + dateTimeString, e);
            timestamp = -1l;//FIXME Need a fallback mechanism (e.g. regex) to attempt parsing if ParseException thrown
        }

        return timestamp;
    }
}
