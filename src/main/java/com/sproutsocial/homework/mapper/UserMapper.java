package com.sproutsocial.homework.mapper;

import com.sproutsocial.homework.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    private ResultSet resultSet;

    public UserMapper(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * Maps the return from sqlite db to a User class
     * @return a User based off the resultSet from the query made to find a user
     */
    public User map() throws SQLException {
        return new User(resultSet.getString("id"), resultSet.getString("twitter_id"),
                resultSet.getString("oauth_token"), resultSet.getString("oauth_secret"));
    }
}
