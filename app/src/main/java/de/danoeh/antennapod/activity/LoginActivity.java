package de.danoeh.antennapod.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.Printer;


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset, btnResetPassword;
    private TextView textDontHaveAccount;
    private Printer printer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        printer = new Printer() {
            @Override
            public void print(int messageId) {
                Toast.makeText(getApplicationContext(), messageId, Toast.LENGTH_SHORT).show();
            }
        };


        if (auth.getCurrentUser() != null && auth.getCurrentUser().isEmailVerified()) {
//           auth.signOut();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.login_activity);

        inputEmail = (EditText) findViewById(R.id.input_email_login);
        inputPassword = (EditText) findViewById(R.id.input_password_login);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textDontHaveAccount = (TextView) findViewById(R.id.dontHaveAccount);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        textDontHaveAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if(!checkFieldsValidation(email,password,printer)){
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (auth.getCurrentUser().isEmailVerified()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(LoginActivity.this, R.string.login_sucess ,Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, R.string.login_unverified, Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, RegisterAndLoginActivity.class));
        finish();
    }

    public boolean checkFieldsValidation(String email, String password, Printer printer){
        if (email == null || email.length() == 0) {
            printer.print(R.string.require_email);
            return false;
        }

        if(!email.contains("@")){
            printer.print(R.string.email_bad_format);
            return false;
        }

        if (password == null || password.length() == 0) {
            printer.print(R.string.require_password);
            return false;
        }

        return true;
    }
    //from https://stackoverflow.com/questions/15686555/display-back-button-on-action-bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, RegisterAndLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
