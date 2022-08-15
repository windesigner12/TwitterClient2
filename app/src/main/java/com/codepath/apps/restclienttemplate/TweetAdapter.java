package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Tweet tweet = tweets.get(position);

            holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvHours;
        TextView tvName;
        RelativeLayout container;
        ImageView tvUrl;

        //////Action Button
        TextView tvChat;
        TextView tvUndo;
        TextView tvRepeat;
        TextView tvHeart;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvHours = itemView.findViewById(R.id.tvhours);
            tvName = itemView.findViewById(R.id.tvName);
            container = itemView.findViewById(R.id.container);
            tvUrl = itemView.findViewById(R.id.tvUrl);
                ////Action BUtton

            tvUndo = itemView.findViewById(R.id.tvUndo);
            tvRepeat = itemView.findViewById(R.id.tvRepeat);
            tvHeart = itemView.findViewById(R.id.tvHeart);
            tvChat = itemView.findViewById(R.id.tvChat);


        }

        public void bind(Tweet tweet) {
            Glide.with(context).load(tweet.user.profileImageUrl) .transform(new CenterCrop(),new RoundedCorners(70)).into(ivProfileImage);
            tvScreenName.setText(tweet.user.screenName);
            tvName.setText("@"+tweet.user.name);
            tvBody.setText(tweet.body);
            tvHours.setText(TimeFormatter.getTimeDifference(tweet.createdAt));
            tvRepeat.setText(tweet.retweet_count);
            tvHeart.setText(tweet.favorite_count);
           // Glide.with(context).load(tweet.url).transform(new CenterCrop(),new RoundedCorners(25)).into(tvUrl);

//
//            try{
//                List<String> ms = tweet.;
//                if(!ms.isEmpty()){
//                    List<String> P = Arrays.asList(ms.get(0).split(" - "));
//                    if (P.get(1).equals("photo")){
//                        tvUrl.setVisibility(View.VISIBLE);
//                        Glide.with(context).load(P.get(0)).transform(new FitCenter(),new RoundedCorners(25)).into(tvUrl);
//
//                    }
//                }
//            }catch(Exception e){
//
//            }
//





            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, Tweet_details.class);
                    i.putExtra("tweet", Parcels.wrap(tweet));

                    Pair<View, String> trans1 = Pair.create(tvBody , "body");
                    Pair<View, String> trans2 = Pair.create(tvName , "name");
                    Pair<View, String> trans3 = Pair.create(tvScreenName , "screenname");

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, trans1,trans2,trans3);
                    context.startActivity(i, options.toBundle());

                }
            });


            tvUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Comment BUTTON");
                Toast.makeText(context , "You Press the Undo Button", Toast.LENGTH_SHORT).show();
            }
        });

        tvRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Press the Repeat Button", Toast.LENGTH_SHORT).show();
                Log.d("Button", "Repeat BUTTON");
            }
        });

        tvChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Press the Chat Button", Toast.LENGTH_SHORT).show();
                Log.d("Button", "Chat BUTTON");
            }
        });

        tvHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Press the Heart Button", Toast.LENGTH_SHORT).show();
                Log.d("Button", "Like BUTTON");
            }
        });



            }
    }


}
