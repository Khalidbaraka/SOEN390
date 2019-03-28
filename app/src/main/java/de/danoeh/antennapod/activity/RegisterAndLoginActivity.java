package de.danoeh.antennapod.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.danoeh.antennapod.R;


public class RegisterAndLoginActivity extends AppCompatActivity {

    private Button registerButton;
    private Button loginButton;
    private ImageView registerAndLoginAvatar;
    private TextView registerAndLoginFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_and_login_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        registerButton = findViewById(R.id.register_main_layout_button);
        loginButton = findViewById(R.id.login_main_layout_button);
        registerAndLoginAvatar = findViewById(R.id.register_and_login_main_layout_avatar);
        registerAndLoginFooter = findViewById(R.id.register_and_login_main_layout_footer);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterAndLoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

}
