package de.danoeh.antennapod.fragment;


import android.os.Bundle;
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

import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.model.Podcast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;




import de.danoeh.antennapod.R;

public class FeelingLuckyFragment extends android.support.v4.app.Fragment {

    public static final String TAG = "FeelingLuckyFragment";
    private Podcast randomPodcast;

    public static String podcast_title;
    public static String podcast_publisher;
    public static String podcast_description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.feeling_lucky, null);
        Button reroll =  mView.findViewById(R.id.reroll_button);
        reroll.setOnClickListener(view -> getRandomPodcast());
        Button add =  mView.findViewById(R.id.add_button);
        add.setOnClickListener(view -> searchItunes());

        return mView;

    }

    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        getRandomPodcast();
    }

    private void searchItunes(){

        //passing the randomly generated podcasts name to itunes search
        ItunesSearchFragment fragment = new ItunesSearchFragment();
        Bundle args = new Bundle();
        args.putString("random_podcast", randomPodcast.getPodcastTitle());
        fragment.setArguments(args);

        final MainActivity activity = (MainActivity) getActivity();

        activity.loadChildFragment(fragment);

    }

    //method to get a random podcast
    private void getRandomPodcast(){

        ImageView podcastImage = getView().findViewById(R.id.randomPodcastImage);
        TextView podcastTitle =  getView().findViewById(R.id.random_podcast_title);
        TextView podcastAuthor =  getView().findViewById(R.id.random_podcast_author);
        TextView podcastDescription =  getView().findViewById(R.id.random_podcast_description);
        podcastDescription.setMovementMethod(new ScrollingMovementMethod());

        String apiKey = "3DyA6A9QQrmshyviEGiAHOvMEaOlp1JwxHgjsnta7E9mAXcq8h";
        String randomPodcastURL = "https://listennotes.p.rapidapi.com/api/v1/just_listen";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(randomPodcastURL).addHeader("X-RapidAPI-Key",apiKey).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                alertUser();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String jsonData = response.body().string();
                    if(response.isSuccessful()){
                        randomPodcast = getPodcastDetails(jsonData);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(randomPodcast.getPodcastImage()).into(podcastImage);
                                podcastTitle.setText(randomPodcast.getPodcastTitle());
                                FeelingLuckyFragment.podcast_title = randomPodcast.getPodcastTitle();
                                podcastAuthor.setText(randomPodcast.getPodcastPublisher());
                                FeelingLuckyFragment.podcast_publisher = randomPodcast.getPodcastPublisher();
                                podcastDescription.setText(randomPodcast.getPodcastDescription());
                                FeelingLuckyFragment.podcast_description = randomPodcast.getPodcastDescription();
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

    public Podcast getPodcastDetails(String jsonData) throws JSONException {
        JSONObject podcastData = new JSONObject(jsonData);

        Podcast randomPodcast = new Podcast();

        randomPodcast.setPodcastImage(podcastData.getString("image"));
        randomPodcast.setPodcastTitle(podcastData.getString("podcast_title"));
        randomPodcast.setPodcastDescription(stripHtml(podcastData.getString("description")));
        randomPodcast.setPodcastPublisher(podcastData.getString("publisher"));

        return randomPodcast;
    }

    private void alertUser() {
        Toast.makeText(getActivity(),"Error!",Toast.LENGTH_LONG).show();
    }

    //method to remove html tags from description strings
    public String stripHtml(String html) {

        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
    }
}
