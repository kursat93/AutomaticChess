package com.akura.kursat.automaticchess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akura.kursat.automaticchess.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user =new User();
        user.getUser();



    }
}
