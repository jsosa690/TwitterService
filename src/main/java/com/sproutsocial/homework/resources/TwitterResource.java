package com.sproutsocial.homework.resources;

import com.sproutsocial.homework.client.TwitterClient;
import com.sproutsocial.homework.domain.Tweet;
import com.sproutsocial.homework.domain.User;
import com.sproutsocial.homework.mapper.TweetMapper;
import com.sproutsocial.homework.mapper.UserMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jdbi.v3.core.Jdbi;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Path("/user")
public class TwitterResource {

    private final Jdbi jdbi;
    private TwitterClient twitterClient;

    public TwitterResource(Jdbi jdbi, TwitterClient twitterClient) {
        this.jdbi = jdbi;
        this.twitterClient = twitterClient;
    }

    /**
     * Searches db for user credentials to pull up timeline
     *
     * @param id    String used to search repository for oauth credentials
     * @param count String used to determine number of tweets to pull for the user
     * @return the list of tweets pulled from the users timeline
     */
    @GET
    @Path("/timeline/{id}")
    public Response findTimeLine(@PathParam("id") String id, @QueryParam("count") String count) throws Exception {
        User user = findUser(id);
        if (user != null) {
            HttpResponse httpResponse = twitterClient.getTimeline(user.getOauth_token(), user.getOauth_secret(), count);
            System.out.println(httpResponse.getStatusLine().toString());
            List<Tweet> tweets = new TweetMapper(EntityUtils.toString(httpResponse.getEntity())).parseTimeline();
            return Response.status(Response.Status.OK).entity(tweets).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("No user with id " + id + " was found.").build();
        }
    }


    /**
     * Posts a tweet within 150 characters to the desired accounts timeline
     *
     * @param id    String used to search repository for oauth credentials
     * @param tweet Object holding the text desired to be published by the given id
     * @return a Tweet object to show the resulting post
     */
    @POST
    @Path("/tweet/{id}")
    public Response makeTweet(@PathParam("id") String id, @Valid Tweet tweet) throws Exception {
        User user = findUser(id);
        if (user != null) {
            HttpResponse httpResponse = twitterClient.postTweet(user.getOauth_token(), user.getOauth_secret(), tweet);
            System.out.println(httpResponse.getStatusLine().toString());
            if (httpResponse.getStatusLine().toString().contains("403")) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Status is a duplicate.").build();
            } else {
                Tweet tweetResult = new TweetMapper(EntityUtils.toString(httpResponse.getEntity())).parseTweet();
                return Response.status(Response.Status.OK).entity(tweetResult).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("No user with id " + id + " was found.").build();
        }

    }

    private User findUser(String id) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from twitter_accounts where id = :id")
                .bind("id", id)
                .map((rs, ctx) -> new UserMapper(rs).map())
                .stream().findFirst().orElse(null));
    }

}
