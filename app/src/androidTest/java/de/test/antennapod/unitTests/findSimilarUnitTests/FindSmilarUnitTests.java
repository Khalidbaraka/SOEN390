package de.test.antennapod.unitTests.findSimilarUnitTests;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.adapter.SimilarPodcastAdapter;
import de.danoeh.antennapod.fragment.FeelingLuckyFragment;
import de.danoeh.antennapod.fragment.FindSimilarFragment;
import de.danoeh.antennapod.model.Podcast;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RunWith(AndroidJUnit4.class)
public class FindSmilarUnitTests {

    private final OkHttpClient client = new OkHttpClient();

    public FindSimilarFragment f= new FindSimilarFragment();
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void getPodcastIDTest() throws Exception{

        boolean pass = run();
       assertEquals(true,pass);

    }


    public boolean run() throws Exception {
        String similarPodcastURL = "https://listennotes.p.rapidapi.com/api/v1/podcasts";
         String apiKey = "3DyA6A9QQrmshyviEGiAHOvMEaOlp1JwxHgjsnta7E9mAXcq8h";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("rsses",similarPodcastURL )
                .build();

        Request request = new Request.Builder()
                .url(similarPodcastURL)
                .addHeader("X-RapidAPI-Key",apiKey)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            {return true;}
        }else{ return false;}

    }











}
