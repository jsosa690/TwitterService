package com.sproutsocial.homework.client;

import com.sproutsocial.homework.domain.Tweet;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwitterClient {

    private static final String TIMELINE_BASE_URL = "https://api.twitter.com/1.1/statuses/home_timeline.json?";

    private static final String TWEET_BASE_URL = "https://api.twitter.com/1.1/statuses/update.json?";

    private String apiKey;
    private String apiSecret;

    public TwitterClient(final String apiKey, final String apiSecret) {
        super();
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public HttpResponse postTweet(String oAuthToken, String oAuthSecret, Tweet tweet) throws IOException, URISyntaxException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(buildTweetUrl(tweet.getText()));
        try {
            buildToken(oAuthToken, oAuthSecret).sign(httpPost);
        } catch (OAuthMessageSignerException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OAuthExpectationFailedException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OAuthCommunicationException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return httpclient.execute(httpPost);
    }

    public HttpResponse getTimeline(String oAuthToken, String oAuthSecret, String count) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(buildTimelineUrl(count));
        try {
            buildToken(oAuthToken, oAuthSecret).sign(httpget);
        } catch (OAuthMessageSignerException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OAuthExpectationFailedException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OAuthCommunicationException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return httpclient.execute(httpget);
    }

    private OAuthConsumer buildToken(String access_token, String access_secret) {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(apiKey,
                apiSecret);
        consumer.setTokenWithSecret(access_token, access_secret);
        return consumer;
    }

    private String buildTweetUrl(final String text) throws UnsupportedEncodingException, URISyntaxException {
        final StringBuilder url = new StringBuilder(TWEET_BASE_URL);
        url.append("status=");
        url.append(URLEncoder.encode(text, "UTF-8"));
        return url.toString();
    }

    private String buildTimelineUrl(String count) {
        final StringBuilder url = new StringBuilder(TIMELINE_BASE_URL);
        if (count != null && !"".equals(count.trim())) {
            url.append("count=");
            url.append(count);
            url.append("&");
        }
        return url.toString();
    }

}
