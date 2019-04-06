package de.danoeh.antennapod.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
    public ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mFeeds = new ArrayList<>();
    private ArrayList<String> mImageURLs = new ArrayList<>();
    private RecyclerView myRecyclerView;
    public SimilarPodcastAdapter adapter;

    public String podcastURL = null;
    public String podcastID = null;
    private String apiKey = "3DyA6A9QQrmshyviEGiAHOvMEaOlp1JwxHgjsnta7E9mAXcq8h";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_find_similar, null);

         myRecyclerView = mView.findViewById(R.id.similar_recyclerView);

        Bundle podcastData = this.getArguments();
        if (podcastData != null && podcastData.containsKey("similar_podcast")) {
            podcastURL = podcastData.getString("similar_podcast",null);
        }
        getPodcastID(podcastURL);

        return mView;

    }


    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public String getPodcastID(String podcastURL){

        String podURL = podcastURL;
        String similarPodcastURL = "https://listennotes.p.rapidapi.com/api/v1/podcasts";

        OkHttpClient client = new OkHttpClient();

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
                        String podcastInfo = podcastData.getJSONArray("podcasts").getString(0);
                        JSONObject podcastData2 = new JSONObject(podcastInfo);

                        podcastID = podcastData2.getString("id");

                        Log.d("podcastID is",podcastID );
                        Log.d("podcastID is",podcastID );

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
        return podcastID;
    }

    public void getSimilarPodcasts(String podcastID){

         ArrayList<String> names = new ArrayList<>();
         ArrayList<String> images = new ArrayList<>();
        ArrayList<String> feeds = new ArrayList<>();

        String similarPodcastURL = "https://listennotes.p.rapidapi.com/api/v1/podcasts/" +
                podcastID + "/recommendations?safe_mode=1";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(similarPodcastURL).addHeader("X-RapidAPI-Key",apiKey).build();

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
                        JSONArray jsonArray = podcastData.getJSONArray("recommendations");
                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("title");
                            String image = object.getString("image");
                            String feed = object.getString("rss");
                            names.add(name);
                            images.add(image);
                            feeds.add(feed);
                        }
                        setupRecycler(names,images,feeds);
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

    public void setupRecycler(ArrayList<String> mNames, ArrayList<String> mImageURLs,ArrayList<String> mFeeds) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressBar progressBar = getView().findViewById(R.id.progbarLoading);
                progressBar.setVisibility(View.GONE);
                adapter = new SimilarPodcastAdapter(mImageURLs,mNames,mFeeds,getContext());
                myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                myRecyclerView.setAdapter(adapter);
            }
        });
    }

    private void alertUser() {
        Toast.makeText(getActivity(),"Error!",Toast.LENGTH_LONG).show();
    }
}
