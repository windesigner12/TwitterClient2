package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class Tweet_details extends AppCompatActivity {


    TextView tvBody;
    TextView tvScreenName;
    TextView tvName;
    ImageView ivProfileImage;
    ImageView tvUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);


        tvBody = findViewById(R.id.tvBody);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvName = findViewById(R.id.tvName);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvUrl = findViewById(R.id.tvUrl);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvBody.setText(tweet.body);
        tvScreenName.setText(tweet.user.screenName);
        tvName.setText("@"+tweet.user.name);
        Glide.with(this).load(tweet.user.profileImageUrl) .transform(new CenterCrop(),new RoundedCorners(100)).into(ivProfileImage);
        Glide.with(this).load(tweet.user.ivUrl1) .transform(new CenterCrop(),new RoundedCorners(25)).into(tvUrl);



    }
}