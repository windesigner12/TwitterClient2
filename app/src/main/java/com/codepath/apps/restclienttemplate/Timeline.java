package com.codepath.apps.restclienttemplate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.SampleModel;
import com.codepath.apps.restclienttemplate.models.SampleModelDao;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetDao;
import com.codepath.apps.restclienttemplate.models.TweetWithUser;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class Timeline extends AppCompatActivity {

    RestClient client;
    TweetDao tweetDao;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetAdapter adapter;
    SwipeRefreshLayout swipeContainer;
    EndlessRecyclerViewScrollListener scrollListener;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //SampleModelDao sampleModelDao = ((RestApplication) getApplicationContext()).getMyDatabase().sampleModelDao();



//
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//               // List<SampleModel> listItems = sampleModelDao.recentItems();
//                Log.d("DB",listItems.toString());
//            }
//        });

        client = RestApplication.getRestClient(this);
        tweetDao = ((RestApplication) getApplicationContext()).getMyDatabase().TweetDao();


        rvTweets = findViewById(R.id.rvTweets);
        swipeContainer = findViewById(R.id.swipeContainer);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("TimelineActivity", "fetching new data" );
                populateHomeTimeline();
            }
        });

        tweets = new ArrayList<>();
        adapter = new TweetAdapter(this, tweets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.setAdapter(adapter);

        rvTweets.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i("TimelineActivity", "Load More Data");
                loadMoreData();
            }

        };

        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("TimelineActivity", "Showing data from databse");
                List<TweetWithUser> tweetWithUsers = tweetDao.recentItems();
                List<Tweet> tweetsFromDB = TweetWithUser.getTweetList(tweetWithUsers);

            adapter.clear();
            adapter.addAll(tweetsFromDB);
            }
        });


        populateHomeTimeline();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.compose){

            Intent intent = new Intent(this, ComposeActivity.class);

            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }
    return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK ){
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweets.add(0 , tweet);
            adapter.notifyItemInserted(0);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadMoreData(){
        client.getNextPageOfTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i("TimelineActivity", "On Success for Load More Data 2" );

                JSONArray jsonArray =json.jsonArray;
                try {
                   List<Tweet> tweets = Tweet.fromJsonArray(jsonArray);
                   adapter.addAll(tweets);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("TimelineActivity", "On Failure for Load More Data" + throwable);

            }
        }, tweets.get(tweets.size() -1).id );

    }

    public void populateHomeTimeline(){

        client.getTimelineTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("LoadOfData","OnSuccess"+ json.toString() );

                JSONArray jsonArray = json.jsonArray;

                try {

                  List<Tweet> tweetsFromNetwork =  Tweet.fromJsonArray(jsonArray);
                    adapter.clear();
                    adapter.addAll(Tweet.fromJsonArray(jsonArray));
                    swipeContainer.setRefreshing(false);


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//                List<User> usersFromNetwork = User.fromJsonTweetArray(tweetsFromNetwork);
//
//         tweetDao.insertModel(usersFromNetwork.toArray(new User[0]));
//               tweetDao.insertModel(tweetsFromNetwork.toArray(new Tweet[0]));
            }
        });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("OK","OnFaillure"+ response.toString());
            }
        });
    }
}