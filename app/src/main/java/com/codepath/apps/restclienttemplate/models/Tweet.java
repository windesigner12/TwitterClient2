package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public User user;
    public long id;
    public String retweet_count;
    public String favorite_count;
    //public String url;

    public Tweet(){

    }


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");
        tweet.retweet_count = jsonObject.getString("retweet_count");
        tweet.favorite_count = jsonObject.getString("favorite_count");
        //tweet.url = jsonObject.getJSONObject("extended_entities ").getString("media_url");


        //String youtubeKey = results.getJSONObject(0).getString("key");

        return tweet;
    }


    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {

        List<Tweet> tweets = new ArrayList<>();

        for(int i =0; i <jsonArray.length(); i++ ){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return tweets;
    }





}

