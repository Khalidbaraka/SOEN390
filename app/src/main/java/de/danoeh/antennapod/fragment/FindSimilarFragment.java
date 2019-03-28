package de.danoeh.antennapod.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.adapter.SimilarPodcastAdapter;
import de.danoeh.antennapod.model.RandomPodcast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;




import de.danoeh.antennapod.R;

public class FindSimilarFragment extends android.support.v4.app.Fragment {

    public static final String TAG = "FindSimilarFragment";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageURLs = new ArrayList<>();

    private RandomPodcast randomPodcast;

    public static String podcast_title;
    public static String podcast_publisher;
    public static String podcast_description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSimilarPodcasts();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_find_similar, null);

        RecyclerView myRecyclerView = mView.findViewById(R.id.similar_recyclerView);
        SimilarPodcastAdapter adapter = new SimilarPodcastAdapter(mImageURLs,mNames,getContext());

        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return mView;

    }

    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    private void getSimilarPodcasts(){
        mImageURLs.add("https://d3sv2eduhewoas.cloudfront.net/channel/image/b7c71eae106646e8b1310e53bb2730c8.jpeg");
        mNames.add("testing");

        mImageURLs.add("https://d3sv2eduhewoas.cloudfront.net/channel/image/b7c71eae106646e8b1310e53bb2730c8.jpeg");
        mNames.add("testing");
    }


}
