package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id",childColumns = "userId"))
public class Tweet {


    @PrimaryKey
    @ColumnInfo
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @Ignore
    public User user;

    @ColumnInfo
    public long userId;


    @ColumnInfo
    public String retweet_count;

    @ColumnInfo
    public String favorite_count;
    //public String ivUrl;

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
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;

        try {
            JSONArray entities_media = jsonObject.getJSONObject("extended_entities").getJSONArray("media");

            for(int i = 0; i < entities_media.length(); i++ ){
                String P = "";
                P += entities_media.getJSONObject(i).getString("media_url_https");
                P += " - ";
                P += entities_media.getJSONObject(0).getString("type");
               // tweet.medias.add(P);

            }
        }catch(Exception e ){
            return tweet;
        }


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

