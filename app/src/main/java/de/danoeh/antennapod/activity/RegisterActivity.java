package de.danoeh.antennapod.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.preferences.UserPreferences;
import de.danoeh.antennapod.model.User;
import de.danoeh.antennapod.model.Printer;


//from https://www.androidhive.info/2016/06/android-getting-started-firebase-simple-login-registration-auth/
public class RegisterActivity extends AppCompatActivity implements Printer {

    private static final String TAG = "Email Verification ";
    private EditText userEmail, userPassword, userFullName;
    private TextView textAlreadyHaveAccount;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Printer printer;
    //For possible enhancement later.
    //private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        printer = new Printer() {
            @Override
            public void print(int messageId) {
                Toast.makeText(getApplicationContext(),messageId, Toast.LENGTH_SHORT).show();
            }
        };
        btnRegister = (Button) findViewById(R.id.registerButton);
        userFullName = (EditText) findViewById(R.id.fullNameRegister);
        userEmail = (EditText) findViewById(R.id.emailRegister);
        userPassword = (EditText) findViewById(R.id.passwordRegister);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textAlreadyHaveAccount = (TextView) findViewById(R.id.alreadHaveAccount);

        textAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                String fullName = userFullName.getText().toString().trim();

                if(!checkFieldsValidation(fullName,email,password, printer)){
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(RegisterActivity.this, R.string.email_already_exists, Toast.LENGTH_SHORT).show();
                                    }
                                    //https://github.com/probelalkhan/GhostApp/tree/master/app/src/main/java/net/simplifiedcoding/ghostapp
                                } else {
                                    auth.getCurrentUser().sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "Email sent.");
                                                        User newUser = new User(email, fullName);

                                                        FirebaseDatabase.getInstance().getReference("users")
                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                progressBar.setVisibility(View.GONE);
                                                                if (task.isSuccessful()) {
                                                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                                    finish();
                                                                    Toast.makeText(RegisterActivity.this, R.string.register_success,
                                                                            Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(RegisterActivity.this, R.string.register_fail +" "+task.getException(),
                                                                            Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, RegisterAndLoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public boolean checkFieldsValidation(String fullName, String email, String password, Printer printer){

        if (fullName == null || fullName.length() == 0) {
            printer.print(R.string.require_fulll_name);
            return false;
        }
        if (email == null || email.length() == 0) {
            printer.print(R.string.require_email);
            return false;
        }

        if (password == null || password.length() == 0) {
            printer.print(R.string.require_password);
            return false;
        }

        if (password.length() < 6) {
            printer.print(R.string.warn_short_password);
            return false;
        }
        return true;
    }

    //Required method override from interface printer.
    @Override
    public void print(int messageId) {
    }
}