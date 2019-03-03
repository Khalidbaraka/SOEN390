package de.danoeh.antennapod.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//Uncomment for later use
//import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.danoeh.antennapod.model.RandomPodcast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import de.danoeh.antennapod.R;

public class FeelingLuckyActivity extends Activity {

    public static final String TAG = "FeelingLuckyActivity";
    private RandomPodcast randomPodcast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeling_lucky);
        getRandomPodcast();
        Button reroll =  findViewById(R.id.reroll_button);
        reroll.setOnClickListener(view -> getRandomPodcast());
    }

    //method to get a random podcast
    private void getRandomPodcast(){

        ImageView podcastImage = findViewById(R.id.randomPodcastImage);
        TextView podcastTitle =  findViewById(R.id.random_podcast_title);
        TextView podcastAuthor =  findViewById(R.id.random_podcast_author);
        TextView podcastDescription =  findViewById(R.id.random_podcast_description);
        podcastDescription.setMovementMethod(new ScrollingMovementMethod());

        String apiKey = "3DyA6A9QQrmshyviEGiAHOvMEaOlp1JwxHgjsnta7E9mAXcq8h";
        String randomPodcastURL = "https://listennotes.p.rapidapi.com/api/v1/just_listen";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(randomPodcastURL).addHeader("X-RapidAPI-Key",apiKey).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //failure handling logic to be added
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    if(response.isSuccessful()){
                        randomPodcast = getPodcastDetails(jsonData);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(randomPodcast.getPodcastImage()).into(podcastImage);
                                podcastTitle.setText(randomPodcast.getPodcastTitle());
                                podcastAuthor.setText(randomPodcast.getPodcastPublisher());
                                podcastDescription.setText(randomPodcast.getPodcastDescription());
                            }
                        });
                    }
                    else{
                        alertUser();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "IO Exception caught: " , e);
                } catch (JSONException e){
                    Log.e(TAG, "JSON Exception caught: " , e);
                }
            }
        });
    }

    private RandomPodcast getPodcastDetails(String jsonData) throws JSONException {
        JSONObject podcastData = new JSONObject(jsonData);

        RandomPodcast randomPodcast = new RandomPodcast();

        randomPodcast.setPodcastImage(podcastData.getString("image"));
        randomPodcast.setPodcastTitle(podcastData.getString("podcast_title"));
        randomPodcast.setPodcastDescription(stripHtml(podcastData.getString("description")));
        randomPodcast.setPodcastPublisher(podcastData.getString("publisher"));

        return randomPodcast;
    }

    private void alertUser() {
        Toast.makeText(getApplicationContext(),"Error!",Toast.LENGTH_LONG).show();
    }

    //method to remove html tags from description strings
    public String stripHtml(String html) {

        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
    }
}
