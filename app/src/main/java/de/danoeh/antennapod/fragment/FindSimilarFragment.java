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
import de.danoeh.antennapod.model.RandomPodcast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;




import de.danoeh.antennapod.R;

public class FindSimilarFragment extends android.support.v4.app.Fragment {

    public static final String TAG = "FeelingLuckyFragment";
    private RandomPodcast randomPodcast;

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

        View mView = inflater.inflate(R.layout.fragment_find_similar, null);


        return mView;

    }

    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }


}
