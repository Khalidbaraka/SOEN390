package de.danoeh.antennapod.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
//Uncomment for later use
//import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import de.danoeh.antennapod.R;

public class FeelingLuckyActivity extends Activity {

    public static final String TAG = "FeelingLuckyActivity";

    private ImageView podcastImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeling_lucky);
        getRandomPodcast();
    }

    //method to get a random podcast
    private void getRandomPodcast(){

        String apiKey = "3DyA6A9QQrmshyviEGiAHOvMEaOlp1JwxHgjsnta7E9mAXcq8h";
        String randomPodcastURL = "https://listennotes.p.rapidapi.com/api/v1/just_listen";

        podcastImage = findViewById(R.id.randomPodcastImage);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(randomPodcastURL).addHeader("X-RapidAPI-Key",apiKey).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                Log.v(TAG, response.body().string());
            }
        });
    }


}
