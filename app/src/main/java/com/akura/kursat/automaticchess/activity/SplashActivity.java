package com.akura.kursat.automaticchess.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akura.kursat.automaticchess.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {

    private String TAG;
    private boolean isMainStarted;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.key_pref_name), Context.MODE_PRIVATE);

        boolean isLogged = sharedpreferences.getBoolean(getString(R.string.user_logged), false);
        if (isLogged) {
            if (isMainStarted) {
                return;
            }
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {
                firebaseUser.reload();

                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
                dbr.setValue("1");
                SplashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
                        finish();
                        isMainStarted = true;
                    }
                });
            }
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
