package com.akura.kursat.automaticchess.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.akura.kursat.automaticchess.R;

/**
 * Created by lenovo on 27.9.2017.
 */

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void register(View view) {
        Intent intent = new Intent(getApplication().getBaseContext(), RegisterActivity.class);
        startActivity(intent);
        finish();


    }

    public void signin(View view) {
        Intent intent = new Intent(getApplication().getBaseContext(), Login2Activity.class);
        startActivity(intent);
        finish();
    }

}
