package com.sproutsocial.homework;

import com.sproutsocial.homework.client.TwitterClient;
import com.sproutsocial.homework.resources.TwitterResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

public class HomeworkApplication extends Application<HomeworkConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HomeworkApplication().run(args);
    }

    @Override
    public String getName() {
        return "Homework";
    }

    @Override
    public void initialize(final Bootstrap<HomeworkConfiguration> bootstrap) {
    }

    @Override
    public void run(final HomeworkConfiguration configuration, final Environment environment) {
        final Jdbi jdbi = new JdbiFactory().build(environment, configuration.getDatabase(), "sqlite");
        final TwitterClient twitterClient = new TwitterClient(configuration.getTwitterConsumerKey(), configuration.getTwitterConsumerSecret());
        environment.jersey().register(new TwitterResource(jdbi, twitterClient));
    }

}
