package de.danoeh.antennapod.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterAndLoginActivity.this, MainActivity.class));
        finish();
    }

    //from https://stackoverflow.com/questions/15686555/display-back-button-on-action-bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
