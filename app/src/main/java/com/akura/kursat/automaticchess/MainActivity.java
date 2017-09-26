package com.akura.kursat.automaticchess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.akura.kursat.automaticchess.activity.HomePage;
import com.akura.kursat.automaticchess.model.User;

public class MainActivity extends AppCompatActivity {

    private Button homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user =new User();
        user.getUser();


        homepage=(Button)findViewById(R.id.btnHomePage);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(),HomePage.class);
                startActivity(i);

            }
        });




    }
}
