package de.danoeh.antennapod.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.danoeh.antennapod.adapter.SimilarPodcastAdapter;
import de.danoeh.antennapod.model.Podcast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




import de.danoeh.antennapod.R;

public class FindSimilarFragment extends android.support.v4.app.Fragment {

    public static final String TAG = "FindSimilarFragment";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageURLs = new ArrayList<>();

    private  String podcastURL = null;
    private  String podcastID = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle randomPodcastData = this.getArguments();
        if (randomPodcastData != null && randomPodcastData.containsKey("similar_podcast")) {
            podcastURL = randomPodcastData.getString("similar_podcast",null);
        }
        getPodcastID(podcastURL);

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

    private void getPodcastID(String podcastURL){

        String apiKey = "3DyA6A9QQrmshyviEGiAHOvMEaOlp1JwxHgjsnta7E9mAXcq8h";
        String podURL = podcastURL;
        String similarPodcastURL = "https://listennotes.p.rapidapi.com/api/v1/podcasts";

        OkHttpClient client = new OkHttpClient();

       // Request request = new Request.Builder().url(similarPodcastURL).addHeader("X-RapidAPI-Key",apiKey).build();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("rsses", podcastURL)
                .build();

        Request request = new Request.Builder()
                .url(similarPodcastURL)
                .addHeader("X-RapidAPI-Key",apiKey)
                .post(requestBody)
                .build();

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
                    if (response.isSuccessful()) {
                        JSONObject podcastData = new JSONObject(jsonData);
                        //JSONArray jsonArray = podcastData.getJSONArray("podcasts");
                        String podcastInfo = podcastData.getJSONArray("podcasts").getString(0);
                        JSONObject podcastData2 = new JSONObject(podcastInfo);
                        podcastID = podcastData2.getString("id");
                        getSimilarPodcasts(podcastID);
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

    private void getSimilarPodcasts(String podcastID){
        mImageURLs.add("https://d3sv2eduhewoas.cloudfront.net/channel/image/b7c71eae106646e8b1310e53bb2730c8.jpeg");
        mNames.add("testing");

        mImageURLs.add("https://d3sv2eduhewoas.cloudfront.net/channel/image/b7c71eae106646e8b1310e53bb2730c8.jpeg");
        mNames.add("testing");
    }

    public Podcast getPodcastDetails(String jsonData) throws JSONException {
        JSONObject podcastData = new JSONObject(jsonData);

        Podcast randomPodcast = new Podcast();

        randomPodcast.setPodcastImage(podcastData.getString("image"));
        randomPodcast.setPodcastTitle(podcastData.getString("podcast_title"));
        randomPodcast.setPodcastPublisher(podcastData.getString("publisher"));

        return randomPodcast;
    }

    private void alertUser() {
        Toast.makeText(getActivity(),"Error!",Toast.LENGTH_LONG).show();
    }
}
