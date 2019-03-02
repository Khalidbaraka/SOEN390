package de.danoeh.antennapod.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ViewFlipper;

import de.danoeh.antennapod.R;

public class CategoriesActivity extends Activity {

    public static final String TAG = "CategoriesActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

    }


}
